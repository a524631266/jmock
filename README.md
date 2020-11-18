所有规则均根据官网介绍
[Mock规则官网](https://github.com/nuysoft/Mock/wiki/Syntax-Specification)

[TOC]
所有规则均参照**前端界**比较流行的mock框架: 
[javascript Mock规则官网](https://github.com/nuysoft/Mock/wiki/Syntax-Specification)
## 背景
市面上已经有很多常用的mock框架，比如easy-mock，mockio， 等，但是生成的数据要不都是给前端使用的，要不就是在java层无法自定义扩展功能。
但是有个前辈已经做了比较好的项目([Mock.java](https://gitee.com/ForteScarlet/Mock.java?_from=gitee_search),[jmockdata](https://gitee.com/keko-boy/jmockdata?_from=gitee_search)),[mockj](https://gitee.com/kkk001/mockj?_from=gitee_search)。调查了很多框架，没有一款以Java注解方式，通过简单灵活的方式快速生成想要的数。

而且支持注解的不能很好地在注解上表达数据的关联,不支持注解的使用起来代码又过于冗余。并不能用于提供其他方面的学习，更有甚者，在比如数据开发/数仓领域为了测试代码逻辑问题，也会要求生成相关的数据用于检验代码的合理性。

因此，本项目的宗旨是以简单，易用（使用注解），方便的方式用于代码的自动生成。

## 项目目标

通过注解的方式，通过自定义规则生成指定想要的随机测试的数据。对于很多项目开发而言，测试数据能够以最快的速度进行项目的交付。同时通过测试数据，也能够很好地进行一些框架的快速学习和教育。因此，本项目的宗旨是，以最小的成本和灵活的配置来搭建一个快速测试的数据。

### maven当前项目坐标

```xml
<dependency>
  <groupId>com.github.a524631266</groupId>
  <artifactId>jmock-core</artifactId>
  <version>1.1.0</version>
</dependency>
```

### 项目依赖
在核心业务逻辑不依赖于其他框架。不过本项目为了快速开发依赖了如下

1. lombok 一款自动生成getter/setter/construct的工具。节约开发时间（不参与核心）
2. log4j 日志框架,只是用来打印日志（不参与核心）
3. findbugs google 开发的一款注解系统，这个只是在方法上加入注解，并没有做其他方面的作用（不参与核心）

## how to use
### 生成一个pojo类。
如下，首先定义了一个父类pojo.可以支持java基本类型（包装类）、字符串、数组、list，日期类型
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

### main.java
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



## 功能介绍
**[案例地址](https://github.com/a524631266/jmock/tree/master/example/src/main/java/com/zhangll/jmock/example)**
### 1. 接受多层pojo递归嵌套
```java
@ToString
class Father{
    Son son;
}
@ToString
class Son {
    Son2 son2;
    private Date date;
}
@ToString
class Son2 {
    @BasicTokenInfo(min = "1" , max = "100")
    private int a;
}


...main(){
   AnnotationMockContext annotationMockContext = new AnnotationMockContext();
    for (int i = 0; i < 100; i++) {
        Object mock = annotationMockContext.mock(Father.class);
        System.out.println(mock);
    }
}

```
### 2. 接受正则表达式(支持基本类型/String/Date类型)
添加正则文法匹配功能 （has done）[使用Java原生的Regrex表达式]
> input
```java
 @BasicTokenInfo(value = {"/\\d{ 1, 3}  abcd\\/ \\d/ [a-bA-H1-4]{1,5}/" , "/[a-z][A-Z][0-9]/", "/\\w\\W\\s\\S\\d\\D/", "/\\d{5,10}/"})
    private String regrex;
    
    
@BasicTokenInfo(value = "/\\d{1,3}/")
    private int regrexInteger;
@BasicTokenInfo(value = "/\\d{1,3}\\.\\d{1,6}/")
private double regrexDouble;
@BasicTokenInfo(value = "/201[1-8]-0[1-8]-0[1-8]/")
    private Date dateRegrex;

@BasicTokenInfo(value = "/[a-zA-Z]/")
    private Character charRegrex;
```
> result 会选取其中一个作为regrex
```text
RegrexPojo(regrex=42283, regrexInteger=656, regrexDouble=476.8, dateRegrex=2014-04-03, charRegrex=n)
RegrexPojo(regrex=2028284986, regrexInteger=20, regrexDouble=27.74132, dateRegrex=2015-01-01, charRegrex=e)
RegrexPojo(regrex=1  abcd/ 4/ 4hb, regrexInteger=393, regrexDouble=334.27, dateRegrex=2013-03-02, charRegrex=w)
```

### 3. 时间函数
```java
@ToString
class DatePojo{
// 表示最小2010-10-10日,最大2010-10-20
    @BasicTokenInfo(min = "2010-10-10" , max = "2010-10-20")
    Date date;

// 同上
 @BasicTokenInfo(min = "2010-10-10 00:10:20" , max = "2010-10-20 00:10:20")
    Timestamp timestamp;

    // 同上
    @BasicTokenInfo(min = "00:10:20" , max = "00:11:20")
    Time time;

// 步长为 2天
    @BasicTokenInfo(min = "2010-10-10" , max = "2010-10-20",step = "2")
    Date date2;

    // 步长为2秒
    @BasicTokenInfo(min = "2010-10-10 00:10:20" , max = "2010-10-20 00:10:20", step = "2")
    Timestamp timestamp2;

    // 步长为2秒
    @BasicTokenInfo(min = "00:10:20" , max = "00:11:20", step = "2")
    Time time2;
}
```

> out put

```shell
DatePojo(date=2010-10-11, timestamp=2010-10-10 17:30:22.0, time=00:10:27, date2=2010-10-10, timestamp2=2010-10-10 00:10:20.0, time2=00:10:20)
DatePojo(date=2010-10-10, timestamp=2010-10-17 20:55:40.0, time=00:11:16, date2=2010-10-12, timestamp2=2010-10-10 00:10:22.0, time2=00:10:22)
DatePojo(date=2010-10-10, timestamp=2010-10-19 14:30:19.0, time=00:10:52, date2=2010-10-14, timestamp2=2010-10-10 00:10:24.0, time2=00:10:24)
DatePojo(date=2010-10-17, timestamp=2010-10-12 22:59:23.0, time=00:10:35, date2=2010-10-16, timestamp2=2010-10-10 00:10:26.0, time2=00:10:26)
....
```

### 4. 容器随机
```java
class Array01 {
    //容器大小在2和3数量之间,其中内的基本类型的约束在
    @ContainerTokenInfo(innerBasicType = @BasicTokenInfo(min = "2", max = "3"))
    @BasicTokenInfo(min = "2", max = "3")
    List<String> list;
    @ContainerTokenInfo(innerBasicType = @BasicTokenInfo(min = "1", max = "10"))
    @BasicTokenInfo(min = "2", max = "3")
    ArrayList<Integer> arrayList;
    @ContainerTokenInfo(innerBasicType = @BasicTokenInfo(min = "1", max = "10"))
    @BasicTokenInfo(min = "2", max = "3")
    Set<Boolean> set;
    @ContainerTokenInfo(innerBasicType = @BasicTokenInfo(min = "1", max = "10"))
    @BasicTokenInfo(min = "2", max = "3")
    HashSet<Double> hashSet;
    @ContainerTokenInfo(innerBasicType = @BasicTokenInfo(min = "1", max = "10"))
    @BasicTokenInfo(min = "2", max = "3")
    LinkedList<Short> linkedList;

}
```

> OutPut

```java
Array01(list=[拿晚芝, 摩笛宋, 贪会信], arrayList=[1, 5], set=[true], hashSet=[6.1], linkedList=[7, 6, 2])
Array01(list=[键僻旋, 便培, 梨忙买], arrayList=[4, 10], set=[true], hashSet=[5.8, 3.1, 1.9], linkedList=[6, 8, 3])
Array01(list=[亏二馅, 落键滴], arrayList=[10, 8, 8], set=[true], hashSet=[9.3, 2.1], linkedList=[5, 8])
Array01(list=[降孕待, 虾蹲], arrayList=[4, 5, 6], set=[true], hashSet=[7.1, 8.8], linkedList=[10, 10, 6])
Array01(list=[眠岭, 宁酒], arrayList=[3, 6, 7], set=[false, true], hashSet=[8.6, 3.1], linkedList=[7, 4])
```

### 5. 基本数据类型检测方法
以int为例子
```java
@ToString
class IntegerPojo{
    @BasicTokenInfo(min = "1000", max = "10000")
    private Integer int1;

    @BasicTokenInfo(value = {"1" , "10", "100"})
    private int int2;
}
```
1. int1 表示为 最小为1000， 最大 10000的随机值
2. int2 表示为 从 value中 1， 10 ， 100 为随机值任一取一个值

同理其他基本数据类型. 不过比较特殊的是boolean类型

```java
@BasicTokenInfo(min = "1" , max = "99")
private boolean bool3;
```
> 这里min和max指的是 boolean为true 的概率为 max/ (max + min) = 99%


### 6. 支持 以@为前缀的语义转换

比如 @First表示姓名 @Middle

```java
 "@First @Middle @last"
```


### 7. 优先级
在pojo中可能会嵌套pojo，同时我们定义

```java
class ListPojo{
 @ContainerTokenInfo(
            innerPojoType = @PojoTokenInfo(
            value = {
                    @TokenMapping(field = "bool3",
                            basicTokenInfo = @BasicTokenInfo(min = "1", max = "2"))
            })
    )
    @BasicTokenInfo(min ="1", max = "1")
    BooleanPojo[] booleanPojos;
}

class BooleanPojo{
    @BasicTokenInfo(min = "1" , max = "100")
    private boolean bool3;
}
```
可以想象，在容器类中定义的优先级是高于原始类的

因此 @BasicTokenInfo(min = "1", max = "2") 会覆盖 @BasicTokenInfo(min = "1" , max = "100")规则

### 8. 1.1.0 新增内容
#### 继承类使用
[demo ](https://github.com/a524631266/jmock/blob/1.1.0/example/src/main/java/com/zhangll/jmock/example/TestForExetends.java)

#### 内部类使用
[demo innerClass](https://github.com/a524631266/jmock/blob/1.1.0/core/src/test/java/com/zhangll/jmock/core/MockContextTest.java)
## 框架支持的类型
支持普通class/内部class

后续可以添加一个

> 内置基本类型@BasicTokenInfo修饰的变量类型

分类 | 类描述
---|---
Java包装类 | Integer.class
Java包装类 | Character.class
Java包装类 | Double.class
Java包装类 | Float.class
Java包装类 | Long.class 
Java包装类 | Short.class
Java包装类 | Boolean.class
java基本类型 | int.class
java基本类型 | char.class
java基本类型 | double.class
java基本类型 | float.class
java基本类型 | long.class 
java基本类型 | short.class
java基本类型 | boolean.class
字符串类型 | String.class
时间类型 | sql.Date.class
时间类型 | sql.Time.class
时间类型 | sql.Timestamp.class




> @ContainerTokenInfo修饰的类型

分类 | 类描述
---|---
数组类型 |  Array.class(int[]...,String[],Object[])
集合类型 | List.class[List子类或list都可以]
集合类型 | Set.class[Set/HashSet等等Set的子类]
> Pojo类型

分类 | 类描述
---|---
Pojo类型 | Object.class

### 注解使用方式
> 注解1: @BasicTokenInfo

用来表达每个字段内置基本类型的取值的约束条件

>注解2: @ContainerTokenInfo

容器,(Set和List)类型的数据之间


> mockContext(上下文)对象

mock上下文对象是用来构建mock对象的上下文.可以利用上下文,关联生成对象之间的关系
```java
@ToString
class Step01 {
    @BasicTokenInfo(min = "5", max = "10", step = "2")
    private int increase;
}

main(){
    AnnotationMockContext annotationMockContext = new AnnotationMockContext();
        for (int i = 0; i < 100; i++) {
            Object mock = annotationMockContext.mock(Step01.class);
            System.out.println(mock);
        }
}
```
> output
```
Step01(increase=5)
Step01(increase=7)
Step01(increase=9)
Step01(increase=5)
Step01(increase=7)
Step01(increase=9)
....
```

此时输出的随机对象是以5为其实开始点,并以步长2在 [min, max]依次轮询增加.


2.step功能还能在array或者list中使用



## 注意： 注解的表达能力有限
在开发的过程中，注解无法嵌套定义，会出现 cyclic annotation element type 编译错误

即，在使用注解的时候，一般表达的是一层含义，无法嵌套使用

不过在平时使用的时候，一般都是比较简单的pojo类来定义，因此也不会出现嵌套内嵌套的关系。

本项目的目的是基于满足最基本所需。

## 为什么不用map？
本人认为 map对象一般是可以直接转化为pojo的key value表达式就可以表达，按照语义上来说map数据结构是pojo的特殊表达方式。

如果大家有疑惑，可以发起issue，我会及时解答的。



> 会支持更多@前缀语义

> 有任何需求的同学可以参与进去哦，也欢迎提bug和随机需求

## 版本更新
后续支持 map
## 项目捐赠
写代码不易...请作者喝杯咖啡呗?

![](data/zhifu.jpg)
<!--![](https://github.com/a524631266/jmock/blob/master/data/zhifu.jpg)
-->
(PS: 支付的时候 请带上你的名字/昵称呀 会维护一个赞助列表~ )
[捐赠列表](CONTRIBUTING.md)



### 解决plugins无法下载
```cmd
mvn dependency:get -DrepoUrl=http://repo.maven.apache.org/maven2/ -Dartifact=org.apache.maven.plugins:maven-gpg-plugin:1.6
```