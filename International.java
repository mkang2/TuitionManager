package projectTwo;

/**
 * The subclass that represents an international student and dealing with them studying abroad
 * A subclass of NonResident
 * @author Ryan Pollack, Michael Kang
 */
public class International extends NonResident{

    private boolean isStudyingAbroad;
    private static final int ADDED_FEE = 2650;
    private static final int MIN_CREDITS = 12;

    /**
     * Constructor where name, major, credits, and studying abroad status is known
     * @param name- student's name
     * @param major- student's major
     * @param credits- credit amount
     * @param abroad- whether or not they are studying abroad
     */
    public International(String name, String major, int credits, boolean abroad)
    {
        super(name, major, credits);
        isStudyingAbroad = abroad;
    }

    /**
     * Updates international student to say they are now studying abroad
     * Sets the tuition amount to 0 and credits amount to 12 and then recalculates the tuition
     */
    public void setStudyingAbroad()
    {
        if(getCredits() > MIN_CREDITS)
        {
            setCredits(MIN_CREDITS);
        }
        
        setDate(null);
        setTotalPayment(0);
        setTuition(0);        
        isStudyingAbroad = true;
        
        tuitionDue();
    }

    /**
     * Calculates the tuition owed
     */
    @Override
    public void tuitionDue()
    {
    	int credits = getCredits();
    	int excess = 0;
    	
    	//checks if credits are above alotted amount
        if(credits > EXCEED_CREDITS)
        {
        	excess = credits % EXCEED_CREDITS * CREDIT_HOURS;
        }else
        {
        	excess = 0;
        }
        
        if(isStudyingAbroad)
        {
            setTuition(UNIVERSITY_FEE + ADDED_FEE);
        }
        else
        {      
            setTuition(FULL_TUITION + excess + UNIVERSITY_FEE + ADDED_FEE);
        }
    }

    /**
     * Makes a string representation of an International student
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
                + Student.DECIMAL_FORMAT.format(getTuition())  + ":total payment:" + Student.DECIMAL_FORMAT.format(getTotalPayment())
                + ":last payment date: " + date + ":non-resident:international" + (isStudyingAbroad ? ":study abroad" : "");
    }
}