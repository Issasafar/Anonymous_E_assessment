package com.issasafar.anonymouse_assessment.viewmodels;

import android.text.TextUtils;
import android.util.Patterns;

import java.util.Objects;

abstract public class InputValidator {
    public static String validateEmail(String email) {
        String result = null;
        if (TextUtils.isEmpty(email.trim())) {
            result = "Email is required";
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()) {
            result = "Invalid email address";
        }
        return result;
    }

    public static String validateName(String name) {
        if (TextUtils.isEmpty(name.trim())) {
            return "Name is required";
        } else {
            return null;
        }
    }
    public static String validatePassword(String password) {
        if (TextUtils.isEmpty(password.trim())) {
            return "Password is empty";

        } else if (password.trim().length() <= 5) {
            return "Password should be more than 5 characters";
        } else {
            return null;
        }
    }

    public static String validateRepeatedPassword(String password, String repeated) {
        if (!Objects.equals(password.trim(), repeated.trim())) {
            return "Password doesn't match";
        } else {
            return null;
        }
    }

    public static String validateSign(String sign) {
        if (TextUtils.isEmpty(sign.trim())) {
            return "Enter your sign";
        } else {
            return null;
        }
    }
}
