package edu.tsystems.javaschool.logapp.api.config;

import edu.tsystems.javaschool.logapp.api.dto.DriverDTO;
import edu.tsystems.javaschool.logapp.api.entity.Driver;
import edu.tsystems.javaschool.logapp.api.exception.EntityNotFoundException;
import edu.tsystems.javaschool.logapp.api.model.DriverUserModel;
import edu.tsystems.javaschool.logapp.api.model.UserModel;
import edu.tsystems.javaschool.logapp.api.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class AuthenticationHandler implements AuthenticationSuccessHandler {

    private final DriverService driverService;

    @Autowired
    public AuthenticationHandler(DriverService driverService) {
        this.driverService = driverService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        String userTitle = "Logapp manager";
        String userStatus = "online";
        String personalCode = "";

        try {

            UserModel principal = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof DriverUserModel) {
                DriverDTO dto = driverService.getDriverById(((DriverUserModel) principal).getDriverId());
                Driver driver = driverService.toEntity(dto);
                userTitle = driver.getDriverFirstName() + " " + driver.getDriverSurname();
                userStatus = driver.getDriverStatus().toString();
                personalCode = driver.getDriverPrivateNum().toString();
            }
        } catch (EntityNotFoundException e) {
            userTitle = "Unknown";
        }

        HttpSession session = request.getSession();
        session.setAttribute("userTitle", userTitle);
        session.setAttribute("userStatus", userStatus);
        session.setAttribute("userPersonalCode", personalCode);

        response.sendRedirect(request.getContextPath() + "/");
    }


}
