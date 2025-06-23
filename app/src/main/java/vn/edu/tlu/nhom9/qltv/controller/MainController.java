package vn.edu.tlu.nhom9.qltv.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import vn.edu.tlu.nhom9.qltv.view.activity.LoginActivity;

public class MainController {

    private final FirebaseAuth mAuth;

    public MainController() {
        mAuth = FirebaseAuth.getInstance();
    }

    public void navigateTo(Context context, String featureName) {
        Toast.makeText(context, "Mở chức năng: " + featureName, Toast.LENGTH_SHORT).show();
        // Sau này bạn sẽ thay thế bằng Intent để mở Activity tương ứng
    }

    public void handleLogout(Activity currentActivity) {
        mAuth.signOut();
        Intent intent = new Intent(currentActivity, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        currentActivity.startActivity(intent);
        currentActivity.finish();
    }
}