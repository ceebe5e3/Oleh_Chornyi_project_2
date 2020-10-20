package chornyi.conferences.web.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ValidationUtil {

    public static boolean isDataValid(HttpServletRequest request, ResourceBundle regexBundle, ResourceBundle messageBundle){
        List<String> parameters = new ArrayList<>(request.getParameterMap().keySet());
        if (parameters.isEmpty())
            return false;

        boolean valid = true;
        for (String parameter : parameters) {
            if (parameter.contains("Id") ||
                    parameter.contains("speaker") ||
                    parameter.contains("submitted") ||
                    parameter.contains("page") ||
                    parameter.contains("conferencesLink"))
                continue;
            if (!isParameterValid(request.getParameter(parameter), regexBundle.getString(parameter + ".regexp"))) {
                request.setAttribute("incorrect_" + parameter, messageBundle.getString("incorrect." + parameter));
                valid = false;
            } else {
                request.setAttribute(parameter, request.getParameter(parameter));
            }
        }
        return valid;
    }

    private static boolean isParameterValid(String parameterData, String regexp) {
        return Pattern.compile(regexp).matcher(parameterData).matches();
    }
}
