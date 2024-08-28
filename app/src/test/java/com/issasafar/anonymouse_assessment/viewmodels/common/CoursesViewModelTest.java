package com.issasafar.anonymouse_assessment.viewmodels.common;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.issasafar.anonymouse_assessment.data.models.common.Answer;
import com.issasafar.anonymouse_assessment.data.models.common.Course;
import com.issasafar.anonymouse_assessment.data.models.common.LongAnswerQuestion;
import com.issasafar.anonymouse_assessment.data.models.common.MultipleChoiceQuestion;
import com.issasafar.anonymouse_assessment.data.models.common.Question;
import com.issasafar.anonymouse_assessment.views.common.CoursesRepository;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import java.util.concurrent.CountDownLatch;


import retrofit2.converter.gson.GsonConverterFactory;

// Assuming your Course, Question, LongAnswerQuestion, MultipleChoiceQuestion, Answer classes exist and are imported properly

public class CoursesViewModelTest {
    public static CoursesRepository coursesRepository = new CoursesRepository();

    public Course getSampleTest() {
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
        return new Course.TestCourse(1, 1,"programming course", questions);
    }
//    java.net.ConnectException: Failed to connect to /127.0.0.1:80
//    java.net.SocketTimeoutException: timeout
    @Test
    public void postData() throws InterruptedException {
        Course course = getSampleTest();
        assertNotNull(coursesRepository);
        assertEquals("programming course", course.getDescription());
        GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create();

        CountDownLatch latch = new CountDownLatch(1); // This will wait for the asynchronous process to complete


        latch.await(); // Wait for the latch to reach 0 before proceeding
        // Add any additional assertions if needed
    }

}
