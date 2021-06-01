package com.team429.hometeacher.question;

import android.util.Pair;

/**
 * 生成不同年级的计算题
 * @author Fanjialiang
 */
public class MathQuestionGenerator {
    public static Pair<String, String> generate(int difficulty) {
        int max, min;
        // 设置随机数范围
        switch (difficulty) {
            case 1:
                max = 20;
                min = 0;
                break;
            case 2:
                max = 50;
                min = 20;
                break;
            case 3:
                max = 100;
                min = 50;
                break;
            default:
                max = 0;
                min = 0;
                break;
        }
        // 生成随机数
        int n1 = (int) (Math.random() * (max - min) + min);
        int n2 = (int) (Math.random() * (max - min) + min);
        if (n1 < n2) {
            int temp = n1;
            n1 = n2;
            n2 = temp;
        }
        int sign = (int) (Math.random() * 2), result;
        int multi = (int) (Math.random() * 2);
        if (multi == 0 && difficulty == 3) {
            // 乘法
            n1 /= 10;
            n2 /= 10;
            return new Pair<>(n1 + " * " + n2 + " = ?", String.valueOf(n1 * n2));
        }else {
            // 加减法
            switch (sign) {
                case 0:
                    result = n1 + n2;
                    return new Pair<>(n1 + " + " + n2 + " = ?", String.valueOf(result));
                case 1:
                    result = n1 - n2;
                    return new Pair<>(n1 + " - " + n2 + " = ?", String.valueOf(result));
            }
        }
        // unreachable
        return new Pair<>("1 + 1 = ?", "2");
    }

}
