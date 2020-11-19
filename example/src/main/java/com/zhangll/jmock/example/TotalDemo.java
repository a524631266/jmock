package com.zhangll.jmock.example;

import com.zhangll.jmock.core.AnnotationMockContext;
import com.zhangll.jmock.core.annotation.BasicTokenInfo;
import com.zhangll.jmock.core.annotation.ContainerTokenInfo;
import com.zhangll.jmock.core.annotation.PojoTokenInfo;
import com.zhangll.jmock.core.annotation.TokenMapping;
import lombok.Getter;
import lombok.ToString;

import java.util.*;

@Getter
@ToString
class Father2 {

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

    @BasicTokenInfo(value = {"张三", "李四" ,"王五" , "@First @Middle @last", "/\\d{5,6}\\w+\\d/"}, count = "1")
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
    @BasicTokenInfo(step = "4", value = {"张三", "李四" ,"王五" , "@First @Middle @last", "/\\d{5,6}\\w+\\d/"}, count = "10")
    private ArrayList<String> sonsNameList;
    private List<String> sonsNameList2;
    private List<Integer> sonsAgeList;
    private LinkedList<Double> sonsMoneyList;
    private LinkedList<Long> sonsLongList;

    @ContainerTokenInfo(
            innerBasicType =  @BasicTokenInfo(min = "15", max = "30")
    )
    private Set<String> sonsNameSet;
    @PojoTokenInfo(
            @TokenMapping(field = "name", basicTokenInfo = @BasicTokenInfo(min = "3", max = "7"))
    )
    private Son sonff;

    @BasicTokenInfo(step = "4", value = {"张三", "李四" ,"王五" , "@First @Middle @last", "/\\d{ 1, 3}  abcd\\/ \\d/"}, count = "10")
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
    @BasicTokenInfo(min = "10", max = "20")
    private ArrayList<Son> sonslist;

    @ContainerTokenInfo(
            innerPojoType =  @PojoTokenInfo(
                    {
                            @TokenMapping(field = "id", basicTokenInfo = @BasicTokenInfo(min = "1", max = "10"))
                    }
            )
    )
    @BasicTokenInfo(min = "3", max = "4")
    private Son[] sonlist2;

    @BasicTokenInfo(value = {"/\\d{ 1, 3}  abcd\\/ \\d/ [a-bA-H1-4]{1,5}/" , "/[a-z][A-Z][0-9]/", "/\\w\\W\\s\\S\\d\\D/", "/\\d{5,10}/"})
    private String regrex;

    // 初始化start方法
    @BasicTokenInfo(min = "3", max = "100", step = "20")
    private int min3Max100;
    @BasicTokenInfo(min = "3", max= "100", step = "-20")
    private int min3Max100ReduceStep;

    @BasicTokenInfo(step = "2", value = {"1", "2", "8", "4", "20"})
    private int intValue;

    @BasicTokenInfo(step = "-3", value = {"1", "2", "8", "4", "20"})
    private int intValueMinus;
////    private Date date2;
}

public class TotalDemo {

    public static void main(String[] args) {
        Father2 mock = new AnnotationMockContext().mock(Father2.class);
        System.out.println(mock);
        System.out.println(mock.getAge());
    }
}
