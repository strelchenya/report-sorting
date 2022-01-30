# Sorting rows by columns

###### Console application for sorting lines of a text file by columns.

-------------------------------------------------------------
- Stack: [JDK 8](http://jdk.java.net/8/), maven
-----------------------------------------------------

### Task:
```
Create a java application that will read data from a source (file: in.txt) and sort it.
The data has a tabular structure. Lines are separated by line breaks. Columns are tab characters.

The data must be sorted and output to the out.txt file.

Sorting is done first by the first column of rows, then by the second (if the rows or numbers in the first column are the same),
and so on. Data related to the same row in the grid must also be in the same row.

Any number in a column is above any non-number, numbers are sorted in ascending order. Strings in lexicographic order.
```