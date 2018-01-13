package cs.ubc.ca.van_volunteers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Daryl on 12/23/2017.
 */

public class PostInfoActivity extends AppCompatActivity {
    String id;
    String title;
    private String account_type = null;
    private FirebaseAuth mAuth;
    Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_info);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        id = getIntent().getStringExtra("id");
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null){
            SharedPreferences pref = getSharedPreferences(Utils.APP_PACKAGE, MODE_PRIVATE);
            account_type = pref.getString("account_type", "");
        }

        FirebaseDatabase.getInstance().getReference("Post").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                post = dataSnapshot.getValue(Post.class);
                TextView tv_title = findViewById(R.id.tv_post_title);
                title = post.getTitle();
                tv_title.setText(title);
                TextView organization = findViewById(R.id.tv_post_organization);
                organization.setText(post.getOrganization());
                TextView city = findViewById(R.id.tv_post_city);
                city.setText(post.getCity());

                if (post.getAddress() == ""){
                    RelativeLayout addresslayout = findViewById(R.id.layout_address);
                    addresslayout.setVisibility(View.INVISIBLE);
                    }
                else {
                    TextView address = findViewById(R.id.tv_post_address);
                    address.setText(post.getAddress());
                }

                TextView description = findViewById(R.id.tv_post_description);
                description.setText(post.getDescription());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        setUpApplyButton();
    }

    public void onCall(View view){
        TextView tv_number = findViewById(R.id.tv_post_number);
        String number = tv_number.getText().toString();
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+ number));
        startActivity(intent);
    }

    public void onEmail(View view){
        if (mAuth.getCurrentUser() != null){
            FirebaseDatabase.getInstance().getReference().child(Utils.POST_DATABASE).child(post.getId())
                    .child(Utils.POST_APPLIED).child(mAuth.getCurrentUser().getUid()).setValue(true);
            FirebaseDatabase.getInstance().getReference().child(Utils.VOLUNTEER_DATABASE).child(mAuth.getCurrentUser().getUid())
                    .child(Utils.VOLUNTEER_APPLIED).child(id).setValue(true);
        }
        String email = post.getEmail();
        Intent intent = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:"));
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, email);
        intent.putExtra(Intent.EXTRA_SUBJECT, title);
        startActivity(intent);
    }

    public void onOrganization(View view){
        post.getOid();

    }

    public void setUpApplyButton(){
        Button button = findViewById(R.id.b_apply);
        if (account_type.equals(Utils.ACCOUNT_TYPE_ORGANIZATION)){
            button.setClickable(false);
            button.setBackgroundColor(getResources().getColor(R.color.grey));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (account_type.equals(Utils.ACCOUNT_TYPE_VOLUNTEER)) {
            getMenuInflater().inflate(R.menu.postinfo_menu, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                Intent upIntent = new Intent(this, ResultsActivity.class);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    NavUtils.navigateUpTo(this, upIntent);
                    finish();
                } else {
                    finish();
                }
                return true;
        }
        return true;
    }
}
