package com.zhangll.jmock.core.ext;

import com.zhangll.jmock.core.AnnotationMockContext;
import com.zhangll.jmock.core.random.RandomType;
import lombok.Getter;
import lombok.ToString;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;
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

public class EnumRandomExecutorTest {
    private AnnotationMockContext context;

    @Before
    public void init(){
        context = new AnnotationMockContext();
        context.register(Enum.class, new EnumRandomExecutor());
    }

    /**
     * 获取一个枚举执行器
     * @throws NoSuchFieldException
     */
    @Test
    public void getExtendExecutorRight() throws NoSuchFieldException {
        Field state = EnumPojo.class.getDeclaredField("state");
        RandomType executor = context.getExecutor(state.getType());
        assertNotNull(executor);
    }

    @Test
    public void getExtendValue() {
        for (int i = 0; i < 100; i++) {
            EnumPojo mock = context.mock(EnumPojo.class);
            State state = mock.getState();
            StringState stringState = mock.getStringState();
            EmptyState emptyState = mock.getEmptyState();
            assertNotNull(state);
            assertNotNull(stringState);
            assertNull(emptyState);
        }
    }

}