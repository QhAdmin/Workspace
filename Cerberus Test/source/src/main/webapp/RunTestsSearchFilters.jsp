<%--
  ~ Cerberus  Copyright (C) 2013  vertigo17
  ~ DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
  ~
  ~ This file is part of Cerberus.
  ~
  ~ Cerberus is free software: you can redistribute it and/or modify
  ~ it under the terms of the GNU General Public License as published by
  ~ the Free Software Foundation, either version 3 of the License, or
  ~ (at your option) any later version.
  ~
  ~ Cerberus is distributed in the hope that it will be useful,
  ~ but WITHOUT ANY WARRANTY; without even the implied warranty of
  ~ MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  ~ GNU General Public License for more details.
  ~
  ~ You should have received a copy of the GNU General Public License
  ~ along with Cerberus.  If not, see <http://www.gnu.org/licenses/>.
--%>
<%@page import="org.cerberus.crud.entity.TestBattery"%>
<%@page import="org.cerberus.crud.entity.Campaign"%>
<%@page import="org.cerberus.crud.service.ITestBatteryService"%>
<%@page import="org.cerberus.crud.service.ICampaignService"%>
<%@page import="org.cerberus.crud.entity.Invariant"%>
<%@page import="org.cerberus.crud.entity.Application"%>
<%@page import="org.cerberus.crud.entity.User"%>
<%@page import="org.cerberus.crud.entity.BuildRevisionInvariant"%>
<%@page import="org.cerberus.crud.service.IInvariantService"%>
<%@page import="java.util.TreeMap"%>
<%@page import="org.cerberus.crud.entity.Project"%>
<%@page import="org.cerberus.crud.entity.Test"%>
<%@page import="org.cerberus.crud.service.IProjectService"%>
<%@page import="org.cerberus.crud.service.ITestService"%>
<%@page import="org.cerberus.crud.service.IDocumentationService"%>
<%@page import="org.cerberus.crud.service.impl.InvariantService"%>
<%@page import="org.cerberus.crud.service.IApplicationService"%>
<%@page import="org.cerberus.crud.service.IUserService"%>
<%@page import="org.cerberus.crud.service.IBuildRevisionInvariantService"%>
<%@ page import="org.apache.log4j.Logger" %>
            <div id="filtersList" style="clear:both;">

