package com.mirim.a3303;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class InputData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_data);

        EditText title = findViewById(R.id.title);
        EditText key = findViewById(R.id.key);
        EditText contents = findViewById(R.id.contents);

        Button save = findViewById(R.id.save);

        // 19개
        String[] CHO = {"ㄱ","ㄲ","ㄴ","ㄷ","ㄸ","ㄹ","ㅁ","ㅂ","ㅃ", "ㅅ","ㅆ","ㅇ","ㅈ","ㅉ","ㅊ","ㅋ","ㅌ","ㅍ","ㅎ"};
        // 21개
        String[] JOONG = {"ㅏ","ㅐ","ㅑ","ㅒ","ㅓ","ㅔ","ㅕ","ㅖ","ㅗ","ㅘ", "ㅙ","ㅚ","ㅛ","ㅜ","ㅝ","ㅞ","ㅟ","ㅠ","ㅡ","ㅢ","ㅣ"};
        // 28개
        String[] JONG = {"","ㄱ","ㄲ","ㄳ","ㄴ","ㄵ","ㄶ","ㄷ","ㄹ","ㄺ","ㄻ","ㄼ", "ㄽ","ㄾ","ㄿ","ㅀ","ㅁ","ㅂ","ㅄ","ㅅ","ㅆ","ㅇ","ㅈ","ㅊ","ㅋ","ㅌ","ㅍ","ㅎ"};

        // 26개 (+97)
        String[] ALPHA = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        // 6개
        String[] SpeChr = {"~", "!", "?", ".", ",", " "};



        String a = parsingHangul("String contents");




        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titleStr = title.getText().toString();
                String keyStr = key.getText().toString();
                String contentStr = contents.getText().toString();
                titleStr = titleStr.trim();
                keyStr = keyStr.trim();
                contentStr = contentStr.trim();

                if (titleStr.length() > 0 && keyStr.length() > 0 && contentStr.length() > 0) {
                    if(!searchData(titleStr)){ //중복값이 없을때

                        FileOutputStream fos = null;
                        try {
                            fos = openFileOutput(titleStr+".dat", Context.MODE_PRIVATE);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        DataOutputStream dos = new DataOutputStream(fos);

                        try {
                            dos.writeUTF("문자열");
                            dos.flush();
                            dos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Intent gotoMain = new Intent(InputData.this, MainActivity.class);
                        gotoMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(gotoMain);

                    }else{
                        Toast.makeText(InputData.this, "중복된 제목입니다 다른 제목을 입력해주세요!", Toast.LENGTH_SHORT).show();
                    }
                } else if (titleStr.length() == 0){
                    Toast.makeText(InputData.this, "제목을 입력해주세요", Toast.LENGTH_SHORT).show();
                } else if (keyStr.length() == 0){
                    Toast.makeText(InputData.this, "암호키를 입력해주세요", Toast.LENGTH_SHORT).show();
                } else if (contentStr.length() == 0){
                    Toast.makeText(InputData.this, "내용을 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });





    }


    // 쌍자치환 암호화 contents 리턴 메소드
    private String makeCipher(String key, String contents){
        String cipherSentence = null;


        return cipherSentence;
    }

    //
    private String parsingHangul(String contents){
        String hangulText = "안녕하셇요";
        char uniVal = "안".charAt(0);
        char cho = (char)((uniVal-0xAC00)/28/21);
        char joong = (char)((uniVal-0xAC00)/28%21);
        char jong = (char)((uniVal-0xAC00)%28);

        Log.d("",(int)cho + " "+ (int)joong + " "+ (int)jong+" "+(int)'*');


        return hangulText;
    }



    //파일명이 중복인지 판별하는 메서드
    private Boolean searchData(String title){
        Boolean result = false;
        String[] files = fileList();
        for(String filename: files){
            if(filename.equals(title+".dat")){
               result = true;
            }
        }
        return result;
    }
}