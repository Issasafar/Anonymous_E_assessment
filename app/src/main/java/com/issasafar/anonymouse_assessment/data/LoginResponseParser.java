package com.issasafar.anonymouse_assessment.data;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class LoginResponseParser {
    public LoginResponse parse(InputStream inputStream) throws Exception {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            StringBuilder responseStringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseStringBuilder.append(line);
            }

            // Parse the JSON or handle the response data as needed
            String jsonResponse = responseStringBuilder.toString();
            // Example: You might use a JSON library to parse the response
            // For simplicity, assuming LoginResponse has a constructor that takes a JSON string.
            return new LoginResponse(jsonResponse);
        }
    }
}
