package carpool.buddy.classes;

import java.util.ArrayList;

public class Teacher extends User{
    private String inSchoolTitle;

    public void setInSchoolTitle(String inSchoolTitle) {this.inSchoolTitle = inSchoolTitle;}

    public String getInSchoolTitle() {return inSchoolTitle;}

    public Teacher(String id, String e, String n, String ut, String ist) {
        super(id, e, n, ut);
        inSchoolTitle = ist;
    }
}
