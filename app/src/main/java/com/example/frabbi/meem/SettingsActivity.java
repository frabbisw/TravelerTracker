package com.example.frabbi.meem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.SwitchCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;


public class SettingsActivity extends BottomBarActivity {

    private EditText geteditname;
    private Button logoutBtn;
    private Button saveChangeBtn;
    private CircleImageView myimageview;
    private SwitchCompat mode;
    private Spinner time;

    private static final int BROWSE_GALLERY_REQUEST_CODE = 100;

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.profilePhoto:
                    openImageGallery();
                    break;

                case R.id.saveChange:
                    saveNewChange();
                    break;

                case R.id.logout:
                    logoutFromAccount();
                    break;
            }
        }
    };


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_settings, frameContent);

        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        settings.setClickable(false);

        logoutBtn = (Button) findViewById(R.id.logout);
        logoutBtn.setOnClickListener(clickListener);

        saveChangeBtn = (Button) findViewById(R.id.saveChange);
        saveChangeBtn.setOnClickListener(clickListener);

        myimageview = (CircleImageView) findViewById(R.id.profilePhoto);
        myimageview.setOnClickListener(clickListener);

        geteditname = (EditText) findViewById(R.id.editname);
        geteditname.setOnClickListener(clickListener);

        time = (Spinner) findViewById(R.id.time);

        mode = (SwitchCompat) findViewById(R.id.auto);
        mode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                showSpinner(isChecked);
            }
        });
    }

    protected void saveNewChange() {

        SharedPreferences sp = getSharedPreferences("ProfileName", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("ProfileName", geteditname.getText().toString());
        editor.commit();

        Toast.makeText(this, "Changes Saved !", Toast.LENGTH_SHORT).show();
        return;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    protected void logoutFromAccount() {
        ISystem.resetAccount(this);
        Toast.makeText(this, "Logged Out !", Toast.LENGTH_SHORT).show();
        finishAffinity();
        startActivity(new Intent(SettingsActivity.this, ActivityLogin.class));
    }

    protected void showSpinner(boolean toShow) {
        if (toShow) {
            time.setVisibility(View.VISIBLE);
        } else {
            time.setVisibility(View.GONE);
        }
    }

    private void openImageGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        if (getPackageManager().resolveActivity(intent, 0) != null) {
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), BROWSE_GALLERY_REQUEST_CODE);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BROWSE_GALLERY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    String path = getRealPathFromURI(this, selectedImageUri);

                    SharedPreferences sp = getSharedPreferences("profilePhoto", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("image_path", path);
                    editor.commit();

                    Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
                    myimageview.setImageBitmap(BitmapFactory.decodeFile(path));

                }
            }
        }
    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
