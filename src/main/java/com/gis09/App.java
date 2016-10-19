package com.gis09;


/**
 * Hello world!
 */
public class App {
    {
        System.out.println("代码块");
    }
    static {
        System.out.println("静态代码块");
    }
    static App app=new App();
    public static void main(String[] args) {
        System.out.println(reverse(123));
    }
    public static int reverse(int n){
        if (n==0) return 0;
        String s= String.valueOf(n);
        int i = s.indexOf("-");//获取符号的位置
        int j=s.lastIndexOf("0"); //获取最后一个0的位置
        if (i<0){
            //正数
            if (j<0){
                //最后一个0根本不存在 直接字符串反转就可以了
                s=reverse(s);
            }else{
                s=s.replaceAll("0*$","");
                s=reverse(s);
            }
        }else{
            //负数
            s=s.substring(1);
            if (j<0){
                //最后一个0根本不存在 直接字符串反转就可以了
                s= reverse(s);
            }else{
                s=s.replaceAll("0*$","");
                s=reverse(s);
            }
            s="-"+s;

        }

        return Integer.parseInt(s);
    }
    private static char[] reverse(char[] ori){
        if (ori.length==1) return ori;
        for (int i = ori.length-1,m=ori.length>>1,j=0; i >=m; i--,j++) {
            ori[i]= (char) (ori[i]^ori[j]);
            ori[j]= (char) (ori[j]^ori[i]);
            ori[i]= (char) (ori[j]^ori[i]);
        }
        return ori;
    }
    public static String reverse(String s) {
        return new StringBuffer(s).reverse().toString();
    }
}
