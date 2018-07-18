package com.threelayer;


import com.threelayer.neuralnetwork.NeuralNetwork;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static Logger logger = Logger.getLogger("main");

    public static void main(String[] args){
        NeuralNetwork neuralNetwork = new NeuralNetwork(2, 4, 1);
        double[] i1 = {0, 0};
        double[] i2 = {0, 1};
        double[] i3 = {1, 0};
        double[] i4 = {1, 1};
        double[] o1 = {0};
        double[] o2 = {1};
        double[] o3 = {1};
        double[] o4 = {0};
        neuralNetwork.setLearningRate(0.2);
        for(int i = 0; i < 2000; i++){
            neuralNetwork.train(i1, o1);
            neuralNetwork.train(i2, o2);
            neuralNetwork.train(i3, o3);
            neuralNetwork.train(i4, o4);
        }

        double[] test1 = {0,0};
        double[] test2 = {0,1};
        double[] test3 = {1,1};
        logger.log(Level.INFO, "should be close to 0 - {0}", neuralNetwork.predict(test1).toString());
        logger.log(Level.INFO, "should be close to 1 - {0}", neuralNetwork.predict(test2).toString());
        logger.log(Level.INFO, "should be close to 0 - {0}", neuralNetwork.predict(test3).toString());


    }
}
