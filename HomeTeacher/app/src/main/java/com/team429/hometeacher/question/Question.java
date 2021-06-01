package com.team429.hometeacher.question;

import java.util.function.Consumer;

import javax.security.auth.callback.Callback;

public class Question {
    public String QuestionString;
    public String AnswerString;
    public Consumer<Question> OnError;
    public Consumer<Question> OnRight;
    public Question(String questionString, String answerString, Consumer<Question> onError, Consumer<Question> onRight){
        QuestionString = questionString;
        AnswerString = answerString;
        OnError = onError;
        OnRight = onRight;
    }
}
