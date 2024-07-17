package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

public class Workroom implements Writable {

    private String name;
    private List<NoteList> lists;

    public Workroom(String name) {
        this.name = name;
        lists = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addList(NoteList l) {
        lists.add(l);
        ActionLog.getInstance().logAction(new Action("Added new list to workroom. Total " + numLists() + " lists."));
    }

    public void removeList(NoteList l) {
        lists.remove(l);
        ActionLog.getInstance().logAction(new Action("Removed list \"" + l.getName() + "\" from workroom. Total " + numLists() + " lists."));
    }

    public List<NoteList> getLists() { return lists; }

    public int numLists() { return lists.size() }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("lists", listsToJson());
        return json;
    }

    private JSONArray listsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (NoteList l : lists) {
            jsonArray.put(l.toJson());
        }
        return jsonArray;
    }

}