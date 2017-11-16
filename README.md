Author: Voltaire Bazurto Blacio

This is a JAVA project based on Spring Framework that takes a file containing names of players and pinfall scores of a bowling game as an input. The input file is parsed and the score game of all players is printed in console using a standard bowling scoring notation. The following youtube video explains in detail such scoring scheme.  

https://www.youtube.com/watch?v=aBe71sD8o8c


Project is based on maven template.
Maven 3.5.2 was used.

a) How to build and test

    1. Enter the /bowling-java-console-application dir:

    2. Inside the directory execute:  
        mvn clean
        mvn compile
        mvn test

b) How to build a fat executable jar:

    1. Enter the /bowling-java-console-application dir:

    2. Inside the directory execute:  
        mvn package

c) Run the generated jar on console using .txt test files:

    1. Enter the /bowling-java-console-application dir:

    2. Inside the directory execute:  
        mvn package

        java -jar target/jobsity-test-bowlingapp-1.0-SNAPSHOT-jar-with-dependencies.jar target/test-classes/extra-rows.txt
    
    4. You can check all the .txt files to test with the generated jar in the folder <bowling-java-console-application-root-dir>/target/test-classes/
    
    5. Another example with a different file:
        java -jar target/jobsity-test-bowlingapp-1.0-SNAPSHOT-jar-with-dependencies.jar target/test-classes/bowling-file.txt
    
    6. The list of availables .txt files to test the application are:
        target/test-classes/all-fouls.txt
        target/test-classes/bad-input-format.txt
        target/test-classes/bowling-file.txt
        target/test-classes/extra-rows.txt
        target/test-classes/perfect-game.txt
        target/test-classes/worst-case.txt

