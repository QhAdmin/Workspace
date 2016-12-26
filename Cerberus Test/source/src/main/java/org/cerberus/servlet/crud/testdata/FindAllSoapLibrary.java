/* DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This file is part of Cerberus.
 *
 * Cerberus is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Cerberus is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Cerberus.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.cerberus.servlet.crud.testdata;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Level;
import org.cerberus.crud.entity.SoapLibrary;
import org.cerberus.log.MyLogger;
import org.cerberus.crud.service.ISoapLibraryService;
import org.cerberus.util.StringUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.util.HtmlUtils;

/**
 *
 * @author cte
 */
public class FindAllSoapLibrary extends HttpServlet {

    private final static String ASC = "asc";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    final void processRequest(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        final PrintWriter out = response.getWriter();
        final PolicyFactory policy = Sanitizers.FORMATTING.and(Sanitizers.LINKS);

        try {
            final String echo = policy.sanitize(request.getParameter("sEcho"));
            final String sStart = policy.sanitize(request.getParameter("iDisplayStart"));
            final String sAmount = policy.sanitize(request.getParameter("iDisplayLength"));
            final String sCol = policy.sanitize(request.getParameter("iSortCol_0"));
            final String sdir = policy.sanitize(request.getParameter("sSortDir_0"));
            String dir = ASC;
            final String[] cols = {"Name", "Type", "Envelope", "Description", "ServicePath", "Method", "ParsingAnswer}"};

            //JSONObject result = new JSONObject();
            //JSONArray array = new JSONArray();
            int amount = 10;
            int start = 0;
            int col = 0;

            String type = "";
            String name = "";
            String envelope = "";
            String description = "";
            String servicePath = "";
            String parsingAnswer = "";
            String method = "";

            name = policy.sanitize(request.getParameter("sSearch_0"));
            type = policy.sanitize(request.getParameter("sSearch_1"));
            envelope = HtmlUtils.htmlEscape(request.getParameter("sSearch_2"));
            description = policy.sanitize(request.getParameter("sSearch_3"));
            servicePath = policy.sanitize(request.getParameter("sSearch_4"));
            parsingAnswer = policy.sanitize(request.getParameter("sSearch_5"));
            method = policy.sanitize(request.getParameter("sSearch_6"));

            List<String> sArray = new ArrayList<String>();
            if (!type.equals("")) {
                String sType = " `type` like '%" + type + "%'";
                sArray.add(sType);
            }
            if (!name.equals("")) {
                String sName = " `name` like '%" + name + "%'";
                sArray.add(sName);
            }
            if (!StringUtil.isNullOrEmpty(envelope)) {
                String sScript = " `envelope` like '%" + envelope + "%'";
                sArray.add(sScript);
            }
            if (!description.equals("")) {
                String sDescription = " `description` like '%" + description + "%'";
                sArray.add(sDescription);
            }
            if (!servicePath.equals("")) {
                String sServicePath = " `servicePath` like '%" + servicePath + "%'";
                sArray.add(sServicePath);
            }
            if (!parsingAnswer.equals("")) {
                String sParsingAnswer = " `parsingAnswer` like '%" + parsingAnswer + "%'";
                sArray.add(sParsingAnswer);
            }
            if (!method.equals("")) {
                String sMethod = " `method` like '%" + method + "%'";
                sArray.add(sMethod);
            }

            StringBuilder individualSearch = new StringBuilder();
            if (sArray.size() == 1) {
                individualSearch.append(sArray.get(0));
            } else if (sArray.size() > 1) {
                for (int i = 0; i < sArray.size() - 1; i++) {
                    individualSearch.append(sArray.get(i));
                    individualSearch.append(" and ");
                }
                individualSearch.append(sArray.get(sArray.size() - 1));
            }

            if (!StringUtil.isNullOrEmpty(sStart)) {
                start = Integer.parseInt(sStart);
                if (start < 0) {
                    start = 0;
                }
            }
            if (!StringUtil.isNullOrEmpty(sAmount)) {
                amount = Integer.parseInt(sAmount);
                if (amount < 10 || amount > 100) {
                    amount = 10;
                }
            }

            if (!StringUtil.isNullOrEmpty(sCol)) {
                col = Integer.parseInt(sCol);
                if (col < 0 || col > 5) {
                    col = 0;
                }
            }
            if (!StringUtil.isNullOrEmpty(sdir)) {
                if (!sdir.equals(ASC)) {
                    dir = "desc";
                }
            }
            String colName = cols[col];

            String searchTerm = "";
            if (!!StringUtil.isNullOrEmpty(request.getParameter("sSearch"))) {
                searchTerm = request.getParameter("sSearch");
            }

            String inds = String.valueOf(individualSearch);

            JSONArray data = new JSONArray(); //data that will be shown in the table

            ApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
            ISoapLibraryService soapLibService = appContext.getBean(ISoapLibraryService.class);

            List<SoapLibrary> soapLibList = soapLibService.findSoapLibraryListByCriteria(start, amount, colName, dir, searchTerm, inds);

            JSONObject jsonResponse = new JSONObject();

            for (SoapLibrary soapLib : soapLibList) {
                JSONArray row = new JSONArray();
                row.put(soapLib.getName())
                        .put(soapLib.getType())
                        .put(soapLib.getEnvelope())
                        .put(soapLib.getDescription())
                        .put(soapLib.getServicePath())
                        .put(soapLib.getMethod())
                        .put((soapLib.getParsingAnswer()));

                data.put(row);
            }

            Integer iTotalRecords = soapLibService.getNumberOfSoapLibraryPerCrtiteria("", "");
            Integer iTotalDisplayRecords = soapLibService.getNumberOfSoapLibraryPerCrtiteria(searchTerm, inds);

            jsonResponse.put("aaData", data);
            jsonResponse.put("sEcho", echo);
            jsonResponse.put("iTotalRecords", iTotalRecords);
            jsonResponse.put("iTotalDisplayRecords", iTotalDisplayRecords);

            response.setContentType("application/json");
            response.getWriter().print(jsonResponse.toString());
        } catch (JSONException ex) {
            MyLogger.log(FindAllSoapLibrary.class.getName(), Level.FATAL, ex.toString());
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
