package com.example.capstone_healthpass;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstone_healthpass.DB.DBType;
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
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent intent = new Intent(SelectActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);//다음 액티비티 화면에


                        break;
                    case R.id.navigation_mypage:
                        if(MainActivity.userName=="") {
                            Toast.makeText(SelectActivity.this, "로그인 후 이용 바랍니다.", Toast.LENGTH_SHORT).show();
                        }else {
                            Intent intent1 = new Intent(SelectActivity.this, MYpageActivity.class);
                            startActivity(intent1);//다음 액티비티 화면에

                            // 예: 마이페이지 화면으로 이동
                        }
                        break;
                    case R.id.navigation_qr_code:
                        Intent intent3 = new Intent(SelectActivity.this,ScanQR.class);

                        startActivity(intent3);

                        break;
                }
                return true;
            }
        });
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
