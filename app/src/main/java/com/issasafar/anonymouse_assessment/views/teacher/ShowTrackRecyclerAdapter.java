package com.issasafar.anonymouse_assessment.views.teacher;

import static com.issasafar.anonymouse_assessment.viewmodels.teacher.AddQuestionsViewModel.QUESTIONS_KEY;
import static com.issasafar.anonymouse_assessment.viewmodels.teacher.TeacherMainViewModel.ADAPTER_KEY;
import static com.issasafar.anonymouse_assessment.views.teacher.TeacherMainActivity.COURSES_KEY;
import static com.issasafar.anonymouse_assessment.views.teacher.ui.main.teacher.ShowTrackFragment.TESTS_RESULTS_KEY;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.issasafar.anonymouse_assessment.R;
import com.issasafar.anonymouse_assessment.data.models.common.Answer;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ShowTrackRecyclerAdapter extends RecyclerView.Adapter<ShowTrackRecyclerAdapter.ViewHolder> {
    public static final String ANSWERS_KEY = "answer_key";
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
        if (holder.getAdapterType() == AdapterType.GENERAL_TEACHER) {
            holder.testResultRecyclerItemBinding.getTestResultViewModel().setCourse(courses.get(position));
        } else if (holder.getAdapterType() == AdapterType.GENERAL_STUDENT) {
            holder.testResultRecyclerItemBinding.getTestResultViewModel().setCourse(courses.get(position));
//            holder.testResultRecyclerItemBinding.getTestResultViewModel().setTestResultForStudentView(results.get(position));
        } else if (holder.getAdapterType() == AdapterType.TEACHER_VIEW) {
            holder.testResultRecyclerItemBinding.getTestResultViewModel().setCourse(courses.get(position));
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
        if (holder.getAdapterType() == AdapterType.GENERAL_TEACHER) {
            Map<String, Integer> data = new HashMap<>();
            data.put("test_id", courses.get(position).getT_id());
            coursesRepository.postData(new CourseRequest(CourseRequest.CourseAction.GET_RESULTS, data), new ResultCallback<CourseResponse>() {
                @Override
                public void onSuccess(CourseResponse data) {
                    if (data.getSuccess()) {
                        ArrayList<TestResult> temp = new Gson().fromJson(new Gson().toJson(data.getData()), new TypeToken<ArrayList<TestResult>>() {
                        }.getType());
                        ArrayList<Course> dumpCourses = new ArrayList<>();
                        Course tempCourse = courses.get(position);
                        for (TestResult result : temp) {
                            dumpCourses.add(tempCourse);
                        }
                        Bundle bundle = new Bundle();
                        bundle.putString(COURSES_KEY,new Gson().toJson(dumpCourses));
                        bundle.putString(TESTS_RESULTS_KEY, new Gson().toJson(data.getData()));
                        bundle.putString(ADAPTER_KEY, new Gson().toJson(AdapterType.TEACHER_VIEW));
                        fragmentManager.beginTransaction().setReorderingAllowed(true).replace(R.id.teacher_fragment_container, ShowTrackFragment.class, bundle).addToBackStack("teacher_view").commit();
                    } else {
                        showSnackBar(data.getMessage());
                    }
                }

                @Override
                public void onError(Exception e) {
                    showSnackBar("Connection error");
                }
            });
        } else if (holder.getAdapterType() == AdapterType.GENERAL_STUDENT) {
            holder.setAdapterType(AdapterType.STUDENT_VIEW);
            holder.testResultRecyclerItemBinding.getTestResultViewModel().setAdapterType(AdapterType.STUDENT_VIEW);
            holder.testResultRecyclerItemBinding.getTestResultViewModel().setTestResultForStudentView(results.get(position));
//            Course course = holder.testResultRecyclerItemBinding.getTestResultViewModel().getCourse();
//            TestResult testResult = holder.testResultRecyclerItemBinding.getTestResultViewModel().getTestResult();
//            holder.testResultRecyclerItemBinding.getTestResultViewModel().setCourse(new Course(course.getT_id(), course.getOwner_id(), testResult.getOwner_name()));
        } else if (holder.getAdapterType() == AdapterType.TEACHER_VIEW || holder.getAdapterType() == AdapterType.STUDENT_VIEW) {
            Bundle bundle = new Bundle();
            TestResult result = holder.testResultRecyclerItemBinding.getTestResultViewModel().getTestResult();
            List<TestResult> tempResults = results.stream().filter((item)->item.getT_id() == holder.testResultRecyclerItemBinding.getTestResultViewModel().getTestResult().getT_id()).collect(Collectors.toList());
            tempResults.sort(Comparator.comparing(TestResult::getR_id));
            int wantedResultId = tempResults.indexOf(result);

            Course course = holder.testResultRecyclerItemBinding.getTestResultViewModel().getCourse();
            JsonObject answersRequestJson = new JsonObject();
            answersRequestJson.addProperty("test_id",result.getT_id());
            answersRequestJson.addProperty("owner_id",result.getOwner_id());
            JsonObject questionsRequestJson = new JsonObject();
            questionsRequestJson.addProperty("test_id", result.getT_id());
            questionsRequestJson.addProperty("owner_id", course.getOwner_id());
            coursesRepository.postData(new CourseRequest(CourseRequest.CourseAction.GET_QUESTIONS, questionsRequestJson), new ResultCallback<CourseResponse>() {
                @Override
                public void onSuccess(CourseResponse data) {
                    if (data.getSuccess()) {
                        bundle.putString(QUESTIONS_KEY, new Gson().toJson(data.getData()));
                    }
                }

                @Override
                public void onError(Exception e) {
                    showSnackBar(e.getMessage());
                }
            });
            coursesRepository.postData(new CourseRequest(CourseRequest.CourseAction.GET_ANSWERS, answersRequestJson), new ResultCallback<CourseResponse>() {
                @Override
                public void onSuccess(CourseResponse data) {
                    if (data.getSuccess()) {

                        ArrayList<Answer> allAnswers = new Gson().fromJson(new Gson().toJson(data.getData()), new TypeToken<ArrayList<Answer>>() {
                        }.getType());
//                        bundle.putString(ANSWERS_KEY, new Gson().toJson(data.getData()));
                        allAnswers.sort(Comparator.comparing(Answer::getQ_id));
                        long attemptsCount = allAnswers.stream().filter((item) -> item.getQ_id() == allAnswers.get(0).getQ_id()).count();
                        List<Answer> wantedAnswers = new ArrayList<>();
                        for (long i = wantedResultId; i < allAnswers.size(); i += attemptsCount) {
                            wantedAnswers.add(allAnswers.get((int) i));
                        }
                        wantedAnswers.sort(Comparator.comparing(Answer::getA_order));
                        bundle.putString(ANSWERS_KEY, new Gson().toJson(wantedAnswers));
                        bundle.putString(ADAPTER_KEY, new Gson().toJson(AdapterType.STUDENT_VIEW));
                        if (holder.getAdapterType() == AdapterType.STUDENT_VIEW) {
                            fragmentManager.beginTransaction()
                                    .addToBackStack("answers_fragment")
                                    .setReorderingAllowed(true)
                                    .replace(R.id.student_main_fragment_container, ShowTrackFragment.class, bundle)
                                    .commit();
                        } else {
                            fragmentManager.beginTransaction()
                                    .addToBackStack("answers_fragment")
                                    .setReorderingAllowed(true)
                                    .replace(R.id.teacher_fragment_container, ShowTrackFragment.class, bundle)
                                    .commit();
                        }

                    } else {
                        Snackbar.make(parent, "Unable to fetch answers from server", Snackbar.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(Exception e) {
                    showSnackBar(e.getMessage());
                }
            });
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
        private AdapterType adapterType;
        private TestResultRecyclerItemBinding testResultRecyclerItemBinding;

        public ViewHolder(@NonNull View itemView, TestResultRecyclerItemBinding testResultRecyclerItemBinding, AdapterType adapterType) {
            super(itemView);
            this.testResultRecyclerItemBinding = testResultRecyclerItemBinding;
            testResultRecyclerItemBinding.executePendingBindings();
            TestResultViewModel resultViewModel = new TestResultViewModel(testResultRecyclerItemBinding, adapterType);
            this.adapterType = adapterType;
            testResultRecyclerItemBinding.setTestResultViewModel(resultViewModel);
        }

        public AdapterType getAdapterType() {
            return adapterType;
        }

        public void setAdapterType(AdapterType adapterType) {
            this.adapterType = adapterType;
        }
    }
}
