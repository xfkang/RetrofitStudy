package com.itbird.bean;

/**
 * Created by itbird on 2022/12/20
 */
public class Category {
    /**
     * 分组ID编号
     */
    private Long id;
    /**
     * 分组名称
     */
    private String name;

    public Long getid() {
        return id;
    }

    public void setid(Long value) {
        this.id = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }
}
