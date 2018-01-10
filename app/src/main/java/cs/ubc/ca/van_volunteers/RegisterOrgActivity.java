package cs.ubc.ca.van_volunteers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RegisterOrgActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_org);
        getIntent().getStringExtra("uid");
    }
}
