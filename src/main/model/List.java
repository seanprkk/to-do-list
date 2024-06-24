import java.util.ArrayList;
import java.time.LocalDateTime;

public class List {
    
    private String name;
    private ArrayList<Note> notesList;
    private LocalDateTime lastUpdated;

    // EFFECTS: creates new list with given name and no notes
    public List(String newName) {
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
        //
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

}