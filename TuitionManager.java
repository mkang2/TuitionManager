package projectTwo;

import java.util.Scanner;
import java.util.StringTokenizer;


/**
 * This is the main running class which takes valid inputs and performs the functions below, as given
 * by the instructions in project 2.
 * It takes in the command inputs of AR, AN, AT, AI for adding various students.
 * It also takes in the command inputs R, C, T, S, F, P, PN, PT, to perform advanced actions on the added students
 * 
 * Format for adding students is: Command, Name, Major, CreditHours, (and sometimes) TriState or International, in that order
 * Format for advanced commands is: Command, Name, Major, (1 or 2 more fields), in that order
 * 
 * 			--Add Commands--	
 * 				AR - Add resident student
 * 				AN - Add nonresident student
 * 				AT - Add tristate student (uses last input for NY or CT)
 * 				AI - Add international student (uses last input as boolean, true if they study abroad, otherwise false)
 * 			--Advanced Commands--
 * 				R  - Removes resident student from roster
 * 				T  - Pay tuition (uses last 2 inputs as payment, date)
 * 				S  - Sets study abroad status (uses last input as status setter)
 * 				F  - Sets amount of financial aid (uses last input as financial aid amount)
 * 			--Alternative Commands--
 * 				C  - Calculates the tuitions for all students on the roster
 * 				P  - prints roster
 * 				PN - prints roster sorted by student names in alphabetical order
 * 				PT - prints students who have made payments in the order of the payment dates
 * 
 * @author Ryan Pollack, Michael Kang
 */
public class TuitionManager
{
    private static final int EMPTY_VALUE = 0;
    private static final int RESIDENT = 1;
    private static final int NONRESIDENT = 2;
    private static final int TRISTATE = 3;
    private static final int INTERNATIONAL = 4;
    
    private static final int NAME_INDEX = 1;
    private static final int MAJOR_INDEX = 2;
    private static final int CREDIT_HOURS_INDEX = 3;
    private static final int STATE_INDEX = 4;
    private static final int ISABROAD_INDEX = 4;
    private static final int FOURTH_INDEX = 4;

    private static final double MAX_AID = 10000;

    private Roster roster = new Roster();

    public void run()
    {
        String currentString = null;
        boolean runAgain = true;
        Scanner scan = new Scanner(System.in);
    	
        System.out.println("Tuition Manager starts running.");

        while(runAgain){
            currentString = scan.nextLine();
            StringTokenizer token = new StringTokenizer(currentString, ",");
            String[] input = new String[token.countTokens()];
            
            int i = 0;
            while(token.hasMoreTokens())
            {
                input[i] = token.nextToken();
                i++;
            }
            
            if(input.length != 0){
                runAgain = checkCommand(input);
            }
        }
    }

    /** This checks whether the input is valid, and will call each method that is attributed to each instruction.
     * @param input is the commandInput array command (Command = 0, Name = 1, Major = 2, then varies)
     * @return returns false if instruction is "Q"
     */
    private boolean checkCommand(String[] commandInput)
    {
    	if(commandInput[0].equals("AR")){
    		if(!validStudent(commandInput, RESIDENT))
    		{
    			return true;
    		}
    		addResident(commandInput);
    	}
    	else if(commandInput[0].equals("AN"))
    	{
    		if(!validStudent(commandInput, NONRESIDENT))
    		{
    			return true;
    		}
    		addNonResident(commandInput);
    	}
    	else if(commandInput[0].equals("AT"))
    	{
    		if(!validStudent(commandInput, TRISTATE))
    		{
    			return true;
    		}
    		addTriState(commandInput);
    	}
    	else if(commandInput[0].equals("AI"))
    	{
    		if(!validStudent(commandInput, INTERNATIONAL))
    		{
    			return true;
    		}
    		addInternational(commandInput);
    	}
    	else if(commandInput[0].equals("R"))
    	{
    		removeStudent(commandInput);
    	}
    	else if(commandInput[0].equals("T"))
    	{
    		payTuition(commandInput);
    	}
    	else if(commandInput[0].equals("S"))
    	{
    		setAbroadStatus(commandInput);
    	}
    	else if(commandInput[0].equals("F"))
    	{
    		setFinancialAid(commandInput);
    	}
    	else if(commandInput[0].equals("C"))
    	{
            if(commandInput.length != 1){
                System.out.println("Invalid Command!");
            }
            calculate();
    	}
    	else if(commandInput[0].equals("P"))
    	{
    		printRoster(commandInput, 1);
    	}
    	else if(commandInput[0].equals("PN"))
    	{
    		printRoster(commandInput, 2);
    	}
    	else if(commandInput[0].equals("PT"))
    	{
    		printRoster(commandInput, 3);
    	}
    	else if(commandInput[0].equals("Q"))
    	{
    		System.out.println("Tuition Manager Terminated");
    		return false;
    	}
    	else
    	{
    		System.out.println("Command '" + commandInput[0] + "' not supported!");
    	}
    	
        return true;      
    }
	
