package demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BadRequest400 extends RuntimeException {
	private static final long serialVersionUID = -138561552243118779L;

	public BadRequest400() {
	}

	public BadRequest400(String message) {
		super(message);
	}

	public BadRequest400(Throwable cause) {
		super(cause);
	}

	public BadRequest400(String message, Throwable cause) {
		super(message, cause);
	}
}
