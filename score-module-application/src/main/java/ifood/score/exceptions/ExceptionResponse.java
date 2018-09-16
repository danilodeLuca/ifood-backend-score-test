package ifood.score.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

@Data
public class ExceptionResponse {

    private static final long serialVersionUID = 1L;

    private HttpStatus httpStatus;
    private String message;

    public ExceptionResponse(HttpStatus code) {
        this.httpStatus = code;
    }

    public ExceptionResponse(HttpStatus code, String message) {
        this.httpStatus = code;
        this.message = message;
    }

}
