JAR_NAME = "npl-textrazor-local-all.jar"

build-jar:
	./gradlew clean shadowJar

run-key-phrase:
	java -jar ./build/libs/${JAR_NAME}