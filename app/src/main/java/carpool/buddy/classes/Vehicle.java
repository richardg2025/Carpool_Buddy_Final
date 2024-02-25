package carpool.buddy.classes;

import java.io.Serializable;
import java.util.ArrayList;

public class Vehicle implements Serializable {
    private String owner;
    private String ownerEmail;
    private String model;
    private int capacity;
    private int totalCapacity;
    private String vehicleID;
    private ArrayList<String> riderUID;
    private boolean open;
    private String vehicleType;
    private boolean electric;

    public void setOwner(String owner) {this.owner = owner;}
    public void setOwnerEmail(String ownerEmail) {this.ownerEmail = ownerEmail;}
    public void setModel(String model) {this.model = model;}
    public void setCapacity(int capacity) {this.capacity = capacity;}
    public void setTotalCapacity(int totalCapacity) {this.totalCapacity = totalCapacity;}
    public void setVehicleID(String vehicleID) {this.vehicleID = vehicleID;}
    public void setRiderUID(ArrayList<String> riderUID) {this.riderUID = riderUID;}
    public void setOpen(boolean open) {this.open = open;}
    public void setVehicleType(String vehicleType) {this.vehicleType = vehicleType;}
    public void setElectric(boolean electric) {this.electric = electric;}

    public String getOwner() {return owner;}
    public String getOwnerEmail() {return ownerEmail;}
    public String getModel() {return model;}
    public int getCapacity() {return capacity;}
    public int getTotalCapacity() {return totalCapacity;}
    public String getVehicleID() {return vehicleID;}
    public ArrayList<String> getRiderUID() {return riderUID;}
    public boolean getOpen() {return open;}
    public String getVehicleType() {return vehicleType;}
    public boolean getElectric() {return electric;}

//    public Vehicle(String ow, String m, int c, String vid, ArrayList<String> rid, boolean op, String vt, boolean e) {
//        owner = ow;
//        model = m;
//        capacity = c;
//        vehicleID = vid;
//        riderUID = rid;
//        open = op;
//        vehicleType = vt;
//        electric = e;
//    }

    public Vehicle(String ow, ArrayList<String> ruid, String oe, String m, int c, int tc, String vid, boolean op, String vt, boolean e) {
        owner = ow;
        riderUID = ruid;
        ownerEmail = oe;
        model = m;
        capacity = c;
        totalCapacity = tc;
        vehicleID = vid;
        open = op;
        vehicleType = vt;
        electric = e;
    }

    public Vehicle(){}
}
