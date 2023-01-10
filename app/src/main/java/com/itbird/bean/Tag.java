package com.itbird.bean;

/**
 * Tag
 */
public class Tag {
    /**
     * 标签ID编号
     */
    private Long id;
    /**
     * 标签名称
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

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
