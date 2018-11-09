package com.hiekn.china.aeronautical.model.vo;

import com.hiekn.china.aeronautical.model.base.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class ConferenceQuery extends PageQuery {

    /**
     * 标题
     */
    @ApiModelProperty(example = "会议名称", value = "会议名称",dataType = "Object")
    private Object title;

    /**
     * URL
     */
    private Object url;

    /**
     * 简称
     */
    private Object shortName;

    /**
     * 其他名称
     */
    private Object otherName;

    public ConferenceQuery() {
    }


    public <T> T getTitle() {
        return (T) this.title;
    }

    public Object getUrl() {
        return this.url;
    }

    public Object getShortName() {
        return this.shortName;
    }

    public Object getOtherName() {
        return this.otherName;
    }

    public <T> void setTitle(T title) {
        this.title = title;
    }

    public void setUrl(Object url) {
        this.url = url;
    }

    public void setShortName(Object shortName) {
        this.shortName = shortName;
    }

    public void setOtherName(Object otherName) {
        this.otherName = otherName;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof ConferenceQuery)) return false;
        final ConferenceQuery other = (ConferenceQuery) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$title = this.getTitle();
        final Object other$title = other.getTitle();
        if (this$title == null ? other$title != null : !this$title.equals(other$title)) return false;
        final Object this$url = this.getUrl();
        final Object other$url = other.getUrl();
        if (this$url == null ? other$url != null : !this$url.equals(other$url)) return false;
        final Object this$shortName = this.getShortName();
        final Object other$shortName = other.getShortName();
        if (this$shortName == null ? other$shortName != null : !this$shortName.equals(other$shortName)) return false;
        final Object this$otherName = this.getOtherName();
        final Object other$otherName = other.getOtherName();
        if (this$otherName == null ? other$otherName != null : !this$otherName.equals(other$otherName)) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $title = this.getTitle();
        result = result * PRIME + ($title == null ? 43 : $title.hashCode());
        final Object $url = this.getUrl();
        result = result * PRIME + ($url == null ? 43 : $url.hashCode());
        final Object $shortName = this.getShortName();
        result = result * PRIME + ($shortName == null ? 43 : $shortName.hashCode());
        final Object $otherName = this.getOtherName();
        result = result * PRIME + ($otherName == null ? 43 : $otherName.hashCode());
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof ConferenceQuery;
    }

    public String toString() {
        return "ConferenceQuery(title=" + this.getTitle() + ", url=" + this.getUrl() + ", shortName=" + this.getShortName() + ", otherName=" + this.getOtherName() + ")";
    }
}
