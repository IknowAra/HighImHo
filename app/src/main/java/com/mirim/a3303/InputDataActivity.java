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
import java.util.ArrayList;
import java.util.Arrays;

public class InputDataActivity extends AppCompatActivity {

    final ArrayList<Integer> usingNum = new ArrayList<Integer>(Arrays.asList(97 , 98 , 99 , 100 , 101 , 102 , 103 , 104 , 105 , 106 , 107 , 108 , 109 , 110 , 111 , 112 , 113 , 114 , 115 , 116 , 117 , 118 , 119 , 120 , 121 , 122 , 200 , 201 , 202 , 203 , 204 , 205 , 206 , 207 , 208 , 209 , 210 , 211 , 212 , 213 , 214 , 215 , 216 , 217 , 218 , 300 , 301 , 302 , 303 , 304 , 305 , 306 , 307 , 308 , 309 , 310 , 311 , 312 , 313 , 314 , 315 , 316 , 317 , 318 , 319 , 320 , 400 , 401 , 402 , 403 , 404 , 405 , 406 , 407 , 408 , 409 , 410 , 411 , 412 , 413 , 414 , 415 , 416 , 417 , 418 , 419 , 420 , 421 , 422 , 423 , 424 , 425 , 426 , 427, 126, 33, 63, 46, 44, 32));
    // 19개 200-218
    String[] CHO = {"ㄱ","ㄲ","ㄴ","ㄷ","ㄸ","ㄹ","ㅁ","ㅂ","ㅃ", "ㅅ","ㅆ","ㅇ","ㅈ","ㅉ","ㅊ","ㅋ","ㅌ","ㅍ","ㅎ"};
    // 21개 300-320
    String[] JOONG = {"ㅏ","ㅐ","ㅑ","ㅒ","ㅓ","ㅔ","ㅕ","ㅖ","ㅗ","ㅘ", "ㅙ","ㅚ","ㅛ","ㅜ","ㅝ","ㅞ","ㅟ","ㅠ","ㅡ","ㅢ","ㅣ"};
    // 28개 400-427
    String[] JONG = {"","ㄱ","ㄲ","ㄳ","ㄴ","ㄵ","ㄶ","ㄷ","ㄹ","ㄺ","ㄻ","ㄼ", "ㄽ","ㄾ","ㄿ","ㅀ","ㅁ","ㅂ","ㅄ","ㅅ","ㅆ","ㅇ","ㅈ","ㅊ","ㅋ","ㅌ","ㅍ","ㅎ"};

