package cs.ubc.ca.van_volunteers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {
    //Test push from home

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
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
