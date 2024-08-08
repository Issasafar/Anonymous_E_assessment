package com.issasafar.anonymouse_assessment.views.teacher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.issasafar.anonymouse_assessment.R;
import com.issasafar.anonymouse_assessment.databinding.ActivityTeacherMainBinding;
import com.issasafar.anonymouse_assessment.views.teacher.ui.main.teacher.TeacherMainFragment;

public class TeacherMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityTeacherMainBinding activityTeacherMainBinding = ActivityTeacherMainBinding.inflate(getLayoutInflater());
        setContentView(activityTeacherMainBinding.getRoot());
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.teacher_fragment_container, TeacherMainFragment.newInstance())
                    .commitNow();
        }
        Toolbar toolbar = activityTeacherMainBinding.toolbar.getRoot();
        setSupportActionBar(toolbar);
        ImageButton menuButton = activityTeacherMainBinding.toolbar.menuIcon;
        menuButton.setOnClickListener((this::showPopupMenu));
    }
    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_main_teacher, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    default:
                        return false;
                }
            }
        });
        popupMenu.show();
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_main, menu);
//        return true;
//    }

}