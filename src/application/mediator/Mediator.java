package application.mediator;

import application.mediator.handlers.IHandler;
import application.mediator.requests.Request;
import application.mediator.validators.IValidator;

import java.util.HashMap;
import java.util.Objects;

public class Mediator {
    private static final HashMap<String, IValidator> validators = new HashMap<>();
    private static final HashMap<String, IHandler> handlers = new HashMap<>();

    public static <T> T send(Request request){
        IValidator validator = validators.get(request.getRequestName());
        IHandler handler = handlers.get(request.getRequestName());

        Objects.requireNonNull(validator, "No registered validator for request: " + request.getRequestName() + ".");
        Objects.requireNonNull(handler, "No registered handler for request: " + request.getRequestName() + ".");

        validator.validate(request);
        handler.handle(request);

        return null;
    }

    public static void registerService(IValidator validator, IHandler handler, Request request){
        validators.put(request.getRequestName(), validator);
        handlers.put(request.getRequestName(), handler);
    }

}
