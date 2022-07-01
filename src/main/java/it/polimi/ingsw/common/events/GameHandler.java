package it.polimi.ingsw.common.events;

import it.polimi.ingsw.common.events.fromServerEvents.NotifyExceptionEvent;

import javax.swing.event.EventListenerList;
import java.util.EventListener;
import java.lang.reflect.*;

/**
 * This class is a singleton that handles the raise of events
 */
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

    /**
     * This method handles the raise of an event using reflection. It looks through the first parameter of the "update" methods of the listener,
     * and verifies that the class of the event called  matches with one of them. If it matches the method is called.
     * @param event event raised.
     */
    public static synchronized void calls(GameEvent event) {
        for (EventListener listener : listeners.getListeners(EventListener.class)) {
            for (Method method : listener.getClass().getMethods()) {//we have used reflection to call the right update method
                try {
                    if (method.getName().equals("update") && method.getParameterTypes()[0] == event.getClass()) {//the event is always the first and only parameter
                           method.invoke(listener, event);
                           break;
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
