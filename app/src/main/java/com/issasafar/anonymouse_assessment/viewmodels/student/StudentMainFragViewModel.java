package com.issasafar.anonymouse_assessment.viewmodels.student;

import static com.issasafar.anonymouse_assessment.viewmodels.teacher.AddQuestionsViewModel.QUESTIONS_KEY;
import static com.issasafar.anonymouse_assessment.viewmodels.teacher.TeacherMainViewModel.ADAPTER_KEY;
import static com.issasafar.anonymouse_assessment.views.teacher.TeacherMainActivity.COURSES_KEY;
import static com.issasafar.anonymouse_assessment.views.teacher.ui.main.teacher.ShowTrackFragment.TESTS_RESULTS_KEY;

import android.content.Context;
import android.os.Bundle;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.issasafar.anonymouse_assessment.R;
import com.issasafar.anonymouse_assessment.data.models.common.Course;
import com.issasafar.anonymouse_assessment.data.models.common.CourseRequest;
import com.issasafar.anonymouse_assessment.data.models.common.CourseResponse;
import com.issasafar.anonymouse_assessment.data.models.common.Question;
import com.issasafar.anonymouse_assessment.data.models.common.QuestionDeserializer;
import com.issasafar.anonymouse_assessment.databinding.FragmentStudentMainBinding;
import com.issasafar.anonymouse_assessment.views.common.CoursesRepository;
import com.issasafar.anonymouse_assessment.views.common.ResultCallback;
import com.issasafar.anonymouse_assessment.views.login.LoginViewModel;
import com.issasafar.anonymouse_assessment.views.student.ui.main.student.SendLackMessageFragment;
import com.issasafar.anonymouse_assessment.views.student.ui.main.student.StudentQuestionsFragment;
import com.issasafar.anonymouse_assessment.views.teacher.ShowTrackRecyclerAdapter;
import com.issasafar.anonymouse_assessment.views.teacher.ui.main.teacher.ShowTrackFragment;

import java.util.ArrayList;

public class StudentMainFragViewModel extends BaseObservable {
    public static String SELECTED_COURSE_KEY = "selected_course_key";
    private FragmentStudentMainBinding fragmentStudentMainBinding;
    private FragmentManager fragmentManager;
    private String courseName = "";
    private Bundle bundle = new Bundle();
    private CoursesRepository coursesRepository;
    private Context context;
    public ArrayList<Course> getCourses() {
        return courses;
    }

    private ArrayList<Course> courses = new ArrayList<>();
    private Course selected = null;
    public StudentMainFragViewModel(FragmentStudentMainBinding fragmentStudentMainBinding, FragmentManager fragmentManager,CoursesRepository coursesRepository,Context context) {
        this.fragmentStudentMainBinding = fragmentStudentMainBinding;
        this.fragmentManager = fragmentManager;
        this.coursesRepository = coursesRepository;
        this.context = context;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public Course getSelected() {
        if (!"".equals(getCourseName()) && courses.stream().anyMatch(item -> item.getDescription().equals(getCourseName()))) {
            setSelected(courses.stream().filter(item -> item.getDescription().equals(getCourseName())).findFirst().get());
            bundle.putString(SELECTED_COURSE_KEY,new Gson().toJson(selected));
        } else {
            setSelected(null);
        }
        return selected;
    }

    public void setSelected(Course selected) {
        this.selected = selected;
    }

    @Bindable
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void getQuestionsClicked() {
        if (getSelected() == null) {
            Snackbar.make(fragmentStudentMainBinding.getRoot(), "Please select an existing course", Snackbar.LENGTH_SHORT).show();
        } else {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("test_id",selected.getT_id());
            jsonObject.addProperty("owner_id",selected.getOwner_id());
            new CoursesRepository().postData(new CourseRequest(CourseRequest.CourseAction.GET_QUESTIONS, jsonObject), new ResultCallback<CourseResponse>() {
                @Override
                public void onSuccess(CourseResponse data) {
                    if (data.getSuccess()) {
                        bundle.putString(QUESTIONS_KEY, new Gson().toJson(data.getData()));
                        fragmentManager.beginTransaction()
                                .setReorderingAllowed(true)
                                .addToBackStack("question_frag")
                                .replace(R.id.student_main_fragment_container, StudentQuestionsFragment.class,bundle)
                                .commit();
                    } else {
                        Snackbar.make(fragmentStudentMainBinding.getRoot(), "Unable to fetch questions form server", Snackbar.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(Exception e) {
                        showSnackBarError(e);
                }
            });
        }
    }
    private void showSnackBarError(Exception e) {
        if (e.getCause().toString().contains("java.net.ConnectException")) {
            Snackbar.make(fragmentStudentMainBinding.getRoot(), "Unable to post message (Connection error)", Snackbar.LENGTH_SHORT).show();
        } else if (e.getCause().toString().contains("java.net.SocketTimeoutException")) {
            Snackbar.make(fragmentStudentMainBinding.getRoot(), "Unable to connect to the server", Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(fragmentStudentMainBinding.getRoot(), "Unknown error", Snackbar.LENGTH_SHORT).show();
        }
    }

    public void getResultsClicked() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("student_id", LoginViewModel.getUserId(context));
       coursesRepository.postData(new CourseRequest(CourseRequest.CourseAction.GET_PREV_TESTS, jsonObject), new ResultCallback<CourseResponse>() {
           @Override
           public void onSuccess(CourseResponse data) {
               if (data.getSuccess()) {
                   bundle.putString(COURSES_KEY,new Gson().toJson(data.getData()));
               }
           }

           @Override
           public void onError(Exception e) {
               showSnackBarError(e);
           }
       });
       coursesRepository.postData(new CourseRequest(CourseRequest.CourseAction.GET_ALL_RESULTS, jsonObject), new ResultCallback<CourseResponse>() {
           @Override
           public void onSuccess(CourseResponse data) {
               if (data.getSuccess()) {
                   bundle.putString(TESTS_RESULTS_KEY,new Gson().toJson(data.getData()));
                   bundle.putString(ADAPTER_KEY, new Gson().toJson(ShowTrackRecyclerAdapter.AdapterType.GENERAL_STUDENT));
                   fragmentManager.beginTransaction()
                           .addToBackStack("show_result_frag")
                           .setReorderingAllowed(true)
                           .replace(R.id.student_main_fragment_container, ShowTrackFragment.class, bundle)
                           .commit();
               } else {
                   Snackbar.make(fragmentStudentMainBinding.getRoot(), "No results available", Snackbar.LENGTH_SHORT).show();
               }
           }

           @Override
           public void onError(Exception e) {
               showSnackBarError(e);
           }
       });

    }

    public void sendLackMessageClicked() {
        if (getSelected() == null) {
            Snackbar.make(fragmentStudentMainBinding.getRoot(), "Please select an existing course", Snackbar.LENGTH_SHORT).show();
        } else {
            fragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .addToBackStack("send_frag")
                    .replace(R.id.student_main_fragment_container, SendLackMessageFragment.class,bundle)
                    .commit();
        }
    }


}