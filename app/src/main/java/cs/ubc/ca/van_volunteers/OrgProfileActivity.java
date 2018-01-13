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

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class OrgProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String oid;
    private Organization organization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_profile);
        mAuth = FirebaseAuth.getInstance();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profile");
        oid = getIntent().getStringExtra("oid");
        Utils.getDatabase().getReference().child(Utils.ORGANIZATION_DATABASE).child(oid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                organization = dataSnapshot.getValue(Organization.class);
                displayProfile();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mAuth.getCurrentUser().getUid().equals(oid)) {
            getMenuInflater().inflate(R.menu.profile_menu, menu);}
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_editprofile) {
            startActivity(new Intent(OrgProfileActivity.this, RegisterOrgActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public void displayProfile(){
        CircleImageView profilePictureView = findViewById(R.id.civ_profile);


        if (organization.getPhotoURL().equals("")) {
            Glide.with(this)
                    .load(R.drawable.profile_image)
                    .into(profilePictureView);
        } else {
            Glide.with(this)
                    .load(organization.getPhotoURL())
                    .into(profilePictureView);
        }

        TextView name = findViewById(R.id.tv_name);
        name.setText(organization.getName());

        TextView bio = findViewById(R.id.tv_bio);
        bio.setText(organization.getBio());

        TextView address = findViewById(R.id.tv_address);
        address.setText(organization.getAddress());

        TextView email = findViewById(R.id.tv_email);
        email.setText(organization.getEmail());

        TextView website = findViewById(R.id.tv_website);
        website.setText(organization.getWebsite());
    }
}
