package com.example.bilibili.pojo;

public class AdminTree {
    private Long streeid;

    private String treename;

    private String treepath;

    private Long ftreeid;

    private String lv;

    private String icon;

    public Long getStreeid() {
        return streeid;
    }

    public void setStreeid(Long streeid) {
        this.streeid = streeid;
    }

    public String getTreename() {
        return treename;
    }

    public void setTreename(String treename) {
        this.treename = treename == null ? null : treename.trim();
    }

    public String getTreepath() {
        return treepath;
    }

    public void setTreepath(String treepath) {
        this.treepath = treepath == null ? null : treepath.trim();
    }

    public Long getFtreeid() {
        return ftreeid;
    }

    public void setFtreeid(Long ftreeid) {
        this.ftreeid = ftreeid;
    }

    public String getLv() {
        return lv;
    }

    public void setLv(String lv) {
        this.lv = lv == null ? null : lv.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }
}