package com.ksu.nafea.logic;

import com.ksu.nafea.logic.account.UserAccount;
import com.ksu.nafea.logic.course.Course;
import com.ksu.nafea.logic.material.Material;

public class User
{
    public static UserAccount userAccount = null;

    public static University university = null;
    public static College college = null;
    public static Major major = null;
    public static Course course = null;
    public static boolean isBrowsing = true;

    public static Material material;
}
