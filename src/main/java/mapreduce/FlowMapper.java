package mapreduce;

import org.apache.hadoop.hdfs.util.EnumCounters;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class FlowMapper extends Mapper<LongWritable, Text, Text, FlowBean>{
    Text k=new Text();
    FlowBean v=new FlowBean();
protected void map(LongWritable key, Text vlaue,Context context) throws IOException, InterruptedException {

    String line=vlaue.toString();
    String[] fields=line.split("\t");
    String phonenum=fields[1];
    String addr=fields[2];
    long upflow=Long.parseLong(fields[fields.length-3]);
    long downflow=Long.parseLong(fields[fields.length-2]);
    k.set(phonenum);
    v.set(upflow,downflow,addr);
    context.write(k,v);
    System.err.println("******Mapper*****");
}
}
