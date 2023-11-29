package application.mediator.common;

public class MediatorSingletonHandler {
    private static Mediator mediator;

    public MediatorSingletonHandler(){
    }

    public Mediator getInstance(){
        return mediator;
    }

    public void initMediator() {
        if(mediator == null)
        {
            mediator = new Mediator();
        }
    }
}
