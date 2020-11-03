package com.zhangll.flink;

import com.zhangll.flink.annotation.BasicTokenInfo;
import com.zhangll.flink.annotation.PojoTokenInfo;
import com.zhangll.flink.annotation.ContainerTokenInfo;
import com.zhangll.flink.annotation.TokenMapping;
import lombok.Getter;
import lombok.ToString;

import java.util.*;

@Getter
@ToString
public class Father {

    @BasicTokenInfo(min = "10", max = "100")
    private int age;
    @BasicTokenInfo(min = "100", max = "1000")
    private Integer id;
    @BasicTokenInfo(min = "1000000", max = "10000000")
    private Long money;
    @BasicTokenInfo(min = "3", max = "5")
    private String name;

    @BasicTokenInfo(count = "1", value = {"@First @Middle @last"})
    private String firstName;

    @BasicTokenInfo(value = {"张三", "李四" ,"王五" , "@First @Middle @last"}, count = "1")
    private String innerName;

    @BasicTokenInfo(min = "10", max = "20")
    private String Address;
    @BasicTokenInfo(min = "1000", max = "2000", dmin = "3", dmax = "8")
    private double money_d;
    @BasicTokenInfo(min = "1000", max = "2000", dmin = "3", dmax = "8")
    private float money_f;
    private char char_1;
    private Character a = 'c';
    private Short wShort;

    @BasicTokenInfo(min = "1000", max = "2000")
    private short uShort;

    private java.sql.Date date;
    private java.sql.Time time;
    private java.sql.Timestamp timestamp;
    // 男为1 女为0
    private boolean sex;
    @ContainerTokenInfo(
            innerBasicType = @BasicTokenInfo(min = "4", max = "7")
    )
    private ArrayList<String> sonsNameList;
    private List<String> sonsNameList2;
    private List<Integer> sonsAgeList;
    private LinkedList<Double> sonsMoneyList;
    private LinkedList<Long> sonsLongList;

    @ContainerTokenInfo(
            innerBasicType =  @BasicTokenInfo(min = "15", max = "30")
    )
    private Set<String> sonsNameSet;

    private Son son;

    private String[] stringArr;
    private Double[] doubleWrapperArr;
    private double[] doubleNoWrapperArr;
    private int[] intNoWrapperArr;
    private Integer[] intWrapperArr;

    private char[] charNoWrapperArr;
    private Character[] charWrapperArr;
//
    @ContainerTokenInfo(
            innerPojoType =  @PojoTokenInfo(
                    {
                            @TokenMapping(field = "id", basicTokenInfo = @BasicTokenInfo(min = "1", max = "10"))
                    }
            ),
            innerBasicType = @BasicTokenInfo(min = "1233", max = "12324")
    )
    @BasicTokenInfo(min = "1", max = "2")
    private ArrayList<Son> sonslist;
    private Son[] sonlist2;

    @BasicTokenInfo(value = {"/\\d/"})
    private String regrex;
////    private Date date2;
}
