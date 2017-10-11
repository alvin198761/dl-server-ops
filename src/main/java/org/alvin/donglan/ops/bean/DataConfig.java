package org.alvin.donglan.ops.bean;

import java.util.List;

public class DataConfig {

    private String author;
    private List<ServerBean> serverList;


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<ServerBean> getServerList() {
        return serverList;
    }

    public void setServerList(List<ServerBean> serverList) {
        this.serverList = serverList;
    }
}
