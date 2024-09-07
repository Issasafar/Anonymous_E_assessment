package com.issasafar.anonymouse_assessment.data.models.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class QuestionDeserializer implements JsonDeserializer {
    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        if (jsonObject.has("choices")) {
            return context.deserialize(jsonObject, MultipleChoiceQuestion.class);
        } else {
            return context.deserialize(jsonObject, LongAnswerQuestion.class);
        }
    }

    public static  ArrayList<Question> getQuestionsFromJson(String jsonString) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Question.class, new QuestionDeserializer())
                .create();

        Type questionListType = new TypeToken<ArrayList<Question>>() {
        }.getType();
        return gson.fromJson(jsonString, questionListType);
    }
}
