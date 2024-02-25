package carpool.buddy.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import carpool.buddy.R;

/**
 * The AuthActivity class handles the authentication process.
 * It checks if the user is already signed in and navigates to the appropriate activity.
 */
public class AuthActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    /**
     * Initializes the activity and sets up the necessary variables and UI elements.
     *
     * @param savedInstanceState The saved instance state bundle.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        mAuth = FirebaseAuth.getInstance();
    }

    /**
     * Checks if the user is already signed in.
     * If the user is signed in, navigates to the main activity.
     */
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    /**
     * Navigates to the sign-in activity.
     *
     * @param v The sign-in button view.
     */
    public void navSignIn(View v) {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

    /**
     * Navigates to the sign-up activity.
     *
     * @param v The sign-up button view.
     */
    public void navSignUp(View v) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}