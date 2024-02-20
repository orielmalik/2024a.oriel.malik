package demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFound404 extends RuntimeException {
	private static final long serialVersionUID = -138561552243118779L;

	public NotFound404() {
	}

	public NotFound404(String message) {
		super(message);
	}

	public NotFound404(Throwable cause) {
		super(cause);
	}

	public NotFound404(String message, Throwable cause) {
		super(message, cause);
	}
}
