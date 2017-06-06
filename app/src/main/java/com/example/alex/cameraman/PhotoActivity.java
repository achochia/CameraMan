package com.example.alex.cameraman;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by alex on 4/13/16.
 */
public class PhotoActivity extends AppCompatActivity
{
    @Override
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.layout_photo_activity);

        File imagePath = new File(getIntent().getExtras().getString(CameraSession.LAST_IMAGE_PATH_CAMERAMAN));

        if(imagePath != null && imagePath.exists())
        {
            Bitmap myBitMap = BitmapFactory.decodeFile(imagePath.getAbsolutePath());
            ((ImageView) findViewById(R.id.last_photo)).setImageBitmap(myBitMap);
            Log.d("Rotation is", (findViewById(R.id.last_photo)).getRotation() + "");
        }
    }
}
