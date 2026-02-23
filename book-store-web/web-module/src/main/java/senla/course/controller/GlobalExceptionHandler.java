package senla.course.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import senla.course.exception.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleBookNotFound(BookNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleOrderNotFound(OrderNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(RequestNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleRequestNotFound(RequestNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(ListBookEmptyException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleBookEmpty(ListBookEmptyException e) {
        return e.getMessage();
    }

    @ExceptionHandler(ListOrderEmptyException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleOrderEmpty(ListOrderEmptyException e) {
        return e.getMessage();
    }

    @ExceptionHandler(ListRequestEmptyException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleRequestEmpty(ListRequestEmptyException e) {
        return e.getMessage();
    }

    @ExceptionHandler(IncorrectIdException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleIncorrectId (IncorrectIdException e) {
        return e.getMessage();
    }

    @ExceptionHandler(ActiveRequestOnBookException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleActiveRequestOnBook (ActiveRequestOnBookException e) {
        return e.getMessage();
    }
}
