package edu.tsystems.javaschool.logapp.api.config;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@Order(Ordered.HIGHEST_PRECEDENCE)
public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    public void onStartup(ServletContext servletContext){
        AnnotationConfigWebApplicationContext context
                = new AnnotationConfigWebApplicationContext();

        servletContext.addListener(new ContextLoaderListener(context));
        servletContext.setInitParameter(
                "contextConfigLocation", "edu.tsystems.javaschool.logapp.api");
    }

    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {PersistenceConfig.class, WebMvcConfig.class};
    }

    protected Class<?>[] getServletConfigClasses() {
        return null;
    }

    protected String[] getServletMappings() {
        return new String[] {"/"};
    }
}
