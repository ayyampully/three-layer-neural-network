package com.threelayer;


import com.threelayer.interfaces.Mapper;
import com.threelayer.matrix.Matrix;
import com.threelayer.neuralnetwork.NeuralNetwork;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.ValidationException;

public class Main {
    private static Logger logger = Logger.getLogger("main");


    public static void main(String[] args){
        NeuralNetwork neuralNetwork = new NeuralNetwork(2, 2, 1);
        double[] input = {0, 1};
        try {
            List out = neuralNetwork.predict(input);
            logger.log(Level.INFO, out.toString());
        } catch (ValidationException ex){
            logger.log(Level.SEVERE, ex.toString());
        }

    }
}
