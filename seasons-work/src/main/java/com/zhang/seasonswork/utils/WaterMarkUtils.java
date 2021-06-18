package com.zhang.seasonswork.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class WaterMarkUtils {
    // 水印的文字
    private static final String waterMarkContent = "Seasons: www.zhangfu.xyz";

    /**
     * 图片添加水印
     * @param originFile 原图片
     * @param freeFile 输出图片
     */

    public static void mark(File originFile, File freeFile) throws IOException {
        // 读取原图片信息
        Image srcImg = ImageIO.read(originFile);
        int srcImgWidth = srcImg.getWidth(null);
        int srcImgHeight = srcImg.getHeight(null);

        // 加水印
        BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bufImg.createGraphics();
        g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);

        Font font = new Font("Courier New", Font.BOLD, 30);
        g.setColor(Color.cyan); // 根据图片的背景设置水印颜色
        g.setFont(font);

        int x = srcImgWidth - getWatermarkLength(waterMarkContent, g) - 5;
        int y = srcImgHeight - 5;
        // int x = (srcImgWidth - getWatermarkLength(watermarkStr, g)) / 2;
        // int y = srcImgHeight / 2;

        g.drawString(waterMarkContent, x, y);
        g.dispose();

        // 输出图片
        FileOutputStream outImgStream = new FileOutputStream(freeFile);
        ImageIO.write(bufImg, "jpg", outImgStream);
        outImgStream.flush();
        outImgStream.close();
    }



    /**
     * 获取水印文字总长度
     * @param waterMarkContent 水印的文字
     * @param g
     * @return 水印文字总长度
     */
    public static int getWatermarkLength(String waterMarkContent, Graphics2D g) {
        return g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length());
    }
}
