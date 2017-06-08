package manuscript.module.user.management.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import manuscript.module.user.management.bean.BasicResponse;
import manuscript.module.user.management.exception.NameAlreadyReservedException;
import manuscript.module.user.management.exception.PasswordValidationException;
import manuscript.module.user.management.exception.SaveUserException;
import manuscript.module.user.management.exception.UserIsNotAuthenticatedException;
import manuscript.module.user.management.exception.UserNotFoundException;

@ControllerAdvice
public class UserRegistrationExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserRegistrationExceptionHandler.class);

	@ExceptionHandler(NameAlreadyReservedException.class)
	@ResponseBody
	private BasicResponse nameAlreadyReservedException(NameAlreadyReservedException exception) {
		BasicResponse response = new BasicResponse();
		response.setExceptionMessage(exception.getErrorMessage());
		return response;
	}

	@ExceptionHandler(PasswordValidationException.class)
	@ResponseBody
	private BasicResponse passwordValidationException(PasswordValidationException exception) {
		BasicResponse response = new BasicResponse();
		response.setExceptionMessage(exception.getMessage());
		return response;
	}

	@ExceptionHandler(SaveUserException.class)
	@ResponseBody
	private BasicResponse saveUserException(SaveUserException exception) {
		BasicResponse response = new BasicResponse();
		response.setExceptionMessage(exception.getMessage());
		return response;
	}

	@ExceptionHandler(UserNotFoundException.class)
	@ResponseBody
	private BasicResponse userNotFoundException(UserNotFoundException exception) {
		BasicResponse response = new BasicResponse();
		response.setExceptionMessage(exception.getMessage());
		return response;
	}

	@ExceptionHandler(UserIsNotAuthenticatedException.class)
	@ResponseBody
	private BasicResponse UserIsNotAuthenticatedException(UserIsNotAuthenticatedException exception) {
		BasicResponse response = new BasicResponse();
		response.setExceptionMessage(exception.getMessage());
		return response;
	}

	// @ExceptionHandler(NameAlreadyReservedException.class)
	// @ResponseBody
	// private BasicResponse userNameAlreadyUsedExceptionHandler(Exception exception) {
	// BasicResponse response = new BasicResponse();
	//
	// response.setExceptionMessage(((NameAlreadyReservedException) exception).getErrorMessage());
	// return response;
	// }
	//
	// @ExceptionHandler(PasswordValidationException.class)
	// @ResponseBody
	// private BasicResponse passwordParityCheckFailedExceptionHandler(Exception exception) {
	// BasicResponse response = new BasicResponse();
	//
	// response.setExceptionMessage(((PasswordValidationException) exception).getErrorMessage());
	// return response;
	// }
	//
	// @ExceptionHandler(GivenOldPasswordIsIncorrectException.class)
	// @ResponseBody
	// private BasicResponse giveAllPasswordIsIncorrectException(GivenOldPasswordIsIncorrectException exception) {
	//// BasicResponse response = new BasicResponse();
	////
	//// response.setExceptionMessage(exception.getErrorMessage());
	// return getExceptionMessage(exception);
	// }
	//
	// private BasicResponse getExceptionMessage(Exception exception){
	// BasicResponse response = new BasicResponse();
	// response.setExceptionMessage(exception.getMessage());
	// return response;
	// }
}
