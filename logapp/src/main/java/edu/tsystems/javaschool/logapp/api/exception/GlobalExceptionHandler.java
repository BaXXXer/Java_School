package edu.tsystems.javaschool.logapp.api.exception;

import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

@ControllerAdvice
@Component
public class GlobalExceptionHandler {

    @ExceptionHandler({SQLException.class, DataAccessException.class, HibernateException.class})
    public ModelAndView handleSQLException(HttpServletRequest request, Exception ex){
        ModelAndView modelAndView = new ModelAndView("exceptions/database_error");
        modelAndView.addObject("exception", ex);
        modelAndView.addObject("url", request.getRequestURL());
        return modelAndView;
    }

    @ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Entity Not Found!")
    @ExceptionHandler(EntityNotFoundException.class)
    public ModelAndView handleEntityNotFoundException(HttpServletRequest request, Exception ex){
        ModelAndView modelAndView = new ModelAndView("exceptions/entityNotFound");
        modelAndView.addObject("exception", ex);
        modelAndView.addObject("url", request.getRequestURL());
        return modelAndView;
    }

    @ExceptionHandler(DuplicateEntityException.class)
    public ModelAndView handleDuplicateEntityException(HttpServletRequest request, Exception ex) {
        ModelAndView modelAndView = new ModelAndView("exceptions/duplicateEntity");
        modelAndView.addObject("exception", ex);
        modelAndView.addObject("url", request.getRequestURL());
        return modelAndView;
    }

    @ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Incorrect operation type and cargo status")
    @ExceptionHandler(InvalidStateException.class)
    public ModelAndView handleInvalidStateException(HttpServletRequest request, Exception ex){
        ModelAndView modelAndView = new ModelAndView("exceptions/invalidStateException");
        modelAndView.addObject("exception", ex);
        modelAndView.addObject("url", request.getRequestURL());
        return modelAndView;

    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({NullPointerException.class, ObjectNotFoundException.class})
    public ModelAndView notFoundHandler(HttpServletRequest request, Exception ex) {
        ModelAndView modelAndView = new ModelAndView("exceptions/generic");
        modelAndView.addObject("exception", ex);
        modelAndView.addObject("url", request.getRequestURL());
        return modelAndView;
    }

}
