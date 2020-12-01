package com.ksu.nafea.logic;

import com.ksu.nafea.ui.nviews.IconData;

public class Major implements IconData
{
    private Integer id,coll_id;
    private String name,plan;

    public Major(Integer id, String name, String plan, Integer coll_id) {
        this.id = id;
        this.coll_id = coll_id;
        this.name = name;
        this.plan = plan;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getColl_id() {
        return coll_id;
    }

    public void setColl_id(Integer coll_id) {
        this.coll_id = coll_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public Major(String name)
    {
        this.name = name;
    }


    public String getText()
    {
        return name;
    }
}
