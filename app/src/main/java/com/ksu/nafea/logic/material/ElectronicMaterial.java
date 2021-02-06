package com.ksu.nafea.logic.material;

import com.ksu.nafea.data.request.QueryRequestFlag;
import com.ksu.nafea.data.sql.EAttributeConstraint;
import com.ksu.nafea.data.sql.ESQLDataType;
import com.ksu.nafea.data.sql.EntityObject;
import com.ksu.nafea.logic.course.Course;
import com.ksu.nafea.logic.Entity;

import java.util.ArrayList;

public class ElectronicMaterial extends Material<ElectronicMaterial>
{
    private String type, extension, url;

    public ElectronicMaterial()
    {
        super();
        type = "";
        extension = "";
        url = "";
    }
    public ElectronicMaterial(Integer id, String name, String type, String url, String extension)
    {
        super(id, name);
        this.type = type;
        this.url = url;
        this.extension = extension;
    }

    @Override
    public String toString()
    {
        return super.toString() + "ElectronicMaterial{" +
                "url='" + url + '\'' +
                ", type=" + type +
                ", extension='" + extension + '\'' +
                '}';
    }


    //-----------------------------------------------[Queries]-----------------------------------------------

    public static void retrieveAllEMatsInCourse(Course course, final QueryRequestFlag<ArrayList<ElectronicMaterial>> requestFlag)
    {
        String condition = "crs_id = " + course.getId();

        try
        {
            getPool().retrieve(ElectronicMaterial.class, requestFlag, "*", condition);
        }
        catch (Exception e)
        {
            String msg = "Failed to retrieve electronic materials in " + course.getName() + " course: " + e.getMessage();
            Entity.sendFailureResponse(requestFlag, TAG, msg);
        }
    }


    //-----------------------------------------------[Entity Override Methods]-----------------------------------------------

    @Override
    public EntityObject toEntity()
    {
        EntityObject entityObject = new EntityObject("e_material");

        entityObject.addAttribute("emat_type", ESQLDataType.STRING, type);
        entityObject.addAttribute("emat_url", ESQLDataType.STRING, url);
        entityObject.addAttribute("emat_name", ESQLDataType.STRING, name);
        entityObject.addAttribute("emat_id", ESQLDataType.INT, id, EAttributeConstraint.PRIMARY_KEY);
        entityObject.addAttribute("emat_extension", ESQLDataType.STRING, extension);

        return entityObject;
    }

    @Override
    public ElectronicMaterial toObject(EntityObject entityObject) throws ClassCastException
    {
        ElectronicMaterial material = new ElectronicMaterial();

        material.id = entityObject.getAttributeValue("emat_id", ESQLDataType.INT, Integer.class);
        material.name = entityObject.getAttributeValue("emat_name", ESQLDataType.STRING, String.class);
        material.type = entityObject.getAttributeValue("emat_type", ESQLDataType.STRING, String.class);
        material.url = entityObject.getAttributeValue("emat_url", ESQLDataType.STRING, String.class);
        material.extension = entityObject.getAttributeValue("emat_extension", ESQLDataType.STRING, String.class);

        return material;
    }

    @Override
    public Class<ElectronicMaterial> getEntityClass()
    {
        return ElectronicMaterial.class;
    }


    //-----------------------------------------------[Getters & Setters]-----------------------------------------------
    public String getType()
    {
        return type;
    }

    public String getExtension()
    {
        return extension;
    }

    public String getUrl()
    {
        return url;
    }


}
