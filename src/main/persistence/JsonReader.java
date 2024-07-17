package persistence;

import model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {

    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Workroom read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWorkRoom(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private Workroom parseWorkRoom(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Workroom wr = new Workroom(name);
        addNoteLists(wr, jsonObject);
        return wr;
    }

    // MODIFIES: wr
    // EFFECTS: parses lists from JSON object and adds them to workroom
    private void addNoteLists(Workroom wr, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("lists");
        for (Object json : jsonArray) {
            JSONObject nextList = (JSONObject) json;
            addNoteList(wr, nextList);
        }
    }

    // MODIFIES: wr
    // EFFECTS: parses list from JSON object and adds it to workroom
    private void addNoteList(Workroom wr, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        ArrayList<Note> notes = addNotes(jsonObject);
        NoteList noteList = new NoteList(name);
        for (Note n : notes) {
            noteList.addNote(n);
        }
        wr.addList(noteList);
    }

    // MODIFIES: wr
    // EFFECTS: returns list of notes parsed from JSON object
    private ArrayList<Note> addNotes(JSONObject listObject) {
        JSONArray jsonArray = listObject.getJSONArray("notes");
        ArrayList<Note> noteList = new ArrayList<Note>();
        for (Object json : jsonArray) {
            JSONObject nextNote = (JSONObject) json;
            noteList.add(produceNote(nextNote));
        }
        return noteList;
    }

    // EFFECTS: returns note parsed from JSON Object
    private Note produceNote(JSONObject noteObject) {
        String text = noteObject.getString("text");
        boolean flagged = noteObject.getBoolean("flagged");
        boolean late = noteObject.getBoolean("late");

        Note thisNote = new Note(text);
        if (flagged) thisNote.changeFlag();
        if (late) thisNote.markLate();

        if (noteObject.getBoolean("hasDate")) {
            int year = noteObject.getInt("dateYear");
            int month = noteObject.getInt("dateMonth");
            int day = noteObject.getInt("dateDay");
            LocalDate date = LocalDate.of(year,month,day);
            thisNote.setDate(date);
        }
    
        return thisNote;
    }

}
