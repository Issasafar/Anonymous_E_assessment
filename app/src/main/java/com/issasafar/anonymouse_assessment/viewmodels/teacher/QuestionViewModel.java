package com.issasafar.anonymouse_assessment.viewmodels.teacher;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.issasafar.anonymouse_assessment.data.models.common.LongAnswerQuestion;
import com.issasafar.anonymouse_assessment.data.models.common.MultipleChoiceQuestion;
import com.issasafar.anonymouse_assessment.data.models.common.Question;
import com.issasafar.anonymouse_assessment.databinding.QuestionRecyclerItemBinding;

public class QuestionViewModel extends BaseObservable {
    private QuestionRecyclerItemBinding questionRecyclerItemBinding;
    private int questionNumber;
    @Bindable
    private String questionTextError;
    @Bindable
    private String answerTextError;
    @Bindable
    private String choice1TextError;
    @Bindable
    private String choice2TextError;
    @Bindable
    private String choice3TextError;
    private final MutableLiveData<String> questionText = new MutableLiveData<>("");
    private final MutableLiveData<String> answerText = new MutableLiveData<>("");
    private final MutableLiveData<String> choice1Text = new MutableLiveData<>("");
    private final MutableLiveData<String> choice2Text = new MutableLiveData<>("");
    private final MutableLiveData<String> choice3Text = new MutableLiveData<>("");

    public boolean isLongAnswerQuestion() {
        return questionRecyclerItemBinding.choicesLayout.getVisibility() == View.GONE;
    }
    public boolean inputIsValid() {
        if (isLongAnswerQuestion()) {
            return questionTextError == null
                    && answerTextError == null;
        }
        return questionTextError == null
                && answerTextError == null
                && choice1TextError == null
                && choice2TextError == null
                && choice3TextError == null;
    }

    public void setQuestionNumber(int number) {
       this.questionNumber  = number;
    }
    public Question getQuestion() {
        if (isLongAnswerQuestion()) {
            return new LongAnswerQuestion(getQuestionText(),questionNumber,getAnswerText());
        } else {
            return new MultipleChoiceQuestion(getQuestionText(),questionNumber,getAnswerText(),new String[]{getChoice1Text(),getChoice2Text(),getChoice3Text()});
        }
    }

    public void notifyAllTexts() {
        setAnswerText(getAnswerText());
        if (!isLongAnswerQuestion()) {
            setChoice1Text(getChoice1Text());
            setChoice2Text(getChoice2Text());
            setChoice3Text(getChoice3Text());
        }
        setQuestionText(getQuestionText());
        notifyPropertyChanged(BR._all);
    }

    @Bindable
    public String getQuestionText() {
        return questionText.getValue();
    }

    public void setQuestionText(String questionText) {
        if (!questionText.trim().isEmpty()) {
            this.questionText.setValue(questionText);
            notifyPropertyChanged(BR.questionText);
            setQuestionTextError(null);
        } else {
            setQuestionTextError("Question is required");
        }
    }

    @Bindable
    public String getAnswerText() {
        return answerText.getValue();
    }

    public void setAnswerText(String answerText) {
        if (!answerText.trim().isEmpty()) {
            this.answerText.setValue(answerText);
            notifyPropertyChanged(BR.answerText);
            setAnswerTextError(null);
        } else {
            setAnswerTextError("Answer is required");
        }
    }

    @Bindable
    public String getChoice1Text() {
        return choice1Text.getValue();
    }

    public void setChoice1Text(String choice1Text) {
        if (!choice1Text.trim().isEmpty()) {
            this.choice1Text.setValue(choice1Text);
            notifyPropertyChanged(BR.choice1TextError);
            setChoice1TextError(null);
        } else {
            setChoice1TextError("Choice is required");
        }
    }

    @Bindable
    public String getChoice2Text() {
        return choice2Text.getValue();
    }

    public void setChoice2Text(String choice2Text) {
        if (!choice2Text.trim().isEmpty()) {
            this.choice2Text.setValue(choice2Text);
            notifyPropertyChanged(BR.choice2TextError);
            setChoice2TextError(null);
        } else {
            setChoice2TextError("Choice is required");
        }
    }

    @Bindable
    public String getChoice3Text() {
        return choice3Text.getValue();
    }

    public void setChoice3Text(String choice3Text) {
        if (!choice3Text.trim().isEmpty()) {
            this.choice3Text.setValue(choice3Text);
            notifyPropertyChanged(BR.choice3TextError);
            setChoice3TextError(null);
        } else {
            setChoice3TextError("Choice is required");
        }
    }

    public QuestionViewModel(QuestionRecyclerItemBinding questionRecyclerItemBinding) {
        this.questionRecyclerItemBinding = questionRecyclerItemBinding;
    }

    public String getQuestionTextError() {
        return questionTextError;
    }

    public String getAnswerTextError() {
        return answerTextError;
    }

    public String getChoice1TextError() {
        return choice1TextError;
    }

    public String getChoice2TextError() {
        return choice2TextError;
    }

    public String getChoice3TextError() {
        return choice3TextError;
    }

    public void setQuestionTextError(String questionTextError) {
        this.questionTextError = questionTextError;
        notifyPropertyChanged(BR.questionTextError);
    }

    public void setAnswerTextError(String answerTextError) {
        this.answerTextError = answerTextError;
        notifyPropertyChanged(BR.answerTextError);
    }

    public void setChoice1TextError(String choice1TextError) {
        this.choice1TextError = choice1TextError;
        notifyPropertyChanged(BR.choice1TextError);
    }

    public void setChoice2TextError(String choice2TextError) {
        this.choice2TextError = choice2TextError;
        notifyPropertyChanged(BR.choice2TextError);
    }

    public void setChoice3TextError(String choice3TextError) {
        this.choice3TextError = choice3TextError;
        notifyPropertyChanged(BR.choice3TextError);
    }
}