    /**
     * Checks to see that the student is legitimate
     * @param input is the commandInput array for adding (Command = 0, Name = 1, Major = 2, Credit Hours = 3, ...)
     * @param type is the type of add Instruction
     * @return True if the input is correct
     */
    private boolean validStudent(String[] commandInput, int type)
    {
        boolean isInternational = (type == INTERNATIONAL);
        
        if(!tryIndex(commandInput, NAME_INDEX)){
            return false;
        }
        if(!tryIndex(commandInput, MAJOR_INDEX)){
        	return false;
        }
        if(!checkMajor(commandInput[2])){
            System.out.println("'" + commandInput[2] + "' is not a valid major.");
            return false;
        }

        if(!tryIndex(commandInput, CREDIT_HOURS_INDEX)){
            return false;
        }
        if(!isValidCreditNum(commandInput[3], isInternational)){
            return false;
        }
        
        switch(type){
            case RESIDENT:
                break;
            case NONRESIDENT:
                break;
            case TRISTATE:
                if(!tryIndex(commandInput, STATE_INDEX)){
                    return false;
                }
                if(!validTristate(commandInput[4])){
                    System.out.println("Not part of the tri-state area.");
                    return false;
                }
                break;
            case INTERNATIONAL:
                if(!tryIndex(commandInput, ISABROAD_INDEX))
                    return false;
                if(!(commandInput[4].toUpperCase().equals("TRUE") || commandInput[4].toUpperCase().equals("FALSE"))){
                    System.out.println("Not a boolean.");
                    return false;
                }
                break;
        }
        return true;
    }

    /**
     * Adds a resident student to the roster
     * @param input is the commandInput array for adding (Command = 0, Name = 1, Major = 2, Credit Hours = 3, ...)
     */
    private void addResident(String[] input){
        Resident resident = new Resident(input[1], input[2].toUpperCase(), Integer.parseInt(input[3]));
        addStudent(resident);
    }

    /**
     * Adds a nonresident student to the roster
     * @param input is the commandInput array for adding (Command = 0, Name = 1, Major = 2, Credit Hours = 3, ...)
     */
    private void addNonResident(String[] input){
        NonResident nonresident = new NonResident(input[1], input[2].toUpperCase(), Integer.parseInt(input[3]));
        addStudent(nonresident);
    }

    /**
     * Adds a TriState student to the roster
     * @param input is the commandInput array for adding (Command = 0, Name = 1, Major = 2, Credit Hours = 3, ...)
     */
    private void addTriState(String[] input){
        TriState tristate = new TriState(input[1], input[2].toUpperCase(), Integer.parseInt(input[3]), input[4].toUpperCase());
        addStudent(tristate);
    }

    /**
     * Adds an international student to the roster if the input is valid.
     * @param input is the commandInput array for adding (Command = 0, Name = 1, Major = 2, Credit Hours = 3, ...)
     */
    private void addInternational(String[] input){
        International international = new International(input[1], input[2].toUpperCase(), Integer.parseInt(input[3]), isAbroad(input[4]));
        addStudent(international);
    }

