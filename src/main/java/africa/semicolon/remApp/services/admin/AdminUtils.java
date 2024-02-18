package africa.semicolon.remApp.services.admin;

import africa.semicolon.remApp.exception.ORMException;
import africa.semicolon.remApp.exceptions.REMAException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AdminUtils {

    private static final String EMAIL_REGEX_PATTERN =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    private static final Pattern pattern = Pattern.compile(EMAIL_REGEX_PATTERN);

    public static void validateEmailAddressList(List<String> email) {
        System.out.println("Emails -> " + email);
        System.out.println(email.size());
        List<String> invalidEmails = new ArrayList<>();
        for (String singleEmail : email) {
            System.out.println("Single Email => " + singleEmail);
            Matcher matcher = pattern.matcher(singleEmail.trim());
                if (!matcher.matches()) {
                    System.out.println("Invalid emails => " + invalidEmails);
                    invalidEmails.add(singleEmail);
                }
        }
        System.out.println(">>>>>>>>> " + invalidEmails);

        if (!invalidEmails.isEmpty()) {
            throw new ORMException("Invalid Email Address: " + String.join(", ", invalidEmails));
        }
    }

    public static String extractToken(String tokenWithPrefix) {
        if (tokenWithPrefix != null && tokenWithPrefix.startsWith("Bearer ")) {
            return tokenWithPrefix.substring(7);
        }
        return null;
    }

//    public static String extractNameFromEmail(String email) {
//        int index = 0;
//        for (int i = 0; i < email.length(); i++) {
//            if (Character.isDigit(email.charAt(i))) {
//                index = i;
//            }
//        }
//        return email.substring(0, index-1);
//    }


    public static String extractNameFromEmail(String email) {
        int atIndex = email.indexOf('@');
        if (atIndex != -1) {
            return email.substring(0, atIndex);
        } else {
            return email;
        }
    }


    public static String getEmailTemplate(String link, String companyName) throws REMAException {
        try (BufferedReader bufferedReader =
                     new BufferedReader(new FileReader("src/main/resources/static/invitation.jsp"))) {
            List<String> templateLines = bufferedReader.lines().collect(Collectors.toList());

            for (int i = 0; i < templateLines.size(); i++) {
                templateLines.set(i, templateLines.get(i)
                        .replace("${link}", link)
                        .replace("${companyName}", companyName));
            }

            return String.join("\n", templateLines);
        } catch (IOException exception) {
            throw new REMAException("Failed to read mail template");
        }
    }


}
