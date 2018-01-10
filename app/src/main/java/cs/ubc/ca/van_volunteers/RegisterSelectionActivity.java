package cs.ubc.ca.van_volunteers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class RegisterSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_selection);
    }

    public void onVolunteer(View view){
        startActivity(new Intent(RegisterSelectionActivity.this, RegisterEmailActivity.class).putExtra("type", Utils.ACCOUNT_TYPE_VOLUNTEER));

    }

    public void onOrganization(View view){
        startActivity(new Intent(RegisterSelectionActivity.this, RegisterEmailActivity.class).putExtra("type", Utils.ACCOUNT_TYPE_ORGANIZATION));
    }
}
