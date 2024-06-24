import java.time.LocalDateTime;

public class Note {

    private String text;
    private LocalDateTime scheduledTime;
    private boolean flagged;

    public Note(String textInput) {
        setText(textInput);
        this.scheduledTime = null;
        this.flagged = false;
    }

    public void setText(String textInput) {
        this.text = textInput;
    }

    public String getText() {
        return this.text;
    }

    public void setTime(LocalDateTime timeInput) {
        this.scheduledTime = timeInput;
    }

    public LocalDateTime getTime() {
        return this.scheduledTime;
    }

    public void changeFlag() {
        this.flagged = !this.flagged;
    }

}