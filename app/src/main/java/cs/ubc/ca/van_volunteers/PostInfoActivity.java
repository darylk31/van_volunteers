package cs.ubc.ca.van_volunteers;

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

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_info);
        id = getIntent().getStringExtra("id");

        FirebaseDatabase.getInstance().getReference("Post").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Post post = dataSnapshot.getValue(Post.class);
                TextView title = findViewById(R.id.tv_post_title);
                title.setText(post.getTitle());
                TextView organization = findViewById(R.id.tv_post_organization);
                organization.setText(post.getOrganization());
                TextView city = findViewById(R.id.tv_post_city);
                city.setText(post.getCity());

                if (post.getAddress() == null){
                    RelativeLayout addresslayout = findViewById(R.id.layout_address);
                    addresslayout.setVisibility(View.INVISIBLE);
                    }
                else {
                    TextView address = findViewById(R.id.tv_post_address);
                    address.setText(post.getAddress());
                }

                if (post.getDescription() == null){
                    RelativeLayout descriptionlayout = findViewById(R.id.layout_description);
                    descriptionlayout.setVisibility(View.INVISIBLE);
                    }
                else {
                    TextView description = findViewById(R.id.tv_post_description);
                    description.setText(post.getDescription());

                }

                if (post.getEmail() == null){
                    RelativeLayout emaillayout = findViewById(R.id.layout_email);
                    emaillayout.setVisibility(View.INVISIBLE);
                }
                else {
                    TextView email = findViewById(R.id.tv_post_email);
                    email.setText(post.getEmail());
                }

                if (post.getPhone() == null){
                    RelativeLayout phonelayout = findViewById(R.id.layout_number);
                    phonelayout.setVisibility(View.INVISIBLE);
                }
                else {
                    TextView phone = findViewById(R.id.tv_post_number);
                    phone.setText(post.getPhone());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
