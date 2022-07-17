package com.zhangll.jmock.core.student;

import com.zhangll.jmock.core.annotation.BasicTokenInfo;
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
    private String native_;
    /**
     * 发证日期
     */
    @BasicTokenInfo(min = "2022-01-15 00:10:20" , max = "2022-01-20 00:10:20", step = "2")
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
