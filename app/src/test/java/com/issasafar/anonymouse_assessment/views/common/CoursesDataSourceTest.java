package com.issasafar.anonymouse_assessment.views.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.issasafar.anonymouse_assessment.data.models.Result;
import com.issasafar.anonymouse_assessment.data.models.common.Course;
import com.issasafar.anonymouse_assessment.data.models.common.CourseRequest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;

class CoursesDataSourceTest {
    @Mock
    private CoursesApiService mockApiService;
    @Mock
    private Call<Result> mockCall;
    @InjectMocks
    private CoursesDataSource coursesDataSource;
    @Mock
    private Retrofit mockRetrofit;
    private AutoCloseable autoCloseable;

    @BeforeEach
    public void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }


    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    public void testCreateTest_CallsApiService() throws IOException {
        Course mockCourse = mock(Course.class);
        CourseRequest<Course> courseRequest = new CourseRequest<Course>(CourseRequest.CourseAction.POST_TEST, mockCourse);
        Call<Result> temp = mockCall;
        when(coursesDataSource.createTest(courseRequest)).thenReturn(temp);
        Call<Result> result = coursesDataSource.createTest(courseRequest);
        ArgumentCaptor<CourseRequest> argumentCaptor = ArgumentCaptor.forClass(CourseRequest.class);
        Call<Result> verify = verify(coursesDataSource.createTest(courseRequest));
        assertEquals(mockCall, verify);
    }


}