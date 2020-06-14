package ml.socshared.service.textanalyze.exception.impl;

import ml.socshared.service.textanalyze.exception.AbstractRestHandleableException;
import ml.socshared.template.exception.SocsharedErrors;
import org.springframework.http.HttpStatus;

public class HttpUnavailableRequestException extends AbstractRestHandleableException {
    public HttpUnavailableRequestException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public HttpUnavailableRequestException(SocsharedErrors errorCode, HttpStatus httpStatus) {
        super(httpStatus);
    }
}
