package com.example.project3;

/**
 * This class is the profile of a student which contains their name and major
 * @author Ryan Pollack, Michael Kang
 */
public class Profile
{
    private String name;
    private Major major;

    /**
     * Constructor where name and major are known.
     * @param name- the student's name
     * @param major- the student's major
     */
    public Profile(String name, Major major)
    {
        this.name = name;
        this.major = major;
    }

    /**
     * Runs a comparison between two Profiles by name and major and checks if they are equal
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj)
    {
        if(obj == this)
        {
            return true;
        }

        if(! (obj instanceof Profile))
        {
            return false;
        }

        Profile profile2 =  (Profile) obj;
        return name.equals(profile2.getName()) && major.equals(profile2.getMajor());
    }

    /**
     * Getter method for name
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Getter method for major
     * @return the major
     */
    public Major getMajor()
    {
        return major;
    }   
    
    /**
     * Makes a string representation of a student's profile
     * @return the string representation
     */
    @Override
    public String toString(){
        return name + ":" + major;
    }
}
