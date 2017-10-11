package org.alvin.donglan.ops.bean;

import java.util.List;

public class ServerBean {

    private String ip;
    private Integer port;
    private String user;
    private String password;
    private String title;
    private List<AppBean> apps;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<AppBean> getApps() {
        return apps;
    }

    public void setApps(List<AppBean> apps) {
        this.apps = apps;
    }

    @Override
    public String toString() {
        return this.title + "(" + this.ip + ":" + this.port + ")";
    }

}
