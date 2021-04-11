package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        //Initializing a new File with the given path
        File file = new File("src/com/company/input.txt");
        Scanner scanner = null;

        //Initializing a new Scanner for the given file
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Reading the number of candles using the scanner
        int n = 0;
        if (scanner != null) {
            n = scanner.nextInt();
        }

        //Checking if n in range[1:1000]
        if (n<1 || n>1000){
            System.out.println("n should be in range [1:1000] but n = " + n);
            return;
        }

        //Initializing a new int array of size n
        int[] candles = new int[n];

        //Setting the scanner to the next line
        scanner.nextLine();
        int i = 0;

        //Reading the next integers, which are the heights of the candles
        //Checking if they satisfy the conditions
        while (scanner.hasNextInt()){
            if(i >= n){
                System.out.println("Number of candles not equal to n!\nn = " + n);
                return;
            }
            candles[i] = scanner.nextInt();
            if(candles[i]>1000 || candles[i]<1){
                System.out.println("Candle height should be in range [1:1000] but found " + candles[i]);
                return;
            }
            i++;
        }

        //Printing the output of the birthdayCakeCandles() method
        System.out.println(birthdayCakeCandles(candles));
    }

    public static int birthdayCakeCandles(int[] heights){
        int max = 0; //the initial max value
        int count = 0; //the counter used for counting the nr. of max values
        for(int height:heights){
            if(height>max){
                count = 0;
                max = height;
            }
            if(height==max){
                count++;
            }
        }
        return count;
    }
}
