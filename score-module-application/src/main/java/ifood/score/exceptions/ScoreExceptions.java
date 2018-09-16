package ifood.score.exceptions;

import org.springframework.http.HttpStatus;

public abstract class ScoreExceptions extends RuntimeException {

    private HttpStatus httpStatus;

    public ScoreExceptions(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

}
