package com.luckybird;

import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * 图像预处理工具类，用于读取训练图片和标签信息，并将图片和标签转换为模型训练所需的格式
 *
 * @author 新云鸟
 */
public class Preprocess {

    /**
     * 图片期望宽度
     */
    final static int EXPECTED_WIDTH = 16;

    /**
     * 图片期望高度
     */
    final static int EXPECTED_HEIGHT = 24;

    /**
     * 读取图片文件，并将图片信息展开为一维信息数组
     *
     * @param path 图片路径
     * @return 图片灰度信息数组
     */
    @SneakyThrows
    public static int[] image2Array(String path) {
        File file = getFile(path);
        BufferedImage image = getImage(file);
        int[] pixels = new int[EXPECTED_WIDTH * EXPECTED_HEIGHT];
        for (int i = 0; i < EXPECTED_HEIGHT; i++) {
            for (int j = 0; j < EXPECTED_WIDTH; j++) {
                pixels[i * EXPECTED_HEIGHT + j] = image.getRGB(j, i);
            }
        }
        return pixels;
    }

    /**
     * 将图片灰度信息数组标准化，即将每个像素值从0-255的范围映射到0.0-1.0的范围
     *
     * @param pixels 图片灰度信息数组
     * @return 图片灰度信息数组标准化后的结果
     */
    public static double[] standardization(int[] pixels) {
        double[] result = new double[pixels.length];
        for (int i = 0; i < pixels.length; i++) {
            result[i] = (pixels[i] & 0xff) / 255.0;
        }
        return result;
    }

    /**
     * 将标签转换为one-hot编码
     * <p>
     * 例如标签0转换为[1, 0, 0, 0, 0, 0, 0, 0, 0, 0]
     *
     * @param tag 标签
     * @return 标签对应的one-hot编码
     */
    public static int[] tag2OneHot(int tag) {
        if (tag < 0 || tag > 9) {
            throw new RuntimeException("标签不在预期范围内");
        }
        int[] result = new int[10];
        result[tag] = 1;
        return result;
    }

    /**
     * 获取文件对象，并检查文件是否存在且为普通文件
     *
     * @param path 文件路径
     * @return 文件对象
     */
    private static File getFile(String path) {
        File file = new File(path);
        if (!file.exists() || !file.isFile()) {
            throw new RuntimeException("文件不存在");
        }
        return file;
    }

    /**
     * 获取图片对象，并检查图片是否为灰度图且尺寸符合预期尺寸
     *
     * @param file 文件对象
     * @return 图片对象
     */
    @SneakyThrows
    private static BufferedImage getImage(File file) {
        BufferedImage image = ImageIO.read(file);
        if (image.getType() != BufferedImage.TYPE_BYTE_GRAY) {
            throw new RuntimeException("图片不是灰度图");
        }
        if (image.getWidth() != EXPECTED_WIDTH || image.getHeight() != EXPECTED_HEIGHT) {
            throw new RuntimeException("图片尺寸不符合预期,期望尺寸为" + EXPECTED_WIDTH + "x" + EXPECTED_HEIGHT);
        }
        return image;
    }
}

