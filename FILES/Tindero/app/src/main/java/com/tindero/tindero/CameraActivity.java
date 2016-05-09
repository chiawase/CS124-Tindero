package com.tindero.tindero;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

public class CameraActivity extends AppCompatActivity {

    private ImageView targetImage;
    File outputFile;
    Uri outputFileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        targetImage = (ImageView)findViewById(R.id.targetImageCamera);

        File imagesFolder = new File(Environment.getExternalStorageDirectory(), "MyImages");
        imagesFolder.mkdirs();

        // unique filename based on the time -- SAVE this File path
        outputFile = new File(imagesFolder, System.currentTimeMillis()+".jpg");

        outputFileUri = Uri.fromFile(outputFile);

        Button b = (Button) findViewById(R.id.bTakePhoto);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                startActivityForResult(intent, 0);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inDither = false;
        options.inPurgeable = true;

        Bitmap bitMap = BitmapFactory.decodeFile(outputFile.getAbsolutePath(), options);

        targetImage.setImageBitmap(bitMap);
    }

    public void photoDone (View v){
        UserDbAdapter dbHelper = new UserDbAdapter(this);
        dbHelper.open();

        Intent intent = getIntent();
        String id = intent.getStringExtra(UserDbAdapter.KEY_ROWID);
        String user = intent.getStringExtra(UserDbAdapter.KEY_USERNAME);

        dbHelper.updatePhoto(id, outputFile.getAbsolutePath());

        intent = new Intent(CameraActivity.this, prof.class);
        intent.putExtra(UserDbAdapter.KEY_USERNAME, user);
        startActivity(intent);
    }
}
