package com.zhangll.jmock.core.random;

import com.zhangll.jmock.core.AnnotationMockContext;
import com.zhangll.jmock.core.annotation.BasicTokenInfo;
import lombok.Getter;
import lombok.ToString;
import org.junit.Test;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;

@Getter
@ToString
class DateFather {
    @BasicTokenInfo( step = "2", value ={"2018-10-20", "2018-10-21", "2018-10-21", "2018-10-22", "2018-10-23"})
    private Date date;
    @BasicTokenInfo(step = "2", value = {"00:01:25", "00:02:30", "00:03:35", "00:04:40", "00:05:45"})
    private Time time;
    @BasicTokenInfo(step = "2", value ={"2018-10-20 00:01:25", "2018-10-20 00:02:30", "2018-10-20 00:03:35", "2018-10-20 00:04:40", "2018-10-20 00:05:45"})
    private Timestamp timestamp;


    @BasicTokenInfo( step = "-3", value ={"2018-10-20", "2018-10-21", "2018-10-21", "2018-10-22", "2018-10-23"})
    private Date date_3;
    @BasicTokenInfo(step = "-3", value = {"00:01:25", "00:02:30", "00:03:35", "00:04:40", "00:05:45"})
    private Time time_3;
    @BasicTokenInfo(step = "-3", value ={"2018-10-20 00:01:25", "2018-10-20 00:02:30", "2018-10-20 00:03:35", "2018-10-20 00:04:40", "2018-10-20 00:05:45"})
    private Timestamp timestamp_3;

    /**
     * 2 以天为长度计算
     */
    @BasicTokenInfo( min = "2018-10-20", max = "2019-10-20" ,step = "2")
    private Date dateUp;
    /**
     * -3 以天为长度计算
     */
    @BasicTokenInfo( min = "2018-10-20", max = "2019-10-20" ,step = "-3")
    private Date dateDown;

    /**
     * 每两秒递增
     */
    @BasicTokenInfo( min = "00:01:25", max = "00:02:30" ,step = "2")
    private Time timeUp;

    /**
     * 每两秒递增
     */
    @BasicTokenInfo( min = "00:01:25", max = "00:02:30" ,step = "-3")
    private Time timeDown;


    /**
     * 每1个小时递增
     */
    @BasicTokenInfo( min = "2018-10-20 00:01:25", max = "2018-10-20 00:02:30" ,step = "3600")
    private Timestamp timestUp;

    /**
     * 每2个小时递减
     */
    @BasicTokenInfo(min = "2018-10-20 00:01:25", max = "2018-10-20 00:02:30" ,step = "-7200")
    private Timestamp timestDown;
}


public class DateStepTest {
    static SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd");
    static SimpleDateFormat TimestampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static SimpleDateFormat TimeFormat = new SimpleDateFormat("HH:mm:ss");
    /**
     * value = {"2018-10-20", "2018-10-21", "2018-10-21", "2018-10-22", "2018-10-23"};
     */
    @Test
    public void testDateValueStep() throws ParseException {
        // 正step
        AnnotationMockContext context = new AnnotationMockContext();
        String[] value = {"2018-10-20", "2018-10-21", "2018-10-21", "2018-10-22", "2018-10-23"};
        int length = value.length;
        int process = 0;
        int step = 2;
        for (int i = 0; i < 100; i++) {
            DateFather mock = (DateFather)context.mock(DateFather.class);
            Date element = mock.getDate();
            process = step * i ;
            assertEquals(DateFormat.parse(value[process % length]), element);
        }
    }

    /**
     * min = "2018-10-20", max = "2019-10-20" ,step = "2"
     *  最小为 2018-10-20 最大 2019-10-20 以天递增
     */
    @Test
    public void testDateUp() throws ParseException {
        // 正step
        AnnotationMockContext context = new AnnotationMockContext();
        String minDate = "2018-10-20";
        String maxDate = "2019-10-20";
        Integer step = 2;
        java.util.Date startDate = DateFormat.parse(minDate);
        java.util.Date endDate = DateFormat.parse(maxDate);

        Calendar instance = Calendar.getInstance();
        instance.setTime(startDate);
        Calendar condition = Calendar.getInstance();
        condition.setTime(endDate);
        long gap = (endDate.getTime() - startDate.getTime()) / (60*60*24*1000);
        for (int i = 0; i < 300; i++) {
            DateFather mock = (DateFather)context.mock(DateFather.class);
            Date element = mock.getDateUp();
            assertEquals(instance.getTime(), element);
            instance.add(Calendar.DATE, step);
            while (instance.after(condition)){
                instance.add(Calendar.DATE, - (int) gap);
            }
        }
    }


