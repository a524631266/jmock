所有规则均根据官网介绍
[Mock规则官网](https://github.com/nuysoft/Mock/wiki/Syntax-Specification)

### 使用注解方式设置
支持嵌套model
> 概念1: 注解BasicTokenInfo
用来表达每个内置基本类型字段的取值的约束条件
```text
Integer.class, 
int.class
char.class
Character.class
Double.class
double.class
Float.class
float.class
Long.class 
long.class 
Short.class
short.class
Boolean.class
boolean.class
String.class
时间类型
sql.Date.class
sql.Time.class
sql.Timestamp.class
数组类型
Array.class
集合类型
List.class
Set.class 
```

>概念2: mockContext
用来构建一个mock对象的上下文
#### 创建两个model，一个Father，一个Son

> 概念3: ContainerTokenInfo
就是容器的类型约束条件
见如下的sonslist 使用方式

```java
package com.zhangll.flink;

import com.zhangll.flink.annotation.BasicTokenInfo;
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
    // 正则表达式生成
    @BasicTokenInfo(value = {"/\\d{ 1, 3}  abcd\\/ \\d/ [a-bA-H1-4]{1,5}/"})
    private String regrex;
////    private Date date2;
}

```
```java
@ToString
public class Son {
    private String name;
    private int id;
    @FieldTokenType(min = "10", max = "20")
    private int age;

    private SonSon son;
}

```

#### 使用方法
使用方法 context 为mock的上下文，可以通过上下文的mock方法
```java
AnnotationMockContext context = new AnnotationMockContext();
for (int i = 0; i < 2; i++) {
    Object mock = context.mock(Father.class);
    System.out.println(mock);
}
```

> 输出结果为
```text
1. Father(age=97, id=749, money=7012716, name=令, firstName=夏 幸 祁, innerName=张三, Address=摇, money_d=1474.3962, money_f=1113.0878, char_1=Ȗ, a=ʂ, wShort=203, uShort=1717, date=2020-10-31, time=07:25:40, timestamp=2020-10-30 02:06:41.0, sex=false, sonsNameList=[箭, 专, 谦, 舌, 轮, 喷, 螺, 塔, 鲜, 避], sonsNameList2=[唯, 再, 杯, 锡, 燃, 宵, 匪, 三, 畜, 向], sonsAgeList=[517, 846, 653, 263, 667, 136, 867, 56, 378, 182], sonsMoneyList=[7.4, 2.2, 5.957, 9.9405, 8.41, 1.51, 1.89, 8.3, 7.774, 3.4809], sonsLongList=[287, 998, 743, 286, 737, 750, 388, 793, 273, 865], sonsNameSet=[着, 乔, 泄, 否, 仙, 循, 绍, 中, 蛾, 环], son=Son(name=浮, id=715, age=17, son=SonSon(name=伏, id=362, age=18)), stringArr=[拒, 瓣, 他, 烘, 熔, 竟, 据, 桌, 党, 化], doubleWrapperArr=[5.15, 3.323, 2.3334, 7.8, 3.4, 5.64, 3.805, 8.64, 2.808, 7.8388], doubleNoWrapperArr=[6.2, 7.04, 3.414, 4.79, 4.5917, 4.518, 1.0301, 2.82, 8.421, 8.361], intNoWrapperArr=[686, 621, 416, 538, 581, 253, 383, 845, 533, 522], intWrapperArr=[844, 20, 161, 981, 279, 682, 607, 707, 8, 111], charNoWrapperArr=[˛, Ƅ, , ŕ, ̊, ͛, Ü, ĉ, ȍ, Ǫ], charWrapperArr=[q, ǃ, ȳ, ̗, ̗, Ϊ, ɭ, Z, Ȇ, ¶])

2. Father(age=96, id=616, money=7651289, name=倒, firstName=汪 暨 国, innerName=张三, Address=吗, money_d=1669.9100904, money_f=1670.301, char_1=Ə, a=˴, wShort=103, uShort=1942, date=2020-10-29, time=09:53:48, timestamp=2020-10-30 09:56:16.0, sex=true, sonsNameList=[夺, 画, 刑, 袋, 对, 端, 舌, 膨, 掩, 妄], sonsNameList2=[贵, 蛇, 罢, 剃, 另, 扯, 延, 削, 股, 穴], sonsAgeList=[377, 688, 492, 833, 572, 679, 261, 162, 743, 816], sonsMoneyList=[8.974, 2.8, 3.1843, 3.7751, 4.6, 3.069, 9.51, 8.473, 1.5364, 3.28], sonsLongList=[261, 604, 445, 731, 400, 593, 618, 627, 956, 146], sonsNameSet=[言, 攀, 窃, 米, 炉, 蕉, 孩, 配, 苏, 迟], son=Son(name=首, id=870, age=18, son=SonSon(name=剖, id=144, age=15)), stringArr=[型, 渠, 低, 胆, 杨, 魔, 桃, 扒, 晨, 因], doubleWrapperArr=[7.173, 3.519, 9.9, 3.018, 3.79, 8.825, 3.7, 6.542, 5.051, 5.8], doubleNoWrapperArr=[5.21, 8.61, 4.8, 8.813, 4.5921, 2.91, 6.5, 6.7, 7.361, 3.0755], intNoWrapperArr=[273, 61, 734, 572, 772, 871, 932, 266, 832, 625], intWrapperArr=[34, 205, 401, 223, 297, 21, 605, 945, 140, 812], charNoWrapperArr=[΃, Ü, ĭ, ͆, ʹ, ƥ, Ζ, ̂, c, ɸ], charWrapperArr=[Τ, Ǵ, ρ, Ȗ, Ɠ, ΀, ċ, ʘ, ², ͊])
```


```java
class Person{
    @FieldTokenType()
    @InnerTokens()
    List<String> listString;
}
```


## 后续改进内容
1. 添加正则文法匹配功能 （has done）
> input
```java
 @BasicTokenInfo(value = {"/\\d{ 1, 3}  abcd\\/ \\d/ [a-bA-H1-4]{1,5}/" , "/[a-z][A-Z][0-9]/", "/\\w\\W\\s\\S\\d\\D/", "/\\d{5,10}/"})
    private String regrex;
```
> result 会选取其中一个作为regrex
```text
"regexp1": "55  abcd/ 3/ h13a2"
"regexp2": "pJ7",
"regexp3": "F)\fp1G",
"regexp4": "561659409"
```

2. 增加（+step）功能 
目前用于 集合类型（list.class, Array.class)

## 注意： 注解的表达能力有限
在开发的过程中，注解无法嵌套定义，会出现 cyclic annotation element type 编译错误

即，在使用注解的时候，一般表达的是一层含义，无法嵌套使用

不过在平时使用的时候，一般都是比较简单的pojo类来定义，因此也不会出现嵌套内嵌套的关系。

本项目的目的是基于满足最基本所需。


