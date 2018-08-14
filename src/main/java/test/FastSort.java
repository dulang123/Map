package test;
public class FastSort{

    public static void main(String []args){
        System.out.println("Hello World");
        int[] a = {200,400,500,300};
        int start = 0;
        int end = a.length-1;
        FastSort fastSort=new FastSort();
        fastSort.sorttest(a,start,end);
        for(int i = 0; i<a.length; i++){
            System.out.println(a[i]);
        }
    }

    public void sorttest(int[] a,int low,int high){
        int start = low;
        int end = high;
        int key = a[low];
        int sum=0;
      System.out.println("low"+low+"high"+high);

        while(end>start){
            System.out.println("end>start****"+end+"\t"+start);
            //从后往前比较
            while(end>start&&a[end]>=key)  //如果没有比关键值小的，比较下一个，直到有比关键值小的交换位置，然后又从前往后比较
            {    System.out.println("end");
                end--;}
            if(a[end]<key){
                System.out.println("end***exchang");
                int temp = a[end];
                a[end] = a[start];
                a[start] = temp;
            }
            //从前往后比较
            while(end>start&&a[start]<=key)//如果没有比关键值大的，比较下一个，直到有比关键值大的交换位
            {
                start++;
                System.out.println("start");
            }
            if(a[start]>key){
                System.out.println("start***exchang");
                int temp = a[start];
                a[start] = a[end];
                a[end] = temp;
            }
            System.out.println(sum); //此时第一次循环比较结束，关键值的位置已经确定了。左边的值都比关键值小，右边的值都比关键值大，但是两边的顺序还有可能是不一样的，进行下面的递归调用
        }
        //递归
        if(start>low) sorttest(a,low,start-1);//左边序列。第一个索引位置到关键值索引-1
        if(end<high) sorttest(a,end+1,high);//右边序列。从关键值索引+1到最后一个

    }

}
