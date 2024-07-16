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
    }

    public void addNote(Note n) {
        this.notesList.add(n);
    }

    public void deleteNote(Note n) {
        this.notesList.remove(n);
    }

    public void clear() {
        this.notesList.clear();
    }

    public void updateTime() {
        this.lastUpdated = LocalDateTime.now();
    }

    public void setName(String nameInput) {
        this.name = nameInput;
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