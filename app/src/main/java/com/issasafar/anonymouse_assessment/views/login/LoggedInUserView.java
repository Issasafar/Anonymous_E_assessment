package com.issasafar.anonymouse_assessment.views.login;

public class LoggedInUserView {
    private String userName;
    //... other data fields that may be accessible to the UI

    LoggedInUserView(String displayName) {
        this.userName = displayName;
    }

   public String getDisplayName() {
        return userName;
    }
}
