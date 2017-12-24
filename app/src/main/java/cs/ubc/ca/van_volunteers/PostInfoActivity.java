package cs.ubc.ca.van_volunteers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
