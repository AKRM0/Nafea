package com.ksu.nafea.logic.course;

import com.ksu.nafea.data.request.QueryRequestFlag;
import com.ksu.nafea.data.sql.EAttributeConstraint;
import com.ksu.nafea.data.sql.ESQLDataType;
import com.ksu.nafea.data.sql.EntityObject;
import com.ksu.nafea.logic.Entity;

import java.util.ArrayList;

public class Comment extends Entity<Comment>
{
    public static final String TAG = "Comment";

    private String firstName, lastName;
    private String comment;
    private String time;


    public Comment()
    {
        firstName = "";
        lastName = "";
        comment = "";
        time = "";
    }

    @Override
    public String toString()
    {
        return "Comment{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", comment='" + comment + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    //-----------------------------------------------[Queries]-----------------------------------------------

    public static void retrieveAllComments(Course course, final QueryRequestFlag<ArrayList<Comment>> requestFlag)
    {
        String selectClause = "s.first_name, s.last_name, crs_comment ,comment_time";
        String joinSection = "RIGHT JOIN student as s ON s.s_email = comment_on_course.s_email";
        String condition = "crs_id = " + course.getId();
        String orderBy = "comment_time desc";

        try
        {
            getPool().retrieve(Comment.class, requestFlag, selectClause, joinSection, condition, "", orderBy);
        }
        catch (Exception e)
        {
            String msg = "Failed to retrieve comments in \"" + course.getName() + "\" course: " + e.getMessage();
            Entity.sendFailureResponse(requestFlag, TAG, msg);
        }
    }


    //-----------------------------------------------[Entity Override Methods]-----------------------------------------------

    @Override
    public EntityObject toEntity()
    {
        EntityObject entityObject = new EntityObject("comment_on_course");

        entityObject.addAttribute("first_name", ESQLDataType.STRING, firstName);
        entityObject.addAttribute("last_name", ESQLDataType.STRING, lastName);
        entityObject.addAttribute("crs_comment", ESQLDataType.STRING, comment);
        entityObject.addAttribute("comment_time", ESQLDataType.STRING, time);

        return entityObject;
    }

    @Override
    public Comment toObject(EntityObject entityObject) throws ClassCastException
    {
        Comment cmt = new Comment();

        cmt.firstName = entityObject.getAttributeValue("first_name", ESQLDataType.STRING, String.class);
        cmt.lastName = entityObject.getAttributeValue("last_name", ESQLDataType.STRING, String.class);
        cmt.comment = entityObject.getAttributeValue("crs_comment", ESQLDataType.STRING, String.class);
        cmt.time = entityObject.getAttributeValue("comment_time", ESQLDataType.STRING, String.class);

        return cmt;
    }

    @Override
    public Class<Comment> getEntityClass()
    {
        return Comment.class;
    }


    //--------------------------------------------------[Getters & Setters]--------------------------------------------------

    public String getFullName()
    {
        return firstName + " " + lastName;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getComment()
    {
        return comment;
    }

    public String getTime()
    {
        return time;
    }


}
