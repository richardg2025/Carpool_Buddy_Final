package carpool.buddy.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;
import java.util.Random;

import carpool.buddy.R;
import carpool.buddy.classes.User;

/**
 * The SignUpActivity class handles the user sign-up process.
 * It allows users to create a new account with their email and password,
 * and saves the user information to Firebase Authentication and Firestore.
 */
public class SignUpActivity extends AppCompatActivity {

    //declare variables
    private FirebaseAuth mAuth;
    private FirebaseFirestore fireStore;
    private EditText emailInput;
    private EditText passwordInput;
    private EditText nameInput;
    private CheckBox isTeacher;
    private CheckBox isStudent;
    private CheckBox isAlumni;
    private CheckBox isParent;

    /**
     * Initializes the activity and sets up the necessary variables and UI elements.
     *
     * @param savedInstanceState The saved instance state bundle.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //declare and initialize variables
        mAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();

        emailInput = findViewById(R.id.editTextEmail);
        passwordInput = findViewById(R.id.editTextPassword);
        nameInput = findViewById(R.id.editTextName);
        isAlumni = findViewById(R.id.checkBoxAlumni);
        isParent = findViewById(R.id.checkBoxParent);
        isStudent = findViewById(R.id.checkBoxStudent);
        isTeacher = findViewById(R.id.checkBoxTeacher);
    }

    //sign up method
    /**
     * Handles the sign-up button click event.
     * Creates a new user account using the provided email and password,
     * and saves the user information to Firebase Authentication and Firestore.
     *
     * @param v The sign-up button view.
     */
    public void signUp(View v) {

        //user input
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();
        String name = nameInput.getText().toString();
        String userType;

        //create new user in firebaseAuth
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            Context context = SignUpActivity.this;
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                //sign up success, nav to main
                if(task.isSuccessful()) {
                    Toast messageToUser = Toast.makeText(context , "Success", Toast.LENGTH_LONG);
                    messageToUser.show();
                    Log.d("SIGN UP", "Successfully signed up");
                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    signedIn(currentUser);
                }

                //sign up failed, toast user
                else {
                    Toast messageToUser = Toast.makeText(context, "Failed", Toast.LENGTH_LONG);
                    messageToUser.show();
                    Log.w("SIGN UP", "Failed to sign up",
                            task.getException());
                    signedIn(null);
                }
            }
        });

        if (isTeacher.isChecked()) {
            userType = "Teacher";
        } else if (isStudent.isChecked()) {
            userType = "Student";
        } else if (isAlumni.isChecked()) {
            userType = "Alumni";
        } else if (isParent.isChecked()) {
            userType = "Parent";
        } else {
            userType = "";
        }

        Random rand = new Random();
        String uid = String.valueOf(rand.nextInt(99999));

        //create new user object
        User newUser = new User(uid, email, name, userType);

        //save user in firebase and sign in
        fireStore.collection("/users").document(email).set(newUser).addOnCompleteListener(task -> {});
    }

    //user signed in, nav to main
    /**
     * Navigates to the main activity after successful sign-up.
     *
     * @param currentUser The currently signed-in FirebaseUser object.
     */
    public void signedIn(FirebaseUser currentUser) {
        if(currentUser != null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}