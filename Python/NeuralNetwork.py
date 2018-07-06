import math
from Matrix import Matrix


class NeuralNetwork:

    def __init__(self, input_nodes, hidden_nodes, output_nodes):
        self.input_nodes = input_nodes
        self.hidden_nodes = hidden_nodes
        self.output_nodes = output_nodes
        self.learning_rate = 0.2
        # weights between input and hidden layer
        self.weights_input_to_hidden = Matrix(self.hidden_nodes, self.input_nodes)
        self.weights_input_to_hidden.randomize()
        # outputs will be row and one col for bias
        self.bias_hidden = Matrix(self.hidden_nodes, 1)
        self.bias_hidden.randomize()

        # weights between hidden and output layer
        self.weights_hidden_to_output = Matrix(self.output_nodes, self.hidden_nodes)
        self.weights_hidden_to_output.randomize()
        # outputs will be row and one col for bias
        self.bias_output = Matrix(self.output_nodes, 1)
        self.bias_output.randomize()

    def predict(self, input_array):
        inputs = Matrix.from_array(input_array)
        # hidden layer is input multiplied by weights
        hidden = Matrix.multiply(self.weights_input_to_hidden, inputs)
        # bias applied
        hidden.add(self.bias_hidden)
        # sigmoid to normalize value
        hidden.apply(self.__sigmoid)

        outputs = Matrix.multiply(self.weights_hidden_to_output, hidden)
        outputs.add(self.bias_output)
        outputs.apply(self.__sigmoid)
        return outputs.to_array()

    def train(self, input_array, target_array):
        inputs = Matrix.from_array(input_array)
        # hidden layer is input multiplied by weights
        hidden = Matrix.multiply(self.weights_input_to_hidden, inputs)
        # bias applied
        hidden.add(self.bias_hidden)
        # sigmoid to normalize value
        hidden.apply(self.__sigmoid)

        outputs = Matrix.multiply(self.weights_hidden_to_output, hidden)
        outputs.add(self.bias_output)
        outputs.apply(self.__sigmoid)

        # Error calculated to tune weights
        # using back propagation
        # Error = target - output
        target = Matrix.from_array(target_array)
        error_output = Matrix.subtract(target, outputs)

        # delta w = learning rate * error * (s(o) * (1 - s(o)) ) * input transposed
        # ---------- ^^^^^^^^^^^^^ this is gradient ^^^^^^^^^^^^^ --------------------
        # w - hidden to output = learning_rate * error_output * __derivative(outputs) * transpose(hidden)
        gradient = outputs.map(self.__derivative)
        gradient.multiply_element_wise(error_output)
        gradient.multiply_element_wise(self.learning_rate)

        hidden_transposed = Matrix.transpose(hidden)
        delta_hidden_to_outputs_weight = Matrix.multiply(gradient, hidden_transposed)

        # update weights and bias
        self.weights_hidden_to_output.add(delta_hidden_to_outputs_weight)
        self.bias_output.add(gradient)

        # Hidden layer error = output error - output

        error_hidden = Matrix.multiply(Matrix.transpose(self.weights_hidden_to_output), error_output)

        # w - input to hidden = learning_rate * error_hidden * __derivative(hidden) * transpose(inputs)
        gradient_hidden = hidden.map(self.__derivative)
        gradient_hidden.multiply_element_wise(error_hidden)
        gradient_hidden.multiply_element_wise(self.learning_rate)

        inputs_transposed = Matrix.transpose(inputs)
        delta_inputs_to_hidden_weight = Matrix.multiply(gradient_hidden, inputs_transposed)

        # update weights and bias
        self.weights_input_to_hidden.add(delta_inputs_to_hidden_weight)
        self.bias_hidden.add(gradient_hidden)

    @staticmethod
    def __sigmoid(x):
        return 1 / (1 + math.exp(-x))

    @staticmethod
    def __derivative(y):
        return y * (1 - y)