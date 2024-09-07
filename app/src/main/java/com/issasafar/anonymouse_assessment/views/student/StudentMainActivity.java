package com.issasafar.anonymouse_assessment.views.student;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.issasafar.anonymouse_assessment.MainActivity;
import com.issasafar.anonymouse_assessment.R;
import com.issasafar.anonymouse_assessment.databinding.ActivityStudentMainBinding;
import com.issasafar.anonymouse_assessment.views.login.LoginViewModel;
import com.issasafar.anonymouse_assessment.views.student.ui.main.student.StudentMainFragment;

public class StudentMainActivity extends AppCompatActivity {
        ActivityStudentMainBinding activityStudentMainBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityStudentMainBinding = ActivityStudentMainBinding.inflate(getLayoutInflater());
        setContentView(activityStudentMainBinding.getRoot());
        Toolbar toolbar = (Toolbar) activityStudentMainBinding.toolbar.getRoot();
        setSupportActionBar(toolbar);
        ImageButton menuButton = activityStudentMainBinding.toolbar.menuIcon;
        menuButton.setOnClickListener((this::showPopupMenu));
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.student_main_fragment_container, StudentMainFragment.newInstance())
                .setReorderingAllowed(true)
                .commit();
    }
    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_main_teacher, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(menuItem -> {
            if (menuItem.getTitle().equals("logout")) {
                LoginViewModel.logout(getApplicationContext());
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                finish();
                return true;
            }
            return false;
        });
        popupMenu.show();
    }
}