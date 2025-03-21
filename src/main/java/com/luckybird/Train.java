package com.luckybird;

/**
 * @author 新云鸟
 */
public class Train {
    public static void main(String[] args) {
        // 数据预处理
        ProcessedData data = Preprocess.processData("/map.json");
        // 初始化模型
        Model model = new Model();
        model.init();
        // 训练模型
        model.train(data);
        // 保存模型
        model.clearParams();
        model.saveModel2Json("model.json");
    }
}