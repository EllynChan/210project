package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;

import model.exception.NotInBoundsException;
import persistence.JsonReader;
import persistence.JsonWriter;

import model.TodoList;

public class GraphicalApp extends JFrame implements ActionListener {
    private JLabel label;
    private JTextField field;
    private JPanel panel;
    private JPanel startPanel;
    private JPanel mainPanel;
    private ImageIcon image;
    private ImageIcon imageTwo;
    private ImageIcon imageThree;
    private ImageIcon imageFour;
    private ImageIcon imageFive;
    private Box boxTwo;
    private JButton imageAsButton;
    private JLabel imageAsLabel;
    private CardLayout cardLayout;
    private TodoList todoList;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/todoList.json";

    //EFFECTS: constructs a GraphicalApp
    public GraphicalApp() {
        super("The App");
        init();

        panel.setLayout(cardLayout);

        panel.add(startPanel, "1");
        panel.add(mainPanel, "2");
        cardLayout.show(panel, "1");

        startDialog();

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setPreferredSize(new Dimension(620, 400));
        //the distance of the button to border
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(50, 13, 13, 13));
        setLayout(new FlowLayout());
        add(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);
        addWindowListener(new SaveOptions());
    }

    //EFFECTS: initializes some fields
    public void init() {
        todoList = new TodoList();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        panel = new JPanel();
        startPanel = new JPanel();
        mainPanel = new JPanel();
        cardLayout = new CardLayout();
    }

    //This is here for the sake of interface and does nothing
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("add")) {
            label.setText(field.getText());
        }
    }

    //EFFECTS: creates a dialog upon start. Loads todoList if yes is selected, otherwise does nothing.
    // will proceed to mainDialog on either choice
    public void startDialog() {
        label = new JLabel("load todo-list?");
        JButton yesBtn = new JButton();
        JButton noBtn = new JButton();

        yesBtn.setText("yes");
        noBtn.setText("no");
        startPanel.setLayout(new BoxLayout(startPanel, BoxLayout.Y_AXIS));
        startPanel.add(label);
        startPanel.add(yesBtn);
        startPanel.add(noBtn);

        actionListenerYesButton(yesBtn);
        actionListenerNoButton(noBtn);

        loadImages();
        imageAsButton = new JButton(image);
        imageAsButton.setOpaque(false);
        imageAsButton.setContentAreaFilled(false);
        imageAsButton.setBorderPainted(false);
        startPanel.add(imageAsButton);
        actionListenerImageButton(imageAsButton);
        startPanel.add(new JLabel("↑ click icon"));
    }

    //EFFECTS: shows the main app panel
    private void mainDialog() {
        label = new JLabel(viewList());
        JButton addBtn = new JButton("Add Task");
        JButton markBtn = new JButton("Mark Complete");
        JButton removeBtn = new JButton("Remove Task");
        JButton saveBtn = new JButton("Save");

        Box box = Box.createVerticalBox();
        mainPanel.add(label);
        mainPanel.add(box);
        box.add(addBtn);
        box.add(markBtn);
        box.add(removeBtn);
        box.add(saveBtn);
        loadImages();
        initializeFields();

        actionListenerAddButton(addBtn);
        actionListenerMarkButton(markBtn);
        actionListenerRemoveButton(removeBtn);
        actionListenerSaveButton(saveBtn);
    }

    //EFFECT: loads the todoList when this button is pressed, and enters mainDialog.
    private void actionListenerYesButton(JButton b) {
        b.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadTodoList();
                cardLayout.show(panel, "2");
                mainDialog();
            }
        });
    }

    //EFFECT: enters mainDialog when this button is pressed
    private void actionListenerNoButton(JButton b) {
        b.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardLayout.show(panel, "2");
                mainDialog();
            }
        });
    }

    //EFFECTS: changes the image for imageAsButton on startDialog when b is pressed.
    private void actionListenerImageButton(JButton b) {
        b.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (imageAsButton.getIcon().equals(image)) {
                    imageAsButton.setIcon(imageTwo);
                } else {
                    imageAsButton.setIcon(image);
                }
                playCoinSound();
            }
        });
    }


    //EFFECTS: saves current todoList to file when this button is pressed
    private void actionListenerSaveButton(JButton b) {
        b.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveTodoList();
                label.setText(viewList() + "<html><br>saved<html>");
            }
        });
    }

    //EFFECTS: adds an incomplete item to the end of the todoList when b is pressed
    private void actionListenerAddButton(JButton b) {
        b.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                todoList.addItem(field.getText());
                label.setText(viewList());
                if (0 < todoList.listSize() && todoList.listSize() <= 6) {
                    boxTwo.remove(imageAsLabel);
                    imageAsLabel = new JLabel(imageFour);
                    boxTwo.add(imageAsLabel);
                }
                if (todoList.listSize() > 6) {
                    boxTwo.remove(imageAsLabel);
                    imageAsLabel = new JLabel(imageFive);
                    boxTwo.add(imageAsLabel);
                }
            }
        });
    }

    //EFFECTS: marks an item from todoList as complete at the given index when b is pressed.
    // Does nothing if the input isn't valid
    private void actionListenerMarkButton(JButton b) {
        b.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    todoList.finishItem(Integer.parseInt(field.getText()));
                } catch (NotInBoundsException e) {
                    System.out.println("there is no task with that index");
                } catch (NumberFormatException e) {
                    System.out.println("input needs to be an integer");
                }
                label.setText(viewList());
            }
        });
    }

    //EFFECTS: removes an item from todoList at the given index when b is pressed.
    // Does nothing if the input isn't valid
    private void actionListenerRemoveButton(JButton b) {
        b.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    todoList.removeItem(Integer.parseInt(field.getText()));
                } catch (NotInBoundsException e) {
                    System.out.println("there is no task with that index");
                } catch (NumberFormatException e) {
                    System.out.println("input needs to be an integer");
                }
                label.setText(viewList());
                if (0 < todoList.listSize() && todoList.listSize() <= 6) {
                    boxTwo.remove(imageAsLabel);
                    imageAsLabel = new JLabel(imageFour);
                    boxTwo.add(imageAsLabel);
                }
                if (todoList.listSize() == 0) {
                    boxTwo.remove(imageAsLabel);
                    imageAsLabel = new JLabel(imageThree);
                    boxTwo.add(imageAsLabel);
                }
            }
        });
    }

    //EFFECTS: constructs a text field
    private void initializeFields() {
        field = new JTextField(10);

        boxTwo = Box.createVerticalBox();
        mainPanel.add(boxTwo);
        boxTwo.add(field);

        makeLabel();
        boxTwo.add(imageAsLabel);
    }

    //EFFECTS: decides which image to use for the label on mainDialog upon entering
    private void makeLabel() {
        imageAsLabel = new JLabel();
        if (todoList.listSize() == 0) {
            imageAsLabel.setIcon(imageThree);
        }

        if (0 < todoList.listSize() && todoList.listSize() <= 6) {
            imageAsLabel.setIcon(imageFour);
        }

        if (todoList.listSize() > 6) {
            imageAsLabel.setIcon(imageFive);
        }
    }

    //Effect: plays the Coin sound
    // From StackOverflow: https://stackoverflow.com/questions/6045384/playing-mp3-and-wav-in-java
    public void playCoinSound() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                    new File("/Users/elaineshi/IdeaProjects/project_f2h3b/data/coin.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }

    //EFFECTS: loads the images used for the icons
    private void loadImages() {
        String sep = System.getProperty("file.separator");
        image = new ImageIcon(System.getProperty("user.dir") + sep
                + "images" + sep + "tdl.png");
        imageTwo = new ImageIcon(System.getProperty("user.dir") + sep
                + "images" + sep + "tdl2.png");
        imageThree = new ImageIcon(System.getProperty("user.dir") + sep
                + "images" + sep + "0.png");
        imageFour = new ImageIcon(System.getProperty("user.dir") + sep
                + "images" + sep + "1.png");
        imageFive = new ImageIcon(System.getProperty("user.dir") + sep
                + "images" + sep + "2.png");
    }

    // MODIFIES: this
    // EFFECTS: loads todoList from file
    private void loadTodoList() {
        try {
            todoList = jsonReader.read();
            System.out.println("Loaded todoList from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: saves the todoList to file
    private void saveTodoList() {
        try {
            jsonWriter.open();
            jsonWriter.write(todoList);
            jsonWriter.close();
            System.out.println("Saved todoList to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    //EFFECTS: returns all items in todoList
    private String viewList() {
        return todoList.returnAllForGraphicalApp();
    }

    //EFFECTS: shows pop-up dialog upon close. Saves todoList if yes is selected, does nothing otherwise.
    //From StackOverflow https://stackoverflow.com/questions/15198549/popup-for-jframe-close-button
    private class SaveOptions extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            int option = JOptionPane.showOptionDialog(
                    GraphicalApp.this,
                    "Save todo-list?",
                    "Save Options", JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE, null, null,
                    null);
            if (option == JOptionPane.YES_OPTION) {
                saveTodoList();
            }
            System.exit(0);
        }
    }
}
