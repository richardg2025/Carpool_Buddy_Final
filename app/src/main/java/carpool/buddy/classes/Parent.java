package carpool.buddy.classes;

import java.util.ArrayList;

public class Parent extends User{
    private ArrayList<String> childrenUIDs;

    public void setChildrenUIDs(ArrayList<String> childrenUIDs) {this.childrenUIDs = childrenUIDs;}
    public void addChildrenUIDs(String childrenUID) {childrenUIDs.add(childrenUID);}

    public ArrayList<String> getChildrenUIDs() {return childrenUIDs;}

    public Parent(String id, String e, String n, String ut, ArrayList<String> cid) {
        super(id, e, n, ut);
        childrenUIDs = cid;
    }
}
