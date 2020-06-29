package com.cs61b;
/*
HW0:
Creative Exercise 1a: Drawing a Triangle

cf. https://sp18.datastructur.es/materials/hw/hw0/hw0#creative-exercise-1a-drawing-a-triangle
 */
public class createStars {
    public static void main(String[] args) {
        //for loop
//        for(int row = 0; row < 5; row++) {
//            for(int col = 0; col <= row; col++) {
//                System.out.print("*");
//            }
//            System.out.println();
//        }


        //while loop
        int row = 0;
        int size = 5;
        while(row < size) {
            int col = 0; //reset col for each while loop
            while(col <= row) {
                System.out.print("*");
                col++;
            }
            System.out.println();
            row++;
        }
    }
}