    // 26개 (+97)
    String[] ALPHA = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    // 6개 126, 33, 63, 46, 44, 32
    String[] SpeChr = {"~", "!", "?", ".", ",", " "};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_data);

        EditText title = findViewById(R.id.title);
        EditText key = findViewById(R.id.key);
        EditText contents = findViewById(R.id.contents);

        Button save = findViewById(R.id.save);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titleStr = title.getText().toString();
                String keyStr = key.getText().toString();
                String contentStr = contents.getText().toString();
                titleStr = titleStr.trim().toLowerCase();
                keyStr = keyStr.trim().toLowerCase();
                contentStr = contentStr.trim().toLowerCase();

                if (titleStr.length() > 0 && keyStr.length() > 0 && contentStr.length() > 0) {
                    if(!searchData(titleStr)){ //중복값이 없을때
                        ArrayList<Integer[]> aaa = addX(chrToAscii(contentStr));
                        ArrayList<Integer[]> keyBoard = (ArrayList<Integer[]>) setBoard(chrToAscii(contentStr));

                        Intent gotoMain = new Intent(InputDataActivity.this, MainActivity.class);
                        gotoMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(gotoMain);

                    }else{
                        Toast.makeText(InputDataActivity.this, "중복된 제목입니다 다른 제목을 입력해주세요!", Toast.LENGTH_SHORT).show();
                    }
                } else if (titleStr.length() == 0){
                    Toast.makeText(InputDataActivity.this, "제목을 입력해주세요", Toast.LENGTH_SHORT).show();
                } else if (keyStr.length() == 0){
                    Toast.makeText(InputDataActivity.this, "암호키를 입력해주세요", Toast.LENGTH_SHORT).show();
                } else if (contentStr.length() == 0){
                    Toast.makeText(InputDataActivity.this, "내용을 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });





    }


    // 내용 각각의 글자의 아스키코드를 담은 리스트를 리턴하는 메서드
    private ArrayList<Integer> chrToAscii(String contents){
        ArrayList<Integer> result = new ArrayList<Integer>(); //결과를 담을 리스트(리턴할 리스트)

        //문자열 길이만큼 반복함(한글자씩 꺼내오기 위함)
        for(int i = 0; i<contents.length(); i++){
            char one = contents.charAt(i); //한글자
            char cho = (char)((one-0xAC00)/28/21); //0~18 사이 +200
            char joong = (char)((one-0xAC00)/28%21); //0~20 사이 +300
            char jong = (char)((one-0xAC00)%28); //0~27 사이 +400

            if((int)cho>=0 && (int)cho<=18 && (int)joong>=0 && (int)joong<=20 && (int)jong>=0 && (int)jong<=27){    //한글일때 처리
                //한글은 아스키코드값으로 나타내지 않음 => 한글 초성중성종성은 여러 자모음이 중복되기 때문에 각각 고유한 값을 주기 위해서
                result.add((int)cho+200); //초성은 200번대
                result.add((int)joong+300); //중성은 300번대
                result.add((int)jong+400); //종성은 400번대
            }else{
                //한글이 아닐때 처리

                if(usingNum.contains((int)one)){ //암호화 대상 글자일경우
                    result.add((int)one); //아스키코드값 삽입
                    Log.d("!",(int)one+"");
                }
            }

        }
        return result; //리턴
    }

    //홀수문자와 연속문자사이에 x를 추가하는 메서드
    private ArrayList<Integer[]> addX(ArrayList<Integer> asciiNum){
        ArrayList<Integer[]> twinData = new ArrayList<Integer[]>(); // 2xn 2차원배열로 쌍자생성
        int i = 1; // while문 처리 변수
        while(asciiNum.size()>i){ // 배열 길이까지 반복
            //중복 글자일 경우
            if(asciiNum.get(i-1)==asciiNum.get(i)){
                twinData.add(new Integer[]{ asciiNum.get(i-1),120 }); //중복 첫글자와 X의 아스키코드 120을 추가
                i++; //X를 추가했기 때문에 1만 추가

            }else{ //중복 글자가 아닐 경우
                twinData.add(new Integer[]{ asciiNum.get(i-1),asciiNum.get(i) }); //두 글자 추가
                i+=2; //둘다 추가했기 때문에 2 추가
            }
        }

        if(asciiNum.size()==i){
            twinData.add(new Integer[]{asciiNum.get(asciiNum.size()-1),120});
        }

        return twinData;
    }


    // 암호판을 2차원 배열로 리턴하는 메서드
    private ArrayList<Integer[]> setBoard(ArrayList<Integer> asciiNum) {

        // 기존데이터 가공, 중복제거
        ArrayList<Integer> resultAll = new ArrayList<Integer>(); //결과를 담을 리스트
        for(int num : asciiNum){
            //처리하지 않는 문자와 중복된 문자를 제외하고
            if(!resultAll.contains(num)){
                resultAll.add(num); //데이터 추가
            }
        }
        // 나머지 값들을 추가
        for(int item : usingNum){
            //중복되지 않은 값만 추가
            if(!resultAll.contains(item))
                resultAll.add(item);
        }

        //2차원 배열로 10x10 암호판을 만듦
        ArrayList<Integer[]> results = new ArrayList<Integer[]>();
        for(int i = 0; i<100; i+=10){
            //리스트와 배열을 합친 2차원배열에 행 삽입
            results.add(new Integer[]{resultAll.get(i),resultAll.get(i+1),resultAll.get(i+2),resultAll.get(i+3),resultAll.get(i+4),resultAll.get(i+5),resultAll.get(i+6),resultAll.get(i+7),resultAll.get(i+8),resultAll.get(i+9)});
        }

        return results; //리턴
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

    //파일을 저장하는 메서드
    private void saveFile(String title, String contents){
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(title+".dat", Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        DataOutputStream dos = new DataOutputStream(fos);

        try {
            dos.writeUTF(contents);
            dos.flush();
            dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}