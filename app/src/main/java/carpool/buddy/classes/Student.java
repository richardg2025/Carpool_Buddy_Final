package carpool.buddy.classes;

import java.util.ArrayList;

public class Student extends User{
    private String graduatingYear;
    private ArrayList<String> parentUIDs;

    public void setGraduatingYear(String graduatingYear) {this.graduatingYear = graduatingYear;}
    public void setParentUIDs(ArrayList<String> parentUIDs) {this.parentUIDs = parentUIDs;}
    public void addParentUIDs(String parentUID) {parentUIDs.add(parentUID);}

    public String getGraduatingYear() {return graduatingYear;}
    public ArrayList<String> getParentUIDs() {return parentUIDs;}

    public Student(String id, String e, String n, String ut, String gy, ArrayList<String> pid) {
        super(id, e, n, ut);
        parentUIDs = pid;
        graduatingYear = gy;
    }
}
