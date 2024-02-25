package carpool.buddy.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import carpool.buddy.R;

/**
 * The MainActivity class represents the main screen of the application.
 * It displays a greeting message and provides navigation to other activities.
 */
public class MainActivity extends AppCompatActivity {

    private String name;
    private TextView greetingTextView;

    /**
     * Initializes the activity and sets up the necessary variables and UI elements.
     *
     * @param savedInstanceState The saved instance state bundle.
     */
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseFirestore fireStore = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        greetingTextView = findViewById(R.id.greetingTextView);

        assert currentUser != null;
        String email = currentUser.getEmail();

        fireStore.collection("users").whereEqualTo("email", email).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot ds : task.getResult().getDocuments()) {
                    name = String.valueOf(ds.get("name"));
                    greetingTextView.setText("Good Morning, " + name);
                }
            }
        });
    }

    /**
     * Navigates to the user profile activity.
     *
     * @param v The profile button view.
     */
    public void navProfile(View v) {
        Intent intent = new Intent(this, UserProfileActivity.class);
        startActivity(intent);
    }

    /**
     * Navigates to the open vehicles activity.
     *
     * @param v The passenger button view.
     */
    public void navPassenger(View v) {
        Intent intent = new Intent(this, OpenVehiclesActivity.class);
        startActivity(intent);
    }

    /**
     * Navigates to the user vehicles activity.
     *
     * @param v The driver button view.
     */
    public void navDriver(View v) {
        Intent intent = new Intent(this, UserVehiclesActivity.class);
        startActivity(intent);
    }
}