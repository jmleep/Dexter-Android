package com.leeper.jordan.rolodex.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jordan on 12/8/15.
 */
public class ContactDetailsValidator {

    public static boolean validateContactDetails(String name, String phoneNumber, String emailAddress) {
        boolean isValid = false;

        if(!name.isEmpty() && validateContactPhoneNumber(phoneNumber) && validateEmailAddress(emailAddress)) {
            isValid = true;
        }

        return isValid;

    }

    private static boolean validateContactPhoneNumber(String phoneNumber) {
        boolean isValid = false;
        Pattern phonePattern = Pattern.compile("\\d{3}\\s?-\\s?\\d{3}\\s?-\\s?\\d{4}|\\d{10}|\\d{3}\\s?-\\s?\\d{7}|\\(\\d{3}\\)\\s?-\\s?\\d{3}\\s?-\\s?\\d{4}");
        Matcher matchPhone = phonePattern.matcher(phoneNumber);

        if(matchPhone.matches()) {
            isValid = true;
        }

        return isValid;
    }

    private static boolean validateEmailAddress(String emailAddress) {
        boolean isValid = false;

        Pattern emailPattern = Pattern.compile(".*@.*\\..*");
        Matcher matchEmail = emailPattern.matcher(emailAddress);

        if(matchEmail.matches()) {
            isValid = true;
        }

        return isValid;
    }
}