    /**
     * min = "2018-10-20", max = "2019-10-20" ,step = "-2"
     *  最小为 2018-10-20 最大 2019-10-20 以天递增 ，从 最大时间开始递减
     */
    @Test
    public void testDateDown() throws ParseException {
        // 正step
        AnnotationMockContext context = new AnnotationMockContext();
        String minDate = "2018-10-20";
        String maxDate = "2019-10-20";
        Integer step = 3;
        java.util.Date startDate = DateFormat.parse(maxDate);
        java.util.Date endDate = DateFormat.parse(minDate);

        Calendar instance = Calendar.getInstance();
        instance.setTime(startDate);
        Calendar condition = Calendar.getInstance();
        condition.setTime(endDate);
        long gap = (startDate.getTime() - endDate.getTime()) / (60*60*24*1000);
        for (int i = 0; i < 300; i++) {
            DateFather mock = (DateFather)context.mock(DateFather.class);
            Date element = mock.getDateDown();
            assertEquals(instance.getTime(), element);
            instance.add(Calendar.DATE, -step);
            while (instance.before(condition)){
                instance.add(Calendar.DATE, (int) gap);
            }
        }
    }
    /**
     * value = {"2018-10-20", "2018-10-21", "2018-10-21", "2018-10-22", "2018-10-23"};
     */
    @Test
    public void testDateValueStepMinus() throws ParseException {
        // 正step
        AnnotationMockContext context = new AnnotationMockContext();
        String[] value = {"2018-10-20", "2018-10-21", "2018-10-21", "2018-10-22", "2018-10-23"};
        int length = value.length;
        // 负step
        int step2 = -3;
        int process2 = length - 1;
        for (int i = 0; i < 100; i++) {
            DateFather mock = (DateFather)context.mock(DateFather.class);
            Date element = mock.getDate_3();
            process2 = process2 < 0? process2 + length:process2;
            assertEquals(DateFormat.parse(value[process2]), element);
            process2 += step2 ;
        }

    }






    /**
     * value = {"00:01:25", "00:02:30", "00:03:35", "00:04:40", "00:05:45"};
     */
    @Test
    public void testTimeValueStep() throws ParseException {
        AnnotationMockContext context = new AnnotationMockContext();
        String[] value = {"00:01:25", "00:02:30", "00:03:35", "00:04:40", "00:05:45"};
        int length = value.length;
        int process = 0;
        int step = 2;
        for (int i = 0; i < 100; i++) {
            DateFather mock = (DateFather)context.mock(DateFather.class);
            Time element = mock.getTime();
            process = step * i ;
            assertEquals(TimeFormat.parse(value[process % length]), element);
        }
    }

    /**
     *  min = "00:01:25", max = "00:02:30" ,step = "2"
     *  2s 轮询方式获取生成数据
     */
    @Test
    public void testTimeDown() throws ParseException {
        // 正step
        AnnotationMockContext context = new AnnotationMockContext();
        String minDate = "00:01:25";
        String maxDate = "00:02:30";
        Integer step = 3;
        java.util.Date startDate = TimeFormat.parse(maxDate);
        java.util.Date endDate = TimeFormat.parse(minDate);

        Calendar instance = Calendar.getInstance();
        instance.setTime(startDate);
        Calendar condition = Calendar.getInstance();
        condition.setTime(endDate);
        long gap = (startDate.getTime() - endDate.getTime()) / 1000;
        for (int i = 0; i < 300; i++) {
            DateFather mock = (DateFather)context.mock(DateFather.class);
            Time element = mock.getTimeDown();

            assertEquals(instance.getTime(), element);
            instance.add(Calendar.SECOND, - step);
            while (instance.before(condition)){
                instance.add(Calendar.SECOND, (int) gap);
            }
        }
    }



    /**
     *  min = "00:01:25", max = "00:02:30" ,step = "2"
     *  2s
     */
    @Test
    public void testTimeUp() throws ParseException {
        // 正step
        AnnotationMockContext context = new AnnotationMockContext();
        String minDate = "00:01:25";
        String maxDate = "00:02:30";
        Integer step = 2;
        java.util.Date startDate = TimeFormat.parse(minDate);
        java.util.Date endDate = TimeFormat.parse(maxDate);

        Calendar instance = Calendar.getInstance();
        instance.setTime(startDate);
        Calendar condition = Calendar.getInstance();
        condition.setTime(endDate);
        long gap = (endDate.getTime() - startDate.getTime()) / 1000;
        for (int i = 0; i < 300; i++) {
            DateFather mock = (DateFather)context.mock(DateFather.class);
            Time element = mock.getTimeUp();
            assertEquals(instance.getTime(), element);
            instance.add(Calendar.SECOND, step);
            while (instance.after(condition)){
                instance.add(Calendar.SECOND, - (int) gap);
            }
        }
    }

