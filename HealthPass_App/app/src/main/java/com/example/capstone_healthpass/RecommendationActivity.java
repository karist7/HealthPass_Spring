package com.example.capstone_healthpass;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.capstone_healthpass.DB.DBType;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class RecommendationActivity extends Activity  {
    String receivedData;
    Intent intent;
    DBType dbHelper = null;

    BottomNavigationView bottomNavigationView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);

        dbTest();
        // 네비게이션 아이템 클릭 리스너 설정
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent intent = new Intent(RecommendationActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);//다음 액티비티 화면에


                        break;
                    case R.id.navigation_mypage:
                        if(MainActivity.account.getName()=="") {
                            Toast.makeText(RecommendationActivity.this, "로그인 후 이용 바랍니다.", Toast.LENGTH_SHORT).show();
                        }else {
                            Intent intent1 = new Intent(RecommendationActivity.this, MYpageActivity.class);
                            intent1.putExtra("name",MainActivity.account.getName());
                            intent1.putExtra("phone", MainActivity.account.getPhone());
                            intent1.putExtra("email", MainActivity.account.getEmail());
                            intent1.putExtra("password", MainActivity.account.getPassword());
                            startActivity(intent1);//다음 액티비티 화면에

                            // 예: 마이페이지 화면으로 이동
                        }
                        break;
                    case R.id.navigation_qr_code:
                        Intent intent3 = new Intent(RecommendationActivity.this,ScanQR.class);

                        startActivity(intent3);

                        break;
                }
                return true;
            }
        });
        Button button = findViewById(R.id.button15);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);//다음 액티비티 화면에 출력
            }
        });
    }

    @SuppressLint("Range")
    public void dbTest(){
        intent = getIntent();
        receivedData = intent.getStringExtra("name");

        dbHelper = new DBType(RecommendationActivity.this);
        String query = "SELECT * FROM info where exercise_name = ? ";
        Log.d("query",query);
        Cursor cursor = dbHelper.validRawQuery(query,receivedData);

        String area="",name="",description="";
        if (cursor.moveToFirst()){
            area = cursor.getString(cursor.getColumnIndex("exercise_area"));
            name = cursor.getString(cursor.getColumnIndex("exercise_name"));
            description = cursor.getString(cursor.getColumnIndex("description"));
        }
        TextView textView1 = findViewById(R.id.ex_area);
        TextView textView2 = findViewById(R.id.ex_name);
        TextView textView3 = findViewById(R.id.ex_description);
        TextView textView4 = findViewById(R.id.youtube);
        String newName = name.replace(" ","+");
        String text = "https://www.youtube.com/results?search_query="+newName+"+운동+방법";


        textView1.setText(area);
        textView2.setText(name);
        textView3.setText(description);
        textView4.setText(text);


    }




}