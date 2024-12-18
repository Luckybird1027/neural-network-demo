package com.luckybird;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.SneakyThrows;

import java.io.FileReader;
import java.io.FileWriter;

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
    private final int INPUT_SIZE = 384;

    /**
     * 隐藏层大小
     */
    private final int HIDDEN_SIZE = 128;

    /**
     * 输出层大小
     */
    private final int OUTPUT_SIZE = 10;

    /**
     * 学习率
     */
    private final double LEARNING_RATE = 0.01f;

    /**
     * 目标迭代次数
     */
    private final int TARGET_EPOCH = 100;

    /**
     * 激活函数类型
     */
    private final ActivationFunctionEnum ACTIVATION_FUNCTION = ActivationFunctionEnum.RELU;

    /**
     * 模型保存路径
     */
    private static final String MODEL_PATH = "model";

    /**
     * 输入层参数数组
     */
    private double[] inputLayer;

    /**
     * 输入层到隐藏层的权重矩阵数组
     */
    private double[][] inputHiddenWeights;

    /**
     * 输入层到隐藏层的偏置量
     */
    private double inputHiddenBias;

    /**
     * 隐藏层参数数组
     */
    private double[] hiddenLayer;

    /**
     * 隐藏层到输出层的权重矩阵数组
     */
    private double[][] hiddenOutputWeights;

    /**
     * 隐藏层到输出层的偏置量
     */
    private double hiddenOutputBias;

    /**
     * 输出层参数数组
     */
    private double[] outputLayer;

    /**
     * 已完成迭代次数
     */
    private int completedEpoch = 0;

    /**
     * 是否已经初始化模型参数
     */
    private boolean initialized = false;


    public Model() {
        inputLayer = new double[INPUT_SIZE];
        inputHiddenWeights = new double[INPUT_SIZE][HIDDEN_SIZE];
        hiddenLayer = new double[HIDDEN_SIZE];
        hiddenOutputWeights = new double[HIDDEN_SIZE][OUTPUT_SIZE];
        outputLayer = new double[OUTPUT_SIZE];
    }

    /**
     * 初始化模型参数，包括输入层、隐藏层和输出层的权重矩阵以及偏置量
     */
    void init() {
        // 初始化偏置参数
        inputHiddenBias = 0;
        hiddenOutputBias = 0;
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
        initialized = true;
        System.out.println("模型参数初始化完成");
    }

    /**
     * 训练模型
     *
     * @param processedData 训练数据集，包括图像数据和标签集合
     */
    void train(ProcessedData processedData) {
        if (!initialized) {
            System.out.println("尚未初始化模型参数，正在进行初始化...");
            init();
        }
        while (completedEpoch < TARGET_EPOCH) {
            trainOneEpoch(processedData);
        }
        System.out.println("模型训练完成");
    }

    /**
     * 训练一个迭代周期
     *
     * @param processedData 训练数据集，包括图像数据和标签集合
     */
    void trainOneEpoch(ProcessedData processedData) {
        double[][] trainData = processedData.getTrainData();
        int[][] trainLabels = processedData.getTrainLabels();
        // 检查所有的输入数据和标签是否匹配
        for (int i = 0; i < trainData.length; ++i) {
            if (trainData[i].length != INPUT_SIZE) {
                throw new IllegalArgumentException("训练数据集中的图像数据维度不匹配");
            }
            if (trainLabels[i].length != OUTPUT_SIZE) {
                throw new IllegalArgumentException("训练数据集的标签集合维度不匹配");
            }
        }
        // 将训练数据集的所有图像依次传入模型进行训练
        final int dataCount = trainData.length;
        for (int i = 0; i < dataCount; ++i) {
            trainOneImage(trainData[i], trainLabels[i]);
        }
        completedEpoch++;
        System.out.println("已完成第" + completedEpoch + "次迭代");
    }

    /**
     * 训练一张图像数据及其标签
     * <p>
     * 进行前馈神经网络训练的核心步骤，包括正向传播、损失计算、反向传播、权重更新等步骤
     *
     * @param imageData  图像数据数组，为展开后的数组
     * @param imageLabel 图像标签，为one-hot编码数组
     */
    void trainOneImage(double[] imageData, int[] imageLabel) {
        // 将图像数据传入输入层
        System.arraycopy(imageData, 0, inputLayer, 0, INPUT_SIZE);

        // 前向传播-计算隐藏层参数
        for (int i = 0; i < HIDDEN_SIZE; ++i) {
            hiddenLayer[i] = 0;
            for (int j = 0; j < INPUT_SIZE; ++j) {
                hiddenLayer[i] += inputHiddenWeights[j][i] * inputLayer[j];
            }
            hiddenLayer[i] += inputHiddenBias;
            hiddenLayer[i] = ACTIVATION_FUNCTION.function.apply(hiddenLayer[i]);
        }

        // 前向传播-计算输出层参数，使用softmax函数作为激活函数
        for (int i = 0; i < OUTPUT_SIZE; ++i) {
            outputLayer[i] = 0;
            for (int j = 0; j < HIDDEN_SIZE; ++j) {
                outputLayer[i] += hiddenOutputWeights[j][i] * hiddenLayer[j];
            }
            outputLayer[i] += hiddenOutputBias;
        }
        double sum = 0;
        double[] expOutputLayer = new double[OUTPUT_SIZE];
        for (int i = 0; i < OUTPUT_SIZE; ++i) {
            expOutputLayer[i] = Math.exp(outputLayer[i]);
            sum += expOutputLayer[i];
        }
        for (int i = 0; i < OUTPUT_SIZE; ++i) {
            outputLayer[i] = expOutputLayer[i] / sum;
        }

        // 计算损失函数，使用交叉熵损失函数
        double loss = 0;
        for (int i = 0; i < OUTPUT_SIZE; ++i) {
            loss += imageLabel[i] * Math.log(outputLayer[i]);
        }

        // 反向传播-计算 损失函数 到 隐藏层到输出层的权重梯度和偏置梯度
        double[] loss2outputGradient = new double[OUTPUT_SIZE];
        for (int i = 0; i < OUTPUT_SIZE; ++i) {
            loss2outputGradient[i] = imageLabel[i] - outputLayer[i];
        }
        double[][] loss2hiddenOutputWeightGradient = new double[HIDDEN_SIZE][OUTPUT_SIZE];
        for (int i = 0; i < HIDDEN_SIZE; ++i) {
            for (int j = 0; j < OUTPUT_SIZE; ++j) {
                loss2hiddenOutputWeightGradient[i][j] = hiddenLayer[i] * loss2outputGradient[j];
            }
        }
        double loss2hiddenOutputBiasGradient = 0;
        for (int i = 0; i < OUTPUT_SIZE; ++i) {
            loss2hiddenOutputBiasGradient += loss2outputGradient[i];
        }

        // 反向传播-计算 损失函数 到 输入层到隐藏层的权重梯度和偏置梯度
        double[] loss2ActivatedHiddenGradient = new double[HIDDEN_SIZE];
        for (int i = 0; i < HIDDEN_SIZE; ++i) {
            loss2ActivatedHiddenGradient[i] = 0;
            for (int j = 0; j < OUTPUT_SIZE; ++j) {
                loss2ActivatedHiddenGradient[i] += loss2outputGradient[j] * hiddenOutputWeights[i][j];
            }
        }
        double[] loss2UnactivatedHiddenGradient = new double[HIDDEN_SIZE];
        for (int i = 0; i < HIDDEN_SIZE; ++i) {
            loss2UnactivatedHiddenGradient[i] = ACTIVATION_FUNCTION.derivative.apply(hiddenLayer[i]) * loss2ActivatedHiddenGradient[i];
        }
        double[][] loss2inputHiddenWeightGradient = new double[INPUT_SIZE][HIDDEN_SIZE];
        for (int i = 0; i < INPUT_SIZE; ++i) {
            for (int j = 0; j < HIDDEN_SIZE; ++j) {
                loss2inputHiddenWeightGradient[i][j] = inputLayer[i] * loss2UnactivatedHiddenGradient[j];
            }
        }
        double loss2inputHiddenBiasGradient = 0;
        for (int i = 0; i < HIDDEN_SIZE; ++i) {
            loss2inputHiddenBiasGradient += loss2UnactivatedHiddenGradient[i];
        }

        // 反向传播-根据计算出的梯度和学习率更新权重和偏置
        for (int i = 0; i < INPUT_SIZE; ++i) {
            for (int j = 0; j < HIDDEN_SIZE; ++j) {
                inputHiddenWeights[i][j] += LEARNING_RATE * loss2inputHiddenWeightGradient[i][j];
            }
        }
        inputHiddenBias += LEARNING_RATE * loss2inputHiddenBiasGradient;
        for (int i = 0; i < HIDDEN_SIZE; ++i) {
            for (int j = 0; j < OUTPUT_SIZE; ++j) {
                hiddenOutputWeights[i][j] += LEARNING_RATE * loss2hiddenOutputWeightGradient[i][j];
            }
        }
        hiddenOutputBias += LEARNING_RATE * loss2hiddenOutputBiasGradient;
        System.out.println("完成一张图片的训练，损失值为：" + loss);
    }

    /**
     * 将模型参数保存为json格式文件，便于后续加载使用
     */
    @SneakyThrows
    void saveModel2Json(String fileName) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String modelJson = gson.toJson(this);
        if (!fileName.endsWith(".json")) {
            fileName += ".json";
        }
        FileWriter fileWriter = new FileWriter(MODEL_PATH + "/" + fileName);
        fileWriter.write(modelJson);
        fileWriter.flush();
        fileWriter.close();
        System.out.println("模型已保存到文件：" + fileName);
    }

    /**
     * 将json格式文件中的模型参数载入到当前模型对象中
     */
    @SneakyThrows
    static Model loadModelFromJson(String fileName) {
        Gson gson = new Gson();
        Model model = gson.fromJson(new FileReader(MODEL_PATH + fileName), Model.class);
        System.out.println("模型已从文件：" + fileName + " 载入");
        return model;
    }

}
