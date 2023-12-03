package application.mediator.common;

import application.mediator.common.IHandler;
import application.mediator.common.Request;
import application.mediator.common.IValidator;

import java.util.HashMap;
import java.util.Objects;

public class Mediator {
    private final HashMap<String, IValidator> validators = new HashMap<>();
    private final HashMap<String, IHandler> handlers = new HashMap<>();

    protected Mediator(){
    }
    public <T> T send(Request request){
        IValidator validator = this.validators.get(request.getRequestName());
        IHandler handler = this.handlers.get(request.getRequestName());

        Objects.requireNonNull(validator, "No registered validator for request: " + request.getRequestName() + ".");
        Objects.requireNonNull(handler, "No registered handler for request: " + request.getRequestName() + ".");

        validator.validate(request);
        handler.handle(request);

        return null;
    }

    public void registerService(IValidator validator, IHandler handler, Request request){
        this.validators.put(request.getRequestName(), validator);
        this.handlers.put(request.getRequestName(), handler);
    }

}
