package cs.ubc.ca.van_volunteers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

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
        mAuth = FirebaseAuth.getInstance();
        SignInListener();
    }

    private void getViews(){
        etEmailAddress = findViewById(R.id.etEmailAddress);
        etPassword = findViewById(R.id.etPassword);
        bSignIn = findViewById(R.id.bSignIn);
    }


    private void SignInListener(){
        bSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = etEmailAddress.getText().toString();
                final String password = etPassword.getText().toString();
                if (email.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please check your fields.", Toast.LENGTH_LONG).show();
                }
                else {
                    showProcessDialog();
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Login Failed.", Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            } else {
                                progressDialog.setMessage("Retrieving account details");
                                getAccountType();
                            }
                        }
                    });
                }
            }
        });
    }

    private void showProcessDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Logging in...");
        progressDialog.show();
    }

    public void onRegister(View view){
        startActivity(new Intent(LoginActivity.this, RegisterSelectionActivity.class));
    }

    public void getAccountType(){
        String uid = mAuth.getCurrentUser().getUid();
        Utils.getDatabase().getReference().child(Utils.ACCOUNT_DATABASE).child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String account_type = (String) dataSnapshot.getValue();
                SharedPreferences.Editor editor = getSharedPreferences(Utils.APP_PACKAGE, MODE_PRIVATE).edit();
                editor.putString("account_type", account_type);
                editor.commit();
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
