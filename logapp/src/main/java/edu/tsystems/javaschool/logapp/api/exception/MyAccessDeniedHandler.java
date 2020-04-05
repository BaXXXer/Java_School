package edu.tsystems.javaschool.logapp.api.exception;

import org.apache.log4j.Logger;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {

    private static final Logger LOG = Logger.getLogger(MyAccessDeniedHandler.class);

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        LOG.error("Non-autheticated access happened" + Arrays.toString(e.getStackTrace()));
        httpServletResponse.sendRedirect("/accessDenied");
    }
}
