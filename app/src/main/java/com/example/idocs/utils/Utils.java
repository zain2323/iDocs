package com.example.idocs.utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.example.idocs.BuildConfig;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {
    private Context context;

    public Utils(Context context) {
        this.context = context;
    }


    public Context getContext() {
        return context;
    }

    public String getFileName(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageInByte = baos.toByteArray();
        imageInByte = digest(imageInByte);
        return bytesToHex(imageInByte);
    }


    public String saveToInternalStorage(Bitmap bitmapImage, String filename, String extension, String dir) {
        ContextWrapper cw = new ContextWrapper(getContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir(dir, Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, filename + extension);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    public Uri savePDFToInternalStorage(byte[] ByteArray, String filename) throws IOException {
        FileOutputStream fileOutputStream = getContext().openFileOutput(filename, Context.MODE_PRIVATE);
        fileOutputStream.write(ByteArray);
        Toast.makeText(getContext(), "SAVED PDF", Toast.LENGTH_SHORT).show();
        fileOutputStream.close();
        File file = new File(filename);
        Uri uri = FileProvider.getUriForFile(context.getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider",file);
        return uri;
    }

    public Uri savePDFToInternalStorageV2(byte[] ByteArray, String filename, String dir) throws IOException {
        ContextWrapper cw = new ContextWrapper(getContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir(dir, Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, filename);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            fos.write(ByteArray);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Uri uri = FileProvider.getUriForFile(context.getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider",new File(directory.getAbsolutePath(), filename));
        return uri;
    }

    public Bitmap loadImageFromStorage(String path, String filename) {

        try {
            File f = new File(path, filename);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            return b;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] digest(byte[] input) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
        byte[] result = md.digest(input);
        return result;
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public byte[] getPdfBytes(Uri uri)
    {
        try {
            ParcelFileDescriptor pdf = getContext().getContentResolver().openFileDescriptor(uri, "r");
            FileDescriptor fileDescriptor = pdf.getFileDescriptor();
            byte[] data = new byte[(int) pdf.getStatSize()];
            FileInputStream fileInputStream = new FileInputStream(fileDescriptor);
            fileInputStream.read(data);
            return data;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
