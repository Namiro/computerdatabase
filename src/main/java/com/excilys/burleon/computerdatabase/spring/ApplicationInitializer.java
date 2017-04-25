package com.excilys.burleon.computerdatabase.spring;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.excilys.burleon.computerdatabase.spring.config.ApplicationConfig;
import com.excilys.burleon.computerdatabase.view.web.constant.Servlet;

@javax.servlet.annotation.HandlesTypes(WebApplicationInitializer.class)
public class ApplicationInitializer implements WebApplicationInitializer {

    private AnnotationConfigWebApplicationContext getContext() {
        final AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(ApplicationConfig.class);
        return context;
    }

    @Override
    public void onStartup(final ServletContext servletContext) throws ServletException {
        final WebApplicationContext context = this.getContext();
        servletContext.setInitParameter("spring.profiles.active", "javaee");
        servletContext.addListener(new ContextLoaderListener(context));

        final ServletRegistration.Dynamic servlet = servletContext.addServlet("dispatcher",
                new DispatcherServlet(context));

        servlet.setLoadOnStartup(1);
        servlet.addMapping("/" + Servlet.SERVLET_COMPUTER_LIST);
        servlet.addMapping("/" + Servlet.SERVLET_COMPUTER_MANAGE);
    }

}