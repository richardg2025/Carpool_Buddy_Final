package carpool.buddy.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Objects;

import carpool.buddy.R;
import carpool.buddy.classes.OpenVehiclesAdapter;
import carpool.buddy.classes.User;
import carpool.buddy.classes.Vehicle;

/**
 * The VehicleProfileActivity class represents the activity for displaying the profile of a vehicle.
 * It retrieves the vehicle and user information from Firebase and displays it on the screen.
 */
public class VehicleProfileActivity extends AppCompatActivity {

    private String userName;
    private String userType;
    private double userRating;
    private String userEmail;
    private String vehicleModel;
    private String vehicleType;
    private boolean vehicleElectric;
    private String vehicleID;
    private int vehicleCapacity;

    private FirebaseFirestore fireStore;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private String currentUserEmail;

    /**
     * Initializes the activity and sets up the necessary variables and UI elements.
     *
     * @param savedInstanceState The saved instance state bundle.
     */
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_profile);

        fireStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        TextView textViewVehicleModel = findViewById(R.id.vehicleModel);
        TextView textViewVehicleType = findViewById(R.id.vehicleType);
        TextView textViewVehicleElectric = findViewById(R.id.vehicleElectric);
        TextView textViewUserName = findViewById(R.id.userName);
        TextView textViewUserType = findViewById(R.id.userType);
        TextView textViewUserRating = findViewById(R.id.userRating);

        assert currentUser != null;
        currentUserEmail = currentUser.getEmail();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            vehicleModel = extras.getString("model");
            userName = extras.getString("owner");
            vehicleType = extras.getString("vehicleType");
            vehicleElectric = extras.getBoolean("electric");
            vehicleID = extras.getString("vid");
            vehicleCapacity = extras.getInt("capacity");
        }

        fireStore.collection("users").whereEqualTo("name", userName).get().addOnCompleteListener(task -> {
            for (DocumentSnapshot ds : task.getResult().getDocuments()) {
                userType = String.valueOf(ds.get("userType"));
                userRating = Double.parseDouble(String.valueOf(ds.get("rating")));
                userEmail = String.valueOf(ds.get("email"));

                textViewUserRating.setText("Rating: " + userRating);
                textViewUserType.setText(userType);
                textViewUserName.setText(userName);
                if (vehicleElectric) {
                    textViewVehicleElectric.setText("EV");
                } else {
                    textViewVehicleElectric.setText("");
                }
                textViewVehicleType.setText(vehicleType);
                textViewVehicleModel.setText(vehicleModel);
            }
        });
    }

    /**
     * Books the vehicle and navigates to the RateDriverActivity.
     *
     * @param v The book vehicle button view.
     */
    public void bookVehicle(View v) {
        if (vehicleCapacity < 1) {
            Toast.makeText(getApplicationContext(), "The vehicle is full, please find another vehicle", Toast.LENGTH_LONG).show();
            navBack(v);
        } else {
            fireStore.collection("vehicles").document(vehicleID).update("riderUID", FieldValue.arrayUnion(currentUserEmail));
            fireStore.collection("vehicles").document(vehicleID).update("capacity", vehicleCapacity - 1);
            navRateDriver(v);
        }
    }

    /**
     * Navigates to the OtherPassengersActivity.
     *
     * @param v The other passengers button view.
     */
    public void navOtherPassengers(View v) {
        Intent intent = new Intent(VehicleProfileActivity.this, OtherPassengersActivity.class);
        intent.putExtra("vehicleID", vehicleID);
        startActivity(intent);
    }

    /**
     * Navigates to the RateDriverActivity.
     *
     * @param v The rate driver button view.
     */
    public void navRateDriver(View v) {
        Intent intent = new Intent(this, RateDriverActivity.class);
        intent.putExtra("driverEmail", userEmail);
        intent.putExtra("driverName", userName);
        intent.putExtra("driverRating", userRating);
        startActivity(intent);
    }

    /**
     * Navigates back to the OpenVehiclesActivity.
     *
     * @param v The back button view.
     */
    public void navBack(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}