package com.zhangll.jmock.core.student;

import lombok.Data;

import java.util.Date;

/**
 * 学生证卡片
 */
@Data
public class StudentSelfCard {
    /**
     * id
     */
    private Long id;
    /**
     * 学生编号
     */
    private Long studentId;
    /**
     * 籍贯
     */
    private Integer native_;
    /**
     * 发证日期
     */
    private Date issueDate;
    /**
     * 失效日期
     */
    private Date endDate;

    /**
     * 备注
     */
    private String note;
}
