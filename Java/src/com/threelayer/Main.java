package com.threelayer;


import com.threelayer.neuralnetwork.Matrix;

public class Main {

    public static void main(String[] args){
        Matrix m = new Matrix(2,2);
        m.randomize();
        m.print();
    }
}
