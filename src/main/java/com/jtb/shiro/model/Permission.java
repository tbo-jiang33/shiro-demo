package com.jtb.shiro.model;

/**
 * @auther: jtb
 * @date: 2019/5/12 16:53
 * @description:
 */
public class Permission {

    private Integer pid;
    private String pname;
    private String url;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
