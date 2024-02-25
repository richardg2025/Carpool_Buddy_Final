package carpool.buddy.classes;

import java.util.ArrayList;

import carpool.buddy.classes.User;

public class Alumni extends User {
    private String graduateYear;

    public void setGraduateYear(String graduateYear) {this.graduateYear = graduateYear;}

    public String getGraduateYear() {return graduateYear;}

    public Alumni(String id, String e, String n, String ut, String gy) {
        super(id, e, n, ut);
        graduateYear = gy;
    }
}
