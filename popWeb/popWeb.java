package org.myorg;

import java.io.IOException;
import java.util.regex.Pattern;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.conf.Configuration;
import java.net.URL;


import org.apache.log4j.Logger;

public class popWeb extends Configured implements Tool {

	private static final Logger LOG = Logger.getLogger(popWeb.class);

	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new popWeb(), args);
		System.exit(res);
	}

	public int run(String[] args) throws Exception {
		Job job = Job.getInstance(getConf(), "popWeb");
		job.setJarByClass(this.getClass());
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		job.setMapperClass(Map.class);
		job.setReducerClass(Reduce.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		return job.waitForCompletion(true) ? 0 : 1;
	}

	public static class Map extends
			Mapper<LongWritable, Text, Text, IntWritable> {
	    private final static IntWritable ONE = new IntWritable(1);
	    private Text word = new Text();
	    private long numRecords = 0;    
	    public void map(LongWritable offset, Text lineText, Context context)
	        throws IOException, InterruptedException {
	      String line = lineText.toString().trim();      
	      final int startIndex = line.indexOf("\"");
	      final int endIndex = line.lastIndexOf("\"");

	      if(startIndex > 1 && endIndex > 2){
	    	  final String reqStr = line.substring(startIndex + 1, endIndex);
	    	  final String realURL = reqStr.split(" ")[1];
	    	  
	    	  if(realURL.startsWith("http")){
	    		  final URL url = new URL(realURL);
	    		  word.set(url.getPath());
	    	  }else{
	    		  word.set(realURL);
	    	  }
	    	  context.write(word, ONE); 
			}
		}
	    
	}

	public static class Reduce extends
			Reducer<Text, IntWritable, Text, IntWritable> {

		private Text tempWord = new Text("");
		private int tempFreq = 0;

		@Override
		public void reduce(Text word, Iterable<IntWritable> counts,
				Context context) throws IOException, InterruptedException {
			int sum = 0;
			for (IntWritable count : counts) {
				sum += count.get();
			}

			if (sum > tempFreq) {
				tempFreq = sum;
				tempWord.set(word);
			}
		}

		@Override
		public void cleanup(Context context) throws IOException, InterruptedException{
			context.write(tempWord, new IntWritable(tempFreq));

		}
	}
}