    /**
     * Adds the students if valid to the roster
     * @param input is the student object
     */
    private void addStudent(Student student){
        if(roster.add(student)){
            System.out.println("Student added.");
        }
        else {
            System.out.println("Student is already in the roster.");
        }
    }

    /**
     * Removes a student from the roster
     * @param input is the commandInput array command (Command = 0, Name = 1, Major = 2, then varies)
     */
    private void removeStudent(String[] input) {
        if(input.length != 3){
            System.out.println("Student is not in the roster.");
            return;
        }
        
        Student student2 = new Student(input[1], input[2].toUpperCase(), EMPTY_VALUE);
        
        if(roster.remove(student2)){
            System.out.println("Student removed from the roster.");
        }
        else{
            System.out.println("Student is not in the roster.");
        }

    }

    /**
     * Calculates total student tuition based on the roster
     */
    private void calculate() {
        roster.calculate();
        System.out.println("Calculation completed.");
    }

    /**
     * Pays the tuition of a specific student at a given amount
     * @param input is the commandInput array command (Command = 0, Name = 1, Major = 2, then varies)
     */
    private void payTuition(String[] input) {
        if(input.length <= 5){
            if(input.length == 3){
                System.out.println("Payment amount missing.");
                return;
            }
            if(input.length < 3){
                System.out.println("Invalid Command!");
            }
            Date date = null;
            if(input.length == 5){
                date = new Date(input[4]);
            }
            String name = input[1], major = input[2].toUpperCase();
            Double paymentAmount = Double.parseDouble(input[3]);
            Student temp = new Student(name, major);
            Student found = roster.getsStudent(temp);
            if(found == null) {
                System.out.println("Student not in the roster.");
                return;
            }
            if(paymentAmount > found.getTuition()){
                System.out.println("Amount is greater than amount due.");
                return;
            }
            if(paymentAmount <= EMPTY_VALUE){
                System.out.println("Invalid amount.");
                return;
            }
            if(!date.isValid()){
                System.out.println("Payment date invalid.");
                return;
            }
            found.makePayment(paymentAmount, date);
            System.out.println("Payment applied.");
        }
    }

    /**
     * Sets the study abroad status based on the input
     * @param input is the commandInput array command (Command = 0, Name = 1, Major = 2, then varies)
     */
    private void setAbroadStatus(String[] input) {
        if(input.length != 4){
            System.out.println("Invalid command!");
            return;
        }
        String name = input[1], major = input[2].toUpperCase();
        Student temp = new Student(name, major);
        Student found = roster.getsStudent(temp);
        if(found == null){
            System.out.println("Couldn't find the international student.");
            return;
        }
        ((International) found).setStudyingAbroad();
        System.out.println("Tuition updated.");

    }

    /**
     * Sets the financial aid based on the input
     * @param input is the commandInput array command (Command = 0, Name = 1, Major = 2, then varies)
     */
    private void setFinancialAid(String[] input) {
        if(input.length != 4){
            if(input.length == 3){
                System.out.println("Missing the amount.");
                return;
            }
            System.out.println("Invalid command!");
            return;
        }
        String name = input[1], major = input[2];
        Double financialAid = Double.parseDouble(input[3]);
        Student temp = new Student(name, major, EMPTY_VALUE);
        Student found = roster.getsStudent(temp);
        if(found == null){
            System.out.println("Student not in the roster.");
            return;
        }
        if(!isValidFinancialAid(financialAid)){
            return;
        }
        if(isPartTime(found)){
            return;
        }
        if(!(found instanceof Resident)){
            System.out.println("Not a resident student.");
            return;
        }
        if(!((Resident) found).giveFinancialAid(financialAid)){
            System.out.println("Awarded once already.");
            return;
        }
        System.out.println("Tuition updated.");
    }

