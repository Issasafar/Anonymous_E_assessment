package com.issasafar.anonymouse_assessment.views.common;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.issasafar.anonymouse_assessment.data.models.common.Answer;
import com.issasafar.anonymouse_assessment.data.models.common.Course;
import com.issasafar.anonymouse_assessment.data.models.common.CourseRequest;
import com.issasafar.anonymouse_assessment.data.models.common.CourseResponse;
import com.issasafar.anonymouse_assessment.data.models.common.LongAnswerQuestion;
import com.issasafar.anonymouse_assessment.data.models.common.MultipleChoiceQuestion;
import com.issasafar.anonymouse_assessment.data.models.common.Question;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;

@ExtendWith(MockitoExtension.class)
class CoursesDataSourceTest {


    @Mock
    private CoursesApiService mockApiService;
    @Mock
    private Call<CourseResponse> mockCall;
    private Retrofit retrofit;
    @InjectMocks
    private CoursesDataSource coursesDataSource;

    @BeforeEach
    public void setUp() {
        coursesDataSource = new CoursesDataSource();
        retrofit = CoursesApiClient.getClient();
        mockApiService = retrofit.create(CoursesApiService.class);
        coursesDataSource.setApiService(mockApiService);
    }

    public static CourseRequest<Course> getSampleTest() {
        Question question1 = new LongAnswerQuestion(1, 1, "what is 2!", 1, "2");
        Question question2 = new MultipleChoiceQuestion(1, 1, "which is best", 2, "A", new String[]{"java", "kotlin", "python"});
        Answer answer1 = new Answer.LongAnswerAnswer(1, "9", 1, 1);
        Answer answer2 = new Answer.MultipleChoiceAnswer(2, "python", 1, 2);
        ArrayList<Question> questions = new ArrayList<>();
        questions.add(question1);
        questions.add(question2);
        ArrayList<Answer> answers = new ArrayList<>();
        answers.add(answer1);
        answers.add(answer2);
        Course course = new Course.TestCourse(1, 1, "programming course", questions);
        return new CourseRequest<Course>(CourseRequest.CourseAction.POST_TEST, course);
    }

    @Test
    public void testPostData_CallsApiService() {
        Course mockCourse = mock(Course.class);
        CourseRequest<Course> courseRequest = new CourseRequest<Course>(CourseRequest.CourseAction.POST_TEST, mockCourse);
        when(mockApiService.postData(courseRequest)).thenReturn(mockCall);
        coursesDataSource.postData(courseRequest);
        verify(mockApiService).postData(courseRequest);
        verifyNoMoreInteractions(mockApiService);
    }

    @Test
    public void mapToJsonValid(){
        Map<String, Integer> somedata = new HashMap<>();
        somedata.put("owner_id",1);
        CourseRequest<Map<String,Integer>> request = new CourseRequest<>(CourseRequest.CourseAction.GET_COURSES,somedata);
        String thing = new Gson().toJson(request);
        assertEquals(thing, "{\"action\":\"GET_COURSES\",\"data\":{\"owner_id\":1}}");
    }

}