package com.threelayer.neuralnetwork;

import com.threelayer.interfaces.Apply;
import com.threelayer.interfaces.Map;
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
    private double learningRate = 0.1;

    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }

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

    public List predict(double[] input){
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

    public void train(double[] i, double[] t){
        Matrix inputs = Matrix.fromArray(i);
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

            //expected output
            Matrix target = Matrix.fromArray(t);
            //error = target - predicted output
            Matrix errorOutput = Matrix.subtract(target, output);

            //delta in weights = learning rate * error * ( sigmoid(output) * 1 - sigmoid(output)) * input transposed
            //in backtracking output -> hidden -> input

            //1. delta in hidden -> output weights
            //learningRate * errorOutput * derive(output) * transpose(hidden)
            //gradient = learningRate * errorOutput * derive(output)
            Matrix gradientHO = output.map(new Derive());
            gradientHO.multiply(errorOutput);//element wise
            gradientHO.multiply(learningRate);//element wise
            Matrix hiddenTransposed = Matrix.transpose(hidden);
            Matrix weightsHODelta = Matrix.multiply(gradientHO, hiddenTransposed);
            //update weights and bias
            weightsHO.add(weightsHODelta);
            biasHO.add(gradientHO);

            //2. delta in input -> hidden weights
            //error in hidden output
            Matrix weightsHOTransposed = Matrix.transpose(weightsHO);//this weights contribute to the total error
            Matrix errorHidden = Matrix.multiply(weightsHOTransposed, errorOutput);
            //gradient = learningRate * errorOutput * derive(output)
            Matrix gradientIH = hidden.map(new Derive());
            gradientIH.multiply(errorHidden);//element wise
            gradientIH.multiply(learningRate);//element wise
            Matrix inputTransposed = Matrix.transpose(inputs);
            Matrix weightsIHDelta = Matrix.multiply(gradientIH, inputTransposed);
            //update weights and bias
            weightsIH.add(weightsIHDelta);
            biasIH.add(gradientIH);

        }catch (ValidationException ex){
            logger.log(Level.SEVERE, ex.toString());
        }
    }

    public class Sigmoid implements Apply {
        public double apply(double x){
            return 1/(1 + Math.exp(-x));
        }
    }

    public class Derive implements Map {
        public double map(double xSigmoid){
            return xSigmoid * ( 1 - xSigmoid );
        }
    }

}

