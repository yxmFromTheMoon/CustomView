package com.yxm.customview;

import java.util.Locale;

/**
 * Created by yxm at 2021/1/11 11:49
 *
 * @email: yxmbest@163.com
 * @description:
 */
public class HelloWorld {

    public static void main(String[] args) {
        System.out.println("Hello World");
    }

    public void makeChange(){}

    public void commit1(){}
    public void commit2(){}
    public void commit3(){}
    public void commit4(){}
    public void commit5(){}
    public void commit6(){}
    public void commit7(){}

    public boolean isPalindrome(String s) {
        if(s.equals("")){
            return true;
        }
        char[] charArray = s.replaceAll("[^a-zA-Z0-9\\u4E00-\\u9FA5]","").toLowerCase().toCharArray();
        int l = 0;
        int r = charArray.length - 1;
        while (l <= r){
            if(charArray[l] == charArray[r]){
                l++;
                r--;
            }else{
                return false;
            }
        }
        return true;
    }
}
