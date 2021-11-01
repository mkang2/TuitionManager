/**
 * Controller class for GUI for tuition manager
 * @author Ryan Pollack, Michael Kang
 */
package com.example.project3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.InputMismatchException;

public class MainController {

    private final int MINIMUM_NUM_CREDITS = 3;
    private final int MAXIMUM_NUM_CREDITS = 24;
    private final int MINIMUM_FULLTIME_CREDITS = 12;

    @FXML
    private RadioButton ba;
    @FXML
    private RadioButton ba2;
    @FXML
    private RadioButton connecticut;
    @FXML
    private TextField credits;
    @FXML
    private RadioButton cs;
    @FXML
    private RadioButton cs2;
    @FXML
    private RadioButton ee;
    @FXML
    private RadioButton ee2;
    @FXML
    private TextField finAid;
    @FXML
    private RadioButton international;
    @FXML
    private RadioButton it;
    @FXML
    private RadioButton it2;
    @FXML
    private RadioButton me;
    @FXML
    private RadioButton me2;
    @FXML
    private TextField name;
    @FXML
    private TextField name2;
    @FXML
    private RadioButton new_york;
    @FXML
    private RadioButton non_resident;
    @FXML
    private TextArea output;
    @FXML
    private TextArea output2;
    @FXML
    private TextArea output3;
    @FXML
    private DatePicker paymentDate;
    @FXML
    private TextField payments;
    @FXML
    private RadioButton resident;
    @FXML
    private CheckBox studyAbroad;
    @FXML
    private RadioButton tristate;
    @FXML
    private TextField tuitionOutput;

    private Roster roster;

    /**
     * Main Controller Constructor
     */
    public MainController()
    {
        roster = new Roster();
    }

    /**
     * Enables study abroad options if international is selected
     * @param event - the event to be handled
     */
    @FXML
    void enableStudyAbroadOption(ActionEvent event) {
        new_york.setDisable(true);
        new_york.setSelected(false);

        connecticut.setDisable(true);
        connecticut.setSelected(false);

        studyAbroad.setDisable(false);
    }

    /**
     * Enables Tristate Options if tristate is selected
     * @param event - the event to be handled
     */
    @FXML
    void enableTristateOptions(ActionEvent event) {
        studyAbroad.setSelected(false);
        studyAbroad.setDisable(true);

        new_york.setDisable(false);
        connecticut.setDisable(false);
    }

    /**
     * disables all tristate and international options
     */
    void disableResidentButtons(){
        tristate.setDisable(true);
        tristate.setSelected(false);

        new_york.setDisable(true);
        new_york.setSelected(false);

        connecticut.setDisable(true);
        connecticut.setSelected(false);

        international.setDisable(true);
        international.setSelected(false);

        studyAbroad.setDisable(true);
        studyAbroad.setSelected(false);
    }

    /**
     * sets resident status buttons to default selection
     * disables tri-state and international options
     * @param event - the event to be handled
     */
    @FXML
    void setResidentStatus(ActionEvent event) {
        disableResidentButtons();

        if(non_resident.isSelected()){
            tristate.setDisable(false);
            international.setDisable(false);
        }
    }
    
    /**
     * Gets the selected major
     * @return - the major selected
     * @throws InputMismatchException if major has not been selected
     */
    private Major getMajor() throws InputMismatchException 
    {
        if(cs.isSelected())
        {
        	return Major.CS;
        }  
        else if(ee.isSelected()) 
        {
            return Major.EE;
        }
        else if(me.isSelected()) 
        {
            return Major.ME;
        }
        else if(it.isSelected()) 
        {
        	return Major.IT;
        }
        else if(ba.isSelected()) 
        {
            return Major.BA;
        }
        else {
            throw new InputMismatchException(); 
        }
    }

    /**
     * Builds the profile for a student
     * @return student profile
     */
    private Profile buildStudentProfile()
    {
    	
        // getting student name
        String studentName = name.getText();
        
        if(studentName.isEmpty())
        {
            output.appendText("Student name field is empty. \n");
            return null;
        }

        // getting student major
        try
        {
            Major major = getMajor();
            return new Profile(studentName, major);
        }
        catch (InputMismatchException majorNotSelected)
        {
            output.appendText("Student major has not been selected \n");
        }

        return null;
    }

