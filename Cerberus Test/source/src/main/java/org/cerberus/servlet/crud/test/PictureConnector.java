package org.cerberus.servlet.crud.test;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Level;
import org.cerberus.crud.entity.Parameter;
import org.cerberus.crud.entity.SavePictureConfig;
import org.cerberus.exception.CerberusException;
import org.cerberus.log.MyLogger;
import org.cerberus.crud.service.IParameterService;
import org.cerberus.crud.service.impl.ParameterService;

import org.elfinder.servlets.AbstractConnectorServlet;
import org.elfinder.servlets.config.AbstractConnectorConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

@WebServlet(name = "PictureConnector", urlPatterns = {"/PictureConnector"})
public class PictureConnector extends AbstractConnectorServlet {

    public static String SHARED_DOCS = "Shared docs";
    public static String THUMBNAIL = "Thumbnailer?p=";
    public static String HOME_SHARED_DOCS = "";
    public static String REALOBJECTURL = "VirtualProxy";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            ApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
            IParameterService parameterService = appContext.getBean(ParameterService.class);
            Parameter param = parameterService.findParameterByKey("cerberus_picture_testcase_path", "");
            HOME_SHARED_DOCS = param.getValue();
        } catch (CerberusException e) {
            MyLogger.log(PictureConnector.class.getName(), Level.FATAL, "Parameter (cerberus_picture_testcase_path) not in Parameter table.");
        }
        if (!StringUtils.isBlank(getServletContext().getInitParameter("THUMBNAIL"))){
            THUMBNAIL = getServletContext().getInitParameter("THUMBNAIL");
        }
        if (!StringUtils.isBlank(getServletContext().getInitParameter("SHARED_DOCS"))){
            SHARED_DOCS = getServletContext().getInitParameter("SHARED_DOCS");
        }
        if (!StringUtils.isBlank(getServletContext().getInitParameter("REALOBJECTURL"))){
            REALOBJECTURL = getServletContext().getInitParameter("REALOBJECTURL");
        }
    }

    @Override
    protected AbstractConnectorConfig prepareConfig(HttpServletRequest httpServletRequest) throws Exception {
        return new SavePictureConfig();
    }
}
