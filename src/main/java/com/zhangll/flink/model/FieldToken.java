package com.zhangll.flink.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 作为词法分析的结果
 * @author zhangll
 */
@Data
@AllArgsConstructor
public class FieldToken {
    /**
     * 语义说明
     * 在 int类型的min表示最小值
     * 在 string语义下min 表示最小长度
     * 在 boolean与一下，是做概率为1的计算min / (min + max
     */
    public int min;
    /**
     * 语义说明
     * 在 int类型的max表示最大值
     * 在 string语义下max 表示最大长度
     * 在 boolean与一下，是做概率为1的计算min / (min + max
     */
    public int max;

    /**
     * 语义分析
     * 在 int 类型中 无该选项
     * 在 string 类型中，为固定长度
     *
     */
    public int count;

    public int dmin;
    public int dmax;
    public int dcount;

    /**
     *  语义级别
     */
    public int step;

    /**
     * 该值为动态变量，比如@FIRST ，在string 语义下是获取 姓名
     *
     *
     */
    public String value;
}
