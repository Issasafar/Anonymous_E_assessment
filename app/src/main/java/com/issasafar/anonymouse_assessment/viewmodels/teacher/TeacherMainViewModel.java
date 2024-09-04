package com.issasafar.anonymouse_assessment.viewmodels.teacher;

import static com.issasafar.anonymouse_assessment.viewmodels.teacher.AddQuestionsViewModel.QUESTIONS_KEY;
import static com.issasafar.anonymouse_assessment.views.teacher.TeacherMainActivity.COURSES_KEY;
import static com.issasafar.anonymouse_assessment.views.teacher.ui.main.teacher.TeacherMainFragment.TARGET_COURSE_KEY;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.issasafar.anonymouse_assessment.BR;
import com.issasafar.anonymouse_assessment.R;
import com.issasafar.anonymouse_assessment.data.models.common.Course;
import com.issasafar.anonymouse_assessment.data.models.common.CourseRequest;
import com.issasafar.anonymouse_assessment.data.models.common.CourseResponse;
import com.issasafar.anonymouse_assessment.data.models.common.Question;
import com.issasafar.anonymouse_assessment.databinding.TeacherFragmentMainBinding;
import com.issasafar.anonymouse_assessment.databinding.TestGeneratedDialogBinding;
import com.issasafar.anonymouse_assessment.views.common.CoursesRepository;
import com.issasafar.anonymouse_assessment.views.common.ResultCallback;
import com.issasafar.anonymouse_assessment.views.login.LoginViewModel;
import com.issasafar.anonymouse_assessment.views.teacher.ShowTrackRecyclerAdapter;
import com.issasafar.anonymouse_assessment.views.teacher.ui.main.teacher.AddQuestionsFragment;
import com.issasafar.anonymouse_assessment.views.teacher.ui.main.teacher.ShowTrackFragment;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

public class TeacherMainViewModel extends BaseObservable {
    public static final Type questionsArrayType = new TypeToken<ArrayList<Question>>() {
    }.getType();
    public static final String ADAPTER_KEY = "ADAPTER_KEY";
    public final MutableLiveData<String> courseName = new MutableLiveData<>("");
    private Context appContext;
    private FragmentManager fragmentManager;
    private TeacherFragmentMainBinding teacherFragmentMainBinding;
    private ArrayList<Question> questions = new ArrayList<>();
    private String questionsJson;
    private CoursesRepository coursesRepository = new CoursesRepository();
    private ArrayList<Course> availableCourses = new ArrayList<>();
    private Course targetCourse;
    private Bundle courseBundle = new Bundle();
    @Bindable
    private String courseNameError = null;

    public TeacherMainViewModel(TeacherFragmentMainBinding teacherFragmentMainBinding, FragmentManager fragmentManager, Context appContext) {
        this.teacherFragmentMainBinding = teacherFragmentMainBinding;
        this.fragmentManager = fragmentManager;
        this.appContext = appContext;
    }

    public ArrayList<Course> getAvailableCourses() {
        return availableCourses;
    }

    public void setAvailableCourses(ArrayList<Course> courses) {
        this.availableCourses = courses;
    }

    public Course getTargetCourse() {
        return targetCourse;
    }

    public void setTargetCourse(Course targetCourse) {
        this.targetCourse = targetCourse;
    }

    public boolean findCourse() {
        Optional<Course> courseSearch = availableCourses
                .stream()
                .filter(item -> item.getDescription().equals(getCourseName()))
                .findFirst();
        if (courseSearch.isPresent()) {
            setTargetCourse(courseSearch.get());
            return true;
        }
        return false;
    }

    public void setQuestionsJson(String json) {
        this.questionsJson = json;
    }

