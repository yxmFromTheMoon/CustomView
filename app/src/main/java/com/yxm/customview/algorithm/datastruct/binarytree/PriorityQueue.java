package com.yxm.customview.algorithm.datastruct.binarytree;

import androidx.core.content.res.TypedArrayUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by Myron at 2021/1/12 23:18
 *
 * @email yxmbest@163.com
 * @description 优先级队列
 */
public class PriorityQueue {

    private int count;
    private int[] datas;
    private int index;

    public PriorityQueue(int count) {
        this.count = count;
        datas = new int[count];
    }

    boolean isEmpty() {
        return count == 0;
    }

    public void push(int data) {
        datas[index] = data;
        shiftUp(index);
        index++;
    }

    private void shiftUp(int index) {
        if(index > 0 && datas[index] > datas[index / 2]){
            swap(datas,index,index / 2);
            shiftUp(index / 2);
        }
    }

    private void shiftDown(int index){

    }

    public int[] getDatas(){
        return datas;
    }

    private void swap(int[] array,int i,int j){
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
