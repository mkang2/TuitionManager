package com.example.project3;

/**
 * The is a subclass that represents a tri-state student
 * Subclass of nonresident
 * @author Ryan Pollack, Michael Kang
 */
public class TriState extends NonResident
{

    private String state;

    /**
     * Constructor where name, major, credits, and state is known.
     * @param name- student's name
     * @param major- student's major
     * @param credits- credit amount
     * @param state- the tri-state (NY or CT) the student is from
     */
    public TriState(Profile profile, int credits, String state)
    {
        super(profile, credits);
        this.state = state;
    }

    /**
     * Calculates tuition due for the tri-state student
     */
    @Override
    public void tuitionDue()
    {
        int credits = getCredits();
        int whatTime;
        int triStateDiscount = 0;
        double fee = 0;
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
        
        if(credits >= MIN_CREDITS_FULL_TIME)
        {
            if (state.equals("NY")) 
            {
            	triStateDiscount = 4000;
            }else 
            {
            	triStateDiscount = 5000;
            }
        }
        setTuition(whatTime + excess + fee - triStateDiscount);
    }

    /**
     * Makes a string representation of TriState
     * @return the string representation
     */
    @Override
    public String toString()
    {
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
                + Student.DECIMAL_FORMAT.format(getTotalPayment()) + ":last payment date: " 
                + date + ":non-resident(tri-state):" + state;
    }
}
