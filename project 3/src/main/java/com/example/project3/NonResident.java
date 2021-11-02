package com.example.project3;

/**
 * The subclass that represents a non-resident student and paying their tuition
 * A subclass of Student and a superclass of TriState and International
 * @author Ryan Pollack, Michael Kang
 */
public class NonResident extends Student
{
    public static final int CREDIT_HOURS = 966;
    public static final int FULL_TUITION = 29737;

    /**
     * Constructor where name, major, and credits is known.
     * @param name- student's name
     * @param major- student's major
     * @param credits- credit amount
     */
    public NonResident(Profile profile, int credits)
    {
        super(profile, credits);
    }

    /**
     * Calculates tuition due for nonresident student
     */
    @Override
    public void tuitionDue()
    {
        int credits = getCredits();
        int whatTime;
        double fee;
        int excess = 0;      
        
        //checks if credits are above alotted amount
        if(credits > EXCEED_CREDITS)
        {
        	excess = credits % EXCEED_CREDITS * CREDIT_HOURS;
        }else
        {
        	excess = 0;
        }
        
        //check if student is full or part time
        if(credits >= MIN_CREDITS_FULL_TIME)
        {
            whatTime = FULL_TUITION;
            fee = UNIVERSITY_FEE;
        }else
        {
            whatTime = credits * CREDIT_HOURS;
            fee = UNIVERSITY_FEE * PART_TIME_RATE;
        }
        
        setTuition(whatTime + excess + fee);
    }

    /**
     * Makes a string representation of a nonresident
     * @return the string representation
     */
    @Override
    public String toString(){
    	String date = "";
        
        if(getLastPaymentDate() == null) 
        {
        	date = "--/--/--";
        }else
        {
        	date = getLastPaymentDate().toString();
        }

        return getProfile().toString() + ":" + getCredits() +  " credit hours:tuition due:"
                + Student.DECIMAL_FORMAT.format(getTuition()) + ":total payment:" 
                + Student.DECIMAL_FORMAT.format(getTotalPayment()) + ":last payment date: " + date + ":non-resident";
    }
}
