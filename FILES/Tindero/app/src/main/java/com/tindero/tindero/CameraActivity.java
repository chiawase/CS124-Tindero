package com.tindero.tindero;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class CameraActivity extends AppCompatActivity {

    private TextView textTargetUri;
    private ImageView targetImage;
    File outputFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        textTargetUri = (TextView)findViewById(R.id.targetUriCamera);
        targetImage = (ImageView)findViewById(R.id.targetImageCamera);

        File sdCard = Environment.getExternalStorageDirectory();
        File directory = new File (sdCard.getAbsolutePath() + "/CameraTest/");

        // unique filename based on the time -- SAVE this File path
        outputFile = new File(directory, System.currentTimeMillis()+".jpg");

        Uri outputFileUri = Uri.fromFile(outputFile);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

        startActivityForResult(intent, 0);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;
                options.inDither = false;
                options.inPurgeable = true;

                Bitmap bitMap = BitmapFactory.decodeFile(outputFile.getAbsolutePath(), options);

                textTargetUri.setText(outputFile.getAbsolutePath());

                //bitMap = Bitmap.createScaledBitmap(bitMap, imageView.getWidth(), imageView.getHeight(), true);
                targetImage.setImageBitmap(bitMap);
            } catch(Exception e) {
                textTargetUri.setText(e.getClass().getName());
            }
        }
    }

    public void photoDone (View v){
        UserDbAdapter dbHelper = new UserDbAdapter(this);
        dbHelper.open();

        Intent intent = getIntent();
        String id = intent.getStringExtra(UserDbAdapter.KEY_ROWID);
        String user = intent.getStringExtra(UserDbAdapter.KEY_USERNAME);

        dbHelper.updatePhoto(outputFile.getAbsolutePath(), id);

        intent = new Intent(CameraActivity.this, prof.class);
        intent.putExtra(UserDbAdapter.KEY_USERNAME, user);
        startActivity(intent);
    }
}
