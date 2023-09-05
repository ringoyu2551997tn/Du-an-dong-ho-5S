package com.datn.dongho5s.Exception;

import com.datn.dongho5s.Exception.CustomException.BadRequestException;
import com.datn.dongho5s.Exception.CustomException.InternalServerException;
import com.datn.dongho5s.Exception.CustomException.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.Locale;

@RestControllerAdvice
public class CustomExceptionHandler {

    @Qualifier("messageSource")
    @Autowired
    private MessageSource msgSource;

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<?> handlerInternalServerException(InternalServerException ex, WebRequest request) {
        //Log error
        ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handlerNotFoundException(NotFoundException ex, WebRequest request) {
        //Log error
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handlerBadRequestException(BadRequestException ex, WebRequest request) {
        //Log error
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    //    Xử lý các exception chưa được khai báo
    @ExceptionHandler(DanhMucNotFoundException.class)
    public ResponseEntity<?> handlerException(DanhMucNotFoundException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<?> processValidateError(MethodArgumentNotValidException ex) {
//        BindingResult result = ex.getBindingResult();
//        List<FieldError> fieldErrors = result.getFieldErrors();
//        String message = "";
//        for (FieldError error : fieldErrors) {
//            String temp = processFieldError(error);
//            message += temp + " ; ";
//        }
//        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST, message);
//        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
//    }

//    private String processFieldError(FieldError error) {
//        String msg = "";
//        if (error != null) {
//            Locale currentLocale = LocaleContextHolder.getLocale();
//            try {
//                msg = msgSource.getMessage(error.getDefaultMessage(), null, currentLocale);
//            } catch (DanhMucNotFoundException e) {
//                msg = error.getDefaultMessage();
//            }
//        }
//        return msg;
//    }
}
