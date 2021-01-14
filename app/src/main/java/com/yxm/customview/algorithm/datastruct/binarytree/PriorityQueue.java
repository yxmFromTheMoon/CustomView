package com.yxm.customview.algorithm.datastruct.binarytree;

/**
 * Created by Myron at 2021/1/12 23:18
 *
 * @email yxmbest@163.com
 * @description 优先级队列
 */
public class PriorityQueue {

    private int count;
    private int[] datas;

    public PriorityQueue(int count) {
        this.count = 0;
        datas = new int[count + 1];
    }

    public PriorityQueue(int[] arr, int n) {
        datas = new int[n + 1];
        for (int i = 0; i < n; i++) {
            datas[i + 1] = arr[i];
        }
        this.count = n;
        for (int index = count / 2; index >= 1; index--) {
            shiftDown(index);
        }
    }

    boolean isEmpty() {
        return count == 0;
    }

    public void push(int data) {
        datas[count + 1] = data;
        count++;
        shiftUp(count);
    }

    public int pop() {
        int max = datas[1];
        datas[1] = datas[count];
        count--;
        shiftDown(1);
        return max;
    }

    private void shiftUp(int index) {
        //节点的索引大于1 && 父节点的值小于当前节点的值时交换
        while (index > 1 && datas[index / 2] < datas[index]) {
            swap(datas, index, index / 2);
            index /= 2;
        }
    }

    private void shiftDown(int index) {
        //保证二叉树有孩子，只需判断有左孩子即可
        while (2 * index <= count) {
            //index位置节点的左孩子
            int j = 2 * index;
            //如果有右孩子 && 右孩子大于左孩子，将要交换的索引变为右孩子
            //的索引
            if (j + 1 <= count && datas[j + 1] > datas[j]) {
                j += 1;
            }
            //该节点与左右孩子中最大的比较,如果大于左右节点则直接break
            if (datas[index] >= datas[j]) {
                break;
            }
            //交换
            swap(datas, j, index);
            //更新节点的索引
            index = j;
        }
    }

    public int[] getDatas() {
        return datas;
    }

    private void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
