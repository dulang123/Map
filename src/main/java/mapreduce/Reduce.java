package mapreduce;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class Reduce extends Reducer<Text, FlowBean, Text,FlowBean>{

    FlowBean val=new FlowBean();

    protected void reduce(Text key, Iterable<FlowBean> values,
                          Context context) throws IOException, InterruptedException {
        long flowup=0;
        long flowdwon=0;
        String addr=null;
        for (FlowBean flowBean:values){
            flowup+=flowBean.getUpflow();
            flowdwon+=flowBean.getDwonflow();
            addr=flowBean.getAddr();
            System.err.println("******"+flowup+"*****"+flowdwon+"****"+addr);
        }
        val.set(flowup,flowdwon,addr);
        context.write(key,val);
    }
}
