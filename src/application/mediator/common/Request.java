package application.mediator.common;

public abstract class Request {
    private final String requestName;

    public Request(String name){
        this.requestName = name;
    }

    public String getRequestName(){
        return this.requestName;
    }
}
