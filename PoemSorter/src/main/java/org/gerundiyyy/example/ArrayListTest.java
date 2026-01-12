package org.gerundiyyy.example;

import java.util.*; import java.io.*;
public class ArrayListTest {
    ArrayList lst = new ArrayList();
    Random generator = new Random();
    void addRandom() {
    lst.add(new Integer(generator.nextInt()));
    }
    public String toString() { return lst.toString();
    }
    public static void main(String args[]) {
        ArrayListTest tst = new ArrayListTest();
        for(int i = 0; i < 100; i++ ) {
            tst.addRandom();
        }
        System.out.println("100 random numbers: "+ tst.toString());
    }
}
