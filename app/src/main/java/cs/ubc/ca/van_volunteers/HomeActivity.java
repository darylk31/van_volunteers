package cs.ubc.ca.van_volunteers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
    //Test push from home shimychu2

    private TextView tvSignUp;
    private TextView tvLogin;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getViews();
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null){
            updateHomePage();
        }
        setListener();
    }

    private void updateHomePage() {
        //TODO: update the home page to look likea login page
        tvLogin.setText("Login as: " + mAuth.getCurrentUser().getEmail().toString());
    }

    private void getViews(){
        tvLogin = findViewById(R.id.tv_login);
        tvSignUp = findViewById(R.id.tv_signup);
    }

    private void setListener(){
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If user is logged in
                if(mAuth.getCurrentUser() != null){

                } else {
                    startActivity(new Intent(HomeActivity.this,LoginActivity.class));
                }
            }
        });
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mAuth.getCurrentUser() != null){
                    tvSignUp.setText("Sign Out");
                    tvSignUp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mAuth.signOut();
                        }
                    });
                } else{
                    startActivity(new Intent(HomeActivity.this,RegisterActivity.class));
                }
            }
        });
    }
    public void onSeeAll(View view){
        startActivity(new Intent(this, ResultsActivity.class).putExtra("keybord", "seeAll"));
    }

    public void onSearch(View view){
        EditText searchText = findViewById(R.id.et_keyword);
        if (searchText.getText() == null) {
            Toast.makeText(getApplicationContext(), "Please type in word to search.", Toast.LENGTH_SHORT).show();
        }
        else {
            String keyword = searchText.getText().toString();
            startActivity(new Intent(this, ResultsActivity.class).putExtra("keyword", keyword));
        }
    }
}
