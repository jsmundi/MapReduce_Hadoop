echo "****************************************************************************************"
echo " MAKING HDFS "
echo "****************************************************************************************"
hadoop fs -mkdir /user/cloudera
hadoop fs -mkdir /user/cloudera/LogHW
hadoop fs -mkdir /user/cloudera/LogHW/ipHits /user/cloudera/LogHW/ipHits/input
hadoop fs -put ../cs453-hw2-dataset/access_log/ /user/cloudera/LogHW/ipHits/input

echo "****************************************************************************************"
echo " BUILDING JAVA "
echo "****************************************************************************************"
mkdir -p build
javac -cp /usr/lib/hadoop/*:/usr/lib/hadoop-mapreduce/* ipHits.java -d build -Xlint 
jar -cvf ipHits.jar -C build/ . 

echo "****************************************************************************************"
echo " RUN HADOOP "
echo "****************************************************************************************"
hadoop fs -rm -r /user/cloudera/LogHW/ipHits/output/
hadoop jar ipHits.jar org.myorg.ipHits /user/cloudera/LogHW/ipHits/input /user/cloudera/LogHW/ipHits/output

echo "****************************************************************************************"
echo " OUTPUT "
echo "****************************************************************************************"
echo "Note: The program uses grep but you can run without grep to see complete file output"
hadoop fs -cat /user/cloudera/LogHW/ipHits/output/* | grep "10.99.99.186"
