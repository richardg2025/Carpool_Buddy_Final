package carpool.buddy.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import carpool.buddy.R;

/**
 * The UserProfileActivity class represents the activity for displaying user profile information.
 * It retrieves user information from Firebase and displays it on the screen.
 */
public class UserProfileActivity extends AppCompatActivity {

    private String uid;
    private String name;
    private String userType;
    private double rating;
    private TextView textViewName;
    private TextView textViewUserType;
    private TextView textViewUID;
    private RatingBar ratingBarRating;

    private FirebaseAuth mAuth;

    /**
     * Initializes the activity and sets up the necessary variables and UI elements.
     *
     * @param savedInstanceState The saved instance state bundle.
     */
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        textViewName = findViewById(R.id.name);
        textViewUserType = findViewById(R.id.userType);
        TextView textViewEmail = findViewById(R.id.email);
        textViewUID = findViewById(R.id.uid);
        ratingBarRating = findViewById(R.id.ratingDisplay);

        FirebaseFirestore fireStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        assert currentUser != null;
        String email = currentUser.getEmail();

        textViewEmail.setText(email);
        uid = "null";
        name = "null";
        userType = "null";
        rating = -1.0;

        fireStore.collection("users").whereEqualTo("email", email).get().addOnCompleteListener(task -> {
            for (DocumentSnapshot ds : task.getResult().getDocuments()) {
                if (ds.get("uid") == null)
                    uid = "null";
                else
                    uid = String.valueOf(ds.get("uid"));
                if (ds.get("userType") == null)
                    userType = "null";
                else
                    userType = String.valueOf(ds.get("userType"));
                if (ds.get("name") == null)
                    name = "null";
                else
                    name = String.valueOf(ds.get("name"));
                if (ds.get("rating") == null)
                    rating = -1.0;
                else
                    rating = ds.getDouble("rating");

                if (uid.equals("null"))
                    textViewUID.setText("missing");
                else
                    textViewUID.setText(uid);
                if (name.equals("null"))
                    textViewName.setText("missing");
                else
                    textViewName.setText(name);
                if (userType.equals("null"))
                    textViewUserType.setText("missing");
                else
                    textViewUserType.setText(userType);
                if (rating == -1.0)
                    ratingBarRating.setRating(0.0F);
                else
                    ratingBarRating.setRating((float) rating);
            }
        });
    }

    /**
     * Signs out the user and navigates to the authentication activity.
     *
     * @param v The sign out button view.
     */
    public void signOut(View v) {
        mAuth.signOut();
        navAuth(v);
    }

    /**
     * Navigates to the user vehicles activity.
     *
     * @param v The user vehicles button view.
     */
    public void navUserVehicles(View v) {
        Intent intent = new Intent(this, UserVehiclesActivity.class);
        startActivity(intent);
    }

    /**
     * Navigates to the authentication activity and finishes the current activity.
     *
     * @param v The authentication button view.
     */
    public void navAuth(View v) {
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
        finish();
    }
}