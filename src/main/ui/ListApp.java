package ui;

import model.Action;
import model.ActionLog;
import model.NoteList;
import model.Workroom;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
Represents the main GUI where user can browse and select lists in the workroom.
 */
public class ListApp implements ActionListener {

    private static final String JSON_STORE = "./data/workroom.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private Workroom workroom;

    private JFrame frame;
    private JPanel panel;
    private JButton addListButton;
    private JButton selectListButton;
    private JButton saveDataButton;
    private JButton loadDataButton;
    private JButton quitButton;
    private JLabel print;
    private JLabel icon;

    private final int elementWidth = 300;
    private final int elementHeight = 25;

    // EFFECTS: starts application by loading workroom and directing to the browse calendar menu
    public CalendarsApp() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        workroom = new Workroom("Workroom");

        frame = new JFrame("Calendar App");
        frame.setSize(1000,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.icon = new JLabel(new ImageIcon("./data/calendar_icon.png"));
        this.icon.setBounds(500,100,elementWidth,300);
        panel = new JPanel();
        placeComponents(panel);
        print = new JLabel("Select an action");
        print.setBounds(400,30, elementWidth, elementHeight);
        panel.add(print);
        panel.add(icon);
        frame.add(panel);
        frame.setVisible(true);
    }

    // EFFECTS: places required buttons onto given JPanel.
    private void placeComponents(JPanel panel) {
        panel.setLayout(null);
        addCalendarButton = new JButton("Add Calendar");
        addCalendarButton.setBounds(10,20, elementWidth, elementHeight);
        addCalendarButton.addActionListener(this);
        panel.add(addCalendarButton);
        selectCalendarButton = new JButton("Select Calendar");
        selectCalendarButton.setBounds(10,50, elementWidth, elementHeight);
        selectCalendarButton.addActionListener(this);
        panel.add(selectCalendarButton);
        saveDataButton = new JButton("Save Data");
        saveDataButton.setBounds(10,80, elementWidth, elementHeight);
        saveDataButton.addActionListener(this);
        panel.add(saveDataButton);
        loadDataButton = new JButton("Load Data");
        loadDataButton.setBounds(10,110, elementWidth, elementHeight);
        loadDataButton.addActionListener(this);
        panel.add(loadDataButton);
        quitButton = new JButton("Quit application");
        quitButton.setBounds(10,140, elementWidth, elementHeight);
        quitButton.addActionListener(this);
        panel.add(quitButton);
    }

    /*
    Represents a JFrame where user chooses a calendar from the list of calendars in workroom.
     */
    private class CalendarSearcher implements ActionListener {
        JFrame searcherFrame;
        JPanel searcherPanel;
        JLabel typeHereLabel;
        JList displayCalendarsList;
        JTextField searchBox;
        JButton submitButton;

        // EFFECTS: creates new JFrame with all needed components.
        public CalendarSearcher() {
            searcherFrame = new JFrame("Select Calendar");
            searcherPanel = new JPanel();
            searcherFrame.setSize(1000,500);
            searcherFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.placeComponents(searcherPanel);
            searcherFrame.add(searcherPanel);
            searcherFrame.setVisible(true);
        }

        // EFFECTS: places required buttons onto given JPanel.
        private void placeComponents(JPanel panel) {
            List<String> eventsIndexed = new ArrayList<>();
            int index = 1;
            for (Calendar c : workroom.getCalendars()) {
                eventsIndexed.add(index + ") " + c.getTitle());
                index++;
            }
            this.displayCalendarsList = new JList(eventsIndexed.toArray());
            displayCalendarsList.setBounds(10,30, elementWidth, 400);
            panel.add(displayCalendarsList);
            typeHereLabel = new JLabel("Enter calendar index here:");
            typeHereLabel.setBounds(100, 30, elementWidth, elementHeight);
            panel.add(typeHereLabel);
            searchBox = new JTextField(3);
            searchBox.setBounds(100, 60, elementWidth, elementHeight);
            panel.add(searchBox);
            submitButton = new JButton("Enter");
            submitButton.setBounds(100, 90, elementWidth, elementHeight);
            submitButton.addActionListener(this);
            panel.add(submitButton);
        }

        // EFFECTS: defines actions for when a button is pressed.
        @Override
        public void actionPerformed(ActionEvent e) {
            int chosenIndex = -1;
            try {
                if (workroom.numCalendars() == 0) {
                    throw new NullPointerException();
                }
                chosenIndex = Integer.parseInt(searchBox.getText()) - 1;
                if (chosenIndex < 0 || chosenIndex >= workroom.numCalendars()) {
                    throw new IndexOutOfBoundsException();
                }
            } catch (NumberFormatException ex) {
                searcherFrame.setVisible(false);
                print.setText("Invalid input.");
            } catch (IndexOutOfBoundsException ex) {
                searcherFrame.setVisible(false);
                print.setText("Invalid input.");
            } catch (NullPointerException ex) {
                searcherFrame.setVisible(false);
                print.setText("No Calendars.");
            }
            Calendar chosenCalendar = workroom.getCalendars().get(chosenIndex);
            print.setText("Select an action.");
            searcherFrame.setVisible(false);
            new CalendarEditor(workroom, chosenCalendar);
        }
    }

    // EFFECTS: defines actions for when a button is pressed.
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addCalendarButton) {
            workroom.addCalendar(new Calendar());
            print.setText("Calendar has been added.");
        } else if (e.getSource() == loadDataButton) {
            loadData();
        } else if (e.getSource() == saveDataButton) {
            saveData();
        } else if (e.getSource() == selectCalendarButton) {
            new CalendarSearcher();
        } else if (e.getSource() == quitButton) {
            for (Action a : ActionLog.getInstance()) {
                System.out.println(a.toString());
                System.out.println();
            }
            System.exit(0);
        }
    }

    private void saveData() {
        try {
            jsonWriter.open();
            jsonWriter.write(workroom);
            jsonWriter.close();
            print.setText("Saved " + workroom.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException ex) {
            print.setText("Unable to write to file: " + JSON_STORE);
        }
    }

    private void loadData() {
        try {
            workroom = jsonReader.read();
            print.setText("Loaded " + workroom.getName() + " from " + JSON_STORE);
        } catch (IOException ex) {
            print.setText("Unable to read from file: " + JSON_STORE);
        }
    }
}
