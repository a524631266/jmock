package com.zhangll.flink;

import lombok.ToString;

@ToString
public class Person {
    private int age;
    private Integer id;
    private String name;
    private String Address;
    private double money_d;
    private float money_f;
    private char firstName;
    private Character a = 'c';
    // 男为1 女为0
    private boolean sex;
}
