package com.ksu.nafea.logic.material;

import com.ksu.nafea.data.request.QueryRequestFlag;
import com.ksu.nafea.data.sql.EAttributeConstraint;
import com.ksu.nafea.data.sql.ESQLDataType;
import com.ksu.nafea.data.sql.EntityObject;
import com.ksu.nafea.logic.course.Course;
import com.ksu.nafea.logic.Entity;

import java.util.ArrayList;

public class PhysicalMaterial extends Material<PhysicalMaterial>
{
    private Integer sellerPhone;
    private String imageUrl, city;
    private Double price;

    public PhysicalMaterial()
    {
        super();
        sellerPhone = 0;
        imageUrl = "";
        city = "";
        price = 0.0;
    }
    public PhysicalMaterial(Integer id, String name, Integer sellerPhone, String imageUrl, String city, Double price)
    {
        super(id, name);
        this.sellerPhone = sellerPhone;
        this.imageUrl = imageUrl;
        this.city = city;
        this.price = price;
    }

    @Override
    public String toString()
    {
        return super.toString() + "PhysicalMaterial{" +
                "sellerPhone='" + sellerPhone + '\'' +
                ", image='" + imageUrl + '\'' +
                ", city='" + city + '\'' +
                ", price=" + price +
                '}';
    }


    //-----------------------------------------------[Queries]-----------------------------------------------

    public static void retrieveAllPMatsInCourse(Course course, final QueryRequestFlag<ArrayList<PhysicalMaterial>> requestFlag)
    {
        String condition = "crs_id = " + course.getId();

        try
        {
            getPool().retrieve(PhysicalMaterial.class, requestFlag, "*", condition);
        }
        catch (Exception e)
        {
            String msg = "Failed to retrieve physical materials in " + course.getName() + " course: " + e.getMessage();
            Entity.sendFailureResponse(requestFlag, TAG, msg);
        }
    }


    //-----------------------------------------------[Entity Override Methods]-----------------------------------------------

    @Override
    public EntityObject toEntity()
    {
        EntityObject entityObject = new EntityObject("physical_material");

        //entityObject.addAttribute("buyer_email", ESQLDataType.STRING, null);
        entityObject.addAttribute("pmat_name", ESQLDataType.STRING, name);
        entityObject.addAttribute("phone", ESQLDataType.INT, sellerPhone);
        entityObject.addAttribute("pmat_photo", ESQLDataType.STRING, imageUrl);
        entityObject.addAttribute("pmat_id", ESQLDataType.INT, id, EAttributeConstraint.PRIMARY_KEY);
        entityObject.addAttribute("pmat_city", ESQLDataType.STRING, city);
        entityObject.addAttribute("pmat_price", ESQLDataType.DOUBLE, price);
        //entityObject.addAttribute("payment_date", ESQLDataType.STRING, null);

        return entityObject;
    }

    @Override
    public PhysicalMaterial toObject(EntityObject entityObject) throws ClassCastException
    {
        PhysicalMaterial material = new PhysicalMaterial();

        material.id = entityObject.getAttributeValue("pmat_id", ESQLDataType.INT, Integer.class);
        material.name = entityObject.getAttributeValue("pmat_name", ESQLDataType.STRING, String.class);
        material.sellerPhone = entityObject.getAttributeValue("phone", ESQLDataType.INT, Integer.class);
        material.imageUrl = entityObject.getAttributeValue("pmat_photo", ESQLDataType.STRING, String.class);
        material.city = entityObject.getAttributeValue("pmat_city", ESQLDataType.STRING, String.class);
        material.price = entityObject.getAttributeValue("pmat_price", ESQLDataType.DOUBLE, Double.class);

        return material;
    }

    @Override
    public Class<PhysicalMaterial> getEntityClass()
    {
        return PhysicalMaterial.class;
    }


    //-----------------------------------------------[Getters & Setters]-----------------------------------------------

    public Integer getSellerPhone()
    {
        return sellerPhone;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public String getCity()
    {
        return city;
    }

    public Double getPrice()
    {
        return price;
    }


}
