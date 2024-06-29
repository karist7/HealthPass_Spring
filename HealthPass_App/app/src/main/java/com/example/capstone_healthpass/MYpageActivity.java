package com.example.capstone_healthpass;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstone_healthpass.server.Account;
import com.example.capstone_healthpass.server.Reservation;
import com.example.capstone_healthpass.server.RetrofitManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MYpageActivity  extends AppCompatActivity {



    private String reserveMinute;
    private String reserveDay;
    private String reserveTime;
    private String reserveEx_name;
    private String reserveSeat;
    private RadioGroup radioGroup;

    public BottomNavigationView bottomNavigationView;
    RetrofitManager retrofitManager = new RetrofitManager();
    private ArrayList<String> array;
    String selectedOption="";
    private Button button;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        reservedInfos(MainActivity.account.getEmail());
        button = findViewById(R.id.reserveCancel);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] resData = selectedOption.split(" ");
                AlertDialog.Builder dlg = new AlertDialog.Builder(MYpageActivity.this);
                dlg.setTitle("예약 취소");
                dlg.setIcon(R.drawable.cat);
                dlg.setMessage("정말 예약을 취소하시겠습니까?");
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(getApplicationContext(),"예약이 취소 되었습니다.",
                                Toast.LENGTH_SHORT).show();
                        canceled(resData[0],resData[1],resData[2],resData[3],resData[4]);

                    }
                });
                dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dlg.show(); //***

            }
        });
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        // 네비게이션 아이템 클릭 리스너 설정
        BottomNavigationManager navigationManager = new BottomNavigationManager(MYpageActivity.this);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationManager);
        radioGroup = findViewById(R.id.radioGroup);


    }
    public void reservedInfos(final String email){
        JsonObject json = new JsonObject();

        Account create = MainActivity.account;

        JsonObject act = new JsonObject();

        act.addProperty("email", create.getEmail());
        act.addProperty("password", create.getPassword());
        act.addProperty("phone", create.getPhone());
        act.addProperty("name", create.getName());
        String jsonString = act.toString();
        json.addProperty("stringAccount",jsonString);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(json));
        Log.d("jsonTest",new Gson().toJson(json));
        retrofitManager.getApiService().reservedInfo(body).enqueue(new Callback<List<Reservation>>() {
            @Override
            public void onResponse(Call<List<Reservation>> call, Response<List<Reservation>> response) {
                if(response.code()==201){
                    List<Reservation> reservationList = response.body();
                    array=new ArrayList<>();
                    for(Reservation reservation:reservationList) {
                        reserveMinute = reservation.getMinute();
                        reserveTime = reservation.getTime();
                        reserveDay = reservation.getDay();
                        reserveSeat = reservation.getSeat();
                        reserveEx_name = reservation.getEx_name();
                        String str = reserveDay + " \t" + reserveTime + "시\t" + reserveMinute + "분\t" + reserveSeat + "\t"+reserveEx_name;
                        RadioButton radioButton = new RadioButton(MYpageActivity.this);
                        radioButton.setText(str);
                        radioGroup.addView(radioButton);
                        radioButton.setOnClickListener(view -> {
                            // 선택된 라디오 버튼의 텍스트를 가져와서 사용 가능
                            selectedOption = ((RadioButton) view).getText().toString();


                        });

                        array.add(str);
                    }


                }
            }

            @Override
            public void onFailure(Call<List<Reservation>> call, Throwable t) {
                Log.d("myPageTest",t.toString());
            }
        });
    }
    public void canceled(final String day, final String time, final String minute, final String seat, final String ex_name){

        retrofitManager.getApiService().reservedCancel(day,time,minute,seat,ex_name).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code()==201){
                    Toast.makeText(MYpageActivity.this, selectedOption+"예약을 취소했습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MYpageActivity.this,MYpageActivity.class);
                    startActivity(intent);
                    finish();

                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("삭제",t.toString());
            }
        });
    }

}
