from NeuralNetwork import NeuralNetwork

nn = NeuralNetwork(2, 6, 1)
training_data = [
    {
        "input": [0, 0],
        "output": [0]
    },
    {
        "input": [1, 1],
        "output": [0]
    },
    {
        "input": [1, 0],
        "output": [1]
    },
    {
        "input": [0, 1],
        "output": [1]
    }
]

for i in range(2000):
    for data in training_data:
        nn.train(data["input"], data["output"])

print(nn.predict([0, 0]))
print(nn.predict([0, 1]))
print(nn.predict([1, 0]))
print(nn.predict([1, 1]))
