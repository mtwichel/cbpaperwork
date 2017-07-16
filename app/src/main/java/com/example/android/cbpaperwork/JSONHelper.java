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

public class JSONHelper {

    public static final String FILE_FOLDER = "citybrew/";
    public static final String FILE_EXTENSION = ".json";
    public static final String FILE_NAME = "data.json";
    public static final String TAG = "JSONHelper";

    private static Map<String, PaperworkData> datas = new HashMap<>();


    public static boolean exportToJSON(Context context, PaperworkData data){
        datas.put(data.getId(), data);

        DataConverter cDatas = new DataConverter();
        cDatas.setData(datas);

        Gson gson = new Gson();

        String jsonString = gson.toJson(cDatas);
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

    public static PaperworkData importFromJSON(Context context, String id){
        FileReader fileReader = null;
        File file = new File(Environment.getExternalStorageDirectory(), FILE_NAME);

        try {
            fileReader = new FileReader(file);
            Gson gson = new Gson();
            DataConverter cData = gson.fromJson(fileReader, DataConverter.class);
            PaperworkData data = cData.getData().get(id);
            return data;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
//            exportToJSON(context, new PaperworkData(id));
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

    public static Map<String, PaperworkData> importAllFromJSON(Context context){
        FileReader fileReader = null;
        File file = new File(Environment.getExternalStorageDirectory(), FILE_NAME);

        try {
            fileReader = new FileReader(file);
            Gson gson = new Gson();
            DataConverter cData = gson.fromJson(fileReader, DataConverter.class);
            Map<String, PaperworkData> data = cData.getData();
            return data;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
//            exportToJSON(context, new PaperworkData(id));
            return new HashMap<>();
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

    static class DataConverter{
        Map<String, PaperworkData> data;

        public Map<String, PaperworkData> getData() {
            return data;
        }

        public void setData(Map<String, PaperworkData> data) {
            this.data = data;
        }
    }
}
