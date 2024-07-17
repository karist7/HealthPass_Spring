package com.example.capstone_healthpass.Recommend;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.capstone_healthpass.BottomNavigationManager;
import com.example.capstone_healthpass.DB.DBType;
import com.example.capstone_healthpass.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SelectActivity extends AppCompatActivity {
    String str;
    String receivedData;
    Intent intent;
    DBType dbHelper = null;
    RadioGroup radioGroup;
    String selectedOption="";

    BottomNavigationView bottomNavigationView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        radioGroup=findViewById(R.id.select_radio);
        dbTest();
        // 네비게이션 아이템 클릭 리스너 설정
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        // 네비게이션 아이템 클릭 리스너 설정
        BottomNavigationManager navigationManager = new BottomNavigationManager(SelectActivity.this);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationManager);
        Button button = findViewById(R.id.button27);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),RecommendationActivity.class);
                intent.putExtra("name", selectedOption);
                startActivity(intent);//다음 액티비티 화면에 출력
            }
        });
    }

    @SuppressLint("Range")
    public void dbTest(){
        intent = getIntent();
        receivedData = intent.getStringExtra("name");
        str = receivedData;
        dbHelper = new DBType(SelectActivity.this);
        String query = "SELECT exercise_name FROM info where exercise_area = ?";
        Log.d("query",query);
        Cursor cursor = dbHelper.validRawQuery(query,receivedData);
        if(cursor.moveToFirst()){
            do{
                RadioButton radioButton = new RadioButton(SelectActivity.this);
                radioButton.setText(cursor.getString(cursor.getColumnIndex("exercise_name")));
                radioGroup.addView(radioButton);
                radioButton.setOnClickListener(view -> {
                    // 선택된 라디오 버튼의 텍스트를 가져와서 사용 가능
                    selectedOption = ((RadioButton) view).getText().toString();
                });
            }while(cursor.moveToNext());
        }



    }

}
