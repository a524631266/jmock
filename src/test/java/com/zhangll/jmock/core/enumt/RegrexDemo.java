package com.zhangll.jmock.core.enumt;

import com.zhangll.flink.expression.regrex.RandomStringGenerator;
import org.junit.Test;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegrexDemo {
    @Test
    public void normalizer(){
        String string = "123435353";
        long l = Long.parseLong(string);
        // 超出限制就会报错
        int i = Integer.parseInt(string);
//        System.out.println(l);
    }

    /**
     * 正则表达和还是
     */
    @Test
    public void regrexCompiler(){
        // 只要不包含\ 的前缀，\n 空格 均可以
        Pattern compile = Pattern.compile("([^\\\\])\\{([0-9]+),([0-9]+)?\\}");

        String str1 = "\\w\\d \\n{1,3}";
        String str2 = "\\d{1}";
        Matcher matcher = compile.matcher(str1);
        Matcher matcher1 = compile.matcher(str2);
//        System.out.println(matcher);
        // find为迭代器
        while (matcher.find()){
            // group 划分依据为， （1） （2） （3）从1开始计数，其中mather.group(0)==mather.group(), 表示原集合
            // 1= 第一个匹配的非\ 字符， 第二个
            String group = matcher.group();
            int start = Integer.parseInt(matcher.group(2));
            int end = 10;
            if(matcher.group(3)!= null){
                end = Integer.parseInt(matcher.group(3));
            }
            str1 = matcher.replaceFirst(matcher.group(1) + "{" + randomAsString(start, end) + "}");
//            System.out.println(str1);
        }
    }

    private String randomAsString(int start, int end) {
        return Integer.toString(new Random().nextInt(end-start + 1) + start , 10);
    }

    @Test
    public void regrexD(){
        String regrex1 = "[a-zA-Z]\\{([0-9]+),([0-9]+)?}";
        String str1 = "\\w\\d{1,3}";
        Pattern compile = Pattern.compile(regrex1);
        Matcher matcher = compile.matcher(str1);
        while (matcher.find()) {
//            System.out.println(matcher.group());
        }
    }

    @Test
    public void testRegrex(){
        String s = "\\w\\d{1, 3}";
        String s1 = new RandomStringGenerator().generateByRegex(s);
//        System.out.println(s1);
    }
}
