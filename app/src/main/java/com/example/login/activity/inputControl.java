package com.example.login.activity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class inputControl {


    public static boolean  isValidPassword(String password){
        Pattern pattern;
        Matcher matcher;


        final String PASSWORD_PATTERN =
                "^" +
                        "(?=.*[0-9])" +         //at least 1 digit
                        "(?=.*[a-z])" +         //at least 1 lower case letter
                        "(?=.*[A-Z])" +         //at least 1 upper case letter
                        "(?=\\S+$)" +           //no white spaces
                        ".{4,20}" +               //at least 4 characters to 20
                        "$";
        pattern =  Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);


        return matcher.matches();
    }
    public static boolean isValidEmail(String email){
        Pattern pattern;
        Matcher matcher;

        final String EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-zA-Z]+\\.+[a-z]+";

        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);

        return matcher.matches();

    }

}
