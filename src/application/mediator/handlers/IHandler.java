package application.mediator.handlers;

import application.mediator.requests.Request;

public interface IHandler {
    <T> T handle(Request request);
}
