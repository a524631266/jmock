package com.zhangll.flink;

import lombok.ToString;

import java.util.*;

@ToString
public class Father {
    private int age;
    private Integer id;
    private Long money;
    private String name;
    private String Address;
    private double money_d;
    private float money_f;
    private char firstName;
    private Character a = 'c';
    private Date date;
    // 男为1 女为0
    private boolean sex;
    private ArrayList<String> sonsNameList;
    private List<Integer> sonsAgeList;
    private LinkedList<Double> sonsMoneyList;
    private LinkedList<Long> sonsLongList;

    private Set<String> sonsNameSet;

}
