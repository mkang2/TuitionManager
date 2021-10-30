package projectTwo;

import java.text.DecimalFormat;

/**
 * This is the superclass that represents a Student object
 * Has two subclasses that are Resident and NonResident
 * @author Ryan Pollack, Michael Kang
 */
public class Student
{
    private Profile profile;

    private int credits;
    private double tuition;
    private double totalPayment;
    private Date lastPaymentDate;

    public static final int MIN_CREDITS_FULL_TIME = 12;
    public static final int MIN_CREDITS = 3;
    public static final int MAX_CREDITS = 24;
    public static final int EXCEED_CREDITS = 16;
    public static final int UNIVERSITY_FEE = 3268;
    public static final double PART_TIME_RATE = 0.8;

    private static final String FORMAT = "##,##0.00";
    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat(FORMAT);
    
    /**
     * Constructor where name, major, and credit amount is known.
     * Sets tuition to zero by default.
     * @param name- name of student
     * @param major- major of student
     * @param credits- number of credits student is taking
     */
    public Student(String name, String major, int credits)
    {
        profile = new Profile(name, major);
        this.credits = credits;
        tuition = 0;
    }

    /**
     * Constructor where name and major is known.
     * @param name- name of student
     * @param major- major of student
     */
    public Student(String name, String major)
    {
        profile = new Profile(name, major);
    }

    /**
     * Getter method for profile of student
     * @return profile
     */
    public Profile getProfile()
    {
        return profile;
    }

    /**
     * Getter method for credits
     * @return amount of credits
     */
    public int getCredits()
    {
        return credits;
    }

    /**
     * Getter method for tuition
     * @return tuition
     */
    public double getTuition()
    {
        return tuition;
    }

    /**
     * Getter for date of last payment
     * @return the last payment date
     */
    public Date getLastPaymentDate()
    {
        return lastPaymentDate;
    }

    /**
     * Getter method for total payment
     * @return total payment
     */
    public double getTotalPayment()
    {
        return totalPayment;
    }

    /**
     * Setter method for tuition
     * @param the new tuition value
     */
    public void setTuition(double tuition)
    {
        this.tuition = tuition;
    }

    /**
     * Setter method for credits
     * @param the new amount of credits
     */
    public void setCredits(int credits)
    {
        this.credits = credits;
    }

    /**
     * Setter method for the date of the latest payment
     * @param the new last payment date
     */
    public void setDate(Date date)
    {
        lastPaymentDate = date;
    }

    /**
     * Setter method for total payment
     * @param the new amount
     */
    public void setTotalPayment(double amount)
    {
        totalPayment = amount;
    }
    
    /**
     * Method that subclasses will override
     */
    public void tuitionDue()
    {
    	//overridden
    }

    /**
     * Makes a payment towards the tuition that is due
     * @param amount- payment amount
     * @param date- the date of the payment
     */
    public void makePayment(double amountPaid, Date date)
    {
        tuition = tuition - amountPaid;
        totalPayment += amountPaid;
        lastPaymentDate = date;
    }

    /**
     * Makes a string representation of a Student object
     * @return the string representation
     */
    @Override
    public String toString()
    {
        String date = "";
        
        if(lastPaymentDate == null) 
        {
        	date = "--/--/--";
        }else
        {
        	date = lastPaymentDate.toString();
        }
        return profile.toString() + ":" + credits +  " credit hours:tuition due:" + tuition
                + ":total payment:" + totalPayment + ":last payment date: " + date;       
    }
}