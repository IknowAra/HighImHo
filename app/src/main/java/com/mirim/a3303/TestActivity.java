package com.mirim.a3303;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class TestActivity extends AppCompatActivity {
    final ArrayList<String> usingStr = new ArrayList<String>(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"));
    int[] tableId = {R.id.table1, R.id.table2, R.id.table3, R.id.table4, R.id.table5, R.id.table6, R.id.table7, R.id.table8, R.id.table9, R.id.table10, R.id.table11, R.id.table12, R.id.table13, R.id.table14, R.id.table15, R.id.table16, R.id.table17, R.id.table18, R.id.table19, R.id.table20, R.id.table21, R.id.table22, R.id.table23,R.id.table24,R.id.table25};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Button startBtn = findViewById(R.id.startBtn);
        Button back = findViewById(R.id.back);

        EditText pureText = findViewById(R.id.pureText);
        EditText key = findViewById(R.id.secretKey);


        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pureStr = pureText.getText().toString();
                String keyStr = key.getText().toString();
                pureStr = makeUsingStr(pureStr.trim().toLowerCase());
                keyStr = makeUsingStr(keyStr.trim().toLowerCase());
                if (pureStr.length() > 0 && keyStr.length() > 0) {
                    ArrayList<String[]> keyTable = setBoard(keyStr);


                }else {
                    Toast.makeText(TestActivity.this, "영어로 값을 입력해주세요", Toast.LENGTH_SHORT).show();
                }

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private String makeUsingStr(String text){
        String result = "";
        for(int i = 0; i<text.length(); i++) {
            String one = String.valueOf(text.charAt(i));
            if(usingStr.contains(one) || one.equals(" ")){
                result += one;
            }
        }

        return result;
    }

    private ArrayList<String[]> setBoard(String key) {
        ArrayList<String> resultAll = new ArrayList<String>(); //결과를 담을 리스트


        for(int i = 0; i<key.length(); i++) {
            String one = String.valueOf(key.charAt(i));
            if(!resultAll.contains(one)){
                resultAll.add(one);
            }
        }

        for(int i=0; i<usingStr.size(); i++){
            //중복되지 않은 값만 추가
            if(!resultAll.contains(usingStr.get(i)))
                resultAll.add(usingStr.get(i));
        }

        boolean qz = true;
        for(int i=0; i<resultAll.size(); i++){
            if(resultAll.get(i).equals("q")||resultAll.get(i).equals("z"))
                if(qz == true){
                    resultAll.set(i, "q/z");
                    qz = false;
                }else {
                    resultAll.remove(i);
                }
        }

        //2차원 배열로 5x5 암호판을 만듦
        TextView tableTv;
        ArrayList<String[]> results = new ArrayList<String[]>();
        for(int i = 0; i<25; i+=5){
            //리스트와 배열을 합친 2차원배열에 행 삽입
            results.add(new String[]{resultAll.get(i),resultAll.get(i+1),resultAll.get(i+2),resultAll.get(i+3),resultAll.get(i+4)});
            Log.d("test",resultAll.get(i)+resultAll.get(i+1)+resultAll.get(i+2)+resultAll.get(i+3)+resultAll.get(i+4));
            tableTv = (TextView) findViewById(tableId[i]); tableTv.setText(resultAll.get(i));
            tableTv = (TextView) findViewById(tableId[i+1]); tableTv.setText(resultAll.get(i+1));
            tableTv = (TextView) findViewById(tableId[i+2]); tableTv.setText(resultAll.get(i+2));
            tableTv = (TextView) findViewById(tableId[i+3]); tableTv.setText(resultAll.get(i+3));
            tableTv = (TextView) findViewById(tableId[i+4]); tableTv.setText(resultAll.get(i+4));
        }

        return results; //리턴
    }

}