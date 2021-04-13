package com.mirim.a3303;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.nio.Buffer;
import java.util.ArrayList;

import static android.os.Environment.getExternalStorageDirectory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listview = (ListView) findViewById(R.id.listview);
        final ArrayList<String> list = new ArrayList<>();

        checkVerify();


        for (int i = 0; i < 50; i++) {
            list.add("item " + i);
        }


        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long id) {
                String selected_item = (String) adapterView.getItemAtPosition(position);
                list.remove(selected_item);
                adapter.notifyDataSetChanged();
            }
        });

        FileWriter writer;

        try {
            File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/myApp");
            if (!dir.exists()) {
                Log.d("  ",getExternalStorageDirectory().getAbsolutePath()+"");
                dir.mkdirs(); // 폴더가 없을경우 생성
                Log.d("  ",getExternalStorageDirectory().getAbsolutePath()+"");
            }

            // myApp 폴더 밑에 myfile.txt 파일 지정
            File file = new File(dir + "/myfile.txt");

            if (!file.exists()) {
                file.createNewFile(); // 파일이 없을 경우 생성
            }

            // 파일에 쓰기
            writer = new FileWriter(file, true);
            writer.write("content");
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }







    }
    public void checkVerify() {
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) { }
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }
}