package de.mirkosertic.desktopsearch;

import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

class SuggestionServlet extends HttpServlet {

    public static final String URL = "/suggestion";

    private final Backend backend;

    public SuggestionServlet(Backend aBackend) {
        backend = aBackend;
    }

    @Override
    protected void service(HttpServletRequest aRequest, HttpServletResponse aResponse) throws ServletException, IOException {
        String theTerm = aRequest.getParameter("term");
        Suggestion[] theTerms = backend.findSuggestionTermsFor(theTerm);

        aResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        aResponse.setHeader("Pragma", "no-cache");
        aResponse.setDateHeader("Expires", 0);
        aResponse.setContentType("application/json; charset=UTF-8");
        aResponse.setCharacterEncoding("UTF-8");

        ObjectMapper theMapper = new ObjectMapper();
        theMapper.writeValue(aResponse.getWriter(), theTerms);
    }
}
