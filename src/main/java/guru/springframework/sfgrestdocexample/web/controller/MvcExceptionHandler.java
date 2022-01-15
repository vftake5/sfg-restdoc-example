package guru.springframework.sfgrestdocexample.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class MvcExceptionHandler
{

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<List> validationErrorHandler(MethodArgumentNotValidException e)
	{
		List<ObjectError> errorList = e.getAllErrors();
		List<String> error = new ArrayList<>(errorList.size());

		e.getAllErrors().forEach(argNotValid -> {
			String err = argNotValid.toString() + " | " + argNotValid.getCode() + " : " + argNotValid.getDefaultMessage();
			System.out.println("ERROR: " + err);
			error.add(err);
		});

		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(BindException.class)
	public ResponseEntity<List> handlingBindException(BindException ex)
	{
		return new ResponseEntity<>(ex.getAllErrors(), HttpStatus.BAD_REQUEST);
	}
}
