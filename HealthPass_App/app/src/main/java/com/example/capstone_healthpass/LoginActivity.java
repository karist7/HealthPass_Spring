package com.example.capstone_healthpass;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.capstone_healthpass.server.RetrofitManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private String nameValue;


    private Button btnCheck, btnCancel;

    private EditText emailText, pwdText;
    RetrofitManager retrofitManager = new RetrofitManager();
    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_login);
        btnCheck = findViewById(R.id.login_activity_btn);
        btnCancel = findViewById(R.id.cancel_btn);
        emailText = findViewById(R.id.editTextTextEmailAddress);
        pwdText = findViewById(R.id.editTextTextPassword);



    }

    public void LoginAccount(View v){
        String email = emailText.getText().toString();
        String pwd = pwdText.getText().toString();
        if (email!=null && pwd!=null){
            loginAccount(email,pwd);
        }
    }
    public void cancel(View v){
        Intent result = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(result);
        finish();
    }

    private void loginAccount(final String email, final String password){
        JsonObject json = new JsonObject();

        json.addProperty("password",password);
        json.addProperty("email",email);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(json));

        retrofitManager.getApiService().loginPost(body).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 201) {

                        String responseBody = null; // 문자열로 변환
                        try {
                            responseBody = response.body().string();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        JSONObject jsonResponse = null;
                        try {
                            jsonResponse = new JSONObject(responseBody);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        String email = null;
                        String phone = null;
                        String name = null;
                        String password = null;
                        if (jsonResponse != null) {

                            try {

                                email = jsonResponse.getString("email");
                                phone = jsonResponse.getString("phone");
                                name = jsonResponse.getString("name");
                                password = jsonResponse.getString("password");
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }

                        }
                        Intent result = new Intent(LoginActivity.this, MainActivity.class);
                        result.putExtra("name",name);
                        result.putExtra("phone",phone);
                        result.putExtra("email",email);
                        result.putExtra("password",password);
                        Toast.makeText(LoginActivity.this, name+"님, 반갑습니다.", Toast.LENGTH_SHORT).show();
                        result.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(result);


                    } else if (response.code() == 202) {
                        Toast.makeText(LoginActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 203) {
                        Toast.makeText(LoginActivity.this, "일치하는 정보가 없습니다.", Toast.LENGTH_SHORT).show();

                    }
                }
                else{
                    Toast.makeText(LoginActivity.this, "네트워크 오류입니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                Log.d("LoginError", t.getMessage());

                Log.d("responseBody",call.request().body().toString());
            }
        });
    }





}
