package cs.ubc.ca.van_volunteers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

public class RegisterOrgActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference orgRef;
    private String photoURL = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_org);
        mAuth = FirebaseAuth.getInstance();
        orgRef = Utils.getDatabase().getReference().child(Utils.ORGANIZATION_DATABASE).child(mAuth.getCurrentUser().getUid());
        getSupportActionBar().setTitle("Profile Update");
    }

    public void onDoneRegister(View view){
        EditText etname = findViewById(R.id.et_name);
        EditText etbio = findViewById(R.id.et_bio);
        EditText etaddress = findViewById(R.id.et_address);
        EditText etwebsite = findViewById(R.id.et_website);
        String name = etname.getText().toString();
        String bio = etbio.getText().toString();
        String address = etaddress.getText().toString();
        String website = etwebsite.getText().toString();

        if(TextUtils.isEmpty(name)) {
            etname.setError("Name required.");
            return;
        }
        if(TextUtils.isEmpty(bio)) {
            etbio.setError("Bio required.");
            return;
        }
        if(TextUtils.isEmpty(address)) {
            etname.setError("Address required.");
            return;
        }
        if(TextUtils.isEmpty(bio)) {
            etbio.setError("Website required.");
            return;
        }


        Organization organization = new Organization(mAuth.getCurrentUser().getUid(), mAuth.getCurrentUser().getEmail(), name,
                address, bio, website, photoURL, false);
        orgRef.setValue(organization);
        Toast.makeText(RegisterOrgActivity.this, "Profile updated!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(RegisterOrgActivity.this, HomeActivity.class));
    }

    public void onUpload(View view){
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1,1)
                .start(RegisterOrgActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE ) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                final ProgressDialog dialog = new ProgressDialog(RegisterOrgActivity.this);
                dialog.setMessage("Uploading image...");
                dialog.show();
                Uri resultUri = result.getUri();
                File photoPath = new File(resultUri.getPath());

                try {
                    Bitmap photo_bitmap = new Compressor(this)
                            .setMaxWidth(200)
                            .setMaxHeight(200)
                            .setQuality(75)
                            .compressToBitmap(photoPath);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    photo_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] photoByteArray = baos.toByteArray();

                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference storageRef = storage.getReference();
                    UploadTask uploadtask = storageRef.child("profile/" + mAuth.getCurrentUser().getUid() + ".jpg").putBytes(photoByteArray);
                    uploadtask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @SuppressWarnings("VisibleForTests")
                        @Override
                        public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setPhotoUri(taskSnapshot.getDownloadUrl())
                                    .build();
                            mAuth.getCurrentUser().updateProfile(profileUpdates).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    setDisplayPicture();
                                    photoURL = taskSnapshot.getDownloadUrl().toString();
                                    Toast.makeText(getApplicationContext(), "Profile updated!", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            });
                        }
                    });
                } catch (IOException e) {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Error uploading image, please try again.", Toast.LENGTH_SHORT).show();
                    finish();
                }


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(getApplicationContext(), "Error uploading image, please try again.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setDisplayPicture(){
        CircleImageView profilePictureView = findViewById(R.id.civ_register);

        if (mAuth.getCurrentUser().getPhotoUrl() == null) {
            Glide.with(this)
                    .load(R.drawable.profile_image)
                    .into(profilePictureView);
        } else {
            Glide.with(this)
                    .load(mAuth.getCurrentUser().getPhotoUrl())
                    .into(profilePictureView);
        }
    }
}
