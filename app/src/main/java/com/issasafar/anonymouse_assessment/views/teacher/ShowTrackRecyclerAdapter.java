package com.issasafar.anonymouse_assessment.views.teacher;

import static com.issasafar.anonymouse_assessment.viewmodels.teacher.TeacherMainViewModel.ADAPTER_KEY;
import static com.issasafar.anonymouse_assessment.views.teacher.ui.main.teacher.ShowTrackFragment.TESTS_RESULTS_KEY;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.issasafar.anonymouse_assessment.R;
import com.issasafar.anonymouse_assessment.data.models.common.Course;
import com.issasafar.anonymouse_assessment.data.models.common.CourseRequest;
import com.issasafar.anonymouse_assessment.data.models.common.CourseResponse;
import com.issasafar.anonymouse_assessment.data.models.common.TestResult;
import com.issasafar.anonymouse_assessment.databinding.TestResultRecyclerItemBinding;
import com.issasafar.anonymouse_assessment.viewmodels.teacher.TestResultViewModel;
import com.issasafar.anonymouse_assessment.views.common.CoursesRepository;
import com.issasafar.anonymouse_assessment.views.common.ResultCallback;
import com.issasafar.anonymouse_assessment.views.teacher.ui.main.teacher.ShowTrackFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShowTrackRecyclerAdapter extends RecyclerView.Adapter<ShowTrackRecyclerAdapter.ViewHolder> {
    private static View parent = null;
    private FragmentManager fragmentManager;
    private Context context;
    private CoursesRepository coursesRepository = new CoursesRepository();
    // courses
    private ArrayList<Course> courses = new ArrayList<Course>();
    // students who had taken the test
    private ArrayList<TestResult> results = new ArrayList<TestResult>();
    private AdapterType adapterType;

    public ShowTrackRecyclerAdapter(FragmentManager fragmentManager, AdapterType adapterType) {
        this.fragmentManager = fragmentManager;
        this.adapterType = adapterType;
    }

    public ShowTrackRecyclerAdapter(FragmentManager fragmentManager, ArrayList<Course> courses, AdapterType adapterType) {
        this.fragmentManager = fragmentManager;
        this.courses = courses;
        this.adapterType = adapterType;
    }

    public ShowTrackRecyclerAdapter(FragmentManager fragmentManager, AdapterType adapterType, ArrayList<TestResult> results) {
        this.fragmentManager = fragmentManager;
        this.adapterType = adapterType;
        this.results = results;
    }

    public ShowTrackRecyclerAdapter(ArrayList<Course> courses, ArrayList<TestResult> results, AdapterType adapterType, FragmentManager fragmentManager) {
        this.courses = courses;
        this.results = results;
        this.adapterType = adapterType;
        this.fragmentManager = fragmentManager;
    }

    public void setResults(ArrayList<TestResult> results) {
        this.results = results;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ShowTrackRecyclerAdapter.AdapterType getAdapterType() {
        return adapterType;
    }

    public void setAdapterType(AdapterType adapterType) {
        this.adapterType = adapterType;
    }

    @NonNull
    @Override
    public ShowTrackRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (ShowTrackRecyclerAdapter.parent == null) {
            ShowTrackRecyclerAdapter.parent = parent;
        }
        TestResultRecyclerItemBinding resultRecyclerItemBinding = TestResultRecyclerItemBinding.inflate(LayoutInflater.from(parent.getContext()));
        View itemView = resultRecyclerItemBinding.getRoot();
        itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new ViewHolder(itemView, resultRecyclerItemBinding, getAdapterType());
    }


    @Override
    public void onBindViewHolder(@NonNull ShowTrackRecyclerAdapter.ViewHolder holder, int position) {
        if (getAdapterType() == AdapterType.GENERAL_STUDENT || getAdapterType() == AdapterType.GENERAL_TEACHER) {
            holder.testResultRecyclerItemBinding.getTestResultViewModel().setCourse(courses.get(position));
        } else {
            holder.testResultRecyclerItemBinding.getTestResultViewModel().setTestResult(results.get(position));
        }
        holder.testResultRecyclerItemBinding.linkText.setOnClickListener((view) -> {
            linkTextClicked(position, holder);
        });
    }

    public void showSnackBar(String message) {
        Snackbar.make(parent, message, Snackbar.LENGTH_SHORT).show();
    }

    public void linkTextClicked(int position, ViewHolder holder) {
        if (adapterType == AdapterType.GENERAL_TEACHER) {
            Map<String, Integer> data = new HashMap<>();
            data.put("t_id", courses.get(position).getT_id());
            coursesRepository.postData(new CourseRequest(CourseRequest.CourseAction.GET_RESULTS, data), new ResultCallback<CourseResponse>() {
                @Override
                public void onSuccess(CourseResponse data) {
                    if (data.getSuccess()) {
                        Bundle bundle = new Bundle();
                        bundle.putString(TESTS_RESULTS_KEY, new Gson().toJson(data.getData()));
                        bundle.putString(ADAPTER_KEY, new Gson().toJson(AdapterType.TEACHER_VIEW));
                        fragmentManager.beginTransaction()
                                .setReorderingAllowed(true)
                                .replace(R.id.teacher_fragment_container, ShowTrackFragment.class, bundle)
                                .addToBackStack("teacher_view")
                                .commit();
                    } else {
                        showSnackBar(data.getMessage());
                    }
                }

                @Override
                public void onError(Exception e) {
                    showSnackBar("Connection error");
                }
            });
        } else if (adapterType == AdapterType.GENERAL_STUDENT) {
            holder.testResultRecyclerItemBinding.getTestResultViewModel().setAdapterType(AdapterType.STUDENT_VIEW);
            Course course = holder.testResultRecyclerItemBinding.getTestResultViewModel().getCourse();
            TestResult testResult = holder.testResultRecyclerItemBinding.getTestResultViewModel().getTestResult();
            holder.testResultRecyclerItemBinding.getTestResultViewModel().setCourse(new Course(course.getT_id(), course.getOwner_id(), testResult.getOwner_name()));
            notifyItemChanged(position);
        } else if (adapterType == AdapterType.TEACHER_VIEW) {
            TestResult result = holder.testResultRecyclerItemBinding.getTestResultViewModel().getTestResult();
            JsonObject jsonObject = new JsonObject();
//            jsonObject.addProperty("t_id",result.getT_id());
//            jsonObject.addProperty("owner_id",result.getOwner_id());
            //todo() replace with actual data t_id and owner_id
            jsonObject.addProperty("t_id",1);
            jsonObject.addProperty("owner_id",1);
            coursesRepository.postData(new CourseRequest(CourseRequest.CourseAction.GET_ANSWERS, jsonObject), new ResultCallback<CourseResponse>() {
                @Override
                public void onSuccess(CourseResponse data) {
                    if (data.getSuccess()) {
                       // todo() pass the answers to the next fragment with adapter for the questions
                    }
                }

                @Override
                public void onError(Exception e) {
                    showSnackBar(e.getMessage());
                }
            });

        } else if (adapterType == AdapterType.STUDENT_VIEW) {
            // todo() implement this
        }
    }

    @Override
    public int getItemCount() {
        if (getAdapterType() == AdapterType.TEACHER_VIEW) {
            return results.size();
        }
        return courses.size();
    }

    public enum AdapterType {
        GENERAL_TEACHER(0), GENERAL_STUDENT(1), TEACHER_VIEW(2), STUDENT_VIEW(3);
        private final int value;

        AdapterType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TestResultRecyclerItemBinding testResultRecyclerItemBinding;

        public ViewHolder(@NonNull View itemView, TestResultRecyclerItemBinding testResultRecyclerItemBinding, AdapterType adapterType) {
            super(itemView);
            this.testResultRecyclerItemBinding = testResultRecyclerItemBinding;
            testResultRecyclerItemBinding.executePendingBindings();
            TestResultViewModel resultViewModel = new TestResultViewModel(testResultRecyclerItemBinding, adapterType);
            testResultRecyclerItemBinding.setTestResultViewModel(resultViewModel);
        }
    }
}
