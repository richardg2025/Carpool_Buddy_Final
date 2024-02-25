package carpool.buddy.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import carpool.buddy.R;
import carpool.buddy.classes.User;
import carpool.buddy.classes.Vehicle;

/**
 * The AddVehicleActivity class handles the process of adding a new vehicle to Firestore.
 * It allows users to input the vehicle details such as model, capacity, and type,
 * and saves the vehicle information to the Firestore database.
 */
public class AddVehicleActivity extends AppCompatActivity {

    // Declare variables
    private FirebaseFirestore fireStore;
    private EditText modelInput;
    private EditText capacityInput;
    private CheckBox isSUV;
    private CheckBox isSedan;
    private CheckBox isMinivan;
    private CheckBox isEV;

    private String userName;
    private String userEmail;

    /**
     * Initializes the activity and sets up the necessary variables and UI elements.
     *
     * @param savedInstanceState The saved instance state bundle.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);

        // Declare and initialize variables
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();


        modelInput = findViewById(R.id.editTextModel);
        capacityInput = findViewById(R.id.editTextCapacity);
        isMinivan = findViewById(R.id.checkBoxMinivan);
        isSUV = findViewById(R.id.checkBoxSUV);
        isSedan = findViewById(R.id.checkBoxSedan);
        isEV = findViewById(R.id.checkBoxEV);

        // Get current user
        assert currentUser != null;
        userEmail = currentUser.getEmail();

        // Get current user's name
        fireStore.collection("users").whereEqualTo("email", userEmail).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot ds : task.getResult().getDocuments()) {
                    // Get user name
                    userName = String.valueOf(ds.get("name"));
                }
            } else {
                // Handle the error
                Log.e("ADD VEHICLE", "Failed to retrieve user name", task.getException());
            }
        });
    }

    /**
     * Handles the add vehicle button click event.
     * Creates a new vehicle object with the provided details,
     * and saves the vehicle information to the Firestore database.
     *
     * @param v The add vehicle button view.
     */
    public void addVehicle(View v) {

        // New intent
        Intent intent = new Intent(this, UserVehiclesActivity.class);

        // User input
        String model = modelInput.getText().toString();
        String capacityString = capacityInput.getText().toString();
        int capacity = Integer.parseInt(capacityString);
        int totalCapacity = Integer.parseInt(capacityString);
        boolean electric = isEV.isChecked();
        String vehicleType;
        if (isMinivan.isChecked()) {
            vehicleType = "Minivan";
        } else if (isSUV.isChecked()) {
            vehicleType = "SUV";
        } else if (isSedan.isChecked()) {
            vehicleType = "Sedan";
        } else {
            vehicleType = "";
        }

        Random rand = new Random();
        String vehicleID = String.valueOf(rand.nextInt(99999));

        ArrayList<String> riderUIDs = new ArrayList<>();
        riderUIDs.add("");

        // Create new vehicle object
        Vehicle newVehicle = new Vehicle(userName, riderUIDs, userEmail, model, capacity, totalCapacity, vehicleID, false, vehicleType, electric);

        // Save vehicle in Firestore and navigate back
        fireStore.collection("/vehicles").document(vehicleID).set(newVehicle).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Vehicle saved successfully
                startActivity(intent);
                finish();
            } else {
                // Failed to save vehicle, handle the error
                Log.e("ADD VEHICLE", "Failed to save vehicle", task.getException());
                Toast.makeText(AddVehicleActivity.this, "Failed to add vehicle", Toast.LENGTH_SHORT).show();
            }
        });
    }
}