    /**
     * value = {"00:01:25", "00:02:30", "00:03:35", "00:04:40", "00:05:45"};
     */
    @Test
    public void testTimeValueStepMinus() throws ParseException {
        // 正step
        AnnotationMockContext context = new AnnotationMockContext();
        String[] value = {"00:01:25", "00:02:30", "00:03:35", "00:04:40", "00:05:45"};
        int length = value.length;
        // 负step
        int step2 = -3;
        int process2 = length - 1;
        for (int i = 0; i < 100; i++) {
            DateFather mock = (DateFather)context.mock(DateFather.class);
            Time element = mock.getTime_3();
            process2 = process2 < 0? process2 + length:process2;
            assertEquals(TimeFormat.parse(value[process2]), element);
            process2 += step2 ;
        }

    }


    /**
     * value = {"2018-10-20 00:01:25", "2018-10-20 00:02:30", "2018-10-20 00:03:35", "2018-10-20 00:04:40", "2018-10-20 00:05:45"};
     */
    @Test
    public void testTimestampValueStep() throws ParseException {
        AnnotationMockContext context = new AnnotationMockContext();
        String[] value ={"2018-10-20 00:01:25", "2018-10-20 00:02:30", "2018-10-20 00:03:35", "2018-10-20 00:04:40", "2018-10-20 00:05:45"};
        int length = value.length;
        int process = 0;
        int step = 2;
        for (int i = 0; i < 100; i++) {
            DateFather mock = (DateFather)context.mock(DateFather.class);
            Timestamp element = mock.getTimestamp();
            process = step * i ;
            assertEquals(TimestampFormat.parse(value[process % length]), element);
        }
    }


    /**
     * value = {"2018-10-20 00:01:25", "2018-10-20 00:02:30", "2018-10-20 00:03:35", "2018-10-20 00:04:40", "2018-10-20 00:05:45"};
     */
    @Test
    public void testTimestampValueStepMinus() throws ParseException {
        // 正step
        AnnotationMockContext context = new AnnotationMockContext();
        String[] value = {"2018-10-20 00:01:25", "2018-10-20 00:02:30", "2018-10-20 00:03:35", "2018-10-20 00:04:40", "2018-10-20 00:05:45"};
        int length = value.length;
        // 负step
        int step2 = -3;
        int process2 = length - 1;
        for (int i = 0; i < 100; i++) {
            DateFather mock = (DateFather)context.mock(DateFather.class);
            Timestamp element = mock.getTimestamp_3();
            process2 = process2 < 0? process2 + length:process2;
            assertEquals(TimestampFormat.parse(value[process2]), element);
            process2 += step2 ;
        }
    }

    /**
     * @BasicTokenInfo(min = "2018-10-20 00:01:25", max = "2018-10-20 00:02:30", step = "-7200")
     */
    @Test
    public void testTimestDown() throws ParseException {
        // 正step
        AnnotationMockContext context = new AnnotationMockContext();
        String minDate = "2018-10-20 00:01:25";
        String maxDate = "2018-10-20 00:02:30";
        Integer step = 7200;
        java.util.Date startDate = TimestampFormat.parse(maxDate);
        java.util.Date endDate = TimestampFormat.parse(minDate);

        Calendar instance = Calendar.getInstance();
        instance.setTime(startDate);
        Calendar condition = Calendar.getInstance();
        condition.setTime(endDate);
        long gap = (startDate.getTime() - endDate.getTime()) / 1000;
        for (int i = 0; i < 300; i++) {
            DateFather mock = (DateFather)context.mock(DateFather.class);
            Timestamp element = mock.getTimestDown();
            assertEquals(instance.getTime(), element);
//            System.out.println(element);
            instance.add(Calendar.SECOND, - step);
            while (instance.before(condition)){
                instance.add(Calendar.SECOND, (int) gap);
            }
        }
    }



    /**
     *   @BasicTokenInfo( min = "2018-10-20 00:01:25", max = "2018-10-20 00:02:30" ,step = "3600")
     *     private Time timestUp;
     */
    @Test
    public void testTimestUp() throws ParseException {
        // 正step
        AnnotationMockContext context = new AnnotationMockContext();
        String minDate = "2018-10-20 00:01:25";
        String maxDate = "2018-10-20 00:02:30";
        Integer step = 3600;
        java.util.Date startDate = TimestampFormat.parse(minDate);
        java.util.Date endDate = TimestampFormat.parse(maxDate);

        Calendar instance = Calendar.getInstance();
        instance.setTime(startDate);
        Calendar condition = Calendar.getInstance();
        condition.setTime(endDate);
        long gap = (endDate.getTime() - startDate.getTime()) / 1000;
        for (int i = 0; i < 300; i++) {
            DateFather mock = (DateFather)context.mock(DateFather.class);
            Timestamp element = mock.getTimestUp();
//            assertEquals(instance.getTime(), element);
            instance.add(Calendar.SECOND, step);
            while (instance.after(condition)){
                instance.add(Calendar.SECOND, - (int) gap);
            }
        }
    }

}
