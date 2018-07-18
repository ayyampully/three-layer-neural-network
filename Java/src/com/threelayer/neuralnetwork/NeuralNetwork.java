package com.threelayer.neuralnetwork;

import com.threelayer.interfaces.Mapper;
import com.threelayer.matrix.Matrix;

import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.ValidationException;
import java.util.List;

public class NeuralNetwork {
    private static Logger logger = Logger.getLogger("neuralnetwork");
    private Matrix weightsIH;
    private Matrix biasIH;
    private Matrix weightsHO;
    private Matrix biasHO;

    public NeuralNetwork(int iNodes, int hNodes, int oNodes){

        //weights between input and hidden
        weightsIH = new Matrix(hNodes, iNodes);
        weightsIH.randomize();
        //bias
        biasIH = new Matrix(hNodes, 1);
        biasIH.randomize();

        //weights between hidden and output
        weightsHO = new Matrix(oNodes, hNodes);
        weightsHO.randomize();
        //bias
        biasHO = new Matrix(oNodes, 1);
        biasHO.randomize();
    }

    public List predict(double[] input) throws ValidationException{
        Matrix inputs = Matrix.fromArray(input);
        Matrix hidden;
        Matrix output;
        try {
            //input to hidden
            hidden = Matrix.multiply(weightsIH, inputs);
            hidden.add(biasIH);
            hidden.apply(new Sigmoid());

            //hidden to output
            output = Matrix.multiply(weightsHO, hidden);
            output.add(biasHO);
            output.apply(new Sigmoid());

            return output.toArray();

        }catch (ValidationException ex){
            logger.log(Level.SEVERE, ex.toString());
            return Collections.emptyList();
        }

    }

    public class Sigmoid implements Mapper {
        public double map(double x){
            return 1/(1 + Math.exp(-x));
        }
    }
}