    /**
     * Adds a resident student to the roster.
     * @param profile - the profile of the student to add
     */
    public void addResidentStudent(Profile profile)
    {
        int creditHours = MINIMUM_NUM_CREDITS;

        // validating credit hours
        try 
        {
            creditHours = Integer.parseInt(credits.getText());
        }catch(NumberFormatException badInput)
        {
            output.appendText("Invalid credit hours. \n");
            return;
        }

        if(creditHours < 0)
        {
            output.appendText("Credit hours can't be negative. \n");
            return;
        }
        else if(creditHours < MINIMUM_NUM_CREDITS)
        {
            output.appendText("Minimum credit hours is 3. \n");
            return;
        }
        else if(creditHours > MAXIMUM_NUM_CREDITS)
        { 
            output.appendText("Credit hours exceed the maximum 24. \n");
            return;
        }

        // adding student to roster
        Resident residentStudent = new Resident(name, major, creditHours);
        boolean studentAdded = roster.add(residentStudent);

        if(studentAdded)
        {
            output.appendText("Student added. \n");
        }else
        {
            output.appendText("Student is already in the roster. \n");
        }
    }

    /**
     * Adds a non-resident student to the roster
     * @param profile - the profile of the student to add
     */
    public void addNonResidentStudent(Profile profile){
        int creditHours = MINIMUM_NUM_CREDITS;

        try 
        {
            creditHours = Integer.parseInt(credits.getText());
        }
        catch(NumberFormatException badInput)
        {
            output.appendText("Invalid credit hours. \n");
            return;
        }

        if(creditHours < 0)
        { 
            output.appendText("Credit hours cannot be negative. \n");
            return;
        }
        else if(creditHours < MINIMUM_NUM_CREDITS)
        { 
            output.appendText("Minimum credit hours is 3. \n");
            return;
        }
        else if(creditHours > MAXIMUM_NUM_CREDITS)
        { 
            output.appendText("Credit hours exceed the maximum 24. \n");
            return;
        }

        // adding student to roster
        NonResident nonResidentStudent = new NonResident(name, major, creditHours);
        boolean studentAdded = roster.add(nonResidentStudent);

        if(studentAdded)
        {
            output.appendText("Student added. \n");
        }
        else
        {
            output.appendText("Student is already in the roster. \n");
        }
    }

    /**
     * Adds tristate student to roster
     * @param profile - the profile of the student to add
     */
    public void addTriStateStudent(Profile profile)
    {
        int creditHours = MINIMUM_NUM_CREDITS;
    
        try 
        {
            creditHours = Integer.parseInt(credits.getText());
        }
        catch(NumberFormatException badInput)
        {
            output.appendText("Invalid credit hours. \n");
            return;
        }

        if(creditHours < 0)
        {
            output.appendText("Credit hours cannot be negative. \n");
            return;
        }
        else if(creditHours < MINIMUM_NUM_CREDITS)
        { 
            output.appendText("Minimum credit hours is 3. \n");
            return;
        }
        else if(creditHours > MAXIMUM_NUM_CREDITS)
        { 
            output.appendText("Credit hours exceed the maximum 24. \n");
            return;
        }

        String stateCode = "";

        if(connecticut.isSelected())
        {
            stateCode = "CT";	
        }
        else if(new_york.isSelected())
        {
            stateCode = "NY";
        }
        else
        {
            output.appendText("State has not been selected. \n");
            return;
        }

        // adding student to roster
        TriState tristateStudent = new TriState(name, major, creditHours, stateCode);
        boolean studentAdded = roster.add(tristateStudent);

        if(studentAdded)
        {
            output.appendText("Student added. \n");
        }
        else
        {
            output.appendText("Student is already in the roster. \n");
        }
    }

