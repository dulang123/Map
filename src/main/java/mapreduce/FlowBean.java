package mapreduce;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class FlowBean implements WritableComparable<FlowBean>{
    private long upflow;
    private long dwonflow;
    private long sum;
    private String addr;


    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public long getUpflow() {
        return upflow;
    }

    public void setUpflow(long upflow) {
        this.upflow = upflow;
    }

    public long getDwonflow() {
        return dwonflow;
    }

    public void setDwonflow(long dwonflow) {
        this.dwonflow = dwonflow;
    }

    public long getSum() {
        return sum;
    }



    public FlowBean() {
    }

    public void set(long upflow, long dwonflow,String addr) {
        this.upflow = upflow;
        this.dwonflow = dwonflow;
        this.addr=addr;
        this.sum=upflow+dwonflow;
    }

    public void setSum(long sum) {
        this.sum = sum;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeLong(upflow);
        dataOutput.writeLong(dwonflow);
        dataOutput.writeLong(sum);
        dataOutput.writeUTF(addr);
    }

    @Override
    public String toString() {
        return upflow+"\t"+dwonflow+"\t"+sum;
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.upflow=dataInput.readLong();
        this.dwonflow=dataInput.readLong();
        this.sum=dataInput.readLong();
        this.addr=dataInput.readUTF();


    }
  //bean比较大小
    @Override
    public int compareTo(FlowBean o) {
        return this.sum>o.getSum()?-1:1;
    }
}
