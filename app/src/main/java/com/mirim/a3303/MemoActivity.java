package com.mirim.a3303;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class MemoActivity extends AppCompatActivity {

    final ArrayList<Integer> usingNum = new ArrayList<Integer>(Arrays.asList(97 , 98 , 99 , 100 , 101 , 102 , 103 , 104 , 105 , 106 , 107 , 108 , 109 , 110 , 111 , 112 , 113 , 114 , 115 , 116 , 117 , 118 , 119 , 120 , 121 , 122 , 200 , 201 , 202 , 203 , 204 , 205 , 206 , 207 , 208 , 209 , 210 , 211 , 212 , 213 , 214 , 215 , 216 , 217 , 218 , 300 , 301 , 302 , 303 , 304 , 305 , 306 , 307 , 308 , 309 , 310 , 311 , 312 , 313 , 314 , 315 , 316 , 317 , 318 , 319 , 320 , 400 , 401 , 402 , 403 , 404 , 405 , 406 , 407 , 408 , 409 , 410 , 411 , 412 , 413 , 414 , 415 , 416 , 417 , 418 , 419 , 420 , 421 , 422 , 423 , 424 , 425 , 426 , 427, 126, 33, 63, 46, 10, 32));
    // 19개 200-218 초성 {"ㄱ","ㄲ","ㄴ","ㄷ","ㄸ","ㄹ","ㅁ","ㅂ","ㅃ", "ㅅ","ㅆ","ㅇ","ㅈ","ㅉ","ㅊ","ㅋ","ㅌ","ㅍ","ㅎ"}
    // 21개 300-320 중성 {"ㅏ","ㅐ","ㅑ","ㅒ","ㅓ","ㅔ","ㅕ","ㅖ","ㅗ","ㅘ", "ㅙ","ㅚ","ㅛ","ㅜ","ㅝ","ㅞ","ㅟ","ㅠ","ㅡ","ㅢ","ㅣ"}
    // 28개 400-427 종성 {"","ㄱ","ㄲ","ㄳ","ㄴ","ㄵ","ㄶ","ㄷ","ㄹ","ㄺ","ㄻ","ㄼ", "ㄽ","ㄾ","ㄿ","ㅀ","ㅁ","ㅂ","ㅄ","ㅅ","ㅆ","ㅇ","ㅈ","ㅊ","ㅋ","ㅌ","ㅍ","ㅎ"}
    // 26개 (+97) 알파벳 {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"}
    // 6개 126, 33, 63, 46, 10, 32 {"~", "!", "?", ".", "\n", ""}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        Intent intent = getIntent();

        TextView tx = findViewById(R.id.secretText);
        Button back = findViewById(R.id.back);
        Animation alphaAnimation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.alpha);

        String fileName = intent.getExtras().getString("fileName")+".dat";
        String key = intent.getExtras().getString("key").toLowerCase();
        ArrayList<Integer[]> keyTable = setBoard(chrToAscii(key));
        ArrayList<Integer[]> numData = new ArrayList<Integer[]>();

        FileInputStream fis = null;
        try {
            fis = openFileInput(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        DataInputStream dis = new DataInputStream(fis);

        try {
            String data = dis.readUTF();
            numData = twinData(data);
            dis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<Integer> decryption = makeDecryption(keyTable,numData);
        String plainText = toRawContents(decryption);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tx.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                tx.setText(plainText);
                tx.startAnimation(alphaAnimation);
                return true;
            }
        });


    }


    // 쌍자치환 복호화 contents 리턴 메소드
    private ArrayList<Integer> makeDecryption(ArrayList<Integer[]> keyBoard, ArrayList<Integer[]> contents){
        ArrayList<Integer> result = new ArrayList<Integer>(); //결과값을 담을 리스트
        int row1,col1; //첫번째 문자의 행열 값을 담을 변수
        int row2,col2; //두번째 문자의 행열 값을 담을 변수

        for (Integer[] twin : contents){ //리스트에서 문자쌍 배열을 읽어옴
            row1=0; col1 = 0; row2=0; col2 = 0; //위치값 초기화
            for(int i = 0; i<10; i++){
                for(int j = 0; j<10; j++){
                    if(keyBoard.get(i)[j].equals(twin[0])){ //첫번째 값이 암호판에 있을경우
                        row1 = i; //행 인덱스 값
                        col1 = j; //열 인덱스 값
                    }
                    if(keyBoard.get(i)[j].equals(twin[1])){ //두번째 값이 암호판에 있을경우
                        row2 = i; //행 인덱스 값
                        col2 = j; //열 인덱스 값
                    }
                }
            }
            if(row1==row2){ //같은 행에 있을경우
                if(col1 == 0){ col1 = 10; } //첫번째 값이 마지막 열일 경우
                if(col2 == 0){ col2 = 10; } //두번째 값이 마지막 열일 경우
                if(keyBoard.get(row1)[col2-1] == 120){ //두번째 값이 x일 경우
                    result.add(keyBoard.get(row1)[col1-1]); // x값을 제외
                }else{
                    result.add(keyBoard.get(row1)[col1-1]); // 위에 있는 값을 추가
                    result.add(keyBoard.get(row1)[col2-1]); // 위에 있는 값을 추가
                }

            }else if(col1 == col2){ //같은 열에 있을경우
                if(row1 == 0){ row1 = 10; } //첫번째 값이 마지막 행일 경우
                if(row2 == 0){ row2 = 10; } //두번째 값이 마지막 행일 경우
                if(keyBoard.get(row2-1)[col1] == 120){ //두번째 값이 x일 경우
                    result.add(keyBoard.get(row1-1)[col1]); // x값을 제외
                }else {
                    result.add(keyBoard.get(row1-1)[col1]);// 왼쪽에 있는 값을 추가
                    result.add(keyBoard.get(row2-1)[col1]);// 왼쪽에 있는 값을 추가
                }

            }else { //다른 행, 열에 있을경우
                if(keyBoard.get(row2)[col1] == 120){ //두번째 값이 x일 경우
                    result.add(keyBoard.get(row1)[col2]);// x값을 제외
                }else{
                    result.add(keyBoard.get(row1)[col2]); // 교차하는 값 추가
                    result.add(keyBoard.get(row2)[col1]); // 교차하는 값 추가
                }
            }

        }

        return result; //암호화 된 문자열 리턴
    }


    //아스키숫자로 된 데이터를 한글로 변환하는 메서드
    private String toRawContents(ArrayList<Integer> numData){
        String rawData = "";
        int i = 0, cho = 0, joong = 0, jong = 0, temp = 0;
        char hangul;

        while (i<numData.size()){
            try{
                if(numData.get(i)>=200 && numData.get(i+1) >=300 && numData.get(i+2) >=400){
                    cho = numData.get(i) - 200;
                    joong = numData.get(i+1) - 300;
                    jong = numData.get(i+2) - 400;
                    hangul = (char) ((cho*21 +joong)*28 +jong + 0xAC00);
                    rawData += hangul;
                    i+=3;
                }else {
                    temp = numData.get(i);
                    rawData += (char)temp;
                    i++;
                }
            }catch (Exception e){
                temp = numData.get(i);
                rawData += (char)temp;
                i++;
            }

        }

        return rawData;
    }

    //문자열을 정수형 배열로 가공하는 메서드
    private ArrayList<Integer[]> twinData(String data){
        ArrayList<Integer[]> twinData = new ArrayList<Integer[]>();
        String subData = data.trim();
        String date[] = subData.split("/");
        for(String twin : date){
            int idx = twin.indexOf(" ");
            twinData.add(new Integer[]{ Integer.valueOf(twin.substring(0, idx)), Integer.valueOf(twin.substring(idx+1))});
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


        for(int i=0; i<100; i++){
            //중복되지 않은 값만 추가
            if(!resultAll.contains(usingNum.get(i)))
                resultAll.add(usingNum.get(i));
        }

        //2차원 배열로 10x10 암호판을 만듦
        ArrayList<Integer[]> results = new ArrayList<Integer[]>();
        for(int i = 0; i<100; i+=10){
            //리스트와 배열을 합친 2차원배열에 행 삽입
            results.add(new Integer[]{resultAll.get(i),resultAll.get(i+1),resultAll.get(i+2),resultAll.get(i+3),resultAll.get(i+4),resultAll.get(i+5),resultAll.get(i+6),resultAll.get(i+7),resultAll.get(i+8),resultAll.get(i+9)});
            Log.d("입력 받은 키로 만든 암호판 ",resultAll.get(i)+" "+resultAll.get(i+1)+" "+resultAll.get(i+2)+" "+resultAll.get(i+3)+" "+resultAll.get(i+4)+" "+resultAll.get(i+5)+" "+resultAll.get(i+6)+" "+resultAll.get(i+7)+" "+resultAll.get(i+8)+" "+resultAll.get(i+9));
        }

        return results; //리턴
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
                }
            }

        }
        return result; //리턴
    }
}