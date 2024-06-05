javac -d ./out ./ru/nsu/khamidullin/*.java
javadoc -d ./javadoc ./ru/nsu/khamidullin/*.java
cd ./out
java -classpath . ru.nsu.khamidullin.Main
sleep 5