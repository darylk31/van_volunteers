package cs.ubc.ca.van_volunteers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText etEmailAddress;
    EditText etPassword;
    Button bSignIn;

    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getViews();
        SignInListener();
    }

    private void getViews(){
        etEmailAddress = findViewById(R.id.etEmailAddress);
        etPassword = findViewById(R.id.etPassword);
        bSignIn = findViewById(R.id.bSignIn);
    }


    private void SignInListener(){
        final String email = etEmailAddress.getText().toString();
        final String password = etPassword.getText().toString();
        showProcessDialog();
        bSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Login Failed.", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        } else{
                            progressDialog.setMessage("Retrieving account details");
                            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                            finish();
                        }
                    }
                });
            }
        });
    }

    private void showProcessDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Logging in...");
        progressDialog.show();
    }
}
