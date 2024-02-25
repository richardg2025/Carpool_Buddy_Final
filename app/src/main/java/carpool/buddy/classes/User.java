package carpool.buddy.classes;

import java.util.ArrayList;

public class User {
    private String uid;
    private String name;
    private String email;
    private String userType;
    private ArrayList<String> ownedVehicles;
    private double rating;

    public void setUid(String uid) {this.uid = uid;}
    public void setName(String name) {this.name = name;}
    public void setEmail(String email) {this.email = email;}
    public void setUserType(String userType) {this.userType = userType;}
    public void setOwnedVehicles(ArrayList<String> ownedVehicles) {this.ownedVehicles = ownedVehicles;}
    public void addOwnedVehicles(String vehicle) {ownedVehicles.add(vehicle);}
    public void setRating(int rating) {this.rating = rating;}

    public String getUid() {return uid;}
    public String getName() {return name;}
    public String getEmail() {return email;}
    public String getUserType() {return userType;}
    public ArrayList<String> getOwnedVehicles() {return ownedVehicles;}
    public double getRating() {return rating;}

//    public User(String id, String n, String e, String ut, ArrayList<String> ov, int r) {
//        uid = id;
//        name = n;
//        email = e;
//        userType = ut;
//        ownedVehicles = ov;
//        rating = r;
//    }
//
//    public User(String n, String e, String ut) {
//        name = n;
//        email = e;
//        userType = ut;
//        rating = 5;
//        ownedVehicles = new ArrayList<String>();
//    }

    public User(String id, String e, String n, String ut) {
        uid = id;
        email = e;
        name = n;
        userType = ut;
        rating = 5;
        ownedVehicles = new ArrayList<>();
    }

    public User() {}
}
