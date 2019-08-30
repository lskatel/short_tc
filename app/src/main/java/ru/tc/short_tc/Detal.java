package ru.tc.short_tc;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

public class Detal extends AppCompatActivity {
    Logger log = Logger.getLogger(Detal.class.getName());

    private static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_ID_READ_WRITE_PERMISSION = 99;
    private String mCurrentPhotoPath;

    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detal);

        ImageButton btn = findViewById(R.id.btn_prev2);
        btn.setOnClickListener(new PrevClick());

        ImageButton btnFind = findViewById(R.id.btn_prev);
        btnFind.setOnClickListener(new FindClick());

        image = findViewById(R.id.img);
        image.setOnClickListener(new ImageClick());
    }


    private class PrevClick implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Detal.this, Structure.class);
            startActivity(intent);
        }
    }

    private class FindClick implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Detal.this, MainActivity.class);
            startActivity(intent);
        }
    }

    private class ImageClick implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (Build.VERSION.SDK_INT >= 23) {
                int readPermission = ActivityCompat.checkSelfPermission(Detal.this, Manifest.permission.READ_EXTERNAL_STORAGE);
                int writePermission = ActivityCompat.checkSelfPermission(Detal.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

                if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED) {
                    Detal.this.requestPermissions(
                            new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_ID_READ_WRITE_PERMISSION);
                    return;
                }
            }

            photo();
        }
    }

    private void photo() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_TAKE_PHOTO);
            /*File photo_file = null;

            try {
                photo_file = createImageFile();
            } catch (IOException e) {
                Toast.makeText(Detal.this, "Error!", Toast.LENGTH_SHORT).show();
            }

            if (photo_file != null) {
                log.info("123456");

                //Uri photoUri = FileProvider.getUriForFile(Detal.this, "ru.tc.short_tc.fileprovider", photo_file);*/
                //intent.putExtra(MediaStore.EXTRA_OUTPUT, mCurrentPhotoPath);

            /*} else {
                Toast.makeText(this, mCurrentPhotoPath, Toast.LENGTH_LONG).show();
            }*/
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            image.setImageBitmap(bitmap);

            /*Uri uri = Uri.parse(mCurrentPhotoPath);
            image.setImageURI(uri);*/
        }
    }

    /*private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        //File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DCIM + "/");
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }*/

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_ID_READ_WRITE_PERMISSION) {
            if (grantResults.length > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED
            && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted!", Toast.LENGTH_LONG).show();
                photo();
            } else {
                Toast.makeText(this, "Permission denied!", Toast.LENGTH_LONG).show();
            }
        }
    }
}
