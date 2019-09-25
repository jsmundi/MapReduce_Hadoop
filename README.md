# Map Reduce Hadoop
# JSMUNDI
### PS1 
The solution is based on Linux environment running cloudera virtual image. Unzip the folder CS453HW2. Inside the folder run the following commands. Ensure your java path is setup correctly. 

Alternatively you can run the shell file runFile.sh from the fileHits folder.
    
```bash
    cd ./fileHits
    
    hadoop fs -mkdir /user/cloudera
    hadoop fs -mkdir /user/cloudera/LogHW
    hadoop fs -mkdir /user/cloudera/LogHW/fileHits /user/cloudera/LogHW2/fileHits/input
    hadoop fs -put ../cs453-hw2-dataset/access_log/ /user/cloudera/LogHW/fileHits/input
    
    mkdir -p build
    javac -cp /usr/lib/hadoop/*:/usr/lib/hadoop-mapreduce/* fileHits.java -d build -Xlint 
    jar -cvf fileHits.jar -C build/ . 
    
    hadoop jar fileHits.jar org.myorg.fileHits /user/cloudera/LogHW/fileHits/input /user/cloudera/LogHW/fileHits/output
    hadoop fs -cat /user/cloudera/LogHW/fileHits/output/* | grep "/assets/js/the-associates.js"
``` 

### PS2
The solution is based on Linux environment running cloudera virtual image. Unzip the folder CS453HW2. Inside the folder run the following commands. Ensure your java path is setup correctly. 

Alternatively you can run the shell file runFile.sh from the fileHits folder.
    
```bash
     cd ./ipHits
    
    hadoop fs -mkdir /user/cloudera
    hadoop fs -mkdir /user/cloudera/LogHW
    hadoop fs -mkdir /user/cloudera/LogHW/ipHits /user/cloudera/LogHW/ipHits/input
    hadoop fs -put ../cs453-hw2-dataset/access_log/ /user/cloudera/LogHW/ipHits/input
    
    mkdir -p build
    javac -cp /usr/lib/hadoop/*:/usr/lib/hadoop-mapreduce/* ipHits.java -d build -Xlint 
    jar -cvf ipHits.jar -C build/ . 
    
    hadoop jar ipHits.jar org.myorg.ipHits /user/cloudera/LogHW/ipHits/input /user/cloudera/LogHW/ipHits/output
    hadoop fs -cat /user/cloudera/LogHW/ipHits/output/* | grep "10.99.99.186"
``` 

### PS3
The solution is based on Linux environment running cloudera virtual image. Unzip the folder CS453HW2. Inside the folder run the following commands. Ensure your java path is setup correctly. 

Alternatively you can run the shell file runFile.sh from the fileHits folder.
    
```bash
    cd ./fileHits
   cd ./popWeb
    
    hadoop fs -mkdir /user/cloudera
    hadoop fs -mkdir /user/cloudera/LogHW
    hadoop fs -mkdir /user/cloudera/LogHW/popWeb /user/cloudera/LogHW/popWeb/input
    hadoop fs -put ../cs453-hw2-dataset/access_log/ /user/cloudera/LogHW2/popWeb/input
    
    mkdir -p build
    javac -cp /usr/lib/hadoop/*:/usr/lib/hadoop-mapreduce/* popWeb.java -d build -Xlint 
    jar -cvf popWeb.jar -C build/ . 
    
    hadoop jar popWeb.jar org.myorg.popWeb /user/cloudera/LogHW/popWeb/input /user/cloudera/LogHW/popWeb/output
    hadoop fs -cat /user/cloudera/LogHW/popWeb/output/*
``` 
