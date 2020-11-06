package com.zhangll.flink.random;

import com.zhangll.flink.AnnotationMockContext;
import com.zhangll.flink.annotation.BasicTokenInfo;
import lombok.Getter;
import lombok.ToString;
import org.junit.Test;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import static org.junit.Assert.assertEquals;

@Getter
@ToString
class DateFather {
    @BasicTokenInfo( step = "2", value ={"2018-10-20", "2018-10-21", "2018-10-21", "2018-10-22", "2018-10-23"})
    private Date date;
    @BasicTokenInfo(step = "2", value = {"00:25", "00:30", "00:35", "00:40", "00:45"})
    private Time time;
    @BasicTokenInfo(step = "2", value ={"2018-10-20 00:25", "2018-10-20 00:30", "2018-10-20 00:35", "2018-10-20 00:40", "2018-10-20 00:45"})
    private Timestamp timestamp;


    @BasicTokenInfo( step = "-3", value ={"2018-10-20", "2018-10-21", "2018-10-21", "2018-10-22", "2018-10-23"})
    private Date date_3;
    @BasicTokenInfo(step = "-3", value = {"00:25", "00:30", "00:35", "00:40", "00:45"})
    private Time time_3;
    @BasicTokenInfo(step = "-3", value ={"2018-10-20 00:25", "2018-10-20 00:30", "2018-10-20 00:35", "2018-10-20 00:40", "2018-10-20 00:45"})
    private Timestamp timestamp_3;
}


public class DateStepTest {

    /**
     * value = {"2018-10-20", "2018-10-21", "2018-10-21", "2018-10-22", "2018-10-23"};
     */
    @Test
    public void testDateValueStep(){
        AnnotationMockContext context = new AnnotationMockContext();
        String[] value = {"2018-10-20", "2018-10-21", "2018-10-21", "2018-10-22", "2018-10-23"};
        int length = value.length;
        int process = 0;
        int step = 2;
        for (int i = 0; i < 100; i++) {
            DateFather mock = (DateFather)context.mock(DateFather.class);
            Date element = mock.getDate();
//            System.out.println(element);
            process = step * i ;
            assertEquals(value[process % length], element);
        }
    }

    /**
     * value = {"00:25", "00:30", "00:35", "00:40", "00:45"};
     */
    @Test
    public void testTimeValueStep(){
        AnnotationMockContext context = new AnnotationMockContext();
        String[] value = {"00:25", "00:30", "00:35", "00:40", "00:45"};
        int length = value.length;
        int process = 0;
        int step = 2;
        for (int i = 0; i < 100; i++) {
            DateFather mock = (DateFather)context.mock(DateFather.class);
            Time element = mock.getTime();
            process = step * i ;
            assertEquals(value[process % length], element);
        }
    }
    /**
     * value = {"2018-10-20 00:25", "2018-10-20 00:30", "2018-10-20 00:35", "2018-10-20 00:40", "2018-10-20 00:45"};
     */
    @Test
    public void testTimestampValueStep(){
        AnnotationMockContext context = new AnnotationMockContext();
        String[] value ={"2018-10-20 00:25", "2018-10-20 00:30", "2018-10-20 00:35", "2018-10-20 00:40", "2018-10-20 00:45"};
        int length = value.length;
        int process = 0;
        int step = 2;
        for (int i = 0; i < 100; i++) {
            DateFather mock = (DateFather)context.mock(DateFather.class);
            Timestamp element = mock.getTimestamp();
            process = step * i ;
            assertEquals(value[process % length], element);
        }
    }

}
