package com.issasafar.anonymouse_assessment.data;

public  class LoginResponse {
    //private String authToken;
    private String userId;
    // Other relevant fields...

    public LoginResponse( String userId /* Other parameters... */) {

        this.userId = userId;
        // Initialize other fields...
    }

    // Getter methods for retrieving the values...

//    public String getAuthToken() {
//        return authToken;
//    }

    public String getUserId() {
        return userId;
    }

    // Other getter methods...
}
