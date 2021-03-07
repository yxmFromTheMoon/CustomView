package com.yxm.customview.algorithm.algorithms.getoffer;

import com.yxm.customview.algorithm.datastruct.binarytree.BST;

import java.util.*;

/**
 * Created by Myron at 2021/3/5 20:10
 *
 * @email yxmbest@163.com
 * @description 剑指offer 题集
 */


public class GetOffer {

    /**
     * 01 二维数组中的查找
     * 在一个二维数组中（每个一维数组的长度相同），
     * 每一行都按照从左到右递增的顺序排序，
     * 每一列都按照从上到下递增的顺序排序。
     * 请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
     */
    public static boolean find(int target, int[][] array) {
        int rows = array.length;
        if (rows == 0)
            return false;
        int cols = array[0].length;
        if (cols == 0)
            return false;

        int row = array.length - 1;
        int column = 0;
        while (row >= 0 && column < cols) {
            if (array[row][column] == target) {
                return true;
            } else if (array[row][column] > target) {
                row--;
            } else {
                column++;
            }
        }

        return false;
    }

    /**
     * 输入一个递增排序的数组和一个数字S，在数组中查找两个数，使得他们的和正好是S，
     * 如果有多对数字的和等于S，输出两个数的乘积最小的。
     * 双指针
     */
    public static ArrayList<Integer> FindNumbersWithSum(int[] array, int sum) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        if (array.length == 0) {
            return list;
        }
        int left = 0;
        int right = array.length - 1;
        while (left < array.length && right >= 0) {
            if (array[left] + array[right] == sum) {
                list.add(array[left]);
                list.add(array[right]);
                break;
            } else if (array[left] + array[right] > sum) {
                right--;
            } else {
                left++;
            }
        }
        return list;
    }


    public static String ReverseSentence(String str) {
        if (str.length() <= 0 || str.trim().equals("")) {
            return str;
        }
        StringBuilder result = new StringBuilder();
        Stack<String> stack = new Stack<>();
        String[] array = str.split(" ");
        for (String s : array) {
            stack.push(s);
        }
        while (!stack.isEmpty()) {
            result.append(stack.pop()).append(" ");
        }
        return result.toString().substring(0, result.length() - 1);
    }

    public static void main(String[] args) {
        int[][] array = new int[3][3];

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                array[i][j] = 2;
            }
        }

        boolean find = find(2, array);
        System.out.println(find);
    }
}
