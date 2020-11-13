package com.zhangll.jmock.example;

import com.zhangll.jmock.core.AnnotationMockContext;
import com.zhangll.jmock.core.annotation.BasicTokenInfo;
import lombok.ToString;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

@ToString
class DatePojo{

    @BasicTokenInfo(min = "2010-10-10" , max = "2010-10-20")
    Date date;


    @BasicTokenInfo(min = "2010-10-10 00:10:20" , max = "2010-10-20 00:10:20")
    Timestamp timestamp;


    @BasicTokenInfo(min = "00:10:20" , max = "00:11:20")
    Time time;


    @BasicTokenInfo(min = "2010-10-10" , max = "2010-10-20",step = "2")
    Date date2;


    @BasicTokenInfo(min = "2010-10-10 00:10:20" , max = "2010-10-20 00:10:20", step = "2")
    Timestamp timestamp2;


    @BasicTokenInfo(min = "00:10:20" , max = "00:11:20", step = "2")
    Time time2;
}
public class SqlDateExa {
    public static void main(String[] args) {
        AnnotationMockContext annotationMockContext = new AnnotationMockContext();
        for (int i = 0; i < 100; i++) {
            Object mock = annotationMockContext.mock(DatePojo.class);
            System.out.println(mock);
        }

    }
}