<%@page contentType="text/html" pageEncoding="UTF-8"%>

        <%@ include file="include/function.jsp" %>
            <%
                final Logger LOG = Logger.getLogger(this.getClass());
                TreeMap<String, String> options = new TreeMap<String, String>();

                IInvariantService invariantService = appContext.getBean(InvariantService.class);
                ITestService testService = appContext.getBean(ITestService.class);
                IProjectService projectService = appContext.getBean(IProjectService.class);
                IDocumentationService docService = appContext.getBean(IDocumentationService.class);
                IApplicationService applicationService = appContext.getBean(IApplicationService.class);
                IUserService userService = appContext.getBean(IUserService.class);
                IBuildRevisionInvariantService buildRevisionInvariantService = appContext.getBean(IBuildRevisionInvariantService.class);
                ICampaignService campaignService = appContext.getBean(ICampaignService.class);
                ITestBatteryService batteryService = appContext.getBean(ITestBatteryService.class);
                
                String myLang = request.getParameter("MyLang");

                try {
            %>

                <form id="formAddNewContentSearch" action="RunTestsSearchResult.jsp" method="post">
                    <br><div class="underlinedDiv"></div>
                        <p style="text-align:left" class="dttTitle">Testcase Filters</p>
                        <div style="float:left">
                            <div style="float:left; width:10%">
                                <div style="width:150px; text-align: left"><%=docService.findLabelHTML("test", "Test", "Test", myLang)%></div>
                                <div>
                                    <%
                                        options.clear();
                                        for (Test testL : testService.getListOfTest()) {
                                            options.put(testL.getTest(), testL.getTest());
                                        }
                                    %>
                                    <%=generateMultiSelect("Test", request.getParameterValues("Test"), options,
                                            "Select a test", "Select Test", "# of # Test selected", 1, true)%>
                                </div>
                            </div>
                            <div style="float:left; width:10%">
                                <div style="width:150px; text-align: left"><%=docService.findLabelHTML("project", "idproject", "Project", myLang)%></div>
                                <div>
                                    <%
                                        options.clear();
                                        for (Project project : projectService.convert(projectService.readAll())) {
                                            if (project.getIdProject() != null && !"".equals(project.getIdProject().trim())) {
                                                options.put(project.getIdProject(), project.getIdProject() + " - " + project.getDescription());
                                            }
                                        }
                                    %>
                                    <%=generateMultiSelect("Project", request.getParameterValues("Project"), options,
                                            "Select a project", "Select Project", "# of # Project selected", 1, true)%>
                                </div>
                            </div>
                            <div style="float:left; width:10%">
                                <div style="clear:both; width:150px; text-align: left"><%=docService.findLabelHTML("application", "System", "System", myLang)%></div>
                                <div style="clear:both">
                                    <%
                                        options.clear();
                                        AnswerList ansDistSystem = applicationService.readDistinctSystem();
                                        for (String system : (List<String>) ansDistSystem.getDataList()) {
                                            options.put(system, system);
                                        }
                                    %>
                                    <%=generateMultiSelect("System", request.getParameterValues("System"), options,
                                            "Select a sytem", "Select System", "# of # System selected", 1, true)%>
                                </div>
                            </div>
                            <div style="float:left">
                                <div style="clear:both; width:150px; text-align: left"><%=docService.findLabelHTML("application", "Application", "Application", myLang)%></div>
                                <div style="clear:both">
                                    <%
                                        options.clear();
                                        for (Application app : applicationService.convert(applicationService.readAll())) {
                                            options.put(app.getApplication(), app.getApplication() + " [" + app.getSystem() + "]");
                                        }
                                    %>
                                    <%=generateMultiSelect("Application", request.getParameterValues("Application"), options,
                                            "Select an application", "Select Application", "# of # Application selected", 1, true)%>
                                </div>
                            </div>
                            <div style="float:left">
                                <div style="clear:both; width:150px; text-align: left"><%=docService.findLabelHTML("testcase", "tcactive", "TestCase Active", myLang)%></div>
                                <div style="clear:both">
                                    <%
                                        options.clear();
                                        options.put("Y", "Yes");
                                        options.put("N", "No");
                                    %>
                                    <%=generateMultiSelect("TcActive", request.getParameterValues("TcActive"), options,
                                            "Select Activation state", "Select Activation", "# of # Activation state selected", 1, true)%> 
                                </div>
                            </div>
                            <div style="float:left">
                                <div style="clear:both; width:150px; text-align: left"><%=docService.findLabelHTML("invariant", "PRIORITY", "Priority", myLang)%></div>
                                <div style="clear:both">
                                    <%
                                        options.clear();
                                        AnswerList ansPriority = invariantService.readByIdname("PRIORITY");
                                        
                                        for (Invariant invPriority :  (List<Invariant>)ansPriority.getDataList()) {
                                            options.put(invPriority.getValue(), invPriority.getValue());
                                        }
                                    %>
                                    <%=generateMultiSelect("Priority", request.getParameterValues("Priority"), options,
                                            "Select a Priority", "Select Priority", "# of # Priority selected", 1, true)%>
                                </div>
                            </div>
                            <div style="float:left">
                                <div style="clear:both; width:150px; text-align: left"><%=docService.findLabelHTML("testcase", "Status", "Status", myLang)%></div>
                                <div style="clear:both">
                                    <%
                                        options.clear();
                                        AnswerList ansStatus = invariantService.readByIdname("TCSTATUS");
                                        for (Invariant statusInv : (List<Invariant>)ansStatus.getDataList()) {
                                            options.put(statusInv.getValue(), statusInv.getValue());
                                        }
                                    %>
                                    <%=generateMultiSelect("Status", request.getParameterValues("Status"), options,
                                            "Select an option", "Select Status", "# of # Status selected", 1, true)%>
                                </div>
                            </div>
                            <div style="float:left">
                                <div style="clear:both; width:150px; text-align: left"><%=docService.findLabelHTML("invariant", "GROUP", "Group", myLang)%></div>
                                <div style="clear:both">
                                    <%
                                        options.clear();
                                        AnswerList ansGroup = invariantService.readByIdname("GROUP");
                                        for (Invariant invGroup : (List<Invariant>)ansGroup.getDataList()) {
                                            if (invGroup.getValue() != null && !"".equals(invGroup.getValue().trim())) {
                                                options.put(invGroup.getValue(), invGroup.getValue());
                                            }
                                        }
                                    %>
                                    <%=generateMultiSelect("Group", request.getParameterValues("Group"), options,
                                            "Select a Group", "Select Group", "# of # Group selected", 1, true)%> 
                                </div>
                            </div>
                            <div style="float:left">
                                <div style="clear:both; width:150px; text-align: left"><%=docService.findLabelHTML("testcase", "targetBuild", "targetBuild", myLang)%></div>
                                <div style="clear:both">
                                    <%
                                        options.clear();
                                        options.put("NTB", "-- No Target Build --");
                                        for (BuildRevisionInvariant invBuild : buildRevisionInvariantService.convert(buildRevisionInvariantService.readBySystemLevel(request.getParameter("system"), 1))) {
                                            if (invBuild.getVersionName() != null && !"".equals(invBuild.getVersionName().trim())) {
                                                options.put(invBuild.getVersionName(), invBuild.getVersionName());
                                            }
                                        }
                                    %>
                                    <%=generateMultiSelect("TargetBuild", request.getParameterValues("TargetBuild"), options,
                                            "Select a Target Build", "Select Target Build", "# of # Target Build selected", 1, true)%> 
                                </div>
                            </div>
                            <div style="float:left">
                                <div style="clear:both; width:150px; text-align: left"><%=docService.findLabelHTML("testcase", "targetRev", "targetRev", myLang)%></div>
                                <div style="clear:both">
                                    <%
                                        options.clear();
                                        options.put("NTR", "-- No Target Rev --");
                                        for (BuildRevisionInvariant invRevision : buildRevisionInvariantService.convert(buildRevisionInvariantService.readBySystemLevel(request.getParameter("system"), 2))) {
                                            if (invRevision.getVersionName() != null && !"".equals(invRevision.getVersionName().trim())) {
                                                options.put(invRevision.getVersionName(), invRevision.getVersionName());
                                            }
                                        }
                                    %>
                                    <%=generateMultiSelect("TargetRev", request.getParameterValues("TargetRev"), options,
                                            "Select a Target Rev", "Select Target Rev", "# of # Target Rev selected", 1, true)%> 
                                </div>
                            </div>
                            <div style="float:left">
                                <div style="clear:both; width:150px; text-align: left"><%=docService.findLabelHTML("testcase", "creator", "Creator", myLang)%></div>
                                <div style="clear:both">
                                    <%
                                        options.clear();
                                        for (User user : userService.findallUser()) {
                                            if (user.getLogin() != null && !"".equals(user.getLogin().trim())) {
                                                options.put(user.getLogin(), user.getLogin());
                                            }
                                        }
                                    %>
                                    <%=generateMultiSelect("Creator", request.getParameterValues("Creator"), options,
                                            "Select a Creator", "Select Creator", "# of # Creator selected", 1, true)%> 
                                </div>
                            </div>
                            <div style="float:left">
                                <div style="clear:both; width:150px; text-align: left"><%=docService.findLabelHTML("testcase", "implementer", "implementer", myLang)%></div>
                                <div style="clear:both">
                                    <%=generateMultiSelect("Implementer", request.getParameterValues("Implementer"), options,
                                            "Select an Implementer", "Select Implementer", "# of # Implementer selected", 1, true)%> 
                                </div>
                            </div>
                                <div style="float:left">
                        <div style="clear:both; width:150px; text-align: left">Campaign</div>
                        <%
                                        options.clear();
                                        options.put("NC", "-- No Campaign --");
                                        for (Campaign campaign : campaignService.findAll()) {
                                            if (campaign.getCampaign() != null && !"".equals(campaign.getCampaign().trim())) {
                                                options.put(campaign.getCampaign(), campaign.getCampaign());
                                            }
                                        }
                                    %>
                        <div style="clear:both">
                            <%=generateMultiSelect("Campaign", request.getParameterValues("Campaign"), options,
                                            "Select a Campaign", "Select a Campaign", "# of # Campaign selected", 1, true)%> 
                        </div>
                    </div>
                    <div style="float:left">
                        <div style="clear:both; width:150px; text-align: left">testBattery</div>
                        <%
                                        options.clear();
                                        options.put("NC", "-- No Battery  --");
                                        for (TestBattery testBattery : batteryService.findAll()) {
                                            if (testBattery.getTestbattery() != null && !"".equals(testBattery.getTestbattery().trim())) {
                                                options.put(testBattery.getTestbattery(), testBattery.getTestbattery());
                                            }
                                        }
                                    %>
                        <div style="clear:both">
                            <%=generateMultiSelect("Battery", request.getParameterValues("Battery"), options,
                                            "Select a Battery", "Select a Battery", "# of # Battery selected", 1, true)%> 
                        </div>
                    </div>
                        </div>
                                <br><br>
                                <button id="submit" name="submit" type="submit">Search</button>
                </form>
                <div id="displayResult">
                    <br>
                    <br>
                    <br>
                    <br>
                    <br>
                    <br>
                </div>
                <%
                }  catch (CerberusException ex){
                    LOG.error("Unable to find Invariant, Application or User : " + ex.toString());
                    out.println("</script>");
                    out.print("<script type='text/javascript'>alert(\"Unfortunately an error as occurred, try reload the page.\\n");
                    out.print("Detail error: " + ex.getMessageError().getDescription() + "\");</script>");
                }

                %>
                
            </div>

        <script type="text/javascript">
            $(document).ready(function() {
                $(".multiSelectOptions").each(function() {
                    var currentElement = $(this);
                    currentElement.multiselect({
                        multiple: true,
                        minWidth: 150,
                        header: currentElement.data('header'),
                        noneSelectedText: currentElement.data('none-selected-text'),
                        selectedText: currentElement.data('selected-text'),
                        selectedList: currentElement.data('selected-list')
                    });
                });

                // prepare all forms for ajax submission
                $('#formAddNewContentSearch').on('submit', function(e) {
                    $('#displayResult').html('<img src="./images/loading.gif"> loading...');
                    e.preventDefault(); // <-- important
                    $(this).ajaxSubmit({
                        target: '#displayResult'
                    });
                });
            });
        </script>
        
