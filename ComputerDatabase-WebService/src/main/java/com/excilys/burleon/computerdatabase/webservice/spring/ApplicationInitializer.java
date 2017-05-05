package com.excilys.burleon.computerdatabase.webservice.spring;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.excilys.burleon.computerdatabase.webservice.spring.config.WebServiceConfig;

@javax.servlet.annotation.HandlesTypes(WebApplicationInitializer.class)
public class ApplicationInitializer implements WebApplicationInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationInitializer.class);

    private AnnotationConfigWebApplicationContext getContext() {
        final AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(WebServiceConfig.class);
        return context;
    }

    @Override
    public void onStartup(final ServletContext servletContext) throws ServletException {
        ApplicationInitializer.LOGGER.trace("onStartup Loading");

        final AnnotationConfigWebApplicationContext context = this.getContext();
        servletContext.setInitParameter("spring.profiles.active", "javaee");
        servletContext.addListener(new ContextLoaderListener(context));

        context.setServletContext(servletContext);
        final ServletRegistration.Dynamic servlet = servletContext.addServlet("dispatcher",
                new DispatcherServlet(context));

        servlet.addMapping("/");
        servlet.addMapping("/" + "computer");
        servlet.addMapping("/" + "company");

        servlet.setLoadOnStartup(1);

        ApplicationInitializer.LOGGER.trace("onStartup Loaded");
    }

}