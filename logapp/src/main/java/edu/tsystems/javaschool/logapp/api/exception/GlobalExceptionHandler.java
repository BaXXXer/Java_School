package edu.tsystems.javaschool.logapp.api.exception;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Arrays;

@ControllerAdvice
@Component
public class GlobalExceptionHandler {

    private static final Logger LOG = Logger.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({SQLException.class, DataAccessException.class, HibernateException.class})
    public ModelAndView handleSQLException(HttpServletRequest request, Exception ex){
        LOG.error(ex.getMessage()+ Arrays.toString(ex.getStackTrace()));
        ModelAndView modelAndView = new ModelAndView("exceptions/database_error");
        modelAndView.addObject("exception", ex);
        modelAndView.addObject("url", request.getRequestURL());
        return modelAndView;
    }

    @ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Entity Not Found!")
    @ExceptionHandler(EntityNotFoundException.class)
    public ModelAndView handleEntityNotFoundException(HttpServletRequest request, Exception ex){
        ModelAndView modelAndView = new ModelAndView("exceptions/entityNotFound");
        LOG.error(ex.getMessage()+Arrays.toString(ex.getStackTrace()));
        modelAndView.addObject("exception", ex);
        modelAndView.addObject("url", request.getRequestURL());
        return modelAndView;
    }
//    @ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Incorrect operation type and cargo status")
    @ExceptionHandler(DuplicateEntityException.class)
    public ModelAndView handleDuplicateEntityException(HttpServletRequest request, Exception ex) {
        LOG.error(ex.getMessage()+Arrays.toString(ex.getStackTrace()));
        ModelAndView modelAndView = new ModelAndView("exceptions/duplicateEntity");
        modelAndView.addObject("exception", ex);
        modelAndView.addObject("url", request.getRequestURL());
        return modelAndView;
    }

//    @ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Incorrect operation type and cargo status")
    @ExceptionHandler(InvalidStateException.class)
    public ModelAndView handleInvalidStateException(HttpServletRequest request, Exception ex){
        LOG.error(ex.getMessage()+Arrays.toString(ex.getStackTrace()));
        ModelAndView modelAndView = new ModelAndView("exceptions/invalidStateException");
        modelAndView.addObject("exception", ex);
        modelAndView.addObject("url", request.getRequestURL());
        return modelAndView;

    }

    @ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Not Found")
    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView handleNotFound(HttpServletRequest request, Exception ex){
        LOG.error(ex.getMessage()+ Arrays.toString(ex.getStackTrace()));
        ModelAndView modelAndView = new ModelAndView("exceptions/404");
        modelAndView.addObject("exception", ex);
        modelAndView.addObject("url", request.getRequestURL());
        return modelAndView;
    }

    @ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Not Found")
    @ExceptionHandler(Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest request, Exception ex){
        LOG.error(ex.getMessage()+ Arrays.toString(ex.getStackTrace()));
        ModelAndView modelAndView = new ModelAndView("exceptions/generic");
        modelAndView.addObject("exception", ex);
        modelAndView.addObject("url", request.getRequestURL());
        return modelAndView;
    }







}
