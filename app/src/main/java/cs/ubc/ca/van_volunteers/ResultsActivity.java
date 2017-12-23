package cs.ubc.ca.van_volunteers;

import android.app.DownloadManager;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ResultsActivity extends AppCompatActivity {

    private String keyword;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private DatabaseReference post_reference;
    private FirebaseRecyclerAdapter<Post, PostViewHolder> recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        keyword = getIntent().getStringExtra("keyword");
        recyclerView = findViewById(R.id.results_recycler);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        post_reference = database.getReference("Post");

        loadPosts();
    }

    public void loadPosts(){
        Query query = post_reference;

        //TODO: query for different keyword.

        FirebaseRecyclerOptions<Post> options = new FirebaseRecyclerOptions.Builder<Post>()
                .setQuery(query, Post.class)
                .build();

        recyclerAdapter = new FirebaseRecyclerAdapter<Post, PostViewHolder> (options) {

            @Override
            public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_results, parent, false);
                return new PostViewHolder(cv);
            }

            @Override
            protected void onBindViewHolder(PostViewHolder holder, int position, final Post model) {
                holder.setTitle(model.getTitle());
                holder.setOrganization(model.getOrganization(), model.getLocation());
                holder.setDescription(model.getDescription());
                holder.setDate(model.getPost_date());
                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(ResultsActivity.this, PostInfoActivity.class).putExtra("id", model.getId()));
                    }
                });
            }
        };

        recyclerAdapter.startListening();
        recyclerView.setAdapter(recyclerAdapter);

    }

    public static class PostViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;

        public PostViewHolder(CardView view) {
            super(view);
            cardView = view;
        }

        public void setTitle(String title){
            TextView tv = cardView.findViewById(R.id.cardview_title);
            tv.setText(title);
        }

        public void setOrganization(String organization, String location){
            TextView tv = cardView.findViewById(R.id.cardview_organization);
            tv.setText(organization + ", " + location);
        }

        public void setDescription(String description){
            TextView tv = cardView.findViewById(R.id.cardview_description);
            tv.setText(description);
        }

        public void setDate(String dateString) {
            TextView tv = cardView.findViewById(R.id.cardview_datePosted);
            Calendar today = Calendar.getInstance();
            Date dateToday = null;
            try {
                dateToday = new SimpleDateFormat("yyyy-MM-dd").parse(today.toString());
                Date cardDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
                if (dateToday == cardDate) {
                    tv.setText("Today");
                } else{
                    String dateOutput = new SimpleDateFormat("MMM dd", Locale.ENGLISH).format(dateString);
                    tv.setText(dateOutput);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
