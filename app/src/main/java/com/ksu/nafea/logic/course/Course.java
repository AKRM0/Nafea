package com.ksu.nafea.logic.course;

import com.ksu.nafea.data.request.QueryRequest;
import com.ksu.nafea.data.request.QueryRequestFlag;
import com.ksu.nafea.data.sql.Attribute;
import com.ksu.nafea.data.sql.EAttributeConstraint;
import com.ksu.nafea.data.sql.ESQLDataType;
import com.ksu.nafea.data.sql.EntityObject;
import com.ksu.nafea.logic.Entity;
import com.ksu.nafea.logic.Major;
import com.ksu.nafea.logic.QueryPostStatus;

import java.util.ArrayList;

public class Course extends Entity<Course>
{
    public final static String TAG = "Course";
    private Integer id;
    private String name,symbol,description;
    private CourseEvaluation evaluation;

    public Course()
    {
        this.id = 0;
        this.name = "None";
        this.symbol = "None";
        this.description = "None";
        this.evaluation = null;
    }
    public Course(Integer id, String name, String symbol, String description)
    {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
        this.description = description;
        this.evaluation = null;
    }



    public static EntityObject getContainEntity(Integer courseID, Integer majorID, String level)
    {
        EntityObject entityObject = new EntityObject("contain");

        entityObject.addAttribute("level", ESQLDataType.STRING, level);
        entityObject.addAttribute("major_id", ESQLDataType.INT, majorID, EAttributeConstraint.FOREIGN_KEY);
        entityObject.addAttribute("crs_id", ESQLDataType.INT, courseID, EAttributeConstraint.PRIMARY_KEY);

        return entityObject;
    }


    @Override
    public String toString()
    {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", symbol='" + symbol + '\'' +
                ", description='" + description + '\'' +
                ", evaluation=" + evaluation.toString() +
                '}';
    }

    //-----------------------------------------------[Queries]-----------------------------------------------
    public static void insert(Major major, String level, Course course, QueryRequestFlag<QueryPostStatus> requestFlag)
    {
        //Output: Return type
        QueryRequest<QueryPostStatus, QueryPostStatus> request = new QueryRequest<>(QueryPostStatus.class);
        request.setRequestFlag(requestFlag);


        //Input: Queries
        EntityObject courseEntity = course.toEntity();
        EntityObject containEntity = getContainEntity(0, major.getId(), level);
        Attribute coursePrimaryKey = courseEntity.getFirstAttribute(EAttributeConstraint.PRIMARY_KEY);

        request.addQuery(courseEntity.createInsertQuery(EAttributeConstraint.PRIMARY_KEY, "[0]"));
        request.addQuery(containEntity.createInsertQuery(EAttributeConstraint.PRIMARY_KEY, "[0]"));

        request.attachQuery(courseEntity.createSelectQuery("MAX(" + coursePrimaryKey.getName() + ") + 1 as result"));


        getPool().executeUpdateQuery(request);
    }

    public static void delete(Major major, String level, Course course, QueryRequestFlag<QueryPostStatus> requestFlag)
    {
        ////Output: Return type
        //QueryRequest<QueryPostStatus, QueryPostStatus> request = new QueryRequest<>(QueryPostStatus.class);
        //request.setRequestFlag(requestFlag);
//
//
        ////Input: Queries
        //EntityObject courseEntity = course.toEntity();
        //EntityObject containEntity = getContainEntity(0, majorID, level);
        //Attribute coursePrimaryKey = courseEntity.getFirstAttribute(EAttributeConstraint.PRIMARY_KEY);
//
        //request.addQuery(courseEntity.createInsertQuery(EAttributeConstraint.PRIMARY_KEY, "[0]"));
        //request.addQuery(containEntity.createInsertQuery(EAttributeConstraint.PRIMARY_KEY, "[0]"));
//
        //request.attachQuery(courseEntity.createSelectQuery("MAX(" + coursePrimaryKey.getName() + ") + 1 as result"));
//
//
        //getPool().executeUpdateQuery(request);
    }


    public static void retrieveCoursesInMajor(Major major, final QueryRequestFlag<ArrayList<Course>> requestFlag)
    {
        String selectClause = "course.crs_id, crs_name, crs_symbol, crs_desc,\n" +
                            "  avg(content_size) as content_size,\n" +
                            "  avg(assignments_difficulty) as assignments_difficulty,\n" +
                            "  avg(exams_difficulty) as exams_difficulty";
        String joinSection = "LEFT JOIN evaluate_course ON evaluate_course.crs_id = course.crs_id";
        String condition = "course.crs_id IN (SELECT crs_id FROM contain WHERE major_id = " + major.getId() + ")";
        String groupBy = "course.crs_id";
        String orderBy = "";

        try
        {
            getPool().retrieve(Course.class, requestFlag, selectClause, joinSection, condition, groupBy, orderBy);
        }
        catch (Exception e)
        {
            String msg = "Failed to retrieve courses in \"" + major.getName() + "\" major: " + e.getMessage();
            Entity.sendFailureResponse(requestFlag, TAG, msg);
        }
    }


    //-----------------------------------------------[Entity Override Methods]-----------------------------------------------
    @Override
    public EntityObject toEntity()
    {
        EntityObject entityObject = new EntityObject("course");

        entityObject.addAttribute("crs_id", ESQLDataType.INT, id, EAttributeConstraint.PRIMARY_KEY);
        entityObject.addAttribute("crs_name", ESQLDataType.STRING, name);
        entityObject.addAttribute("crs_symbol", ESQLDataType.STRING, symbol);
        entityObject.addAttribute("crs_desc", ESQLDataType.STRING, description);

        return entityObject;
    }

    @Override
    public Course toObject(EntityObject entityObject) throws ClassCastException
    {
        Course crs = new Course();

        crs.id = entityObject.getAttributeValue("crs_id", ESQLDataType.INT, Integer.class);
        crs.name = entityObject.getAttributeValue("crs_name", ESQLDataType.STRING, String.class);
        crs.symbol = entityObject.getAttributeValue("crs_symbol", ESQLDataType.STRING, String.class);
        crs.description = entityObject.getAttributeValue("crs_desc", ESQLDataType.STRING, String.class);
        Double contentSize = entityObject.getAttributeValue("content_size", ESQLDataType.DOUBLE, Double.class);
        Double assignmentsDifficulty = entityObject.getAttributeValue("assignments_difficulty", ESQLDataType.DOUBLE, Double.class);
        Double examsDifficulty = entityObject.getAttributeValue("exams_difficulty", ESQLDataType.DOUBLE, Double.class);

        crs.evaluation = new CourseEvaluation(contentSize, assignmentsDifficulty, examsDifficulty);

        return crs;
    }

    @Override
    public Class<Course> getEntityClass()
    {
        return Course.class;
    }


    //--------------------------------------------------[Getters & Setters]--------------------------------------------------
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getDescription() {
        return description;
    }

    public CourseEvaluation getEvaluation()
    {
        return evaluation;
    }


}
