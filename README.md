Author: Voltaire Bazurto Blacio

Project is based on maven template.
Maven 3.5.2 was used.

a) How to build and test

    1. Enter the jobsity-test-bowlingapp dir:

    2. Inside the directory execute:  
        mvn clean
        mvn compile
        mvn test

b) How to build a fat executable jar:

    1. Enter the jobsity-test-bowlingapp dir:

    2. Inside the directory execute:  
        mvn package

c) Run the generated jar on console using .txt test files:

    1. Enter the jobsity-test-bowlingapp dir:

    2. Inside the directory execute:  
        mvn package

        java -jar target/jobsity-test-bowlingapp-1.0-SNAPSHOT-jar-with-dependencies.jar target/test-classes/extra-rows.txt
    
    4. You can check all the .txt files to test with the generated jar in the folder <jobsity-test-bowlingapp-root-dir>/target/test-classes/
    
    5. Another example with a different file:
        java -jar target/jobsity-test-bowlingapp-1.0-SNAPSHOT-jar-with-dependencies.jar target/test-classes/bowling-file.txt
    
    6. The list of availables .txt files to test the application are:
        target/test-classes/all-fouls.txt
        target/test-classes/bad-input-format.txt
        target/test-classes/bowling-file.txt
        target/test-classes/extra-rows.txt
        target/test-classes/perfect-game.txt
        target/test-classes/worst-case.txt

