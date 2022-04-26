package it.polimi.ingsw.common.events;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.view.View;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventException;
import javax.swing.event.EventListenerList;
import java.util.EventListener;
import java.util.EventObject;
import java.lang.reflect.*;

public  class GameHandler {
    private static GameHandler instance;
    private static final EventListenerList listeners = new EventListenerList();


    public static GameHandler getInstance() {
        if (instance == null)
            instance = new GameHandler();
        return instance;
    }

    public static void addEventListener(EventListener listener) {
        listeners.add(EventListener.class, listener);
    }


    public static void calls(GameEvent event) {
        for (EventListener listener : listeners.getListeners(EventListener.class)) {
            for (Method method : listener.getClass().getMethods()) {//we have used reflection to call the right update method
                try {
                    if (method.getName().equals("update") && method.getParameterTypes()[0] == event.getClass()) {//the event is always the first and only parameter
                       try {
                           method.invoke(listener, event);
                           break;
                       // TODO completare dopo la GameView
                       // Handles exceptions raised by controller: notifies client that will repeat action
                       }catch(RuntimeException e){
                           GameHandler.calls(new NotifyExceptionEvent(instance, e));
                       }
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {}
            }
        }
    }


}
