package com.issasafar.anonymouse_assessment.viewmodels.student;

import android.content.Context;
import android.widget.RadioGroup;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.issasafar.anonymouse_assessment.R;
import com.issasafar.anonymouse_assessment.data.models.common.Answer;
import com.issasafar.anonymouse_assessment.data.models.common.LongAnswerQuestion;
import com.issasafar.anonymouse_assessment.data.models.common.MultipleChoiceQuestion;
import com.issasafar.anonymouse_assessment.data.models.common.Question;
import com.issasafar.anonymouse_assessment.databinding.StudentQuestionItemBinding;
import com.issasafar.anonymouse_assessment.views.login.LoginViewModel;

import java.util.Objects;


public class StudentQuestionViewModel extends BaseObservable {
    public final static int CHOICE1 = 0;
    public final static int CHOICE2 = 1;
    public final static int CHOICE3 = 2;
    private Context context;
    @Bindable
    private String[] choices = new String[3];
    @Bindable
    private Question question;
    @Bindable
    private Answer answer;
    private int choiceNumber;
    private StudentQuestionItemBinding studentQuestionItemBinding;

    public StudentQuestionViewModel(Context context, StudentQuestionItemBinding studentQuestionItemBinding) {
        this.context = context;
        this.studentQuestionItemBinding = studentQuestionItemBinding;
    }

    public void showStudentTestAnswers(Question question, Answer answer) {
        setQuestion(question);
        setAnswer(answer);
        if (answer.getA_type() == Question.QuestionType.MULTI) {
            if (Objects.equals(answer.getDescription(), choices[CHOICE1])) {
                studentQuestionItemBinding.choicesRadioGroup.check(R.id.choice1_button);
            } else if (Objects.equals(answer.getDescription(), choices[CHOICE2])) {
                studentQuestionItemBinding.choicesRadioGroup.check(R.id.choice2_button);
            } else {
                studentQuestionItemBinding.choicesRadioGroup.check(R.id.choice3_button);
            }
        }
        studentQuestionItemBinding.choice1Button.setClickable(false);
        studentQuestionItemBinding.choice2Button.setClickable(false);
        studentQuestionItemBinding.choice3Button.setClickable(false);
        studentQuestionItemBinding.answerTextLayout.getEditText().setClickable(false);
        studentQuestionItemBinding.answerTextLayout.getEditText().setFocusable(false);
        studentQuestionItemBinding.answerTextLayout.getEditText().setCursorVisible(false);

    }
    public boolean isCorrect() {
        if (question.getType() == Question.QuestionType.LONG) {
            return Objects.equals(answer.getDescription().trim().toLowerCase(), ((LongAnswerQuestion) question).getSolution().trim().toLowerCase());
        } else {
            return Objects.equals(choices[choiceNumber].trim().toLowerCase(), ((MultipleChoiceQuestion) question).getSolution().trim().toLowerCase());
        }
    }

    public String[] getChoices() {
        return choices;
    }

    public void setChoices(String[] choices) {
        this.choices = choices;
        notifyPropertyChanged(BR.choices);
    }

    @Bindable
    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
        notifyPropertyChanged(BR.answer);
    }

    @Bindable
    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
        if (question instanceof MultipleChoiceQuestion) {
            setChoices(((MultipleChoiceQuestion) question).getChoices());
            setAnswer(new Answer.MultipleChoiceAnswer(question.getQ_id(), "", Integer.parseInt(LoginViewModel.getUserId(context)), question.getQ_order(), question.getT_id()));
        } else {
            setAnswer(new Answer.LongAnswerAnswer(question.getQ_id(), "", Integer.parseInt(LoginViewModel.getUserId(context)), question.getQ_order(), question.getT_id()));
        }
        notifyPropertyChanged(BR.question);
    }


    public int getChoiceNumber() {
        return choiceNumber;
    }

    public void setChoiceNumber(int choiceNumber) {
        this.choiceNumber = choiceNumber;
    }

    public void onChoiceSelected(RadioGroup radioGroup, int id) {
        if (id == R.id.choice1_button) {
            setChoiceNumber(CHOICE1);
        } else if (id == R.id.choice2_button) {
            setChoiceNumber(CHOICE2);
        } else {
            setChoiceNumber(CHOICE3);
        }
        getAnswer().setDescription(choices[choiceNumber]);
    }
}
