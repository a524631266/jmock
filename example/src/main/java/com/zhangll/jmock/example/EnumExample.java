package com.zhangll.jmock.example;

import com.zhangll.jmock.core.AnnotationMockContext;
import com.zhangll.jmock.core.annotation.BasicTokenInfo;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

enum State{
    START(0),
    RUNNGING(1),
    END(2);
    int value;
    State(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}

enum StringState{
    START("123"),
    RUNNGING("1233"),
    END("234234");

    private final String value;

    StringState(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
enum EmptyState{

}
@Getter
@ToString
class EnumPojo{
    State state;
    EmptyState emptyState;
    StringState stringState;
}

public class EnumExample {


    public static void main(String[] args) {
        EnumPojo mock = new AnnotationMockContext().mock(EnumPojo.class);
        System.out.println(mock);
    }
}
