package com.zhangll.flink.config;

import com.zhangll.flink.model.FieldToken;

public class MockConfiguration {
    // string[]
    public static FieldToken  defaultArrayFieldToken = new FieldToken.FieldTokenBuilder()
            .setCount(10)
            .build();
    public static FieldToken  defaultBooleanFieldToken = new FieldToken.FieldTokenBuilder()
            .setMin(1)
            .setMax(1)
            .setValue(new String[]{"true"})
            .build();
    public static FieldToken  defaultListFieldToken = new FieldToken.FieldTokenBuilder()
            .setCount(10)
            .build();
    public static FieldToken  defaultCharFieldToken = new FieldToken.FieldTokenBuilder()
            .setMin(1)
            .setMax(1000)
            .build();
    public static FieldToken  defaultDoubleFieldToken = new FieldToken.FieldTokenBuilder()
            .setMin(1)
            .setMax(10)
            .setDmin(1)
            .setDmax(5)
            .build();
    public static FieldToken  defaultFloatFieldToken = new FieldToken.FieldTokenBuilder()
            .setMin(1)
            .setMax(10)
            .setDmin(1)
            .setDmax(5)
            .build();
    public static FieldToken  defaultIntFieldToken = new FieldToken.FieldTokenBuilder()
            .setMin(1)
            .setMax(1000)
            .build();
    public static FieldToken  defaultLongFieldToken = new FieldToken.FieldTokenBuilder()
            .setMin(10)
            .setMax(1000)
            .build();
    public static FieldToken  defaultShortFieldToken = new FieldToken.FieldTokenBuilder()
            .setMin(1)
            .setMax(1000)
            .build();
    public static FieldToken  defaultStringFieldToken = new FieldToken.FieldTokenBuilder()
            .setMin(1)
            .setMax(3)
            .build();
    public static FieldToken  defaultDateFieldToken = new FieldToken.FieldTokenBuilder()
            .setMin(new Long(System.currentTimeMillis() / 1000 - 24 *60 *60).intValue())
            .setMax(new Long(System.currentTimeMillis() / 1000 + 24 *60 *60).intValue())
            .build();
    public static FieldToken  defaultTimeFieldToken = new FieldToken.FieldTokenBuilder()
            .setMin(new Long(System.currentTimeMillis() / 1000 - 24 *60 *60).intValue())
            .setMax(new Long(System.currentTimeMillis() / 1000 + 24 *60 *60).intValue())
            .build();
    public static FieldToken  defaultTimestampFieldToken = new FieldToken.FieldTokenBuilder()
            .setMin(new Long(System.currentTimeMillis() / 1000 - 24 *60 *60).intValue())
            .setMax(new Long(System.currentTimeMillis() / 1000 + 24 *60 *60).intValue())
            .build();
}
