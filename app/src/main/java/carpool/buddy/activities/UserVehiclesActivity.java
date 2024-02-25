package carpool.buddy.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Collections;

import carpool.buddy.R;
import carpool.buddy.classes.UserVehiclesAdapter;
import carpool.buddy.classes.Vehicle;

/**
 * The UserVehiclesActivity class represents the activity for displaying user vehicles.
 * It retrieves the vehicles owned by the current user from Firebase and displays them in a RecyclerView.
 */
public class UserVehiclesActivity extends AppCompatActivity {

    private ArrayList<Vehicle> vehicles;
    private UserVehiclesAdapter.RecyclerViewClickListener itemListener;
    private FirebaseFirestore fireStore;
    private String email;
    private RecyclerView recyclerView;

    /**
     * Initializes the activity and sets up the necessary variables and UI elements.
     *
     * @param savedInstanceState The saved instance state bundle.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_vehicles);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        fireStore = FirebaseFirestore.getInstance();

        recyclerView = findViewById(R.id.vehiclesRecyclerView);

        assert currentUser != null;
        email = currentUser.getEmail();

        getAndDisplayVehicles();
    }

    /**
     * Retrieves the vehicles owned by the current user from Firebase and displays them in a RecyclerView.
     */
    public void getAndDisplayVehicles() {
        fireStore.collection("vehicles").whereEqualTo("ownerEmail", email).get().addOnCompleteListener(task -> {
            vehicles = new ArrayList<>();

            for (QueryDocumentSnapshot ds : task.getResult()) {
                vehicles.add(ds.toObject(Vehicle.class));
                sendVehicleInfo();

                UserVehiclesAdapter myAdapter = new UserVehiclesAdapter(vehicles, itemListener);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(myAdapter);
            }
        });
    }

    /**
     * Sends the vehicle information to the DriverPassengersActivity when a vehicle item is clicked.
     */
    public void sendVehicleInfo() {
        itemListener = (v, position) -> {
            Intent intent = new Intent(this, DriverPassengersActivity.class);
            intent.putExtra("vehicleID", vehicles.get(position).getVehicleID());
            fireStore.collection("vehicles").document(vehicles.get(position).getVehicleID()).update("open", true);
            startActivity(intent);
        };
    }

    /**
     * Navigates to the AddVehicleActivity.
     *
     * @param v The add vehicle button view.
     */
    public void navAddVehicle(View v) {
        Intent intent = new Intent(this, AddVehicleActivity.class);
        startActivity(intent);
    }

    /**
     * Navigates back to the MainActivity.
     *
     * @param v The back button view.
     */
    public void navBack(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}