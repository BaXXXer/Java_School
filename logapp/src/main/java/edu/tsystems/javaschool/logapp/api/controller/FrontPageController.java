package edu.tsystems.javaschool.logapp.api.controller;

import edu.tsystems.javaschool.logapp.api.entity.User;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class FrontPageController {
    @RequestMapping("/")
    public void commonFrontPage(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (request.isUserInRole(User.UserRole.ROLE_MANAGER.toString())) {
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/trucks"));
        }
        else if (request.isUserInRole(User.UserRole.ROLE_DRIVER.toString())) {
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/myOrder"));
        }
        else {
            throw new AccessDeniedException("User role is unknown.");
        }
    }

}
