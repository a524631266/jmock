package com.zhangll.flink.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.io.File;

/**
 * 作为词法分析的结果,类似于springmvc中的 mappingInfo信息
 * @author zhangll
 */
@Getter
@AllArgsConstructor
@ToString
public class FieldToken {
    /**
     * 语义说明
     * 在 int类型的min表示最小值
     * 在 string语义下min 表示最小长度
     * 在 boolean与一下，是做概率为1的计算min / (min + max
     */
    private int min;
    /**
     * 语义说明
     * 在 int类型的max表示最大值
     * 在 string语义下max 表示最大长度
     * 在 boolean与一下，是做概率为1的计算min / (min + max
     */
    private int max;

    /**
     * 语义分析
     * 在 int 类型中 无该选项
     * 在 string 类型中，为固定长度
     *
     */
    private int count;

    private int dmin;
    private int dmax;
    private int dcount;

    /**
     *  语义级别
     */
    private int step;

    /**
     * 该值为动态变量，比如@FIRST ，在string 语义下是获取 姓名
     *
     *
     */
    private String[] value = {};
    /**
     *
     */
    private FieldToken subFieldToken;

    FieldToken(FieldTokenBuilder builder){
        this.min = builder.getMin();
        this.max = builder.getMax();
        this.count = builder.getCount();
        this.dmin = builder.getDmin();
        this.dmax = builder.getDmax();
        this.dcount = builder.getDcount();
        this.step = builder.getStep();
        this.value = builder.getValue();
        this.subFieldToken = builder.getSubFieldToken();
    }

    /**
     * 内部创建构建者，屏蔽对象的设置，用来封装
     * 内容
     */
    @Getter
    public static class FieldTokenBuilder {
        private int min;
        private int max;
        private int count;
        private int dmin;
        private int dmax;
        private int dcount;
        private int step;
        private String[] value = {};
        private FieldToken subFieldToken = FieldTokenFactory.getDefaultFieldToken();
        public FieldToken build() {
            if(min > max) {
                throw new IllegalArgumentException("min must not large than max");
            }
            if(dmin > dmax){
                throw  new IllegalArgumentException("dmin must not larger than dmax");
            }
            return  new FieldToken(this);
        }

        public FieldTokenBuilder setMin(int min) {
            this.min = min;
            return this;
        }
        public FieldTokenBuilder setMax(int max) {
            this.max = max;
            return this;
        }

        public FieldTokenBuilder setCount(int count) {
            if(count < 0) {
                throw  new IllegalArgumentException("count must be positive");
            }
            this.count = count;
            return this;
        }

        public FieldTokenBuilder setDcount(int dcount) {
            this.dcount = dcount;
            return this;
        }

        public FieldTokenBuilder setDmin(int dmin) {
            this.dmin = dmin;
            return this;
        }

        public FieldTokenBuilder setStep(int step) {

            this.step = step;
            return this;
        }

        public FieldTokenBuilder setDmax(int dmax) {

            this.dmax = dmax;
            return this;
        }

        public FieldTokenBuilder setValue(String[] value) {
            this.value = value;
            return this;
        }

        public FieldTokenBuilder setSubFieldToken(FieldToken subFieldToken) {
            if(subFieldToken  == null) {
                throw  new IllegalArgumentException("FieldToken must not be null");
            }
            this.subFieldToken = subFieldToken;
            return this;
        }
    }
}
