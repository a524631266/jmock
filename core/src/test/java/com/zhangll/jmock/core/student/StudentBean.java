package com.zhangll.jmock.core.student;

import com.zhangll.jmock.core.annotation.BasicTokenInfo;
import lombok.Data;

/**
 * 学生基本信息
 */
@Data
public class StudentBean {
    /**
     * id
     */
    private Long id;
    /**
     * 中文名
     */
    @BasicTokenInfo(value = {"@First @Middle @last"}, count = "1")
    private String cnname;
    /**
     * 籍贯
     */
    @BasicTokenInfo(min = "1", max = "2")
    private Sex sex;

    /**
     * 备注
     */
    @BasicTokenInfo(min = "4", max = "10")
    private String note;
    
    /**
     * 一对一关系，学生都有一个卡片
     * 一对一使用associate
     */
    private StudentSelfCard studentSelfCard;
}
