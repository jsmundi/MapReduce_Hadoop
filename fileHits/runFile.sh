echo "****************************************************************************************"
echo " MAKING HDFS "
echo "****************************************************************************************"
hadoop fs -mkdir /user/cloudera
hadoop fs -mkdir /user/cloudera/LogHW
hadoop fs -mkdir /user/cloudera/LogHW/fileHits /user/cloudera/LogHW/fileHits/input
hadoop fs -put ../cs453-hw2-dataset/access_log/ /user/cloudera/LogHW/fileHits/input

echo "****************************************************************************************"
echo " BUILDING JAVA "
echo "****************************************************************************************"
mkdir -p build
javac -cp /usr/lib/hadoop/*:/usr/lib/hadoop-mapreduce/* fileHits.java -d build -Xlint 
jar -cvf fileHits.jar -C build/ . 

echo "****************************************************************************************"
echo " RUN HADOOP "
echo "****************************************************************************************"
hadoop fs -rm -r /user/cloudera/LogHW/fileHits/output/
hadoop jar fileHits.jar org.myorg.fileHits /user/cloudera/LogHW/fileHits/input /user/cloudera/LogHW/fileHits/output

echo "****************************************************************************************"
echo " OUTPUT "
echo "****************************************************************************************"
echo "Note: The program uses grep but you can run without grep to see complete file output"
hadoop fs -cat /user/cloudera/LogHW/fileHits/output/* | grep "/assets/js/the-associates.js"
