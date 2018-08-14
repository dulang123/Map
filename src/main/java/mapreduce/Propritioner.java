package mapreduce;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

import java.util.HashMap;

public class Propritioner  extends Partitioner<Text,FlowBean> {
    public static HashMap<String,Integer> hashMap=new HashMap<String, Integer>();
    static {
        hashMap.put("四川",0);
        hashMap.put("北京",1);
        hashMap.put("上海",2);


    }
    @Override
    public int getPartition(Text text, FlowBean flowBean, int i) {
        Integer code=hashMap.get(flowBean.getAddr().substring(0,2));
        if (code!=null){
            return code;
        }
        return 3;
    }
}
