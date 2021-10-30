package projectTwo;

/**
 * This is the roster class, which is a growable array list data structure of students.
 * It includes methods that manipulate the array and its students. 
 * @author Ryan Pollack, Michael Kang
 */
public class Roster 
{
    private Student[] roster;
    private int size;
    private static final int NOT_FOUND = -1;
    private static final int HAS_SAME_NAME = 0;
    
    public static final int EMPTY = 0;

    /**
     * This is the roster constructor, which creates a new roster and sets its length to 4.
     */
    public Roster()
    {
        roster = new Student[4];
        size = 0;
    }

    /**
	 * This is the find method, which finds and returns the index of an album if it is in the array.
	 * If the student cannot be found, return NOT_FOUND
	 * @param a student
	 * @return the index of a student
     */
    private int find(Student student)
    {
        for(int i = 0; i < size; i++)
        {
            if(roster[i].getProfile().equals(student.getProfile()))
            {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Finds a student and then returns them
     * @param student- the target student
     * @return the target student, null if not found
     */
    public Student getsStudent(Student student)
    {
        int index = find(student);
        if(index != NOT_FOUND)
        {
            return roster[index];
        }
        return null;
    }

    /**
     * This is the grow method, which increases the size of the array by 4
     */
    private void grow()
    {
        Student[] temp = new Student[size+4];
        for(int i = 0; i < size; i++)
        {
            temp[i] = roster[i];
        }

        roster = temp;
    }

    /**
	 * This is the add method, which adds a student to the roster.
	 * @param student
	 * @return true if student can be added and false otherwise
	 */
    public boolean add(Student student)
    {
        if(find(student) != NOT_FOUND)
        {
            return false;
        }
        if(size + 1 == roster.length + 1)
        {
            grow();
        }
        roster[size] = student;
        size++;
        return true;
    }

	/**
	 * This is the remove method, which deletes a student in the roster
	 * @param student
	 * @return true if the student is deleted, false if student does not exist
	 */
    public boolean remove(Student student)
    {
        int index = find(student);
        
        if(index == NOT_FOUND)
        {
            return false;
        }
        for(int j = index; j < size-1; j++)
        {
            roster[j] = roster[j+1];
        }
        roster[size-1] = null;
        size--;
        
        return true;
    }

    /**
     * Calculates the tuition that is due for each student
     */
    public void calculate()
    {

        for(int i = 0; i < size; i++)
        {
            if(roster[i].getLastPaymentDate() == null && roster[i].getTuition() == 0)
            {
                roster[i].tuitionDue();
            }
        }

    }

	/**
	 * This is the set abroad status, which updates the studying abroad status of an international student
	 * @param student 
	 * @return true if set abroad status was done, false if student does not exist
	 */
    public boolean setAbroadStatus(Student student){

        int index = find(student);
        
        if(index == NOT_FOUND)
        {
            return false;
        }

        International internationalStudent = (International) student;
        internationalStudent.setStudyingAbroad();
        
        return true;
    }

	/**
	 * This is the print method, which prints the roster of students without specifying the order. 
	 */
    public void print(){

        for(int i = 0; i < size; i++){
            System.out.println(roster[i].toString());
        }
    }

	/**
	 * This is the print by name method, which prints a roster by student name in alphabetical order
	 */
    public void printByName(){

        mergeSort(roster, 0, size-1, 2);

        for(int i = 0; i < size; i++){
            System.out.println(roster[i].toString());
        }

    }

	/**
	 * This is the print by payment date method, which prints a roster by payment date.
	 */
    public void printByPaymentDate(){
        int count = 0;
        for(int i = 0; i < size; i++)
        {
            if (roster[i].getTotalPayment() > 0)
            {
                count++;
            }
        }

        Student[] paid = new Student[count];
        count = 0;
        for(int i = 0; i < size; i++)
        {
            if(roster[i].getTotalPayment() > 0)
            {
                paid[count] = roster[i];
                count++;
            }
        }

        mergeSort(paid, 0, paid.length - 1, 1);
        for (Student student : paid) 
        {
            System.out.println(student.toString());
        }
    }

	/**
	 * Merge Sort algorithm that sorts collections based on order.
	 * @param temp- roster to be sorted
	 * @param l- left index of the current sub-array
	 * @param r- right index of the current sub-array
	 * @param order- type of order for sorting, where 1 is for payment date and 2 is for name
	 */
    private void mergeSort(Student[] temp, int l, int r, int order){
        if(l >= r){
            return;
        }
        int m = l + (r-l)/2;


        mergeSort(temp, l, m, order);
        mergeSort(temp,m + 1, r, order);

        // 1 for Date, 2 for Name;
        if(order == 1){
            mergeDate(temp, l, m, r);
        }
        else{
            mergeName(temp, l, m, r);
        }
    }

	/**
	 * This is the merge name method, which sorts the students' names using merge sort algorithm.
	 * @param temp roster 
	 * @param left index 
	 * @param middle index 
	 * @param right index 
	 */
    private void mergeName(Student[] temp, int l, int m, int r){
        int sizeL = m - l + 1;
        int sizeR = r - m;

        Student[] L = new Student[sizeL];
        Student[] R = new Student[sizeR];
        for(int i = 0; i < sizeL; i++){
            L[i] = temp[l + i];
        }
        for(int i = 0; i < sizeR; i++){
            R[i] = temp[m + i + 1];
        }
        int leftP = 0;
        int rightP = 0;
        int current = l;

        while(leftP < sizeL && rightP < sizeR){
            int comp = L[leftP].getProfile().getName().compareTo(R[rightP].getProfile().getName());
            if(comp <= HAS_SAME_NAME){
                temp[current] = L[leftP];
                leftP++;
            }
            else{
                temp[current] = R[rightP];
                rightP++;
            }
            current++;
        }
        while(leftP < sizeL){
            temp[current] = L[leftP];
            leftP++;
            current++;
        }
        while(rightP < sizeR){
            temp[current] = R[rightP];
            rightP++;
            current++;
        }
    }

	/**
	 * This is the merge date method, which implements a merge sort algorithm. 
	 * @param temp array
	 * @param left index
	 * @param middle index
	 * @param right index 
	 */
    private void mergeDate(Student[] temp, int l, int m, int r){
        int sizeL = m - l + 1;
        int sizeR = r - m;

        Student[] L = new Student[sizeL];
        Student[] R = new Student[sizeR];
        for(int i = 0; i < sizeL; i++){
            L[i] = temp[l + i];
        }
        for(int i = 0; i < sizeR; i++){
            R[i] = temp[m + i + 1];
        }
        int leftP = 0;
        int rightP = 0;
        int current = l;

        while(leftP < sizeL && rightP < sizeR){
            int comp = L[leftP].getLastPaymentDate().compareTo(R[rightP].getLastPaymentDate());
            if(comp >= Date.SAME_DATE){
                temp[current] = L[leftP];
                leftP++;
            }
            else{
                temp[current] = R[rightP];
                rightP++;
            }
            current++;
        }
        while(leftP < sizeL){
            temp[current] = L[leftP];
            leftP++;
            current++;
        }
        while(rightP < sizeR){
            temp[current] = R[rightP];
            rightP++;
            current++;
        }
    }

    /**
	 * This is the get size method, which gets and returns the size of the roster.
     */
    public int getSize() 
    {
        return size;
    }
}