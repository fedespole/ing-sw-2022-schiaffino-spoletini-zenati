package it.polimi.ingsw.common.events.fromServerEvents;

import it.polimi.ingsw.common.events.GameEvent;
import it.polimi.ingsw.view.ViewData;

public class UpdatedDataEvent extends GameEvent {
    private final ViewData viewData;

    public UpdatedDataEvent(Object source,ViewData viewData) {
        super(source);
        this.viewData= viewData;
    }

    public ViewData getViewData() {
        return viewData;
    }
}
