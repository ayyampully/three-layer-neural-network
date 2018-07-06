import Matrix from './Matrix.js';

class NeuralNetwork{
    constructor(input_nodes, hidden_nodes, output_nodes){
        this.input_nodes = input_nodes;
        this.hidden_nodes = hidden_nodes;
        this.output_nodes = output_nodes;
        this.learning_rate = 0.2;
        
        //between input and hidden
        this.weights_ih = new Matrix(this.hidden_nodes, this.input_nodes);
        this.weights_ih.randomize();
        //Bias
        this.bias_h = new Matrix(this.hidden_nodes, 1);
        this.bias_h.randomize();
        
        //between hidden and output
        this.weights_ho = new Matrix(this.output_nodes, this.hidden_nodes);
        this.weights_ho.randomize();
        //Bias
        this.bias_o = new Matrix(this.output_nodes, 1);
        this.bias_o.randomize();
    }
    
    predict(i){
        //input to hidden
        let inputs = Matrix.fromArray(i);
        let hidden = Matrix.multiply(this.weights_ih, inputs);
        hidden.add(this.bias_h);
        hidden.apply(sigmoid);
        
        //hidden to output
        let output = Matrix.multiply(this.weights_ho, hidden);
        output.add(this.bias_o);
        output.apply(sigmoid);
        
        return output.toArray();
    }
    
    train(i, t){
        //input to hidden
        let inputs = Matrix.fromArray(i);
        let hidden = Matrix.multiply(this.weights_ih, inputs);
        hidden.add(this.bias_h);
        hidden.apply(sigmoid);
        
        //hidden to output
        let outputs = Matrix.multiply(this.weights_ho, hidden);
        outputs.add(this.bias_o);
        outputs.apply(sigmoid);
        
        //Error = target i - outputs i
        let targets = Matrix.fromArray(t);
        
        let errorOutput = Matrix.subtract(targets, outputs);
        // delta w = learing rate * error * ( sigmoid(output) * 1- s(o) ) * input transposed 
        // delat w for h -> o = lr * errorOutput * derive(outputs) * transpose(hidden)
        let gradient = outputs.map(derive);
        gradient.multiply(errorOutput);
        gradient.multiply(this.learning_rate);
        
        let hiddenT = Matrix.transpose(hidden);
        let delta_ho_weights = Matrix.multiply(gradient, hiddenT);
        
        // update weights and bias
        this.weights_ho.add(delta_ho_weights);
        this.bias_o.add(gradient);
        
        // delat w for i -> h = lr * errorHidden * derive(hidden) * transpose(inputs)
        let errorOutput_t = Matrix.transpose(this.weights_ho);
        let errorHidden = Matrix.multiply(errorOutput_t, errorOutput);
        
        let gradient_hidden = hidden.map(derive);
        gradient_hidden.multiply(errorHidden);
        gradient_hidden.multiply(this.learning_rate);
        
        let inputsT = Matrix.transpose(inputs);
        let delta_ih_weights = Matrix.multiply(gradient_hidden, inputsT);
        this.weights_ih.add(delta_ih_weights);
        // update weights and bias
        this.bias_h.add(gradient_hidden);

    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    printWeightsAndBias(){
        console.info('---------------Weights for hidden---------------')
        this.weights_ih.printTable();
        console.info('---------------Bias for hidden---------------')
        this.bias_h.printTable();
        console.info('---------------Weights for output---------------')
        this.weights_ho.printTable();
        console.info('---------------Bias for output---------------')
        this.bias_o.printTable();
    }
}
function derive(y){
    return y * (1 - y);
}
function sigmoid(x){
    return 1/(1 + Math.exp(-x));
}
export default NeuralNetwork;