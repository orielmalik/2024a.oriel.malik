package demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class UnauthorizedAccess401 extends RuntimeException {
	private static final long serialVersionUID = -138561552243118779L;

	public UnauthorizedAccess401() {
	}

	public UnauthorizedAccess401(String message) {
		super(message);
	}

	public UnauthorizedAccess401(Throwable cause) {
		super(cause);
	}

	public UnauthorizedAccess401(String message, Throwable cause) {
		super(message, cause);
	}
}
