package com.example.bilibili.pojo;

import java.util.Date;

public class Tasks {
    private Long id;

    private String name;

    private Date dateStart;

    private Date dateStop;

    private String taskDesc;

    private String giftsDesc;

    private Integer state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateStop() {
        return dateStop;
    }

    public void setDateStop(Date dateStop) {
        this.dateStop = dateStop;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc == null ? null : taskDesc.trim();
    }

    public String getGiftsDesc() {
        return giftsDesc;
    }

    public void setGiftsDesc(String giftsDesc) {
        this.giftsDesc = giftsDesc == null ? null : giftsDesc.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}