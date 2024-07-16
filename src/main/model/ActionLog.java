package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class ActionLog implements Iterable<Action> {
    /** the only ActionLog in the system (Singleton Design Pattern) */
    private static ActionLog theLog;
    private Collection<Action> actions;

    /**
     * Prevent external construction.
     * (Singleton Design Pattern).
     */
    private ActionLog() {
        actions = new ArrayList<Action>();
    }

    /**
     * Gets instance of ActionLog - creates it
     * if it doesn't already exist.
     * (Singleton Design Pattern)
     * @return  instance of ActionLog
     */
    public static ActionLog getInstance() {
        if (theLog == null) {
            theLog = new ActionLog();
        }
        return theLog;
    }

    /**
     * Adds an action to the action log.
     * @param e the action to be added
     */
    public void logAction(Action e) {
        actions.add(e);
    }

    /**
     * Clears the action log and logs the action.
     */
    public void clear() {
        actions.clear();
        logAction(new Action("Action log cleared."));
    }

    @Override
    public Iterator<Action> iterator() {
        return actions.iterator();
    }
}
