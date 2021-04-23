package com.mirim.a3303;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class MemoActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_memo);

        Intent intent = getIntent();

        String fileName = intent.getExtras().getString("fileName");
        String key = intent.getExtras().getString("key");
        //ArrayList<Integer[]> secretPlate = (ArrayList<Integer[]>) chrToAscii(key);

        FileInputStream fis = null;
        try {
            fis = openFileInput(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        DataInputStream dis = new DataInputStream(fis);

        try {
            String data = dis.readUTF();

            dis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    // 암호판을 2차원 배열로 리턴하는 메서드
    private static ArrayList<Integer[]> setBoard(ArrayList<Integer> asciiNum) {
        //암호판을 만들 문자열의 아스키코드 값만 모아둔 리스트
        final ArrayList<Integer> usingNum = new ArrayList<Integer>(Arrays.asList(97 , 98 , 99 , 100 , 101 , 102 , 103 , 104 , 105 , 106 , 107 , 108 , 109 , 110 , 111 , 112 , 113 , 114 , 115 , 116 , 117 , 118 , 119 , 120 , 121 , 122 , 200 , 201 , 202 , 203 , 204 , 205 , 206 , 207 , 208 , 209 , 210 , 211 , 212 , 213 , 214 , 215 , 216 , 217 , 218 , 300 , 301 , 302 , 303 , 304 , 305 , 306 , 307 , 308 , 309 , 310 , 311 , 312 , 313 , 314 , 315 , 316 , 317 , 318 , 319 , 320 , 400 , 401 , 402 , 403 , 404 , 405 , 406 , 407 , 408 , 409 , 410 , 411 , 412 , 413 , 414 , 415 , 416 , 417 , 418 , 419 , 420 , 421 , 422 , 423 , 424 , 425 , 426 , 427, 126, 33, 63, 46, 44, 32));

        // 기존데이터 가공, 중복제거
        ArrayList<Integer> resultAll = new ArrayList<Integer>(); //결과를 담을 리스트
        for(int num : asciiNum){
            //처리하지 않는 문자와 중복된 문자를 제외하고
            if(usingNum.contains(num) && !resultAll.contains(num)){
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

        return results; //암호판 리턴
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
                result.add((int)one); //아스키코드값 그대로를 삽입
            }

        }

        return result; //리턴
    }
}