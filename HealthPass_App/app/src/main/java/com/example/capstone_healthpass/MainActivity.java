package com.example.capstone_healthpass;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstone_healthpass.Account.JoinActivity;
import com.example.capstone_healthpass.Account.LoginActivity;
import com.example.capstone_healthpass.DB.DataBaseHelper;
import com.example.capstone_healthpass.Recommend.RoutineActivity;
import com.example.capstone_healthpass.Reserve.ReserveDaytimeActivity;
import com.example.capstone_healthpass.server.Account;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    boolean flag;

    public static Account account = new Account();

    private Button join_Btn;
    private Button reserve_btn;
    private Button routine_btn;
    private Button gps_btn;
    private TextView name;
    private Button Health_diary_btn;
    private WeekPlanActivity weekPlanActivity;
    private BottomNavigationView bottomNavigationView;
    private Button login_btn,logout_btn;
    Intent intent;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        account.setName("");
        account.setEmail("");
        account.setPhone("");
        account.setPassword("");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        login_btn=findViewById(R.id.login_btn);
        join_Btn = (Button) findViewById(R.id.register_Btn);
        name=findViewById(R.id.name);
        intent=getIntent();
        logout_btn = findViewById(R.id.logout_btn);
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view.getContext());
                alertDialogBuilder.setMessage("로그아웃 하시겠습니까?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(MainActivity.this, "로그아웃 완료했습니다..", Toast.LENGTH_SHORT).show();
                                account.setName("");
                                account.setEmail("");
                                account.setPhone("");
                                account.setPassword("");

                                Intent logout_intent = new Intent(MainActivity.this, MainActivity.class);
                                login_btn.setVisibility(View.VISIBLE);
                                join_Btn.setVisibility(View.VISIBLE);
                                logout_btn.setVisibility(View.INVISIBLE);
                                startActivity(logout_intent);
                                finish();
                            }
                        });
                alertDialogBuilder.setNegativeButton("No",
                        new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss(); // 취소 버튼을 누르면 팝업을 닫습니다.
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create(); // AlertDialog 생성
                alertDialog.show(); // AlertDialog 표시
            }
        });
        if (intent != null && intent.hasExtra("name")){
            Log.d("accountTest", account.getName());
            account.setName(intent.getStringExtra("name"));
            account.setEmail(intent.getStringExtra("email"));
            account.setPhone(intent.getStringExtra("phone"));
            account.setPassword(intent.getStringExtra("password"));

            name.setText(account.getName());
            login_btn.setVisibility(View.INVISIBLE);
            join_Btn.setVisibility(View.INVISIBLE);
            logout_btn.setVisibility(View.VISIBLE);

        }
        else if(account.getName()!="") {
            name.setText(account.getName());
            Log.d("accountTest2", account.getName());
            Log.d("names",name.getText().toString());
            login_btn.setVisibility(View.INVISIBLE);
            join_Btn.setVisibility(View.INVISIBLE);
            logout_btn.setVisibility(View.VISIBLE);
        }
        else{
            Log.d("accountTest3", account.getName());
            account.setName("");
            name.setText("");
            account.setEmail("");
            account.setPhone("");
            account.setPassword("");
            login_btn.setVisibility(View.VISIBLE);
            join_Btn.setVisibility(View.VISIBLE);
        }


        getDB();
        // 네비게이션 아이템 클릭 리스너 설정
        BottomNavigationManager navigationManager = new BottomNavigationManager(MainActivity.this);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationManager);


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);

                startActivity(intent);//다음 액티비티 화면에 출력

            }
        });

        join_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //다음 액티비티로 가는 것
                //Intent
                Intent intent = new Intent(MainActivity.this, JoinActivity.class);

                startActivity(intent);//다음 액티비티 화면에 출력


            }
        });
        //회원가입 누르면 JoinActivity로 이동


        reserve_btn = (Button) findViewById(R.id.reserve_btn);
        reserve_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //다음 액티비티로 가는 것
                //Intent
                if(account.getName()=="") {
                    Toast.makeText(MainActivity.this, "로그인 후 이용 바랍니다.", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(MainActivity.this, ReserveDaytimeActivity.class);

                    startActivity(intent);//다음 액티비티 화면에 출력

                }
            }
        });
//운동기구 예약 누르면 DaytimeActivity로 이동

        //헬스다이어리 누르면 WeekPlanFragment 로이동


        routine_btn = (Button) findViewById(R.id.routine_btn);
        routine_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RoutineActivity.class);
                intent.putExtra("name",MainActivity.account.getName());
                intent.putExtra("phone", MainActivity.account.getPhone());
                intent.putExtra("email", MainActivity.account.getEmail());
                intent.putExtra("password", MainActivity.account.getPassword());

                startActivity(intent);//다음 액티비티 화면에 출력

            }
        });
//운동추천 누르면 RoutineActivity로 이동


        gps_btn= (Button) findViewById(R.id.gps_btn);
        gps_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,gpsActivity.class);
                startActivity(intent);//다음 액티비티 화면에 출력

            }
        });
        //메인엑티비티에서 스크롤엑티비티로 이동가능하게하는intent 사용



        Health_diary_btn =findViewById(R.id.Health_diary_btn);

// Health_diary_btn 클릭 이벤트 핸들러 설정
        Health_diary_btn.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View v){
                 //기존 버튼들 숨기기

               Intent week = new Intent(MainActivity.this,WeekPlanActivity.class);
               startActivity(week);

            }
        });
    }

    // 뒤로 가기 버튼 또는 다른 방법으로 WeekPlanFragment를 숨길 때 기존 버튼들을 다시 표시
// 예를 들어, WeekPlanFragment에서 뒤로 가기 버튼을 처리할 때
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(!flag){
            join_Btn.setVisibility(View.VISIBLE);
            reserve_btn.setVisibility(View.VISIBLE);
            name.setVisibility(View.VISIBLE);
            logout_btn.setVisibility(View.INVISIBLE);
        }

        // 기존 버튼들 다시 표시
        routine_btn.setVisibility(View.VISIBLE);
        gps_btn.setVisibility(View.VISIBLE);
        Health_diary_btn.setVisibility((View.VISIBLE));
        login_btn.setVisibility(View.VISIBLE);
    }
    @SuppressLint("Range")
    public void getDB(){
        DataBaseHelper dbHelper = new DataBaseHelper(this);
        dbHelper.close();
    }



}
