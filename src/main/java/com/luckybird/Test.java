package com.luckybird;

/**
 * @author 新云鸟
 */
public class Test {
    public static void main(String[] args) {
        // 加载模型
        Model loadedModel = Model.loadModelFromJson("model.json");
        // 测试模型
        loadedModel.predict("dataset/0-1.png");
        loadedModel.predict("dataset/1-2.png");
        loadedModel.predict("dataset/2-3.png");
        loadedModel.predict("dataset/3-4.png");
        loadedModel.predict("dataset/4-5.png");
        loadedModel.predict("dataset/5-1.png");
        loadedModel.predict("dataset/6-2.png");
        loadedModel.predict("dataset/7-3.png");
        loadedModel.predict("dataset/8-4.png");
        loadedModel.predict("dataset/9-5.png");
    }
}