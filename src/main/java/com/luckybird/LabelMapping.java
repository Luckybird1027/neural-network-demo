package com.luckybird;

import lombok.Getter;

/**
 * 标签映射类
 * <p>
 * 用于存储图像路径和对应的标签信息
 *
 * @author 新云鸟
 */
@Getter
public class LabelMapping {

    /**
     * 图像路径
     */
    String imagePath;

    /**
     * 图像对应的标签
     */
    int trainLabel;
}
