package com.zhangll.jmock.example.normalpojo;

import com.zhangll.jmock.core.annotation.BasicTokenInfo;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Son {
    private String name;
    private int id;
    @BasicTokenInfo(min = "10", max = "20")
    private int age;
    private SonSon son;
}
