package mapreduce;

import java.io.IOException;
import java.util.Iterator;
import java.util.StringTokenizer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class Jop {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
    // "localhost:9000" 需要根据实际情况设置一下
    //conf.set("mapreduce.jobtracker.address", "localhost");
    //conf.set("mapreduce.framework.name","local");
        System.setProperty("hadoop.home.dir", "E:\\hadoop-3.0.0");
    // 一个hdfs文件系统中的 输入目录 及 输出目录
    String[] ioArgs = new String[]{"C:\\Users\\Administrator.PC-20180801PYJE\\phone", "C:\\Users\\Administrator.PC-20180801PYJE\\aads"};
        System.out.println(ioArgs);
    String[] otherArgs = new GenericOptionsParser(conf, ioArgs).getRemainingArgs();
        if (otherArgs.length != 2) {
        System.err.println("Usage: Score Average <in> <out>");
        System.exit(2);
    }
    Job job = Job.getInstance(conf);
        job.setJarByClass(Jop.class);
    // 设置Map、Combine和Reduce处理类
        job.setMapperClass(FlowMapper.class);
        //job.setCombinerClass(Reduce.class);
        job.setReducerClass(Reduce.class);

    // 设置输出类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);
    // 将输入的数据集分割成小数据块splites，提供一个RecordReder的实现
        job.setInputFormatClass(TextInputFormat.class);
    // 提供一个RecordWriter的实现，负责数据输出
        job.setOutputFormatClass(TextOutputFormat.class);
        //分区个数
        job.setNumReduceTasks(4);
        job.setPartitionerClass(Propritioner.class);
    // 设置输入和输出目录
        FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
}
}
