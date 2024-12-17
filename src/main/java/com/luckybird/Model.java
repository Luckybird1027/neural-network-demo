package com.luckybird;

/**
 * 前馈神经网络模型
 * <p>
 * 该神经网络模型包含三层：输入层、隐藏层和输出层。
 *
 * @author 新云鸟
 */
public class Model {

    /**
     * 输入层大小
     */
    final int INPUT_SIZE = 384;

    /**
     * 隐藏层大小
     */
    final int HIDDEN_SIZE = 128;

    /**
     * 输出层大小
     */
    final int OUTPUT_SIZE = 10;

    /**
     * 输入层参数数组
     */
    double[] inputLayer;

    /**
     * 输入层到隐藏层的权重矩阵数组
     */
    double[][] inputHiddenWeights;

    /**
     * 输入层到隐藏层的偏置量
     */
    double inputHiddenBias;

    /**
     * 隐藏层参数数组
     */
    double[] hiddenLayer;

    /**
     * 隐藏层到输出层的权重矩阵数组
     */
    double[][] hiddenOutputWeights;

    /**
     * 隐藏层到输出层的偏置量
     */
    double hiddenOutputBias;

    /**
     * 输出层参数数组
     */
    double[] outputLayer;

    /**
     * 初始化模型参数，包括输入层、隐藏层和输出层的权重矩阵以及偏置量
     */
    void init() {
        // 初始化参数数组
        inputLayer = new double[INPUT_SIZE];
        inputHiddenWeights = new double[INPUT_SIZE][HIDDEN_SIZE];
        inputHiddenBias = 0;
        hiddenLayer = new double[HIDDEN_SIZE];
        hiddenOutputWeights = new double[HIDDEN_SIZE][OUTPUT_SIZE];
        hiddenOutputBias = 0;
        outputLayer = new double[OUTPUT_SIZE];

        // 使用He初始化的方法初始化权重矩阵
        double std1 = Math.sqrt(2f / INPUT_SIZE);
        for (int i = 0; i < INPUT_SIZE; ++i) {
            for (int j = 0; j < HIDDEN_SIZE; ++j) {
                inputHiddenWeights[i][j] = std1 * Math.random();
            }
        }
        double std2 = Math.sqrt(2f / HIDDEN_SIZE);
        for (int i = 0; i < HIDDEN_SIZE; ++i) {
            for (int j = 0; j < OUTPUT_SIZE; ++j) {
                hiddenOutputWeights[i][j] = std2 * Math.random();
            }
        }
    }

    /**
     * 训练一个完整的训练周期
     *
     * @param trainData   训练数据集，为所有展开后的数组组成的二维数组
     * @param trainLabels 训练数据集的标签集合，为one-hot编码数组组成的二维数组
     */
    void trainOneEpoch(int[][] trainData, int[][] trainLabels) {
        // 检查所有的输入数据和标签是否匹配

        // 将训练数据集的所有图像依次传入模型进行训练
        final int dataCount = trainData.length;
        for (int i = 0; i < dataCount; ++i) {
            trainOneImage(trainData[i], trainLabels[i]);
        }
    }

    /**
     * 训练一张图像数据及其标签
     * <p>
     * 进行前馈神经网络训练的核心步骤，包括正向传播、损失计算、反向传播、权重更新等步骤
     *
     * @param imageData  图像数据数组，为展开后的数组
     * @param imageLabel 图像标签，为one-hot编码数组
     */
    void trainOneImage(int[] imageData, int[] imageLabel) {

    }

    /**
     * 将模型参数保存为json格式文件，便于后续加载使用
     */
    void saveModel2Json() {

    }

    /**
     * 将json格式文件中的模型参数载入到当前模型对象中
     */
    void loadModelFromJson() {

    }

    void forward() {

    }

    void backward() {

    }


}
