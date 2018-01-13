package cs.ubc.ca.van_volunteers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class VolunteerProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String uid;
    private Volunteer volunteer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_profile);
        mAuth = FirebaseAuth.getInstance();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profile");
        uid = getIntent().getStringExtra("uid");
        Utils.getDatabase().getReference().child(Utils.VOLUNTEER_DATABASE).child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                volunteer = dataSnapshot.getValue(Volunteer.class);
                displayProfile();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mAuth.getCurrentUser().getUid().equals(uid)) {
            getMenuInflater().inflate(R.menu.profile_menu, menu);}
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_editprofile) {
            startActivity(new Intent(VolunteerProfileActivity.this, RegisterVolunteerActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public void displayProfile(){
        CircleImageView profilePictureView = findViewById(R.id.civ_profile);


        if (volunteer.getPhotoURL() == "") {
            Glide.with(this)
                    .load(R.drawable.profile_image)
                    .into(profilePictureView);
        } else {
            Glide.with(this)
                    .load(volunteer.getPhotoURL())
                    .into(profilePictureView);
        }

        TextView name = findViewById(R.id.tv_name);
        name.setText(volunteer.getName());

        TextView bio = findViewById(R.id.tv_bio);
        bio.setText(volunteer.getBio());

        TextView age = findViewById(R.id.tv_age);
        String birthday = volunteer.getBirthday();
        try {
            Date birthdate = new SimpleDateFormat("yyyy-MM-dd").parse(birthday);
            Calendar currentDate = Calendar.getInstance();
            Long time= currentDate.getTime().getTime() / 1000 - birthdate.getTime() / 1000;
            int years = Math.round(time) / 31536000;
            age.setText(Integer.toString(years));

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
