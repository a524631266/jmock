package com.zhangll.jmock.core.student;

import com.zhangll.jmock.core.AnnotationMockContext;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class StudentTest {
    private AnnotationMockContext context =  new AnnotationMockContext();
    /**
     * Rigorous Test :-)
     */
    @Test
    public void testAnnotation()
    {
        int count = 0;
        while ((count ++) < 2){
            StudentBean mock = context.mock(StudentBean.class);
            System.out.println(mock);
        }
    }
}
