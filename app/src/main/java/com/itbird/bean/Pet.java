package com.itbird.bean;

import java.util.Arrays;

/**
 * Created by itbird on 2022/12/20
 */
public class Pet {
    /**
     * 分组
     */
    private Category category;
    /**
     * 宠物ID编号
     */
    private long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 照片URL
     */
    private String[] photoUrls;
    /**
     * 宠物销售状态
     */
    private Status status;
    /**
     * 标签
     */
    private Tag[] tags;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category value) {
        this.category = value;
    }

    public long getid() {
        return id;
    }

    public void setid(long value) {
        this.id = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String[] getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(String[] value) {
        this.photoUrls = value;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status value) {
        this.status = value;
    }

    public Tag[] getTags() {
        return tags;
    }

    public void setTags(Tag[] value) {
        this.tags = value;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "category=" + category +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", photoUrls=" + Arrays.toString(photoUrls) +
                ", status=" + status +
                ", tags=" + Arrays.toString(tags) +
                '}';
    }
}
