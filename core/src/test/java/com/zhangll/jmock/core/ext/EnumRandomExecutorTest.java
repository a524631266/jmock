package com.zhangll.jmock.core.ext;

import com.zhangll.jmock.core.AnnotationMockContext;
import com.zhangll.jmock.core.random.RandomType;
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
class EnumPojo{
    State state;
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
    public void getExtendValue() throws NoSuchFieldException {
        EnumPojo mock = context.mock(EnumPojo.class);
        System.out.println(mock);
    }

}