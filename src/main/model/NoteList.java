package model;

import java.util.ArrayList;
import java.time.LocalDateTime;

import persistence.Writable;

public class NoteList implements Writable {
    
    private String name;
    private ArrayList<Note> notesList;
    private LocalDateTime lastUpdated;

    // EFFECTS: creates new list with given name and no notes
    public NoteList(String newName) {
        this.name = newName;
        this.notesList = new ArrayList<Note>();
        ActionLog.getInstance().logAction(new Action("Created new list \"" + newName + "\"."));
    }

    public void addNote(Note n) {
        this.notesList.add(n);
        ActionLog.getInstance().logAction(new Action("Added new note \"" + n.getText() + "\" in note list \"" + this.name + "\"."));
    }

    public void deleteNote(Note n) {
        this.notesList.remove(n);
        ActionLog.getInstance().logAction(new Action("Deleted note \"" + n.getText() + "\" in note list \"" + this.name + "\"."));
    }

    public void clear() {
        this.notesList.clear();
        ActionLog.getInstance().logAction(new Action("Cleared all notes in note list \"" + this.name + "\"."));
    }

    public void updateTime() {
        this.lastUpdated = LocalDateTime.now();
        ActionLog.getInstance().logAction(new Action("Updated date for note list \"" + this.name + "\"."));
    }

    public void setName(String nameInput) {
        String temp = this.name;
        this.name = nameInput;
        ActionLog.getInstance().logAction(new Action("Changed name of note list \"" + temp + "\" to \"" + this.name + "\"."));
    }

    public String getName() {
        return this.name;
    }

    public LocalDateTime getLastUpdatedTime() {
        return this.lastUpdated;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("lastUpdatedYear", lastUpdated.getYear());
        json.put("lastUpdatedMonth", lastUpdated.getMonth());
        json.put("lastUpdatedDay", lastUpdated.getDayOfMonth());
        json.put("lastUpdatedHour", lastUpdated.getHour());
        json.put("lastUpdatedMinute", lastUpdated.getMinute());
        json.put("notes", notesToJson());
        return json;
    }

    private JSONArray notesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Note n : notesList) {
            jsonArray.put(n.toJson());
        }
        return jsonArray;
    }

}