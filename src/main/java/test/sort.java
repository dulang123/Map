package test;

public class sort {
    public static void main(String[] args) {
         int arr[]={200,400,500,300};
        int i, j;
        int sum=0;
        for (i = 0; i <arr.length - 1; i++)          //外层循环控制趟数，总趟数为len-1
            for (j = 0; j <arr.length - 1 - i; j++){
                if (arr[j] > arr[j + 1]){
                    int t=arr[j];
                    arr[j]=arr[j + 1];
                    arr[j + 1]=t;
                }
                sum++;
            }

               System.out.println(sum);
                for (i = 0; i <=arr.length - 1; i++){
                    System.out.println(arr[i]);
                }


    }




}
