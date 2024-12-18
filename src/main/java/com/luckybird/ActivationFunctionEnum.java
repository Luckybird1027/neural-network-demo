package com.luckybird;

import lombok.AllArgsConstructor;

import java.util.function.Function;

/**
 * 激活函数枚举类
 *
 * @author 新云鸟
 */
@AllArgsConstructor
public enum ActivationFunctionEnum {

    SIGMOID("sigmoid", (x) -> 1.0 / (1 + Math.exp(-x)), (x) -> x * (1 - x)),
    RELU("relu", (x) -> Math.max(0, x), (x) -> x > 0 ? 1d : 0d);

    /**
     * 激活函数名称
     */
    final String name;

    /**
     * 激活函数的实现
     */
    final Function<Double, Double> function;

    /**
     * 激活函数的导数
     */
    final Function<Double, Double> derivative;

}
