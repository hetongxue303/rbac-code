package com.hetongxue.utils;

import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.base.Captcha;

/**
 * @Description: Captcha工具类
 * @ClassNmae: CaptchaUtil
 * @Author: 何同学
 * @DateTime: 2022-05-23 19:25
 **/
public class CaptchaUtil {


    private final static int width = 111;
    private final static int height = 36;
    private final static int length = 2;
    private final static int fontSize = 2;

    public static Captcha getCaptcha() {
        FixedArithmeticCaptcha captcha = new FixedArithmeticCaptcha(width, height);
        captcha.setLen(length);
        return captcha;
    }

    static class FixedArithmeticCaptcha extends ArithmeticCaptcha {
        public FixedArithmeticCaptcha(int width, int height) {
            super(width, height);
        }

        @Override
        protected char[] alphas() {
            // 生成随机数字和运算符
            int n1 = num(1, 10), n2 = num(1, 10);
            int opt = num(4);

            // 计算结果
            int res = new int[]{n1 + n2, n1 - n2, n1 * n2, n1 / n2}[opt];
            // 转换为字符运算符
            char optChar = "+-x/".charAt(opt);

            this.setArithmeticString(String.format("%s%c%s=?", n1, optChar, n2));
            this.chars = String.valueOf(res);

            return chars.toCharArray();
        }

    }

}
