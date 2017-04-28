package com.excilys.burleon.computerdatabase.webapp.spring;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.excilys.burleon.computerdatabase.webapp.constant.Servlet;
import com.excilys.burleon.computerdatabase.webapp.spring.config.WebAppConfig;

@javax.servlet.annotation.HandlesTypes(WebApplicationInitializer.class)
public class ApplicationInitializer implements WebApplicationInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationInitializer.class);

    private AnnotationConfigWebApplicationContext getContext() {
        final AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(WebAppConfig.class);
        return context;
    }

    @Override
    public void onStartup(final ServletContext servletContext) throws ServletException {
        ApplicationInitializer.LOGGER.trace("onStartup Loading");
        
        final WebApplicationContext context = this.getContext();
        servletContext.setInitParameter("spring.profiles.active", "javaee");
        servletContext.addListener(new ContextLoaderListener(context));

        final ServletRegistration.Dynamic servlet = servletContext.addServlet("dispatcher",
                new DispatcherServlet(context));

        servlet.setLoadOnStartup(1);
        servlet.addMapping("/" + Servlet.SERVLET_COMPUTER_LIST);
        servlet.addMapping("/" + Servlet.SERVLET_COMPUTER_MANAGE);
        
        ApplicationInitializer.LOGGER.trace("onStartup Loaded");
    }

}