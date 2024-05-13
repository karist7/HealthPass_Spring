package com.example.capstone_healthpass;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigationManager implements BottomNavigationView.OnNavigationItemSelectedListener {

    private Context context;

    public BottomNavigationManager(Context context) {
        this.context = context;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                Intent intent = new Intent(context, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
                break;
            case R.id.navigation_mypage:
                if (MainActivity.account.getName().equals("")) {
                    Toast.makeText(context, "로그인 후 이용 바랍니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent1 = new Intent(context, MYpageActivity.class);
                    intent1.putExtra("name", MainActivity.account.getName());
                    intent1.putExtra("phone", MainActivity.account.getPhone());
                    intent1.putExtra("email", MainActivity.account.getEmail());
                    intent1.putExtra("password", MainActivity.account.getPassword());
                    context.startActivity(intent1);
                }
                break;
            case R.id.navigation_qr_code:
                Intent intent3 = new Intent(context, ScanQR.class);
                context.startActivity(intent3);
                break;
        }
        return true;
    }
}