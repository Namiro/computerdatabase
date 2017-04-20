package com.excilys.burleon.computerdatabase.spring;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.excilys.burleon.computerdatabase.spring.config.MainConfig;

@javax.servlet.annotation.HandlesTypes(WebApplicationInitializer.class)
public class ApplicationInitializer implements WebApplicationInitializer {

    private AnnotationConfigWebApplicationContext getContext() {
        final AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(MainConfig.class);
        return context;
    }

    @Override
    public void onStartup(final ServletContext servletContext) throws ServletException {
        final WebApplicationContext context = this.getContext();
        servletContext.addListener(new ContextLoaderListener(context));
    }

}