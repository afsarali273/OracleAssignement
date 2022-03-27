# JMS SQE Challenge

## How to Run Project from Command Line
```cmd
mvn clean package
cd ./deployment
java -jar oracle-assignment-jar-with-dependencies.jar
```


## How to Run Project via Docker
```bash
mvn clean package
cd ./deployment
docker build -t uitest-image .
docker run uitest-image
```
### Test Results
 - You can find test result in the current directory (./deployment)
  - test-output/ ---> This folder will contain html reports (index.html, emailable-report.html)
  - artifacts    ---> This folder will contain screenshots
