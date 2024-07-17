package model;

import java.time.LocalDate;

import persistence.Writable;

public class Note implements Writable {

    private String text;
    private LocalDate scheduledDate;
    private boolean flagged;
    private boolean late;

    public Note(String textInput) {
        setText(textInput);
        this.scheduledDate = null;
        this.flagged = false;
        this.late = false;
    }

    public void setText(String textInput) {
        String prevText = this.text;
        this.text = textInput;
        ActionLog.getInstance().logAction(new Action("Changed note text \"" + prevText + "\" to \"" + this.text + "\"."));
    }

    public String getText() {
        return this.text;
    }

    public void setDate(LocalDate dateInput) {
        this.scheduledDate = timeInput;
        ActionLog.getInstance().logAction(new Action("Changed date of note \"" + this.text + "\" to "+ this.scheduledDate.toString() +"."));
    }

    public LocalDate getDate() {
        return this.scheduledDate;
    }

    public void deleteDate() {
        this.scheduledDate = null;
        ActionLog.getInstance().logAction(new Action("Deleted date of note \"" + this.text + "\"."));
    }

    public void changeFlag() {
        this.flagged = !this.flagged;
    }

    public void markLate() {
        this.late = true;
        ActionLog.getInstance().logAction(new Action("Marked note \"" + this.text + "\" to late."));
    }

    public void unmarkLate() {
        this.late = false;
        ActionLog.getInstance().logAction(new Action("Unmarked note \"" + this.text + "\" to late."));
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("text", text);
        json.put("flagged", flagged);
        json.put("late", late);
        if (getDate() != null) {
            json.put("hasDate", true);
            json.put("dateYear", getDate().getYear());
            json.put("dateMonth", getDate().getMonth());
            json.put("dateDay", getDate().getDayOfMonth());
        } else {
            json.put("hasDate", false);
        }
        return json;
    }

}