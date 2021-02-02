package com.ksu.nafea.logic.account;

import com.ksu.nafea.data.sql.Attribute;
import com.ksu.nafea.data.sql.EAttributeConstraint;
import com.ksu.nafea.data.sql.ESQLDataType;
import com.ksu.nafea.data.sql.EntityObject;
import com.ksu.nafea.logic.College;
import com.ksu.nafea.logic.Major;
import com.ksu.nafea.logic.University;

public class Student extends UserAccount<Student>
{
    private University university;
    private College college;
    private Major major;

    public Student()
    {
        super();
        university = null;
        college = null;
        major = null;
    }
    public Student(String email, String password, String firstName, String lastName, Integer majorID)
    {
        super(email, password, firstName, lastName);
        this.major = new Major(majorID, "");
    }


    @Override
    public String toString()
    {
        return "Student:\n" + super.toString() + "\n" +
                "[" + university.toString() + "]\n" +
                "[" + college.toString() + "]\n" +
                "[" + major.toString() + "]";
    }

    //-----------------------------------------------[UserAccount Override Methods]-----------------------------------------------

    @Override
    protected String getLoginSelectData()
    {
        return "student.s_email, student.password, student.first_name, student.last_name,\n" +
                " major.major_id, major.major_name, major.major_plan,\n" +
                " college.coll_id, college.coll_name, college.coll_category,\n" +
                " university.univ_id, university.univ_name, university.univ_city";
    }

    @Override
    protected String getLoginJoinData()
    {
        String joinData = EntityObject.createInnerJoinSection("student", "major", "major_id") + "\n ";
        joinData += EntityObject.createInnerJoinSection("major", "college", "coll_id") + "\n ";
        joinData += EntityObject.createInnerJoinSection("college", "university", "univ_id");

        return joinData;
    }

    @Override
    protected String getLoginCondition()
    {
        String email = Attribute.getSQLValue(getEmail(), ESQLDataType.STRING);
        String password = Attribute.getSQLValue(getPassword(), ESQLDataType.STRING);

        return "student.s_email = " + email + " AND student.password = " + password;
    }

    //-----------------------------------------------[Entity Override Methods]-----------------------------------------------
    @Override
    public EntityObject toEntity()
    {
        EntityObject entityObject = new EntityObject("student");

        entityObject.addAttribute("s_email", ESQLDataType.STRING, email, EAttributeConstraint.PRIMARY_KEY);
        entityObject.addAttribute("password", ESQLDataType.STRING, password);
        entityObject.addAttribute("first_name", ESQLDataType.STRING, firstName);
        entityObject.addAttribute("last_name", ESQLDataType.STRING, lastName);

        Integer majorID = major != null ? major.getId() : null;
        entityObject.addAttribute("major_id", ESQLDataType.INT, majorID, EAttributeConstraint.FOREIGN_KEY);

        return entityObject;
    }

    @Override
    public Student toObject(EntityObject entityObject) throws ClassCastException
    {
        Student student = new Student();

        student.email = entityObject.getAttributeValue("s_email", ESQLDataType.STRING, String.class);
        student.password = entityObject.getAttributeValue("password", ESQLDataType.STRING, String.class);
        student.firstName = entityObject.getAttributeValue("first_name", ESQLDataType.STRING, String.class);
        student.lastName = entityObject.getAttributeValue("last_name", ESQLDataType.STRING, String.class);

        Integer majorID = entityObject.getAttributeValue("major_id", ESQLDataType.INT, Integer.class);
        String majorName = entityObject.getAttributeValue("major_name", ESQLDataType.STRING, String.class);

        Integer collegeID = entityObject.getAttributeValue("coll_id", ESQLDataType.INT, Integer.class);
        String collegeName = entityObject.getAttributeValue("coll_name", ESQLDataType.STRING, String.class);
        String collegeCategory = entityObject.getAttributeValue("coll_category", ESQLDataType.STRING, String.class);

        Integer universityID = entityObject.getAttributeValue("univ_id", ESQLDataType.INT, Integer.class);
        String universityName = entityObject.getAttributeValue("univ_name", ESQLDataType.STRING, String.class);
        String universityCity = entityObject.getAttributeValue("univ_city", ESQLDataType.STRING, String.class);

        student.university = new University(universityID, universityName, universityCity);
        student.college = new College(collegeID, collegeName, collegeCategory);
        student.major = new Major(majorID, majorName);

        return student;
    }

    @Override
    public Class<Student> getEntityClass() {
        return Student.class;
    }


}
