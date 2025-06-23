package vn.edu.tlu.nhom9.qltv.view.activity;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import vn.edu.tlu.nhom9.qltv.R;
import vn.edu.tlu.nhom9.qltv.controller.MainController;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MainController mainController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        setContentView(R.layout.activity_main);

        mainController = new MainController();

        setupToolbar();
        bindViewsAndSetListeners();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Mở menu điều hướng", Toast.LENGTH_SHORT).show();
        });
    }

    private void bindViewsAndSetListeners() {
        findViewById(R.id.card_quan_ly_sach).setOnClickListener(this);
        findViewById(R.id.card_quan_ly_phieu_muon).setOnClickListener(this);
        findViewById(R.id.card_quan_ly_doc_gia).setOnClickListener(this);
        findViewById(R.id.card_thong_ke).setOnClickListener(this);
        findViewById(R.id.btn_logout).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.card_quan_ly_sach) {
            mainController.navigateToFeature(this, R.string.quan_ly_sach);
        } else if (id == R.id.card_quan_ly_phieu_muon) {
            mainController.navigateToFeature(this, R.string.quan_ly_phieu_muon);
        } else if (id == R.id.card_quan_ly_doc_gia) {
            mainController.navigateToFeature(this, R.string.quan_ly_doc_gia);
        } else if (id == R.id.card_thong_ke) {
            mainController.navigateToFeature(this, R.string.thong_ke);
        } else if (id == R.id.btn_logout) {
            mainController.handleLogout(this);
        }
    }
}