package com.luckybird;

import lombok.Data;

/**
 * @author 新云鸟
 */
@Data
public class ProcessedData {

    /**
     * 处理好的训练数据集，为所有展开后的数组组成的二维数组
     */
    private double[][] trainData;

    /**
     * 处理好的训练数据集的标签集合，为one-hot编码数组组成的二维数组
     */
    private int[][] trainLabels;

}
