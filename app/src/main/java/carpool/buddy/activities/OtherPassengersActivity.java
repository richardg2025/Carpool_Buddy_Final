package carpool.buddy.activities;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import carpool.buddy.R;
import carpool.buddy.classes.User;
import carpool.buddy.classes.PassengersAdapter;

/**
 * The OtherPassengersActivity class displays the list of passengers for an open vehicle.
 */
public class OtherPassengersActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_other_passengers);

        fireStore = FirebaseFirestore.getInstance();

        recyclerView = findViewById(R.id.passengersRecyclerView);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            vehicleID = extras.getString("vehicleID");
        }

        getAndDisplayPassengers();
    }

    /**
     * Retrieves and displays the list of passengers for the open vehicle.
     */
    public void getAndDisplayPassengers() {
        DocumentReference vehicleRef = fireStore.collection("vehicles").document(vehicleID);
        vehicleRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot vehicleSnapshot = task.getResult();
                if (vehicleSnapshot != null && vehicleSnapshot.exists()) {
                    ArrayList<String> otherRiderUIDs = (ArrayList<String>) vehicleSnapshot.get("riderUID");

                    ArrayList<Task<DocumentSnapshot>> tasks = new ArrayList<>();
                    assert otherRiderUIDs != null;
                    for (String riderUID : otherRiderUIDs) {
                        try {
                            DocumentReference userRef = fireStore.collection("users").document(riderUID);
                            tasks.add(userRef.get());
                        } catch (IllegalArgumentException e) {
                            Log.d(TAG, "Invalid document reference: " + e.getMessage());
                        }
                    }

                    Task<List<Task<?>>> allTasks = Tasks.whenAllComplete(tasks);
                    allTasks.addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            List<Task<?>> completedTasks = task1.getResult();
                            ArrayList<User> passengers = new ArrayList<>();
                            for (Task<?> completedTask : completedTasks) {
                                if (completedTask.isSuccessful()) {
                                    DocumentSnapshot userSnapshot = (DocumentSnapshot) completedTask.getResult();
                                    if (userSnapshot != null && userSnapshot.exists()) {
                                        User passenger = userSnapshot.toObject(User.class);
                                        passengers.add(passenger);
                                    }
                                } else {
                                    Log.d(TAG, "Error getting document: " + completedTask.getException());
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
                    Log.d(TAG, "Vehicle document does not exist.");
                }
            } else {
                Log.d(TAG, "Error getting vehicle document: " + task.getException());
            }
        });
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