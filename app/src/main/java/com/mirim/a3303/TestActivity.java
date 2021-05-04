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
import java.util.List;

public class TestActivity extends AppCompatActivity {
    final ArrayList<String> usingStr = new ArrayList<String>(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"));
    int[] tableId = {R.id.table1, R.id.table2, R.id.table3, R.id.table4, R.id.table5, R.id.table6, R.id.table7, R.id.table8, R.id.table9, R.id.table10, R.id.table11, R.id.table12, R.id.table13, R.id.table14, R.id.table15, R.id.table16, R.id.table17, R.id.table18, R.id.table19, R.id.table20, R.id.table21, R.id.table22, R.id.table23,R.id.table24,R.id.table25};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Button startBtn = findViewById(R.id.startBtn);
        Button back = findViewById(R.id.back);
        Button decryptionBtn = findViewById(R.id.decryptionBtn);

        EditText pureText = findViewById(R.id.pureText);
        EditText key = findViewById(R.id.secretKey);


        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pureStr = pureText.getText().toString();
                String keyStr = key.getText().toString();
                pureStr = makeUsingStr(pureStr.trim().toLowerCase());
                keyStr = makeUsingStr(keyStr.trim().toLowerCase());
                String withoutSpace = "";
                String spaceIdx = "";
                int count = 0;

                if (pureStr.length() > 0 && keyStr.length() > 0) {
                    for(int i = 0; i<pureStr.length(); i++){
                        String one = String.valueOf(pureStr.charAt(i));
                        if(one.equals(" ")){
                            spaceIdx += (i-count)+" ";
                            count++;
                        }else{
                            withoutSpace += one;
                        }
                    }

                    ArrayList<String[]> keyTable = setBoard(keyStr);
                    ArrayList<String[]> twinData = addX(withoutSpace);
                    ArrayList<String[]> cipherTwinData = makeCipher(keyTable, twinData);

                    String finalSpaceIdx = spaceIdx;
                    decryptionBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            makeDecryption(keyTable, cipherTwinData, finalSpaceIdx);
                        }
                    });

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

    //홀수문자와 연속문자사이에 x를 추가하는 메서드
    private ArrayList<String[]> addX(String text){
        ArrayList<String[]> twinData = new ArrayList<String[]>(); // 2xn 2차원배열로 쌍자생성
        String showText = "";
        TextView tpd = findViewById(R.id.twinPureData);


        int i = 1; // while문 처리 변수
        while(text.length()>i){ // 배열 길이까지 반복

            //중복 글자일 경우
            if(String.valueOf(text.charAt(i-1)).equals(String.valueOf(text.charAt(i)))){
                twinData.add(new String[]{ String.valueOf(text.charAt(i-1)),"x" }); //중복 첫글자와 X의 아스키코드 120을 추가
                showText += String.valueOf(text.charAt(i-1))+"x"+"/";
                i++; //X를 추가했기 때문에 1만 추가

            }else{ //중복 글자가 아닐 경우
                twinData.add(new String[]{ String.valueOf(text.charAt(i-1)),String.valueOf(text.charAt(i)) }); //두 글자 추가
                showText += String.valueOf(text.charAt(i-1))+String.valueOf(text.charAt(i))+"/";
                i+=2; //둘다 추가했기 때문에 2 추가
            }
        }

        if(text.length()==i){
            twinData.add(new String[]{String.valueOf(text.charAt(i-1)),"x"});
            showText += String.valueOf(text.charAt(i-1))+"x";
        }
        tpd.setText(showText);

        return twinData;
    }

    //암호화 메소드
    private ArrayList<String[]> makeCipher(ArrayList<String[]> keyBoard, ArrayList<String[]> contents){
        String cipherSentence = ""; //결과값을 담을 문자열 변수
        ArrayList<String[]> twinData = new ArrayList<String[]>();
        String Zidx = "";
        TextView cipher = findViewById(R.id.cipherText);
        int row1,col1; //첫번째 문자의 행열 값을 담을 변수
        int row2,col2; //두번째 문자의 행열 값을 담을 변수

        for (String[] twin : contents){ //리스트에서 문자쌍 배열을 읽어옴
            row1=0; col1 = 0; row2=0; col2 = 0; //위치값 초기화
            if(twin[0].equals("z")){
                twin[0] = "q";
                Zidx += contents.indexOf(twin)*2+" ";
                Log.d("df",Zidx);

            }else if(twin[1].equals("z")){
                twin[1] = "q";
                Zidx += contents.indexOf(twin)*2+1+" ";
                Log.d("df",Zidx);
            }

            for(int i = 0; i<5; i++){
                for(int j = 0; j<5; j++){
                    if(keyBoard.get(i)[j].equals("q|z")){
                        keyBoard.get(i)[j] = "q";
                    }

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

            if(row1 == row2){ //같은 행에 있을경우
                if(col1 == 4){ col1 = -1; } //첫번째 값이 마지막 열일 경우
                if(col2 == 4){ col2 = -1; } //두번째 값이 마지막 열일 경우

                cipherSentence +=  keyBoard.get(row1)[col1+1] + keyBoard.get(row1)[col2+1]; // 각각 밑에 있는 값을 추가
                twinData.add(new String[]{keyBoard.get(row1)[col1+1],keyBoard.get(row1)[col2+1]});

            }else if(col1 == col2){ //같은 열에 있을경우
                if(row1 == 4){ row1 = -1; } //첫번째 값이 마지막 행일 경우
                if(row2 == 4){ row2 = -1; } //두번째 값이 마지막 행일 경우

                cipherSentence +=  keyBoard.get(row1+1)[col1] + keyBoard.get(row2+1)[col1]; // 각각 오른쪽에 있는 값을 추가
                twinData.add(new String[]{keyBoard.get(row1+1)[col1],keyBoard.get(row2+1)[col1]});

            }else { //다른 행, 열에 있을경우
                cipherSentence +=  keyBoard.get(row2)[col1]+ keyBoard.get(row1)[col2]; // 각각 교차하는 값 추가
                twinData.add(new String[]{keyBoard.get(row2)[col1],keyBoard.get(row1)[col2]});
            }

            cipherSentence += "/"; // 두개씩 묶어서 저장, 구분하기 위함
        }
        cipher.setText(cipherSentence);

        twinData.add(new String[]{Zidx});
        return twinData; //암호화 된 문자열 리턴
    }

    //복호화 메소드
    private String makeDecryption(ArrayList<String[]> keyBoard, ArrayList<String[]> contents, String spaceIdx){
        String decryptionSentence = ""; //결과값을 담을 문자열 변수
        TextView dtv = findViewById(R.id.decryption);

        int row1,col1; //첫번째 문자의 행열 값을 담을 변수
        int row2,col2; //두번째 문자의 행열 값을 담을 변수

        String Zidx = contents.get(contents.size()-1)[0];
        Zidx = Zidx.trim();
        contents.remove(contents.size()-1);
        List<String> listZ = Arrays.asList(Zidx.split(" "));
        List<String> listSpace = Arrays.asList(spaceIdx.split(" "));

        for (String[] twin : contents){ //리스트에서 문자쌍 배열을 읽어옴
            row1=0; col1 = 0; row2=0; col2 = 0; //위치값 초기화

            for(int i = 0; i<5; i++){
                for(int j = 0; j<5; j++){
                    if(keyBoard.get(i)[j].equals("q|z")){
                        keyBoard.get(i)[j] = "q";
                    }

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

            if(row1 == row2){ //같은 행에 있을경우
                if(col1 == 0){ col1 = 5; } //첫번째 값이 마지막 열일 경우
                if(col2 == 0){ col2 = 5; } //두번째 값이 마지막 열일 경우
                if(keyBoard.get(row1)[col2-1].equals("x")){ //두번째 값이 x일 경우
                    decryptionSentence +=  keyBoard.get(row1)[col1-1]+" "; // x값을 제외
                }else{
                    decryptionSentence +=  keyBoard.get(row1)[col1-1] + keyBoard.get(row1)[col2-1];
                }

            }else if(col1 == col2){ //같은 열에 있을경우
                if(row1 == 0){ row1 = 5; } //첫번째 값이 마지막 행일 경우
                if(row2 == 0){ row2 = 5; } //두번째 값이 마지막 행일 경우
                if(keyBoard.get(row2-1)[col1].equals("x")){ //두번째 값이 x일 경우
                    decryptionSentence +=  keyBoard.get(row1-1)[col1]+" "; // x값을 제외
                }else{
                    decryptionSentence +=  keyBoard.get(row1-1)[col1] + keyBoard.get(row2-1)[col1];
                }

            }else { //다른 행, 열에 있을경우
                if(keyBoard.get(row1)[col2].equals("x")){ //두번째 값이 x일 경우
                    decryptionSentence +=  keyBoard.get(row2)[col1]+" "; // x값을 제외
                }else{
                    decryptionSentence +=  keyBoard.get(row2)[col1]+ keyBoard.get(row1)[col2]; // 각각 교차하는 값 추가
                }
            }
        }
        String Zcheck = "";
        for(int i = 0; i<decryptionSentence.length();i++){
            if(listZ.contains(i+"")){
                Zcheck += "z";
            }else if(!String.valueOf(decryptionSentence.charAt(i)).equals(" ")){
                Zcheck += decryptionSentence.charAt(i);
            }
        }
        String result = "";
        for (int i = 0; i<Zcheck.length(); i++){
            if(listSpace.contains(i+"")){
                result+= " ";
            }
            result += Zcheck.charAt(i);
        }


        dtv.setText(result);

        return result; //암호화 된 문자열 리턴
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
                    resultAll.set(i, "q|z");
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