package africa.semicolon.remApp.services.admin;

import africa.semicolon.remApp.exception.ORMException;
import africa.semicolon.remApp.exceptions.REMAException;
import africa.semicolon.remApp.models.Company;
import africa.semicolon.remApp.models.Employee;
import africa.semicolon.remApp.services.company.CompanyService;
import africa.semicolon.remApp.services.employee.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@AllArgsConstructor
public class AdminUtils {

    private static EmployeeService employeeService;
    private static CompanyService companyService;
    private static final String EMAIL_REGEX_PATTERN =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    private static final Pattern pattern = Pattern.compile(EMAIL_REGEX_PATTERN);

    @SneakyThrows
    public static void validateEmailAddressList(List<String> email, String companyUniqueId) {
        List<String> invalidEmails = new ArrayList<>();
        if (email.isEmpty()) throw new REMAException("Email is empty");
        for (String singleEmail : email) {
            validateEmailDoesntExistWithCompany(singleEmail, companyUniqueId);
            Matcher matcher = pattern.matcher(singleEmail.trim());
                if (!matcher.matches()) {
                    invalidEmails.add(singleEmail);
                }
        }

        if (!invalidEmails.isEmpty()) {
            throw new ORMException("Invalid Email Address: " + String.join(", ", invalidEmails));
        }
    }

    @SneakyThrows
    private static void validateEmailDoesntExistWithCompany(String singleEmail, String companyUniqueId) {
        List<String> existingEmails = new ArrayList<>();
//        boolean isPresent = false;
        Company company = companyService.findByUniqueID(companyUniqueId);
        List<Employee> employees = company.getEmployee();
        for (Employee employee: employees) {
            if (employee.getEmail().equals(singleEmail));
//            isPresent = true;
            existingEmails.add(singleEmail);
        }

        if (!existingEmails.isEmpty()) {
            throw new ORMException("Email address exist: " + String.join(", ", existingEmails));
        }
//        if (!isPresent) {
//            throw new REMAException(String.format("%s already exist", singleEmail));
//        }
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