    public ArrayList<Question> getQuestions() {
        if (questions == null && questionsJson != null) {
            questions = new Gson().fromJson(questionsJson, questionsArrayType);
        }
        if (questions != null) {
            Collections.shuffle(questions);
            for (int i = 0; i < questions.size(); i++) {
                questions.get(i).setQ_order(i+1);
            }
        }
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public String getCourseName() {
        return courseName.getValue();
    }

    public void setCourseName(String name) {
        this.courseName.setValue(name);
        checkEmptyCourseName();
        if (!findCourse()) {
            Course targetCourse = new Course(-1, Integer.parseInt(LoginViewModel.getUserId(appContext)), teacherFragmentMainBinding.getTeacherMainFragment().getCourseName());
            setTargetCourse(targetCourse);
        }
        String courseJson = new Gson().toJson(getTargetCourse());
        courseBundle.putString(TARGET_COURSE_KEY, courseJson);
        fragmentManager.setFragmentResult(TARGET_COURSE_KEY, courseBundle);
    }

    private boolean checkEmptyCourseName() {
        if (!"".equals(getCourseName()) && getCourseName() != null) {
            setCourseNameError(null);
            return false;
        } else {
            setCourseNameError("No course name provided");
            return true;
        }
    }

    public String getCourseNameError() {
        return courseNameError;
    }

    public void setCourseNameError(String courseNameError) {
        this.courseNameError = courseNameError;
        notifyPropertyChanged(BR.courseNameError);
    }

    public void addQuestionClicked() {
        if (!checkEmptyCourseName()) {

            Bundle bundle = new Bundle();
            bundle.putString(QUESTIONS_KEY, questionsJson);
            fragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.teacher_fragment_container, AddQuestionsFragment.class, bundle)
                    .addToBackStack("add_questions_frag")
                    .commit();
        }
    }

    public void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(teacherFragmentMainBinding.getRoot(), message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    public void showSnackBar(String message, Snackbar.Callback callback) {
        Snackbar snackbar = Snackbar.make(teacherFragmentMainBinding.getRoot(), message, Snackbar.LENGTH_SHORT);
        snackbar.addCallback(callback);
        snackbar.show();
    }

    public void generateTestClicked() {
        if (getQuestions() != null && !getQuestions().isEmpty()) {
            if (getTargetCourse() == null) {
                targetCourse = new Course(Integer.parseInt(LoginViewModel.getUserId(appContext)), getCourseName());
            }
            Course.TestCourse testCourse = new Course.TestCourse(targetCourse.getT_id(), targetCourse.getOwner_id(), targetCourse.getDescription(), getQuestions());
            coursesRepository.postData(new CourseRequest(CourseRequest.CourseAction.POST_TEST, testCourse), new ResultCallback<CourseResponse>() {
                @Override
                public void onSuccess(CourseResponse data) {
                    if (data.getSuccess()) {
                        new Handler(Looper.getMainLooper()).post(() -> {
                            showTestCreatedDialog(data.getMessage());
                        });
                    } else {
                        showSnackBar(data.getMessage());
                    }
                }

                @Override
                public void onError(Exception e) {
                    if (e.getCause().toString().contains("java.net.ConnectException")) {
                        showSnackBar("Connection error");
                    } else if (e.getCause().toString().contains("java.net.SocketTimeoutException")) {
                        showSnackBar("Unable to connect to the server");
                    }
                }
            });
        } else {
            showSnackBar("No question have been set");
        }

    }

    private void showTestCreatedDialog(String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(appContext);
        TestGeneratedDialogBinding testGeneratedDialogBinding = TestGeneratedDialogBinding.inflate(LayoutInflater.from(appContext));
        alertDialogBuilder.setView(testGeneratedDialogBinding.getRoot());
        alertDialogBuilder.setCancelable(false);
        AlertDialog dialog = alertDialogBuilder.create();
        testGeneratedDialogBinding.exitButton.setOnClickListener((view) -> {
            dialog.cancel();
        });
        //todo: set the view button click property for the dialog
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public void getPrevResultsClicked() {
        if (!getAvailableCourses().isEmpty()) {
            Bundle bundle = new Bundle();
            bundle.putString(ADAPTER_KEY, new Gson().toJson(ShowTrackRecyclerAdapter.AdapterType.GENERAL_TEACHER));
            bundle.putString(COURSES_KEY, new Gson().toJson(getAvailableCourses()));
            fragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.teacher_fragment_container, ShowTrackFragment.class, bundle)
                    .addToBackStack("show_track_frag")
                    .commit();
        } else {
            showSnackBar("No tests created to get results");
        }

    }

//    public void dropDownClicked() {
//        ImageView imageView = teacherFragmentMainBinding.dropdownIcon;
//        ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(imageView, "rotation", (imageView.getRotation() + 180f) % 360f);
//        rotateAnimator.setDuration(10);
//        rotateAnimator.start();
//    }

}