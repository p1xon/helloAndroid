package com.example.administrator.chabaike.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/6/23.
 */
public class Info implements Parcelable{


    /**
     * count : 0
     * description : 从图中可以看出，魏晨女友身材纤细，与魏晨站在一起很养眼，两人很相衬
     * fcount : 0
     * fromname : TechWeb
     * fromurl : http://www.techweb.com.cn/irouter/2016-06-22/2350424.shtml
     * id : 11157
     * img : /top/160622/d8937bcdbbf833a7e792b8a2a0a951d0.jpg
     * keywords : 魏晨和女友秀恩爱
     * rcount : 0
     * time : 1466595165000
     * title : 魏晨和女友秀恩爱:粉丝大方送祝福
     * topclass : 2
     */
    private long id;
    private String title;//资讯标题
    private int topclass;//一级分类
    private String img;//图片
    private String description;//描述
    private String keywords;//关键字
    private String message;//资讯内容
    private int count;//访问次数
    private int fcount;//收藏数
    private int rcount;//评论读数
    private String fromname;
    private String fromurl;
    private String time;

    public Info() {
    }

    protected Info(Parcel in) {
        img = in.readString();
        description = in.readString();
        rcount = in.readInt();
        time = in.readString();
        id = in.readLong();
        title = in.readString();
    }

    public static final Creator<Info> CREATOR = new Creator<Info>() {
        @Override
        public Info createFromParcel(Parcel in) {
            return new Info(in);
        }

        @Override
        public Info[] newArray(int size) {
            return new Info[size];
        }
    };

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFcount() {
        return fcount;
    }

    public void setFcount(int fcount) {
        this.fcount = fcount;
    }

    public String getFromname() {
        return fromname;
    }

    public void setFromname(String fromname) {
        this.fromname = fromname;
    }

    public String getFromurl() {
        return fromurl;
    }

    public void setFromurl(String fromurl) {
        this.fromurl = fromurl;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public int getRcount() {
        return rcount;
    }

    public void setRcount(int rcount) {
        this.rcount = rcount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTopclass() {
        return topclass;
    }

    public void setTopclass(int topclass) {
        this.topclass = topclass;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(img);
        dest.writeString(description);
        dest.writeInt(rcount);
        dest.writeString(time);
        dest.writeLong(id);
        dest.writeString(title);
    }
}

