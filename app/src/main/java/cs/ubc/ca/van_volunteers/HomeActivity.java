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

    private TextView tvSignUp;
    private TextView tvLogin;
    private TextView tvWelcome;
    private TextView tvWelcomeEmail;
    private TextView tvNewPost;

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
        tvWelcome.setVisibility(View.VISIBLE);
        tvWelcomeEmail.setText(mAuth.getCurrentUser().getEmail());
        tvLogin.setText("Visit Profile");
        tvSignUp.setVisibility(View.INVISIBLE);
        tvNewPost.setVisibility(View.VISIBLE);
    }

    private void getViews(){
        tvLogin = findViewById(R.id.tv_login);
        tvSignUp = findViewById(R.id.tv_signup);
        tvWelcome = findViewById(R.id.tv_welcome);
        tvWelcomeEmail = findViewById(R.id.tv_welcome_email);
        tvNewPost = findViewById(R.id.tv_newPost);
    }

    private void setListener(){
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mAuth.getCurrentUser() != null){
                    startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                } else {
                    startActivity(new Intent(HomeActivity.this,LoginActivity.class));
                }
            }
        });
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    startActivity(new Intent(HomeActivity.this,RegisterActivity.class));
            }
        });
        tvNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, CreatePostActivity.class));
            }
        });
    }

    public void onSeeAll(View view){
        startActivity(new Intent(this, ResultsActivity.class).putExtra("keyword", "seeAll"));
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
