package com.ksu.nafea.logic;

import com.ksu.nafea.data.QueryResultFlag;
import com.ksu.nafea.data.pool.CollegePool;
import com.ksu.nafea.ui.nviews.IconData;

import java.util.ArrayList;

public class College implements IconData
{
    private Integer id,univ_id;
    private String name,category;
    private ArrayList<Major> majors;

    public College(){}

    public College(Integer id, String name, String category, Integer univ_id)
    {
        this.id = id;
        this.univ_id = univ_id;
        this.name = name;
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public Integer getIconID()
    {
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



    public static void retrieveCollegeOnCategory(Integer univ_id, String coll_category, final QueryResultFlag resultFlag)
    {
        CollegePool.retrieveOnCategory(univ_id, coll_category, new QueryResultFlag()
        {
            @Override
            public void onQuerySuccess(Object queryResult)
            {
                if(queryResult != null)
                {
                    ArrayList<College> colleges = (ArrayList<College>) queryResult;
                    if(colleges != null)
                    {
                        resultFlag.onQuerySuccess(colleges);
                        return;
                    }
                }

                resultFlag.onQuerySuccess(null);
            }

            @Override
            public void onQueryFailure(String failureMsg)
            {
                resultFlag.onQueryFailure(failureMsg + "/Retrieve College On Category");
            }
        });
    }

    public static void retrieveAllCategories(Integer univ_id, final QueryResultFlag resultFlag)
    {
        CollegePool.retrieveAllCategories(univ_id, new QueryResultFlag()
        {
            @Override
            public void onQuerySuccess(Object queryResult)
            {
                if(queryResult != null)
                {
                    ArrayList<College> colleges = (ArrayList<College>) queryResult;
                    if(colleges != null)
                    {
                        ArrayList<String> categories = new ArrayList<String>();
                        for(int i = 0; i < colleges.size(); i++)
                        {
                            College college = colleges.get(i);
                            categories.add(college.getCategory());
                        }

                        resultFlag.onQuerySuccess(categories);
                        return;
                    }
                }

                resultFlag.onQuerySuccess(null);
            }

            @Override
            public void onQueryFailure(String failureMsg)
            {
                resultFlag.onQueryFailure(failureMsg + "/Retrieve All Categories");
            }
        });
    }

}
