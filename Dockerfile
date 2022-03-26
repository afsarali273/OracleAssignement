FROM markhobson/maven-chrome
WORKDIR /

COPY input /input/

COPY oracle-assignment-jar-with-dependencies.jar /oracle-assignment-jar-with-dependencies.jar

ENTRYPOINT ["java", "-jar", "oracle-assignment-jar-with-dependencies.jar"]