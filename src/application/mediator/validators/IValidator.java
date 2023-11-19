package application.mediator.validators;

import application.mediator.requests.Request;

public interface IValidator {
    void validate(Request request);
}
