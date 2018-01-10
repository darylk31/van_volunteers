package cs.ubc.ca.van_volunteers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ResultsActivity extends AppCompatActivity {

    private String keyword;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private ImageButton filterButton;
    private LinearLayoutManager linearLayoutManager;
    private DatabaseReference post_reference;
    private FirebaseRecyclerAdapter<Post, PostViewHolder> recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        keyword = getIntent().getStringExtra("keyword");
        searchView = findViewById(R.id.results_searchview);
        if (!keyword.equals("seeAll")) {
            searchView.setQuery(keyword, false);
        }
        setSearchListener();
        filterButton = findViewById(R.id.bFilter);
        setFilterListener();
        recyclerView = findViewById(R.id.results_recycler);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        post_reference = database.getReference("Post");

        loadPosts();
    }

    public void setSearchListener(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (s.isEmpty()){
                    keyword = "seeAll";
                    recyclerAdapter.stopListening();
                    loadPosts();
                } else {
                    keyword = s;
                    recyclerAdapter.stopListening();
                    loadPosts();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    public void setFilterListener(){
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

    }

    public void loadPosts(){
        Query query;
        if (keyword.equals("seeAll")){
            query = post_reference;
        }
        else{
            query = post_reference
                    .orderByChild(Utils.POST_TITLE)
                    .startAt(keyword)
                    .endAt(keyword + "\uf8ff");
        }

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
                holder.setOrganization(model.getOrganization());
                holder.setCity(model.getCity());
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

        public void setOrganization(String organization){
            TextView tv = cardView.findViewById(R.id.cardview_organization);
            tv.setText(organization);
        }

        public void setCity (String city){
            TextView tv = cardView.findViewById(R.id.cardview_city);
            tv.setText(city);
        }

        public void setDescription(String description){
            TextView tv = cardView.findViewById(R.id.cardview_description);
            tv.setText(description);
        }

        public void setDate(String dateString) {
            TextView tv = cardView.findViewById(R.id.cardview_datePosted);
            String dateOutput = null;
            try {
                Date post_date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
                SimpleDateFormat card_date_format = new SimpleDateFormat("MMM dd");
                dateOutput = card_date_format.format(post_date);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            tv.setText(dateOutput);
        }
    }
}
