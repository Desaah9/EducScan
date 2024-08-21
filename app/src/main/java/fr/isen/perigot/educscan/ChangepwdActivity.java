package fr.isen.perigot.educscan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import at.favre.lib.crypto.bcrypt.BCrypt;
import fr.isen.perigot.educscan.ui.paramètre.ParametresFragment;


public class ChangepwdActivity extends AppCompatActivity {

    private EditText oldPasswordEditText, newPasswordEditText, usernameEditText;
    private Button changePasswordButton;
    private ImageButton backButton;


    String currentUserUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepwd);

        oldPasswordEditText = findViewById(R.id.old_password_editText);
        newPasswordEditText = findViewById(R.id.new_password_editText);
        usernameEditText = findViewById(R.id.username_editText);
        //confirmPassword = findViewById(R.id.confirmPassword);
        changePasswordButton = findViewById(R.id.change_password_button);
        backButton = findViewById(R.id.back_button);
        
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirection vers la page Paramètre
                Intent intent = new Intent(ChangepwdActivity.this, ParametresFragment.class);
                startActivity(intent);
            }
        });


        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateUsername() | !validateOldPassword() | !validateNewPassword()) {
                    // Validation failed
                } else {
                    changePassword();
                }
            }
        });


        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });
    }

    private Boolean validateUsername() {
        String val = usernameEditText.getText().toString().trim();
        if (val.isEmpty()) {
            usernameEditText.setError("Username cannot be empty");
            return false;
        } else {
            usernameEditText.setError(null);
            currentUserUsername = val; // Update the currentUserUsername
            return true;
        }
    }

    private Boolean validateOldPassword() {
        String val = oldPasswordEditText.getText().toString().trim();
        if (val.isEmpty()) {
            oldPasswordEditText.setError("Old password cannot be empty");
            return false;
        } else {
            oldPasswordEditText.setError(null);
            return true;
        }
    }


    private Boolean validateNewPassword() {
        String val = newPasswordEditText.getText().toString().trim();
        // Implement additional validation if needed
        if (val.isEmpty()) {
            newPasswordEditText.setError("New password cannot be empty");
            return false;
        } else {
            newPasswordEditText.setError(null);
            return true;
        }
    }

    /*private Boolean validateConfirmPassword() {
        String val = confirmPassword.getText().toString().trim();
        String newPasswordVal = newPassword.getText().toString().trim();
        if (val.isEmpty()) {
            confirmPassword.setError("Confirm password cannot be empty");
            return false;
        } else if (!val.equals(newPasswordVal)) {
            confirmPassword.setError("Passwords do not match");
            return false;
        } else {
            confirmPassword.setError(null);
            return true;
        }
    }*/

    private void changePassword() {
        String userOldPassword = oldPasswordEditText.getText().toString().trim();
        String userNewPassword = newPasswordEditText.getText().toString().trim();
        //String userConfirmPassword = confirmPassword.getText().toString().trim();

        // Fetch the user's current password from Firebase
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("username").equalTo(currentUserUsername);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String passwordFromDB = snapshot.child(currentUserUsername).child("password").getValue(String.class);

                    // Verify old password using BCrypt
                    if (BCrypt.verifyer().verify(userOldPassword.toCharArray(), passwordFromDB).verified) {
                        // Old password is correct, proceed to update the password
                        String newPasswordHash = BCrypt.withDefaults().hashToString(12, userNewPassword.toCharArray());
                        reference.child(currentUserUsername).child("password").setValue(newPasswordHash);

                        // Password changed successfully, you can show a success message or navigate to another activity
                        Toast.makeText(ChangepwdActivity.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        // Old password is incorrect, show error message
                        Toast.makeText(ChangepwdActivity.this, "Incorrect old password", Toast.LENGTH_SHORT).show();
                        oldPasswordEditText.setError("Incorrect old password");
                        oldPasswordEditText.requestFocus();
                    }
                } else {
                    // User not found
                    Toast.makeText(ChangepwdActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error if needed
            }
        });
    }
}
