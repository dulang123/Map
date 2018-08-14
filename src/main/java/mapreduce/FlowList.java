package mapreduce;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import java.io.IOException;

public class FlowList {

    public static class MapList extends Mapper<LongWritable,Text, FlowBean,Text> {
        FlowBean k=new FlowBean();
        Text v=new Text();
        // 实现map函数
        @Override
        protected void map(LongWritable key, Text value,Context context)
                throws IOException, InterruptedException {
            String line=value.toString();
            String[] fields=line.split("\t");
            String phonenum=fields[0];
            String addr=fields[4];
            long upflow=Long.parseLong(fields[1]);
            long downflow=Long.parseLong(fields[2]);
            System.err.println(upflow+"\t"+downflow+"\t"+addr);
            k.set(upflow,downflow,addr);
            v.set(phonenum);
            context.write(k,v);
        }
    }


    public static class ReduceList extends Reducer<FlowBean,Text, Text, FlowBean> {
        // 实现reduce函数
        @Override
        protected void reduce(FlowBean key, Iterable<Text> values,Context context)
                throws IOException, InterruptedException {
            context.write(values.iterator().next(),key);
       }
    }
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        System.setProperty("hadoop.home.dir", "E:\\hadoop-3.0.0");
        // 一个hdfs文件系统中的 输入目录 及 输出目录
        String[] ioArgs = new String[]{"C:\\Users\\Administrator.PC-20180801PYJE\\aads", "C:\\Users\\Administrator.PC-20180801PYJE\\aad"};
        System.out.println(ioArgs);
        String[] otherArgs = new GenericOptionsParser(conf, ioArgs).getRemainingArgs();
        if (otherArgs.length != 2) {
            System.err.println("Usage: Score Average <in> <out>");
            System.exit(2);
        }
        Job job = Job.getInstance(conf);
        job.setJarByClass(FlowList.class);
        // 设置Map、Combine和Reduce处理类
        job.setMapperClass(MapList.class);
        //job.setCombinerClass(ReduceList.class);
        job.setReducerClass(ReduceList.class);

        job.setMapOutputKeyClass(FlowBean.class);
        job.setMapOutputValueClass(Text.class);
        // 设置输出类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);

        // 设置输入和输出目录
        FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
