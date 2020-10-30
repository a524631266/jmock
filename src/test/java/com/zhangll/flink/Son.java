package com.zhangll.flink;

import com.zhangll.flink.annotation.FieldTokenType;
import lombok.ToString;

@ToString
public class Son {
    private String name;
    private int id;
    @FieldTokenType(min = "10", max = "20")
    private int age;

    private SonSon son;
}
