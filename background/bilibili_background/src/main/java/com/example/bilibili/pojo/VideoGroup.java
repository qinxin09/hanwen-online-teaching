package com.example.bilibili.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class VideoGroup {
    private String id;

    private String title;

    private Long authorId;

    private String brief;

    private String coverUrl;

    private Date createTime;

    private Boolean istoll;

    private BigDecimal price;

    private Integer state;

    private Long playCount;

    private Long commentCount;

    private String attachementNames;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief == null ? null : brief.trim();
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl == null ? null : coverUrl.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Boolean getIstoll() {
        return istoll;
    }

    public void setIstoll(Boolean istoll) {
        this.istoll = istoll;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getPlayCount() {
        return playCount;
    }

    public void setPlayCount(Long playCount) {
        this.playCount = playCount;
    }

    public Long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }

    public String getAttachementNames() {
        return attachementNames;
    }

    public void setAttachementNames(String attachementNames) {
        this.attachementNames = attachementNames == null ? null : attachementNames.trim();
    }
}