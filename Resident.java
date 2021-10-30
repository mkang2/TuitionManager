package projectTwo;

/**
 * This is a class that represents a resident student and deals with receiving financial aid
 * Subclass of Student
 * @author Ryan Pollack, Michael Kang
 */
public class Resident extends Student
{

    private double financialAid;
    private static final int CREDIT_HOURS = 404;
    private static final int FULL_TUITION = 12536;

    /**
     * Constructor where name, major, and credit amount is known
     * @param name- the name of student
     * @param major- the major of student
     * @param credits- the amount of credits
     */
    public Resident(String name, String major, int credits)
    {
        super(name, major, credits);
    }

    /**
     * Gives the financial aid of a student and properly updates the tuition
     * @param aidAmount- the amount of finanical aid given
     * @return true if aid was given, false if aid had already been given before
     */
    public boolean giveFinancialAid(double aidAmount)
    {
        if(financialAid != 0){
            return false;
        }
        financialAid = aidAmount;
        setTuition(getTuition() - financialAid);
        return true;
    }

    /**
     * Determines the tuition owed to a resident student
     */
    @Override
    public void tuitionDue()
    {
        int credits = getCredits();
        int excess = 0;
        double fee = 0;
        int whatTime = 0;
        
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
     * Makes a string representation of a resident
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

        double tuition = getTuition() < 0 ? 0 : getTuition();
        
        return getProfile().toString() + ":" + getCredits() +  " credit hours:tuition due:"
                + Student.DECIMAL_FORMAT.format(tuition) + ":total payment:"
                + Student.DECIMAL_FORMAT.format(getTotalPayment()) + ":last payment date: " + date + ":resident" 
                + ((financialAid == 0) ? "" : ":financial aid $" + Student.DECIMAL_FORMAT.format(financialAid));
    }
}