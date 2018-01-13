package cs.ubc.ca.van_volunteers;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreatePostActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        mDatabase = Utils.getDatabase().getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CreatePostActivity.this);
        builder.setTitle("Discard");
        builder.setMessage("All information will be discarded, are you sure?");
        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(CreatePostActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void onCancel(View view) {
        onBackPressed();
    }


    public void onPost(View view) {
        EditText et_position = findViewById(R.id.et_position);
        EditText et_description = findViewById(R.id.et_description);
        Spinner city_spinner = findViewById(R.id.spinner_city);

        EditText et_number = findViewById(R.id.et_number);
        EditText et_address = findViewById(R.id.et_address);
        EditText et_age = findViewById(R.id.et_age);
        EditText et_experience = findViewById(R.id.et_experience);

        String position = et_position.getText().toString();
        String description = et_description.getText().toString();
        String city = city_spinner.getSelectedItem().toString();

        String number = et_number.getText().toString();
        String address = et_address.getText().toString();
        String age = et_age.getText().toString();
        String experience = et_experience.getText().toString();

        if (position.isEmpty() || city.isEmpty() || description.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please ensure starred forms are not empty.", Toast.LENGTH_SHORT).show();
        } else {
            showProcessDialog();
            String id = mDatabase.push().getKey();
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String date = dateFormat.format(calendar.getTime());

            Post post = new Post(id, position, mAuth.getCurrentUser().getDisplayName(), mAuth.getCurrentUser().getUid(),
                    date, date, city, description, mAuth.getCurrentUser().getEmail());
            mDatabase.child(Utils.POST_DATABASE).child(id).setValue(post);
            mDatabase.child(Utils.ORGANIZATION_DATABASE).child(mAuth.getCurrentUser().getUid()).child(Utils.ORGANIZATION_POSTS).child(id).setValue(true);
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(CreatePostActivity.this, HomeActivity.class));
        }
    }

    private void showProcessDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Making your post...");
        progressDialog.show();
    }
}
