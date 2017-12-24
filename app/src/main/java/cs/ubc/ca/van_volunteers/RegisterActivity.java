package cs.ubc.ca.van_volunteers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterActivity extends AppCompatActivity {

    private EditText etEmailAddress;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private Button bRegister;

    private String EmailAddress;
    private String Password;

    private ProgressDialog progressDialog;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getViews();
        buttonListener();
    }

    private void getViews(){
        etEmailAddress = (EditText) findViewById(R.id.etEmailAddress);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
        bRegister = (Button) findViewById(R.id.bRegister);
    }

    private void buttonListener(){
        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validateForm())
                    return;
                //TODO: Add captcha verification

                createUser();
            }
        });
    }

    private void createUser() {
        showProgressDialog();
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(EmailAddress,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                hideProgressDialog();
                if(!task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Sorry, an error has occurred.\n Please try again.",Toast.LENGTH_LONG).show();
                    mAuth.signOut();
                } else{
                    DatabaseReference mDatabase = Utils.getDatabase().getReference();
                    Account newAccount = new Account(EmailAddress,false,"");
                    mDatabase.child(Utils.ACCOUNT_DATABASE).child(mAuth.getUid()).setValue(newAccount);
                    Toast.makeText(getApplicationContext(),"Registration Successful.",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(RegisterActivity.this,HomeActivity.class));
                    finish();
                }
            }
        });

    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Register");
        progressDialog.setMessage("Registering Account...");
        progressDialog.show();
    }

    private void hideProgressDialog() {
        progressDialog.dismiss();
    }


    private boolean validateForm(){
        EmailAddress = etEmailAddress.getText().toString().trim();
        Password = etPassword.getText().toString().trim();
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(EmailAddress).matches()){
            Toast.makeText(getApplicationContext(),"Invalid Email Address", Toast.LENGTH_LONG).show();
            return false;
        }
        if(!Password.equals(etConfirmPassword.getText().toString().trim())){
            Toast.makeText(getApplicationContext(),"Password does not match", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;

    }
}
