package com.example.capstone_healthpass.Recommend;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.capstone_healthpass.BottomNavigationManager;
import com.example.capstone_healthpass.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.capstone_healthpass.R;
public class RoutineActivity extends Activity {
    private RadioGroup radioGroup;
    private BottomNavigationView bottomNavigationView;

    String str = "";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);
        radioGroup = findViewById(R.id.radiogroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // 선택된 라디오 버튼의 ID를 가져옵니다.
                RadioButton selectedRadioButton = findViewById(checkedId);

                // 선택된 라디오 버튼의 텍스트를 가져옵니다.
                str = selectedRadioButton.getText().toString();





            }
        });
        // 네비게이션 아이템 클릭 리스너 설정
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        // 네비게이션 아이템 클릭 리스너 설정
        BottomNavigationManager navigationManager = new BottomNavigationManager(RoutineActivity.this);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationManager);
    }


    public void clickCheck(View view){
        if (str!="") {
            Intent intent = new Intent(RoutineActivity.this, SelectActivity.class);
            intent.putExtra("name", MainActivity.account.getName());
            intent.putExtra("phone", MainActivity.account.getPhone());
            intent.putExtra("email", MainActivity.account.getEmail());
            intent.putExtra("password", MainActivity.account.getPassword());
            startActivity(intent);//다음 액티비티 화면에 출력

        }
        else{
            Toast.makeText(this, "부위를 선택해주세요", Toast.LENGTH_SHORT).show();
        }

    }

}