package carpool.buddy.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import carpool.buddy.R;

/**
 * The SignInActivity class handles the user sign-in process.
 * It allows users to sign in to their existing account using their email and password.
 */
public class SignInActivity extends AppCompatActivity {

    //declare variables
    private FirebaseAuth mAuth;
    private EditText emailInput;
    private EditText passwordInput;

    /**
     * Initializes the activity and sets up the necessary variables and UI elements.
     *
     * @param savedInstanceState The saved instance state bundle.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //declare and initialize variables
        mAuth = FirebaseAuth.getInstance();
        emailInput = findViewById(R.id.editTextEmail);
        passwordInput = findViewById(R.id.editTextPassword);
    }

    //sign in method
    /**
     * Handles the sign-in button click event.
     * Attempts to sign in the user using the provided email and password.
     *
     * @param v The sign-in button view.
     */
    public void signIn(View v) {
        //user input
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        //sign in using user input
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                //sign in success, nav to main
                Log.d("SIGN IN", "Successfully signed in");
                signedIn();
            }
            else {
                //sign in failed, display message to user
                Log.w("SIGN IN", "Failed to sign in", task.getException());
                signedIn();
            }
        });
    }

    //user signed in, nav to main
    /**
     * Navigates to the main activity after successful sign-in.
     *
     */
    public void signedIn() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}