package com.example.alex.cameraman;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraSession extends AppCompatActivity
{
    private int TAKE_PHOTO_CODE = 1;
    private String mImage;
    public static final String LAST_IMAGE_PATH_CAMERAMAN = "LAST_IMAGE_PATH_CAMERAMAN";

    //    public static int count = 0;
    private static final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/cameraman";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_session);

        File newDir = new File(dir);
        Log.d("Folder path", newDir.getAbsolutePath());

        if (!newDir.exists())
        {
            Log.d("Folder", "NULL");
            if (!newDir.mkdirs())
            {
                Log.d("File made", "failed! ");
            }
        }
    }

    public void takePicture(View v)
    {
        File newFile = null;
        try
        {
            newFile = createImageFile();
        }
        catch (Exception e)
        {
            Log.e("FILE CREATION FAILURE: ", e.getMessage());
        }

        if (newFile != null)
        {
            Uri outputFile = Uri.fromFile(newFile);
            mImage = newFile.getAbsolutePath();
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFile);
            if (cameraIntent.resolveActivity(getPackageManager()) != null)
            {
                startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
            }
        }
    }

    public void showPicture(View v)
    {
        if(mImage != null)
        {
            Intent newIntent = new Intent(this, PhotoActivity.class);
            newIntent.putExtra(LAST_IMAGE_PATH_CAMERAMAN, mImage);

            if (newIntent.resolveActivity(getPackageManager()) != null)
            {
                startActivity(newIntent);
            }
        }
        else
        {
            (Toast.makeText(this, "No photo to show", Toast.LENGTH_SHORT)).show();
        }
    }

    @Override
    protected void onActivityResult(int reqCode, int resCode, Intent data)
    {
        super.onActivityResult(reqCode, resCode, data);
        if (reqCode == TAKE_PHOTO_CODE && resCode == RESULT_OK)
        {
            Log.d("CameraMan", "Picture saved");
        }
    }

    private File createImageFile() throws IOException
    {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);

        if(storageDir.exists())
            Log.d("storageDit", "exists");
        // Save a file: path for use with ACTION_VIEW intents
        return File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
    }
}