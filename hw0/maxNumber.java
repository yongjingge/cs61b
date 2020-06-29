package com.cs61b;
/*
HW0:
Exercise 2
create a function with the signature public static int max(int[] m) that returns the maximum value of an int array.
 */
public class maxNumber {
    public static int max(int[] m) {
        if(m.length <= 1 || m == null) {
            return 0;
        }
        int max = m[0];
        for(int i = 1; i < m.length - 1; i++) {
            max = Math.max(m[i], max);
        }
        return max;
    }
    public static void main(String[] args) {
        int[] numbers = new int[]{9, 2, 15, 2, 22, 10, 6};
        System.out.println(max(numbers));
    }
}