    /**
     * Adds an international student to the roster
     * @param profile - the profile of the student to add
     */
    public void addInternationalStudent(Profile profile){
        int creditHours = MINIMUM_NUM_CREDITS;

        try 
        {
            creditHours = Integer.parseInt(credits.getText());
        }
        catch(NumberFormatException badInput)
        {
            output.appendText("Invalid credit hours. \n");
            return;
        }

        if(creditHours < 0)
        {
            output.appendText("Credit hours cannot be negative. \n");
            return;
        }
        else if(creditHours < MINIMUM_NUM_CREDITS)
        {
            output.appendText("Minimum credit hours is 3. \n");
            return;
        }
        else if(creditHours > MAXIMUM_NUM_CREDITS)
        { 
            output.appendText("Credit hours exceed the maximum 24. \n");
            return;
        }

        if(creditHours < MINIMUM_FULLTIME_CREDITS)
        { 
            output.appendText("International students must enroll at least 12 credits. \n");
            return;
        }

        // adding student to roster
        International internationalStudent = new International(name, major, creditHours, studyAbroad.isSelected());
        boolean studentAdded = roster.add(internationalStudent);

        if(studentAdded)
        {
            output.appendText("Student added. \n");
        }
        else
        {
            output.appendText("Student is already in the roster. \n");
        }
    }

    /**
     * Adds a student to the roster
     * @param event - the event to be handled
     */
    @FXML
    void addStudent(ActionEvent event)
    {
    	
        //build profile of a student
        Profile studentProfile = buildStudentProfile();
        
        if(studentProfile == null)
        { 
            return;
        }

        //finding what type of student it is and recongizing it as correct type (international, tri-state, etc)
        boolean isResidentStudent = resident.isSelected();
        boolean isNonResidentStudent = non_resident.isSelected() && !tristate.isSelected() && !international.isSelected();
        boolean isTriStateStudent = tristate.isSelected();
        boolean isIntlStudent = international.isSelected();

        if(isResidentStudent)
            addResidentStudent(studentProfile);
        else if(isNonResidentStudent)
            addNonResidentStudent(studentProfile);
        else if(isTriStateStudent)
            addTriStateStudent(studentProfile);
        else if(isIntlStudent)
            addInternationalStudent(studentProfile);
        else
            output.appendText("Student status has not been selected \n");
    }
    
    /**
     * Removes student from the roster
     * @param event - the event to be handled
     */
    @FXML
    void removeStudent(ActionEvent event) 
    {
        Profile studentProfile = buildStudentProfile();

        if(studentProfile == null)
        {
            return;
        }

        int dummyCredits = 0;
        Student studentToRemove = new Student(name, major, dummyCredits);
        boolean removed = roster.remove(studentToRemove);

        if(removed) 
        {
            output.appendText("Student removed from roster. \n");
        }
        else 
        {
            output.appendText("Student is not in the roster. \n");
    	}
    }

    /**
     * Calculates tuition dues for all students
     * @param event - the event to be handled
     */
    @FXML
    void calculateTuitionDues(ActionEvent event) 
    {
        roster.calculate();
        output3.appendText("Calculation completed. \n");
    }

    @FXML
    void pay(ActionEvent event) 
    {
    	
    }

    /**
     * prints the roster by student names
     * @param event - the event to be handled
     */
    @FXML
    void printByNames(ActionEvent event) 
    {
        roster.printByName(output3);
    }

    /**
     * prints the roster by payment dates
     * @param event - the event to be handled
     */
    @FXML
    void printByPaymentDates(ActionEvent event) 
    {
        roster.printByPaymentDate(output3);
    }

    /**
     * prints the roster
     * @param event - the event to be handled
     */
    @FXML
    void printRoster(ActionEvent event) 
    {
        roster.print(output3);
    }

    /**
     * prints the tuition due for a student
     * @param event - the event to be handled
     */
    @FXML
    void printTuitionDue(ActionEvent event) 
    {
        Profile studentProfile = buildStudentProfile();

        if(studentProfile == null){
            return;
        }

        int dummyCredits = 0;

        Student studentToCalculate = new Student(name, major, dummyCredits);
        double tuition = roster.calculate(studentToCalculate);

        if(tuition == -1.0){
            output.appendText("Student is not in the roster. \n");
        }else{
            tuitionOutput.clear();
            tuitionOutput.appendText(tuition + "");
        }
    }

    @FXML
    void setFinAid(ActionEvent event) 
    {

    }
}
