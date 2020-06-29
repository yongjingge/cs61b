/*
HW0:
Exercise 4

Write a function windowPosSum(int[] a, int n)
that replaces each element a[i] with the sum of a[i] through a[i + n],
but only if a[i] is positive valued.
If there are not enough values because we reach the end of the array,
we sum only as many values as we have.
 */
public class windowPosSum {
    public static void windowPosSum(int[] nums, int n) {
        int len = nums.length - 1;
        for (int i = 0; i <= len; i++) {
            //continue if nums[i] is negative
            if(nums[i] < 0) {
                continue;
            }

            for(int j = 1; j <= n; j++) {
                //break if i + j is out of bound
                if(i + j > len) {
                    break;
                }
                nums[i] += nums[i + j];
            }
        }
    }

    public static void main(String[] args) {
        int[] a = {1, 2, -3, 4, 5, 4};
        int n = 3;
        windowPosSum(a, n);

        // Should print 4, 8, -3, 13, 9, 4
        System.out.println(java.util.Arrays.toString(a));
    }
}
