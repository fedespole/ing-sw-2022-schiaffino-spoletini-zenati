package it.polimi.ingsw.common.events;

import it.polimi.ingsw.common.events.fromServerEvents.NotifyExceptionEvent;

import javax.swing.event.EventListenerList;
import java.util.EventListener;
import java.lang.reflect.*;

public class GameHandler {
    private static GameHandler instance;
    private static final EventListenerList listeners = new EventListenerList();

    // Singleton
    public static synchronized GameHandler getInstance() {
        if (instance == null)
            instance = new GameHandler();
        return instance;
    }

    public static synchronized void addEventListener(EventListener listener) {
        listeners.add(EventListener.class, listener);
    }

    public static synchronized void calls(GameEvent event) {
        for (EventListener listener : listeners.getListeners(EventListener.class)) {
            for (Method method : listener.getClass().getMethods()) {//we have used reflection to call the right update method
                try {
                    if (method.getName().equals("update") && method.getParameterTypes()[0] == event.getClass()) {//the event is always the first and only parameter
                       try {
                           method.invoke(listener, event);
                           break;
                       // Handles exceptions raised by controller: notifies client that will repeat action
                       }catch(RuntimeException e){
                           GameHandler.calls(new NotifyExceptionEvent(getInstance(), e));
                       }
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
