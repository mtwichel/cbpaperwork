package com.example.android.cbpaperwork;

import android.content.Context;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data.Date;
import data.PaperworkData;
import data.PaperworkDataWrapper;

public class JSONHelper {

    public static final String FILE_FOLDER = "citybrew/";
    public static final String FILE_EXTENSION = ".json";
    public static final String FILE_NAME = "data.json";
    public static final String TAG = "JSONHelper";


    public static boolean exportToJSON(Context context, PaperworkDataWrapper data) {
        Gson gson = new Gson();

        String jsonString = gson.toJson(data);
        Log.i(TAG, "export to Json" + jsonString);

        FileOutputStream fileOutputStream= null;
        File file = new File(Environment.getExternalStorageDirectory(), FILE_NAME );

        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(jsonString.getBytes());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

    public static PaperworkDataWrapper importFromJSON(Context context) {
        FileReader fileReader = null;
        File file = new File(Environment.getExternalStorageDirectory(), FILE_NAME);

        try {
            fileReader = new FileReader(file);
            Gson gson = new Gson();
            PaperworkDataWrapper datas = gson.fromJson(fileReader, PaperworkDataWrapper.class);
            return datas;

        } catch (FileNotFoundException e) {
            exportToJSON(context, new PaperworkDataWrapper());
            return importFromJSON(context);

        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }


    public static PaperworkData importFromJSON(Context context, Integer id) {
        FileReader fileReader = null;
        File file = new File(Environment.getExternalStorageDirectory(), FILE_NAME);

        try {
            fileReader = new FileReader(file);
            Gson gson = new Gson();
            PaperworkDataWrapper datas = gson.fromJson(fileReader, PaperworkDataWrapper.class);
            return datas.getData(id);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            exportToJSON(context, new PaperworkDataWrapper());
            Toast.makeText(context, "Made File", Toast.LENGTH_SHORT).show();
            return null;
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }


    public static void deleteJson() {
        File file = new File(Environment.getExternalStorageDirectory(), FILE_NAME);
        file.delete();
    }
}
