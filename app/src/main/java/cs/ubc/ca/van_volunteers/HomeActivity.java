package cs.ubc.ca.van_volunteers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String account_type = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null){
            SharedPreferences pref = getSharedPreferences(Utils.APP_PACKAGE, MODE_PRIVATE);
            account_type = pref.getString("account_type", "");
        }
        getSupportActionBar().setTitle("Van Volunteers");
        //setUpFloatingButton();

        // TESTING UPLOAD ACTIVITY TO REMOVE LATER
        Button b = findViewById(R.id.testupload);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,VerificationUpload.class);
                startActivity(intent);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        System.out.println("Account Type:" + account_type);
        int id = item.getItemId();
        if (id == R.id.item_profile) {
            if (account_type == null) {
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            }
            else {
                if (account_type.equals(Utils.ACCOUNT_TYPE_VOLUNTEER)) {
                    startActivity(new Intent(HomeActivity.this, VolunteerProfileActivity.class).putExtra("uid", mAuth.getCurrentUser().getUid()));
                } else {
                    startActivity(new Intent(HomeActivity.this, OrgProfileActivity.class).putExtra("oid", mAuth.getCurrentUser().getUid()));
                }
            }
        }

        if (id == R.id.item_settings){
            if (account_type == null) {
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            }
            else {
                startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void setUpFloatingButton(){
        if (account_type.equals(Utils.ACCOUNT_TYPE_ORGANIZATION)) {
            FloatingActionButton button = findViewById(R.id.b_create);
            button.setVisibility(View.VISIBLE);
        }
    }

    public void onCreatePost(View view){
        startActivity(new Intent(HomeActivity.this, CreatePostActivity.class));
    }
}
