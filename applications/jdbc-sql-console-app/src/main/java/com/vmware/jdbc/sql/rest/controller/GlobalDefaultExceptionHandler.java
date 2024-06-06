package com.vmware.jdbc.sql.rest.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import nyla.solutions.core.util.Debugger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalDefaultExceptionHandler {
    @SneakyThrows
    @ExceptionHandler(Exception.class)
    public void exception(Exception e, HttpServletResponse response) {
        log.error("EXCEPTION: {}",e);

        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.getWriter().println(Debugger.stackTrace(e));
    }
}
