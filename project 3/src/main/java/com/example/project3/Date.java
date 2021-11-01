package com.example.project3;

import java.util.Calendar;
import java.util.StringTokenizer;

/**
 * The class represents the payment dates as "MM/DD/YYYY"
 * @author Ryan Pollack, Michael Kang
 */
public class Date implements Comparable<Date> 
{

    private int day;
    private int month;
    private int year;

    private static final int QUADRENNIAL = 4;
    private static final int CENTENNIAL = 100;
    private static final int QUATERCENTENNIAL = 400;
    
    private static final int TWENTYONE = 2021;

    private static final int MIN_DAYS = 1;
    private static final int MAX_DAYS = 31;

    private static final int MIN_MONTH = 0;
    private static final int MAX_MONTH = 11;
    
    private static final int BEFORE_DATE = -1;
    public static final int SAME_DATE = 0;
    private static final int AFTER_DATE = 1;

    /**
     * Constructor that initializes the month, day, and year by the given date.
     * @param date- the format date of "MM/DD/YYYY".
     */
    public Date(String date) 
    {
        StringTokenizer st = new StringTokenizer(date, "/");
        
        month = Integer.parseInt(st.nextToken());
        day = Integer.parseInt(st.nextToken());
        year = Integer.parseInt(st.nextToken());
    }

    /**
     * Constructor that initializes the month, year, and day by today's date.
     */
    public Date()
    {
        Calendar currentDate = Calendar.getInstance();
        month = currentDate.get(Calendar.MONTH) + 1; 
        day = currentDate.get(Calendar.DAY_OF_MONTH);
        year = currentDate.get(Calendar.YEAR);
    }

    /**
     * Checks if the date is a valid Calender date.
     * It excludes any dates from the 1980s and before.
     * @return true if the date is valid, false otherwise.
     */
    public boolean isValid() 
    {
        int monthOffset = month - 1;
        
        if(monthOffset < MIN_MONTH || monthOffset > MAX_MONTH || day < MIN_DAYS || day > MAX_DAYS || year < TWENTYONE)
        {
            return false;
        }
        
        if(SAME_DATE >= this.compareTo(new Date()))
        {
            return false;
        }
        if(MAX_DAYS - 3 >= day)
        {
            return true;
        }
        if(MAX_DAYS - 2 == day)
        {
            if(monthOffset == Calendar.FEBRUARY)
            {
                if(year % QUADRENNIAL == 0)
                {
                    if(year % CENTENNIAL == 0)
                    {
                        if(year % QUATERCENTENNIAL == 0)
                        {
                            return true;
                        }
                        return false;
                    }
                    return true;
                }
                return false;
            }
            return true;
        }
        if(day == MAX_DAYS - 1 && monthOffset != Calendar.FEBRUARY + 1)
        {
            return true;
        }
        if(day == MAX_DAYS && (monthOffset == Calendar.JANUARY || monthOffset == Calendar.DECEMBER 
        		|| monthOffset == Calendar.MARCH || monthOffset == Calendar.MAY || monthOffset == Calendar.JULY
                || monthOffset == Calendar.AUGUST || monthOffset == Calendar.OCTOBER))
        {
        	return true;
        }

        return false;
    }

    /**
     * Compares the date to another date.
     * @param date- the date that is to be compared to
     * @return -1 if younger, 0 if equal, or 1 if older
     */
    @Override
    public int compareTo(Date date)
    {
        if(year > date.getYear())
        {
        	return BEFORE_DATE;
        }
        if(year == date.getYear()){
            if(month > date.getMonth()) {
            	return BEFORE_DATE;
            }           	
            if(month == date.getMonth())
            {
                if(day > date.getDay())
                {
                	return BEFORE_DATE;
                }
                if(day == date.getDay())
                {
                	return SAME_DATE;
                }
            }
        }
        return AFTER_DATE;
    }

    /**
     * Getter method for the value of month.
     * @return the month
     */
    public int getMonth(){
        return month;
    }
    
    /**
     * Getter method for the value of day.
     * @return the day.
     */
    public int getDay(){
        return day;
    }

    /**
     * Getter method for the value of year.
     * @return the year
     */
    public int getYear(){
        return year;
    }
    
    /**
     * Return string representation of date.
     * @return the format date of "MM/DD/YYYY".
     */
    @Override
    public String toString(){
        return month + "/" + day + "/" + year;
    }
}