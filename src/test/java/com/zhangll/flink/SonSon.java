package com.zhangll.flink;

import com.zhangll.flink.annotation.FieldTokenType;
import lombok.ToString;

@ToString
public class SonSon {
    private String name;
    private int id;
    @FieldTokenType(min = "10", max = "20")
    private int age;
}
