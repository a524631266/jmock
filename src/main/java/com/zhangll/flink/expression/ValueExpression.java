package com.zhangll.flink.expression;

/**
 * 表达式生成器
 * @First @middle @Last
 * 或者正则表达式生成器
 * /\d{5,10}/ ==> "561659409"
 * /\w\W\s\S\d\D/,  ==>"F)\fp1G",
 * /[a-z][A-Z][0-9]/ ==> "pJ7"
 */
public class ValueExpression {
    private String value;

    public ValueExpression(String value) {
        this.value = value;
    }


    public String generate() {
        return null;
    }
}
