package chornyi.conferences.web.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class PaginationUtil {

    private String getParameter(HttpServletRequest request, String parameter, String defaultParameter) {
        if (request.getParameter(parameter) != null && !request.getParameter(parameter).isEmpty())
            return request.getParameter(parameter);
        else
            return defaultParameter;
    }

    public Map<String, Integer> getAttributes(HttpServletRequest request, int lines) {

        int currentPage = Integer.parseInt(getParameter(request, "current-page", "1"));
        int recordsPerPage = Integer.parseInt(getParameter(request, "records-per-page", "5"));
        int nOfPages = lines / recordsPerPage;

        if (lines % recordsPerPage > 0)
            nOfPages++;

        if (currentPage > nOfPages)
            currentPage = nOfPages;

        int begin = currentPage * recordsPerPage - recordsPerPage;

        if (begin < 0)
            begin = 0;

        Map<String, Integer> paginationAttributes = new HashMap<>();
        paginationAttributes.put("nOfPages", nOfPages);
        paginationAttributes.put("currentPage", currentPage);
        paginationAttributes.put("recordsPerPage", recordsPerPage);
        paginationAttributes.put("begin", begin);

        return paginationAttributes;
    }
}
