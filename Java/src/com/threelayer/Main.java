package com.threelayer;


import com.threelayer.neuralnetwork.Matrix;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.ValidationException;

public class Main {
    private static Logger logger = Logger.getLogger("main");
    public static void main(String[] args){
        Matrix m1 = new Matrix(2, 2);
        Matrix m2 = new Matrix(2, 2);
        m1.randomize();
        m2.randomize();

        m1.print();
        m2.print();
        try {
            Matrix m3 = Matrix.multiply(m1, m2);
            m3.print();
        } catch (ValidationException ex){
            logger.log(Level.SEVERE, ex.toString());
        }
    }
}
