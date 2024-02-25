package carpool.buddy.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

import carpool.buddy.R;
import carpool.buddy.classes.OpenVehiclesAdapter;
import carpool.buddy.classes.Vehicle;

/**
 * The OpenVehiclesActivity class represents the activity that displays open vehicles.
 * It retrieves vehicle information from Firebase and displays it in a RecyclerView.
 */
public class OpenVehiclesActivity extends AppCompatActivity {

    private FirebaseFirestore fireStore;
    private ArrayList<Vehicle> vehicles;
    private OpenVehiclesAdapter.RecyclerViewClickListener itemListener;
    private RecyclerView recyclerView;

    /**
     * Initializes the activity and sets up the necessary variables and UI elements.
     *
     * @param savedInstanceState The saved instance state bundle.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_vehicles);

        fireStore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.openVehiclesRecyclerView);

        getAndDisplayVehicles();
    }

    /**
     * Retrieves the open vehicles from Firebase and displays them in the RecyclerView.
     */
    public void getAndDisplayVehicles() {
        fireStore.collection("vehicles").whereEqualTo("open", true).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                vehicles = new ArrayList<>();

                for (QueryDocumentSnapshot ds : task.getResult()) {
                    vehicles.add(ds.toObject(Vehicle.class));
                }

                sendVehicleInfo();

                OpenVehiclesAdapter myAdapter = new OpenVehiclesAdapter(vehicles, itemListener);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(myAdapter);
            }
        });
    }

    /**
     * Sends the selected vehicle information to the VehicleProfileActivity.
     */
    public void sendVehicleInfo() {
        itemListener = (v, position) -> {
            Intent intent = new Intent(this, VehicleProfileActivity.class);
            Vehicle selectedVehicle = vehicles.get(position);
            intent.putExtra("model", selectedVehicle.getModel());
            intent.putExtra("vehicleType", selectedVehicle.getVehicleType());
            intent.putExtra("electric", selectedVehicle.getElectric());
            intent.putExtra("owner", selectedVehicle.getOwner());
            intent.putExtra("vid", selectedVehicle.getVehicleID());
            intent.putExtra("capacity", selectedVehicle.getCapacity());
            startActivity(intent);
        };
    }
}