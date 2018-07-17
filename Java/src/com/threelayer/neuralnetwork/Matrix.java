package com.threelayer.neuralnetwork;

import javax.xml.bind.ValidationException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Matrix{
    private int rows;
    private int cols;
    private double[][] data;
    private Logger logger = Logger.getLogger("matrix");
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

    public static Matrix multiply(Matrix m1, Matrix m2) throws ValidationException{
        if(m1.cols == m2.rows){
            Matrix dotProduct = new Matrix(m1.rows, m2.cols);
            for(int i = 0; i < m1.rows; i++){
                for(int j = 0; j < m2.cols; j++){
                    double sum = 0;
                    for(int k = 0; k < m2.rows; k++){
                        sum += m1.data[i][k] * m2.data[k][j];
                    }
                    dotProduct.data[i][j] = sum;
                }

            }
            return dotProduct;
        } else{
            throw new ValidationException("Columns should match rows of the matrix.");
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
