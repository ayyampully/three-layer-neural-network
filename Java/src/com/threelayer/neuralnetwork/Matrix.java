package com.threelayer.neuralnetwork;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Matrix{
    private int rows;
    private int cols;
    private double[][] data;
    private Logger logger = Logger.getAnonymousLogger();
    public Matrix(double[][] array){
        rows = array[0].length;
        cols = array.length;
        data = array;
    }
    public Matrix(int r, int c){
        rows = r;
        cols = c;
        data = new double[rows][cols];
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                data[i][j] = 0;
            }
        }
    }
    public void randomize(){
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                data[i][j] = Math.random() * 2 -1;
            }
        }
    }

    public void print(){
        StringBuilder temp = new StringBuilder();
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                temp.append(data[i][j]);
                temp.append(", ");
            }
        }
        logger.log(Level.INFO, temp.toString());
    }
}
