package org.aplusscreators.hakikisha.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;

public class FileUtils {

    private static final String TAG = "FileUtils";

    public static File createFileInternalStorageFile(Context context, String filename) {
        File internalFileDir = context.getFilesDir();
        return new File(internalFileDir, filename);
    }

    public static File createImageFile(Context context, String fileName, String extension) {
        try {
            File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File file = File.createTempFile(fileName, extension, storageDir);
            return file;
        } catch (Exception ex) {
            Log.e(TAG, "createImageFile: " + ex);
            return null;
        }
    }

    public static File createVideoFile(Context context, String fileName, String extension) {
        try {
            File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_MOVIES);
            File file = File.createTempFile(fileName, extension, storageDir);
            return file;
        } catch (Exception ex) {
            Log.e(TAG, "createVideoFile: " + ex);
            return null;
        }
    }
}
