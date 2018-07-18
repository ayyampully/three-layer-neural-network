package com.threelayer.matrix;

import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.threelayer.interfaces.Apply;
import com.threelayer.interfaces.Map;

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

    public void multiply(double n){
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.cols; j++){
                this.data[i][j] *= n;
            }
        }
    }

    public void multiply(Matrix m){
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.cols; j++){
                this.data[i][j] *= m.data[i][j];
            }
        }
    }

    public static Matrix fromArray(double[] arr){
        Matrix m = new Matrix(arr.length, 1);
        for(int i = 0, len = arr.length; i < len; i++){
            m.data[i][0] = arr[i];
        }
        return m;
    }

    public List toArray(){
        ArrayList arr = new ArrayList();
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.cols; j++){
                arr.add(this.data[i][j]);
            }
        }
        return arr;
    }

    public static Matrix subtract(Matrix a, Matrix b){
        Matrix result = new Matrix(a.rows, a.cols);
        for(int i = 0; i < a.rows; i++){
            for(int j = 0; j < a.cols; j++){
                result.data[i][j] = a.data[i][j] - b.data[i][j];
            }
        }
        return result;
    }

    public Matrix map(Map fn){
        Matrix result = new Matrix(this.rows, this.cols);
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.cols; j++){
                result.data[i][j] = fn.map(this.data[i][j]);
            }
        }
        return result;
    }

    public void apply(Apply fn){
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.cols; j++){
                this.data[i][j] = fn.apply(this.data[i][j]);
            }
        }
    }

    public static Matrix transpose(Matrix matrix){
        Matrix result = new Matrix(matrix.cols, matrix.rows);

        for(int i = 0; i < matrix.rows; i++){
            for(int j = 0; j < matrix.cols; j++){
                result.data[j][i] = matrix.data[i][j];
            }
        }
        return result;
    }

    public void add(double n){
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.cols; j++){
                this.data[i][j] += n;
            }
        }
    }

    public void add(Matrix m){
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.cols; j++){
                this.data[i][j] += m.data[i][j];
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
