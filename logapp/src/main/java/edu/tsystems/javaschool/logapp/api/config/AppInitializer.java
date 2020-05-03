package edu.tsystems.javaschool.logapp.api.config;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

@Order(Ordered.HIGHEST_PRECEDENCE)
public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {PersistenceConfig.class, WebMvcConfig.class};
    }

    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{};
    }

    protected String[] getServletMappings() {
        return new String[] {"/"};
    }
}
