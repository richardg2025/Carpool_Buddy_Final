package carpool.buddy.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

import carpool.buddy.R;

/**
 * The RateDriverActivity class represents the activity for rating a driver.
 * It allows the user to rate the driver and submit the rating to Firebase.
 */
public class RateDriverActivity extends AppCompatActivity {

    private RatingBar ratingBar;
    private String driverEmail;
    private String driverName;
    private double driverRating;

    private FirebaseFirestore fireStore;

    /**
     * Initializes the activity and sets up the necessary variables and UI elements.
     *
     * @param savedInstanceState The saved instance state bundle.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_driver);

        ratingBar = findViewById(R.id.driverRatingBar);
        TextView name = findViewById(R.id.name);
        TextView email = findViewById(R.id.email);

        fireStore = FirebaseFirestore.getInstance();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            driverEmail = extras.getString("driverEmail");
            driverName = extras.getString("driverName");
            driverRating = extras.getDouble("driverRating");
        }

        name.setText(driverName);
        email.setText(driverEmail);
    }

    /**
     * Submits the driver rating to Firebase and navigates back to the main activity.
     *
     * @param v The submit button view.
     */
    public void submitRating(View v) {
        double getRating = ratingBar.getRating();
        fireStore.collection("users").document(driverEmail).update("rating", (driverRating + getRating) / 2);
        navMain(v);
    }

    /**
     * Navigates back to the main activity.
     *
     * @param v The navigation button view.
     */
    public void navMain(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}