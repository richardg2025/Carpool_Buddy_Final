package carpool.buddy.activities;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import carpool.buddy.R;
import carpool.buddy.classes.User;
import carpool.buddy.classes.PassengersAdapter;

/**
 * The DriverPassengersActivity class displays the list of passengers for a driver's vehicle.
 * It allows the driver to start the ride and navigate to other activities.
 */
public class DriverPassengersActivity extends AppCompatActivity {

    private String vehicleID;
    private FirebaseFirestore fireStore;
    private RecyclerView recyclerView;

    /**
     * Initializes the activity and sets up the necessary variables and UI elements.
     *
     * @param savedInstanceState The saved instance state bundle.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_passengers);

        fireStore = FirebaseFirestore.getInstance();

        recyclerView = findViewById(R.id.passengersRecyclerView);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            vehicleID = extras.getString("vehicleID");
        }

        getAndDisplayPassengers();
    }

    /**
     * Retrieves and displays the list of passengers for the driver's vehicle.
     */
    public void getAndDisplayPassengers() {
        DocumentReference dr1 = fireStore.collection("vehicles").document(vehicleID);
        dr1.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot ds1 = task.getResult();
                if (ds1 != null && ds1.exists()) {
                    ArrayList<String> otherRiderUIDs = (ArrayList<String>) ds1.get("riderUID");

                    ArrayList<Task<DocumentSnapshot>> tasks = new ArrayList<>();
                    assert otherRiderUIDs != null;
                    for (String pointerValue : otherRiderUIDs) {
                        try {
                            DocumentReference documentRef = fireStore.collection("users").document(pointerValue);
                            tasks.add(documentRef.get());
                        } catch (IllegalArgumentException e) {
                            Log.d(TAG, "Invalid document reference: " + e.getMessage());
                        }
                    }

                    Task<List<DocumentSnapshot>> allTasks = Tasks.whenAllSuccess(tasks);
                    allTasks.addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            List<DocumentSnapshot> documentSnapshots = task1.getResult();
                            ArrayList<User> passengers = new ArrayList<>();
                            for (DocumentSnapshot document : documentSnapshots) {
                                if (document != null && document.exists()) {
                                    User obj = document.toObject(User.class);
                                    passengers.add(obj);
                                }
                            }
                            PassengersAdapter myAdapter = new PassengersAdapter(passengers);
                            recyclerView.setLayoutManager(new LinearLayoutManager(this));
                            recyclerView.setAdapter(myAdapter);
                            Log.d(TAG, "Objects: " + passengers);
                        } else {
                            Log.d(TAG, "Error getting documents: " + task1.getException());
                        }
                    });
                } else {
                    Log.d(TAG, "First document does not exist.");
                }
            } else {
                Log.d(TAG, "Error getting first document: " + task.getException());
            }
        });
    }

    /**
     * Starts the ride by updating the vehicle's capacity and open status.
     *
     * @param v The start ride button view.
     */
    @SuppressLint("SetTextI18n")
    public void startRide(View v) {
        fireStore.collection("vehicles").document(vehicleID).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    int totalCapacity = Objects.requireNonNull(document.getLong("totalCapacity")).intValue();
                    int currentCapacity = Objects.requireNonNull(document.getLong("capacity")).intValue();

                    fireStore.collection("vehicles").document(vehicleID).update("capacity", totalCapacity);

                }
            }
        });

        fireStore.collection("vehicles").document(vehicleID).update("open", false);
        fireStore.collection("vehicles").document(vehicleID).update("riderUID", null);
        fireStore.collection("vehicles").document(vehicleID).update("riderUID", FieldValue.arrayUnion(""));

        navMain(v);
    }

    /**
     * Navigates to the main activity.
     *
     * @param v The main button view.
     */
    public void navMain(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Refreshes the current activity.
     *
     * @param v The refresh button view.
     */
    public void navRefresh(View v) {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}