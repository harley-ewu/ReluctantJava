package application.mediator.common;

public interface IHandler<T> {
    T handle(Request request);
}
