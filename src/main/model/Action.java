package model;

import java.util.Calendar;
import java.util.Date;

/**
 * Represents an action on the Note List App.
 */
public class Action {
    private static final int HASH_CONSTANT = 13;
    private Date dateLogged;
    private String description;

    /**
     * Creates an action with the given description
     * and the current date/time stamp.
     * @param description  a description of the action
     */
    public Action(String description) {
        dateLogged = Calendar.getInstance().getTime();
        this.description = description;
    }

    /**
     * Gets the date of this action (includes time).
     * @return  the date of the action
     */
    public Date getDate() {
        return dateLogged;
    }

    /**
     * Gets the description of this action.
     * @return  the description of the action
     */
    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (other.getClass() != this.getClass()) {
            return false;
        }

        Action otherAction = (Action) other;

        return (this.dateLogged.equals(otherAction.dateLogged)
                && this.description.equals(otherAction.description));
    }

    @Override
    public int hashCode() {
        return (HASH_CONSTANT * dateLogged.hashCode() + description.hashCode());
    }

    @Override
    public String toString() {
        return getDate().toString() + "\n" + getDescription();
    }
}
