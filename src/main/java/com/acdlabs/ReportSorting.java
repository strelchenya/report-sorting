package com.acdlabs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ReportSorting {

    private static final String READ_FILE = "src/main/resources/in.txt";
    private static final String WRITE_FILE = "src/main/resources/out.txt";
    private static final String DELIMITER_TAB = "\t";
    private static final String NEW_LINE = "\n";
    private static final String NUMBERS_MATCHING = "(\\-?\\d*\\.?\\d+)";

    public static void main(String[] args) {
        List<List<String>> reportLinesSorted = readFileInList();
        writeFile(reportLinesSorted);
    }

    private static List<List<String>> readFileInList() {
        List<List<String>> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(READ_FILE))) {
            for (String line; (line = reader.readLine()) != null; ) {
                String[] split = line.split(DELIMITER_TAB, -1);
                lines.add(Arrays.asList(split));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        sortLines(lines);
        return lines;
    }

    private static void sortLines(List<List<String>> lines) {
        lines.sort(new Comparator<List<String>>() {
            private Collator collator = Collator.getInstance();

            @Override
            public int compare(List<String> line1, List<String> line2) {
                for (int i = 0; ; i++) {
                    if (i == line1.size()) {
                        return (i == line2.size() ? 0 : -1);
                    }
                    if (i == line2.size()) {
                        return 1;
                    }

                    String nodeString1 = line1.get(i);
                    String nodeString2 = line2.get(i);
                    BigDecimal node1 = null;
                    BigDecimal node2 = null;

                    if(nodeString1.matches(NUMBERS_MATCHING)){
                        node1 = new BigDecimal(nodeString1);
                    }
                    if(nodeString2.matches(NUMBERS_MATCHING)){
                        node2 = new BigDecimal(nodeString2);
                    }

                    int comparison =
                            (node1 == null ? (node2 == null ? this.collator.compare(nodeString1, nodeString2): 1) :
                            (node2 == null ? -1 : node1.compareTo(node2)));
                    if (comparison != 0){
                        return comparison;
                    }
                }
            }
        });
    }

    private static void writeFile(List<List<String>> reportLists) {
        StringBuilder report = new StringBuilder();
        for (List<String> line : reportLists) {
            for (int i = 0; i < line.size(); i++) {
                report.append(line.get(i));
                if (i < line.size() - 1) {
                    report.append(DELIMITER_TAB);
                }
            }
            report.append(NEW_LINE);
        }

        byte[] reportToBytes = report.toString().getBytes();
        try {
            Files.write(Paths.get(WRITE_FILE), reportToBytes);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
