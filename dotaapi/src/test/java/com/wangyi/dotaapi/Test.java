package com.wangyi.dotaapi;

/**
 * @author wangyi
 * @create 2018-12-12 10:07 PM
 **/
public class Test {


    public static void main(String[] args) {
        T2 t = new T2(new T1()) ;
        try {
            System.out.println(t.div(1,0));
        }catch (Exception e){
            System.out.println("------------");
        }
    }
}

class T1{
    public int div(int a,int b) {
        if (b == 0){
            throw new RuntimeException("wrong") ;
        }
        return a / b ;
    }
}
class T2{
    T1 t ;

    public T2(T1 t) {
        this.t = t;
    }

    public int div(int a, int b){
        return t.div(a,b);
    }
}