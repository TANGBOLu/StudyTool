package com.example.tang.studytool.db;

import java.io.Serializable;

/**
 * Created by Tang on 2017/1/6.
 */

public class PlanItem implements Serializable {
    private int id;
    private String title;
    private String context;
    private int iscomp;

    public int getId() {
        return id;
    }

    public int getIsComp() {
        return iscomp;
    }

    public String getContext() {
        return context;
    }

    public String getTitle() {
        return title;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIsComp(int isComp) {
        this.iscomp = isComp;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