    /**
     * Prints the roster given the type of print order
     * @param input the input array for the instruction
     * @param type 1 = student names, 2 = names in alphabetical order, 3 = students who paid in order of payment date
     */
    private void printRoster(String[] input, int type) {
        if(roster.getSize() == Roster.EMPTY){
            System.out.println("Student roster is empty!");
            return;
        }
        switch(type){
            case 1:
                System.out.println("* list of students in the roster **");
                roster.print();
                break;
            case 2:
                System.out.println("* list of students ordered by name **");
                roster.printByName();
                break;
            case 3:
                System.out.println("* list of students made payments ordered by payment date **");
                roster.printByPaymentDate();
                break;
            default:
                break;
        }
        System.out.println("* end of roster **\n");
    }

    /**
     * Checks if the credit amount is valid
     * @param creditString input is string that contains  credit amount to be checked
     * @param isInternational input is a boolean that tells if international rule is applied
     * @return
     */
    private boolean isValidCreditNum(String creditString, boolean isInternational){
        int credit = 0;
        try {
            credit = Integer.parseInt(creditString);
        }
        catch (Exception e){
            System.out.println("Invalid credit hours.");
            return false;
        };
        if(credit > Student.MAX_CREDITS){
            System.out.println("Credit hours exceed the maximum 24.");
            return false;
        }
        else if (credit < 0) {
            System.out.println("Credit hours cannot be negative.");
            return false;
        }
        else if(credit < Student.MIN_CREDITS){
            System.out.println("Minimum credit hours is 3.");
            return false;
        }
        else if(credit < Student.MIN_CREDITS_FULL_TIME && isInternational){
            System.out.println("International students must enroll at least 12 credits.");
            return false;
        }
        return true;
    }

    /**
     * Checks if the amount of financial aid is valid
     * @param input is the amount of financial aid to check
     * @return true if the amount is valid
     */
    private boolean isValidFinancialAid(double financialAid){
        if(financialAid <= 0 || financialAid > MAX_AID){
            System.out.println("Invalid amount.");
            return false;
        }
        return true;
    }

    /**
     * Checks if the student is part-time
     * @param input is the student object
     * @return returns true if the student is part-time
     */
    private boolean isPartTime(Student student){
        if(student.getCredits() < Student.MIN_CREDITS_FULL_TIME){
            System.out.println("Parttime student doesn't qualify for the award.");
            return true;
        }
        return false;
    }


    /**
     * Try catch based on current index
     * @param input the input array for the instruction
     * @param index index that we are checking
     * @return true if not out of bounds
     */
    private boolean tryIndex(String[] input, int index){
        try{
            String test = input[index];
        }
        catch (ArrayIndexOutOfBoundsException e){
            if(index == NAME_INDEX || index == MAJOR_INDEX || index == FOURTH_INDEX){
                System.out.println("Missing data in command line.");
            }
            else if(index == CREDIT_HOURS_INDEX){
                System.out.println("Credit hours missing.");
            }
            return false;
        }
        return true;
    }

    /**
     * Checks if the major is a valid major
     * @param input is the major the student has
     * @return true is the major is valid
     */
    private boolean checkMajor(String major){
        if(major.toUpperCase().equals("CS") || major.toUpperCase().equals("IT") || major.toUpperCase().equals("BA") ||
                major.toUpperCase().equals("EE") || major.toUpperCase().equals("ME"))
            return true;
        return false;
    }

    /**
     * Checks if TriState is valid
     * @param sinput is the TriState student is from
     * @return true if the TriState is valid
     */
    private boolean validTristate(String state){
        if(state.toUpperCase().equals("NY") || state.toUpperCase().equals("CT"))
            return true;
        return false;
    }

    /**
     * Checks if student is abroad
     * @param input is the string value of abroad
     * @return true if "TRUE"
     */
    private boolean isAbroad(String Abroad){
        if(Abroad.toUpperCase().equals("TRUE")){
            return true;
        }
        return false;
    }
}