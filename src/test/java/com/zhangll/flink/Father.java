package com.zhangll.flink;

import com.zhangll.flink.annotation.FieldTokenType;
import lombok.Getter;
import lombok.ToString;

import java.util.*;

@Getter
@ToString
public class Father {
    @FieldTokenType(min = "10", max = "100")
    private int age;
    @FieldTokenType(min = "100", max = "1000")
    private Integer id;
    @FieldTokenType(min = "1000000", max = "10000000")
    private Long money;
    @FieldTokenType(min = "3", max = "5")
    private String name;
    @FieldTokenType(min = "10", max = "20")
    private String Address;
    @FieldTokenType(min = "1000", max = "2000", dmin = "3", dmax = "8")
    private double money_d;
    private float money_f;
    private char firstName;
    private Character a = 'c';
    private java.sql.Date date;
    private java.sql.Time time;
    private java.sql.Timestamp timestamp;
    // 男为1 女为0
    private boolean sex;
    private ArrayList<String> sonsNameList;
    private List<Integer> sonsAgeList;
    private LinkedList<Double> sonsMoneyList;
    private LinkedList<Long> sonsLongList;

    private Set<String> sonsNameSet;

}
