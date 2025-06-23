package vn.edu.tlu.nhom9.qltv.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import vn.edu.tlu.nhom9.qltv.R;
import vn.edu.tlu.nhom9.qltv.controller.LoginController;

public class LoginActivity extends AppCompatActivity implements LoginController.LoginListener {

    private TextInputEditText etEmail, etPassword;
    private Button btnLogin;
    private ImageView ivLogo;
    private ProgressBar progressBar;
    private LoginController loginController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Kiểm tra nếu người dùng đã đăng nhập thì vào thẳng MainActivity
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_login);

        loginController = new LoginController();
        bindViews();

        Glide.with(this)
                .load("https://storage.googleapis.com/tagjs-prod.appspot.com/v1/l2ocGNYrX8/e6v0jj9u_expires_30_days.png")
                .into(ivLogo);

        btnLogin.setOnClickListener(v -> performLogin());
    }

    private void bindViews() {
        ivLogo = findViewById(R.id.iv_logo);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        progressBar = findViewById(R.id.progress_bar);
    }

    private void performLogin() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        showLoading(true);
        loginController.handleLogin(email, password, this);
    }

    @Override
    public void onLoginSuccess(String message) {
        showLoading(false);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onLoginFailure(String error) {
        showLoading(false);
        Toast.makeText(this, "Lỗi: " + error, Toast.LENGTH_LONG).show();
    }

    private void showLoading(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        btnLogin.setEnabled(!isLoading);
    }
}