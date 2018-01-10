package cs.ubc.ca.van_volunteers;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_info);
        id = getIntent().getStringExtra("id");

        FirebaseDatabase.getInstance().getReference("Post").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Post post = dataSnapshot.getValue(Post.class);
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

                if (post.getEmail() == ""){
                    RelativeLayout emaillayout = findViewById(R.id.layout_email);
                    emaillayout.setVisibility(View.INVISIBLE);
                }
                else {
                    TextView email = findViewById(R.id.tv_post_email);
                    email.setText(post.getEmail());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void onCall(View view){
        TextView tv_number = findViewById(R.id.tv_post_number);
        String number = tv_number.getText().toString();
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+ number));
        startActivity(intent);
    }

    public void onEmail(View view){
        TextView tv_email = findViewById(R.id.tv_post_email);
        String email = tv_email.getText().toString();
        Intent intent = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:"));
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, email);
        intent.putExtra(Intent.EXTRA_SUBJECT, title);

        startActivity(intent);
    }
}
