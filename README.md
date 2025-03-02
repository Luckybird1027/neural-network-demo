# Java Feedforward Neural Network Handwritten Digit Recognition Models

English | [简体中文](README_ZH.md)

A simple deep learning project for personal learning purposes, implemented in Java and not relying on any off-the-shelf
deep learning framework. The project manually builds a feed-forward neural network model that focuses on the task of
classifying handwritten digits 0-9. With this project, you can run and train the model in any Java-enabled environment
to verify its effectiveness on handwritten digit recognition.

## Introduction

This project implements a simple feed-forward neural network for handwritten digit recognition.

### Project Features

- **No framework dependencies**: the components of the neural network are implemented entirely manually using Java.
- **Extensibility**: the network structure and parameters can be adjusted as needed.
- **Cross-platform**: It can be run as long as there is a Java environment.

### Model Features

- **Input data**: the image is a single-channel grayscale map with a size of 16x24 pixels.

- **Network structure**:
    - **Input layer**: contains 384 neurons corresponding to each pixel of the image.
    - **Hidden layer**: contains 128 neurons, using the ReLU activation function.
    - **Output layer**: contains 10 neurons corresponding to the categorization of the numbers 0-9, using the Softmax
      activation function.

- **Activation function**: provided by `ActivationFunctionEnum` class, supports two common activation functions, ReLU
  and Sigmoid.

- **Image Preprocessing**: done by the `Preprocess` class, which converts the image into a one-dimensional array and
  normalizes it to fit the input requirements of the model.

  Below is a schematic of the network:

  ![nn](/doc/image/nn.svg)

## Principle

Feedforward Neural Network (FNN) is one of the most basic types of neural networks. Its working principle includes the
following steps:

1. **Feedforward Propagation**: the input data is passed through the layers of the network, processed by the weighting
   and activation functions in each layer, and finally the prediction result is output.
2. **Loss Calculation**: the error between the prediction result and the real label is calculated using the
   cross-entropy loss function.
3. **Backpropagation**: adjusts the weights and biases of the network to minimize the error by calculating the gradient
   of the loss function.
4. **Weight update**: update the network parameters using gradient descent to gradually improve the prediction accuracy
   of the model.

## Experiment

To run this project, follow the steps below:

1. **Clone the project code**:
   First, clone the project code locally via Git. Open a command line terminal and execute the following command:
   ```bash
   git clone https://github.com/Luckybird1027/neural-network-demo.git
   ```

2. **Enter the project directory**:
   ```bash
   cd neural-network-demo
   ```

3. **Install Maven dependencies**:
   Ensure that Maven is installed on your system. then run the following command in the project root directory to
   install the dependencies required by the project:
   ```bash
   mvn clean install
   ```

4. **Train the model**:
   Run `Train.java` to train the model. You can run this file in the IDE or from the command line using the following
   command:

   ```bash
   mvn exec:java -Dexec.mainClass="com.luckybird.Train”
   ```
   After training is complete, the model is saved as a JSON file.

5. **Test the model**:
   Run `Test.java` to make predictions using the trained model. You can run this file in the IDE or from the command
   line using the following command:
   ```bash
   mvn exec:java -Dexec.mainClass="com.luckybird.Test”
   ```

With these steps, you can run and test the project in your local environment to experience the complete process of
handwriting digit recognition.

## Results

Once the training is complete, you can use the code in `Test.java` to make predictions on new images. The prediction
results will show the category of the image and its probability. The training proved to be good and was able to
accurately predict the numbers and probabilities represented by the handwritten digit images.

![dataset](/doc/image/dataset.png)
![result](/doc/image/result.png)

With these results, you can visualize how well the model performs on the handwritten digit recognition task.

Translated with DeepL.com (free version)