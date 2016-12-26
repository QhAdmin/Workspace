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
import org.cerberus.crud.entity.SqlLibrary;
import org.cerberus.log.MyLogger;
import org.cerberus.crud.service.ISqlLibraryService;
import org.cerberus.util.StringUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author bcivel
 */
public class FindAllSqlLibrary extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        PolicyFactory policy = Sanitizers.FORMATTING.and(Sanitizers.LINKS);

        try {
            String echo = policy.sanitize(request.getParameter("sEcho"));
            String sStart = policy.sanitize(request.getParameter("iDisplayStart"));
            String sAmount = policy.sanitize(request.getParameter("iDisplayLength"));
            String sCol = policy.sanitize(request.getParameter("iSortCol_0"));
            String sdir = policy.sanitize(request.getParameter("sSortDir_0"));
            String dir = "asc";
            String[] cols = {"Name", "Type", "Database", "Script", "Description"};

            /*            JSONObject result = new JSONObject();
             JSONArray array = new JSONArray();*/
            int amount = 10;
            int start = 0;
            int col = 0;

            String name = "";
            String type = "";
            String database = "";
            String script = "";
            String description = "";

            name = policy.sanitize(request.getParameter("sSearch_0"));
            type = policy.sanitize(request.getParameter("sSearch_1"));
            database = policy.sanitize(request.getParameter("sSearch_2"));
            script = policy.sanitize(request.getParameter("sSearch_3"));
            description = policy.sanitize(request.getParameter("sSearch_4"));

            List<String> sArray = new ArrayList<String>();
            if (!name.equals("")) {
                String sName = " `name` like '%" + name + "%'";
                sArray.add(sName);
            }
            if (!type.equals("")) {
                String sType = " `type` like '%" + type + "%'";
                sArray.add(sType);
            }
            if (!database.equals("")) {
                String sDatabase = " `database` like '%" + database + "%'";
                sArray.add(sDatabase);
            }
            if (!script.equals("")) {
                String sScript = " `script` like '%" + script + "%'";
                sArray.add(sScript);
            }
            if (!description.equals("")) {
                String sDescription = " `description` like '%" + description + "%'";
                sArray.add(sDescription);
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
                if (!sdir.equals("asc")) {
                    dir = "desc";
                }
            }
            String colName = cols[col];

            String searchTerm = "";
            if (!StringUtil.isNullOrEmpty(request.getParameter("sSearch"))) {
                searchTerm = request.getParameter("sSearch");
            }

            String inds = String.valueOf(individualSearch);

            JSONArray data = new JSONArray(); //data that will be shown in the table

            ApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
            ISqlLibraryService sqlLibService = appContext.getBean(ISqlLibraryService.class);

            List<SqlLibrary> sqlLibList = sqlLibService.findSqlLibraryListByCriteria(start, amount, colName, dir, searchTerm, inds);

            JSONObject jsonResponse = new JSONObject();

            for (SqlLibrary sqlLib : sqlLibList) {
                JSONArray row = new JSONArray();
                row.put(sqlLib.getName())
                        .put(sqlLib.getType())
                        .put(sqlLib.getDatabase())
                        .put(sqlLib.getScript())
                        .put(sqlLib.getDescription());

                data.put(row);
            }

            Integer iTotalRecords = sqlLibService.getNumberOfSqlLibraryPerCriteria("", "");
            Integer iTotalDisplayRecords = sqlLibService.getNumberOfSqlLibraryPerCriteria(searchTerm, inds);

            jsonResponse.put("aaData", data);
            jsonResponse.put("sEcho", echo);
            jsonResponse.put("iTotalRecords", iTotalRecords);
            jsonResponse.put("iTotalDisplayRecords", iTotalDisplayRecords);

            response.setContentType("application/json");
            response.getWriter().print(jsonResponse.toString());

        } catch (JSONException ex) {
            MyLogger.log(FindAllSqlLibrary.class.getName(), Level.FATAL, ex.toString());
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
