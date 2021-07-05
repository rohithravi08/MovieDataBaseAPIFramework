# MovieDatabaseAutomationFramework -- Guidelines
Framework is based on Rest assured and cucumber

# Prerequisites
Cucumber 4.0 and above Java 8.0 and above

# Usage
Test Runner file --> To execute an idividual or multiple test scenario, give the specific tag given in the feature files in the tags property
features package --> BDD Test cases are stored under feature files
StepDefinitions package --> BDD test step implementation are stored under corresponding step definition files

# Execution on Local
Scenario can be executed from gitbash or IntelliJ terminal. Give the test tag in the test runner file and then execute--> mvn test verify

# Execution on Docker
1)run the following command to build the image from docker file

docker build -t samplemaven:latest .

2)Now that the image is built and saved on your local. Run the container using this image by running the following command.

docker run -it --name samplecontainer samplemaven:latest /bin/bash

3) After running this command container will be started and you will be inside the /sample directory by default. Now run the command

mvn clean test verify

# Reporting
HTML and JSON reports are automatically generated and stored in the default ./target folder.

# Reporting is done by two plugins in pom.xml file

maven-cucucmber-reporting
cucumber-html
