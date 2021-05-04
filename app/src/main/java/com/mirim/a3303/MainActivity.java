package com.mirim.a3303;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listview = (ListView) findViewById(R.id.listview);
        final ArrayList<String> list = new ArrayList<>();

        String[] files = fileList();

        for (int i = 0; i < files.length; i++) {
            list.add(files[i].substring(0,files[i].length()-4));
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long id) {
                String selected_item = (String) adapterView.getItemAtPosition(position);

                final EditText et = new EditText(MainActivity.this);
                FrameLayout container = new FrameLayout(MainActivity.this);
                FrameLayout.LayoutParams params = new  FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.leftMargin = getResources().getDimensionPixelSize(R.dimen.dialog_margin);
                params.rightMargin = getResources().getDimensionPixelSize(R.dimen.dialog_margin);
                et.setLayoutParams(params);
                container.addView(et);
                final AlertDialog.Builder alt_bld = new AlertDialog.Builder(MainActivity.this,R.style.MyAlertDialogStyle);
                alt_bld.setTitle("암호키 입력").setMessage("\n  쉿! 신중하게 입력하세요~\n\n").setIcon(R.drawable.ic_house_key).setCancelable(
                        true).setView(container).setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String key = et.getText().toString();


                                Intent gotoSecret = new Intent(MainActivity.this, MemoActivity.class);
                                gotoSecret.putExtra("fileName",selected_item);
                                gotoSecret.putExtra("key",key);

                                startActivity(gotoSecret);
                            }
                        });
                AlertDialog alert = alt_bld.create();
                alert.show();

            }
        });


        // InputData로 이동 버튼
        Button add_post = findViewById(R.id.add_post);
        add_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoInputData = new Intent(getApplicationContext(), InputDataActivity.class);
                startActivity(gotoInputData);
            }
        });

        Button goto_test = findViewById(R.id.goto_Test);
        goto_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoTest = new Intent(getApplicationContext(), TestActivity.class);
                startActivity(gotoTest);
            }
        });




    }
}