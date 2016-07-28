package co.ohlora.userapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import co.ohlora.userapp.models.User;

public class SignUpActivity extends BaseActivity {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private EditText mEmailField;
    private EditText mPasswordField;
    private EditText mApartField;
    private EditText mHomeField;
    private EditText mPhoneField;
    private Button mSignUpBtn;

    public String apart;
    public String home;
    public String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        mEmailField = (EditText) findViewById(R.id.field_email);
        mPasswordField = (EditText) findViewById(R.id.field_password);
        mApartField = (EditText) findViewById(R.id.field_apart);
        mHomeField = (EditText) findViewById(R.id.field_home);
        mPhoneField = (EditText) findViewById(R.id.field_phone);

        mSignUpBtn = (Button) findViewById(R.id.button_sign_up);

        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUp();
            }
        });

    }

    private void SignUp() {
        if (!validateForm()) {
            return;
        }

        showProgressDialog();
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();
        apart = mApartField.getText().toString();
        home = mHomeField.getText().toString();
        phone = mPhoneField.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        hideProgressDialog();

                        if (task.isSuccessful()) {
                            onAuthSuccess(task.getResult().getUser());
                        } else {
                            Toast.makeText(SignUpActivity.this, "Sign Up Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void onAuthSuccess(FirebaseUser user) {
        String username = usernameFromEmail(user.getEmail());

        String useraddress = apart + "/" + home;

        // Write new user
        writeNewUser(user.getUid(), username, user.getEmail(), useraddress, phone);

        // Go to MainActivity
        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
        finish();
    }

    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(mEmailField.getText().toString())) {
            mEmailField.setError("Required");
            result = false;
        } else {
            mEmailField.setError(null);
        }

        if (TextUtils.isEmpty(mPasswordField.getText().toString())) {
            mPasswordField.setError("Required");
            result = false;
        } else {
            mPasswordField.setError(null);
        }

        if (TextUtils.isEmpty(mApartField.getText().toString())) {
            mApartField.setError("Required");
            result = false;
        } else {
            mApartField.setError(null);
        }

        if (TextUtils.isEmpty(mHomeField.getText().toString())) {
            mHomeField.setError("Required");
            result = false;
        } else {
            mHomeField.setError(null);
        }

        if (TextUtils.isEmpty(mPhoneField.getText().toString())) {
            mPhoneField.setError("Required");
            result = false;
        } else {
            mPhoneField.setError(null);
        }

        return result;
    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    private void writeNewUser(String userId, String name, String email, String useraddress, String home) {
        User user = new User(name, email, useraddress, home);

        mDatabase.child("users").child(userId).setValue(user);
    }
}
