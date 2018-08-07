Barren Land Analysis
====================

This is a Maven project, so it can be built running `mvn clean install`. This will both execute unit tests and build the
executable which will be found in `target`.

The jar, `land_analysis-1.0-SNAPSHOT.jar` takes two parameters, the width and the length of the plot. In the case
described in the instructions, these will be 400 and 600. For example, if you want to direct `testInput.txt` to STDIN
and you want to use the example width and height, you could use the following command to run the program.

```
java -jar target/land_analysis-1.0-SNAPSHOT.jar 400 600 < testInput.txt
```

The areas of fertile land will written to STDOUT delimited by spaces.