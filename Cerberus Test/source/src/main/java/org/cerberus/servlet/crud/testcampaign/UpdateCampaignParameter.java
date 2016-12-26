/*
 * Cerberus  Copyright (C) 2013  vertigo17
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
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
package org.cerberus.servlet.crud.testcampaign;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.cerberus.crud.entity.CampaignParameter;
import org.cerberus.engine.entity.MessageGeneral;
import org.cerberus.enums.MessageGeneralEnum;
import org.cerberus.exception.CerberusException;
import org.cerberus.crud.service.ICampaignService;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author memiks
 */
@WebServlet(name = "UpdateCampaignParameter", urlPatterns = {"/UpdateCampaignParameter"})
public class UpdateCampaignParameter extends HttpServlet {

    private ICampaignService campaignService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
        campaignService = appContext.getBean(ICampaignService.class);
        PolicyFactory policy = Sanitizers.FORMATTING.and(Sanitizers.LINKS);

        String pk = policy.sanitize(request.getParameter("id"));
        String name = policy.sanitize(request.getParameter("columnName"));
        String value = policy.sanitize(request.getParameter("value"));

        response.setContentType("text/html");
        try {
            CampaignParameter campaignParameter = campaignService.findCampaignParameterByKey(Integer.parseInt(pk));
            if (name != null && "Value".equals(name.trim())) {
                campaignParameter.setValue(value);
            } else {
                throw new CerberusException(new MessageGeneral(MessageGeneralEnum.NOT_IMPLEMEMTED));
            }
            campaignService.updateCampaignParameter(campaignParameter);
            response.getWriter().print(value);
        } catch (CerberusException ex) {
            response.getWriter().print(ex.getMessageError().getDescription());
        }
    }
}
