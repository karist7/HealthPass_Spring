package com.example.capstone_healthpass.Reserve;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.capstone_healthpass.MainActivity;
import com.example.capstone_healthpass.server.Account;
import com.example.capstone_healthpass.server.RetrofitManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.capstone_healthpass.R;
public class ReserveConfirmActivity  extends Activity {
    String strMinute="";
    //daytime 에서 가져올 변수
    TextView tvYear3, tvMonth3, tvDay3, tvHour3, tvMinute3;

    TextView tvName2,tvPhone2;
    TextView seat2,personnel2;
    Button btnConfirm;
    RetrofitManager retrofitManager = new RetrofitManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_confirm);
        setTitle("운동기구 예약 확정");

        //예약 날 정보
        //이전 day time 변수들
        tvYear3 = (TextView) findViewById(R.id.tvYear3);
        tvMonth3 = (TextView) findViewById(R.id.tvMonth3);
        tvDay3 = (TextView) findViewById(R.id.tvDay3);
        tvHour3 = (TextView) findViewById(R.id.tvHour3);
        tvMinute3 = (TextView) findViewById(R.id.tvMinute3);

        tvYear3.setText(((ReserveDaytimeActivity)ReserveDaytimeActivity.DayContext).tvYear.getText());
        tvMonth3.setText(((ReserveDaytimeActivity)ReserveDaytimeActivity.DayContext).tvMonth.getText());
        tvDay3.setText(((ReserveDaytimeActivity)ReserveDaytimeActivity.DayContext).tvDay.getText());
        tvHour3.setText(((ReserveDaytimeActivity)ReserveDaytimeActivity.DayContext).tvHour.getText());
        tvMinute3.setText(((ReserveDaytimeActivity)ReserveDaytimeActivity.DayContext).tvMinute.getText());

        //메인 액티비티로 이동해 정보 수정 가능 back기능
        Button btnReturn = (Button)findViewById(R.id.BtnReturnToTable);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //두번째 액티비티의 화면 동작 설계
        //이전 화면 정보 뿌리기
        tvName2 = (TextView)findViewById(R.id.tvName2);
        tvPhone2 = (TextView)findViewById(R.id.tvPhone2);
        seat2 = (TextView)findViewById(R.id.Seat2);
        personnel2 = (TextView)findViewById(R.id.Personnel2);

        //앞의 MainActivity 의 정보 받아오기
        //context로 액티비티 전체를 받아와 이전에 저장된 텍스트 변수의 텍스트를 가져옴
        tvName2.setText(((ReserveMachineActivity)ReserveMachineActivity.ReserveContext).tvName.getText());
        tvPhone2.setText(((ReserveMachineActivity)ReserveMachineActivity.ReserveContext).tvPhone.getText());
        seat2.setText(((ReserveMachineActivity)ReserveMachineActivity.ReserveContext).seat.getText());
        personnel2.setText(((ReserveMachineActivity)ReserveMachineActivity.ReserveContext).personnel.getText());



        Button btnFinish = (Button)findViewById(R.id.BtnFinish);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, Integer.parseInt(tvYear3.getText() .toString()));
                calendar.set(Calendar.MONTH, Integer.parseInt(tvMonth3.getText().toString())-1);
                calendar.set(Calendar.DAY_OF_MONTH,Integer.parseInt(tvDay3.getText() .toString()));
                Date date = calendar.getTime();

                // 원하는 날짜 형식 지정
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                // 날짜를 문자열로 변환
                String day = sdf.format(date);

                String time = tvHour3.getText().toString();
                String seat = seat2.getText().toString();

                int minute = Integer.parseInt(tvMinute3.getText().toString());

                if(minute < 30){
                    strMinute="0";
                }
                else{
                    strMinute="30";
                }
                String ex_name = personnel2.getText().toString();
                reserveEx(day,time,strMinute,seat,ex_name);

            }
        });

    }
    public void reserveEx(final String day, final String time, final String minute,final String seat,final String ex_name){
        JsonObject json = new JsonObject();
        json.addProperty("ex_name", ex_name);
        json.addProperty("seat", seat);
        json.addProperty("date",day);
        json.addProperty("hour",time);
        json.addProperty("minute",minute);

        JsonObject act = new JsonObject();
        Account create = MainActivity.account;

        act.addProperty("email", create.getEmail());
        act.addProperty("password", create.getPassword());
        act.addProperty("phone", create.getPhone());
        act.addProperty("name", create.getName());
        String jsonString = act.toString();
        json.addProperty("stringAccount",jsonString);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(json));
        Log.d("jsonTest",new Gson().toJson(json));
        retrofitManager.getApiService().reserved(body).enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {

                if(response.code()==201){
                    Log.d("Reservation","ReservationComplete");

                    Intent intent = new Intent(ReserveConfirmActivity.this, ReserveEndActivity.class);
                    intent.putExtra("name",MainActivity.account.getName());
                    intent.putExtra("phone", MainActivity.account.getPhone());
                    intent.putExtra("email", MainActivity.account.getEmail());
                    intent.putExtra("password", MainActivity.account.getPassword());
                    startActivity(intent);//다음 액티비티 화면에 출력

                }
                else if(response.code()==202){
                    Log.d("Reservation","해당 기구는 이미 예약됨");
                    Toast.makeText(ReserveConfirmActivity.this, "해당 기구는 이미 예약됨", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), ReserveMachineActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if(response.code()==203){
                    Log.d("Reservation","시간 중복");
                    Toast.makeText(ReserveConfirmActivity.this, "선택하신 시간에 이미 예약 기록이 있습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), ReserveDaytimeActivity.class);
                    startActivity(intent);
                    finish();

                }
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                Log.d("fail",t.toString());
            }
        });
    }

}