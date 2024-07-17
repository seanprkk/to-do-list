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
        this.scheduledTime = null;
        this.flagged = false;
        this.late = false;
    }

    public void setText(String textInput) {
        this.text = textInput;
    }

    public String getText() {
        return this.text;
    }

    public void setDate(LocalDate dateInput) {
        this.scheduledDate = timeInput;
    }

    public LocalDate getDate() {
        return this.scheduledDate;
    }

    public void deleteDate() {
        this.scheduledDate = null;
    }

    public void changeFlag() {
        this.flagged = !this.flagged;
    }

    public void markLate() {
        this.late = true;
    }

    public void unmarkLate() {
        this.late = false;
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