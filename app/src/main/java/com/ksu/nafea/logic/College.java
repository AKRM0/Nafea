package com.ksu.nafea.logic;

import com.ksu.nafea.ui.nviews.IconData;

public class College implements IconData
{
    private Integer id,univ_id;
    private String name,category;

    public College(){}

    public College(Integer id, String name, String category, Integer univ_id) {
        this.id = id;
        this.univ_id = univ_id;
        this.name = name;
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUniv_id() {
        return univ_id;
    }

    public void setUniv_id(Integer univ_id) {
        this.univ_id = univ_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public College(String name)
    {
        this.name = name;
    }


    public String getText()
    {
        return name;
    }
}
