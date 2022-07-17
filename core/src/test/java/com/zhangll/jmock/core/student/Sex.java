package com.zhangll.jmock.core.student;

/**
 * 使用自定义枚举类，来处理数据
 *
 */
public enum Sex {
    Male(1, "男"),
    Female(2, "女");

    private final int index;
    private final String gender;

    Sex(int index, String gender) {
        this.index = index;
        this.gender = gender;
    }

    public int getIndex() {
        return index;
    }

    public String getGender() {
        return gender;
    }

    public static Sex getSex(int id) {
        if(id == 1) {
            return Male;
        } else if( id == 2) {
            return Female;
        }
        return null;
    }

}
