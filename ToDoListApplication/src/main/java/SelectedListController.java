import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

/*
 *  UCF COP3330 Fall 2021 Application Assignment 1 Solution
 *  Copyright 2021 Diego Figueroa
 */

public class SelectedListController {

    @FXML
    private TextField DescriptionInput;

    @FXML
    private Button deleteButton;

    @FXML
    private TextField dueDateInput;

    @FXML
    private Button saveListButton;

    @FXML
    private TextField ItemNameInput;

    @FXML
    private Button addOrEditButton;

    @FXML
    private Button clearListButton;

    @FXML
    private TextField completedInput;

    @FXML
    private TableView<Item> tableView;

    @FXML
    private TableColumn<Item, String> taskCol;

    @FXML
    private TableColumn<Item, String> descriptionCol;

    @FXML
    private TableColumn<Item, String> dateCol;

    @FXML
    private TableColumn<Item, String> completedCol;

    @FXML
    private Text toDoListName;

    @FXML
    private Button showAllButton;

    @FXML
    private Button showCompletedButton;

    @FXML
    private Text errorText;

    @FXML
    private TextField fileNameInput;

    @FXML
    private Button showIncompletedButton;


    @FXML
    private Button loadFileButton;

    private final ObservableList<Item> toDoList = FXCollections.observableArrayList();
    private final ObservableList<Item> completedList = FXCollections.observableArrayList();
    private final ObservableList<Item> incompleteList = FXCollections.observableArrayList();

    public void addToList(String name, String description, String date, String completed){
        toDoList.add(new Item(name, description, date, completed));
    }

    public ObservableList<Item> getObservableList(){
        return toDoList;
    }


    //Makes sure that the table is editable
    @FXML
    public void initialize() {
        taskCol.setCellValueFactory(
                new PropertyValueFactory<>("name")
        );
        taskCol.setCellFactory(TextFieldTableCell.forTableColumn());
        taskCol.setOnEditCommit(
                t -> (t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setName(t.getNewValue())
        );
        descriptionCol.setCellValueFactory(
                new PropertyValueFactory<>("description")
        );
        descriptionCol.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionCol.setOnEditCommit(
                t -> (t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setDescription(t.getNewValue())
        );
        dateCol.setCellValueFactory(
                new PropertyValueFactory<>("dueDate")
        );
        dateCol.setCellFactory(TextFieldTableCell.forTableColumn());
        dateCol.setOnEditCommit(
                t -> (t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setDueDate(t.getNewValue())
        );
        completedCol.setCellValueFactory(
                new PropertyValueFactory<>("completed")
        );
        completedCol.setCellFactory(TextFieldTableCell.forTableColumn());
        completedCol.setOnEditCommit(
                t -> (t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setCompleted(t.getNewValue())
        );
        tableView.setItems(toDoList);
    }

//Checks if the new task meets the requirements and adds to list when add button is clicked
    @FXML
    void addList(ActionEvent event) {

        if(notPopulatedCheck(ItemNameInput.getText()) || notPopulatedCheck(DescriptionInput.getText())){
            errorText.setText("Name and Description field cannot be empty!");
            return;
        }
        else if(!dueDateInput.getText().equals("") && dateFormatCheck(dueDateInput.getText())){
            errorText.setText("Input date as YYYY-MM-DD!");
            return;
        }
        else if(!dueDateInput.getText().equals("")){
            String[] fullDate = dueDateInput.getText().split("-");
            int year = Integer.parseInt(fullDate[0]);
            int month = Integer.parseInt(fullDate[1]);
            int day = Integer.parseInt(fullDate[2]);

            if(!dateExistCheck(year, month, day)){
                errorText.setText("Input an existing date!");
                return;
            }
            if(!(completedInput.getText().equals("y") || completedInput.getText().equals("Y")
                    || completedInput.getText().equals("n") || completedInput.getText().equals("N"))){
                errorText.setText("Completed must be answered with Y or N");
                return;
            }

        }
        addToList(ItemNameInput.getText(), DescriptionInput.getText(), dueDateInput.getText(), completedInput.getText());

        ItemNameInput.clear();
        DescriptionInput.clear();
        dueDateInput.clear();
        completedInput.clear();
        errorText.setText("Task Added!");
    }

    //clears full list when clear list button is pressed
    @FXML
    void clearFullList(ActionEvent event) {
       clearFullList();
    }

    void clearFullList(){
        toDoList.clear();
        completedList.clear();
        incompleteList.clear();
    }

    //deletes indicated task from list when task name is written on task text input and delete button is clicked
    @FXML
    void deleteSelectedItem(ActionEvent event) {
        toDoList.removeIf(Item -> ItemNameInput.getText().equals(Item.getName()));
        completedList.removeIf(Item -> ItemNameInput.getText().equals(Item.getName()));
        incompleteList.removeIf(Item -> ItemNameInput.getText().equals(Item.getName()));

    }

    //saves list to indicated file from file choosing text input when save list button is pressed
    @FXML
    void saveListToFile(ActionEvent event) throws IOException {
        String fileName = fileNameInput.getText();

        FileWriter writer = new FileWriter("./data/"+ fileName + ".txt");
        for(Item t: toDoList){
            writer.write(t.getName() + "//" + t.getDescription() + "//" + t.getDueDate() + "//" + t.getCompleted() + "//");
        }
        writer.close();
        fileNameInput.clear();
        errorText.setText("File Saved!");
    }

    //allows user to choose file when load list button is clicked
    @FXML
    void loadFile(ActionEvent event) {
        File file = chooseFile();

        if(file != null){
            try{
                clearFullList();
                FileInputStream fileLoaded = new FileInputStream(file);
                Scanner sc = new Scanner(fileLoaded);
                String[] in = sc.nextLine().split("//");
                for(int i=0; i<in.length; i+=4){
                    if(dateFormatCheck(in[i+2])){
                        toDoList.add(new Item(in[i], in[i+1], "", in[i+3]));
                    }
                    else{
                        toDoList.add(new Item(in[i], in[i+1], in[i+2], in[i+3]));
                    }
                }
                sc.close();
                errorText.setText("File Uploaded");

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else{
            errorText.setText("File not found, try again!");
        }

    }

    //shows all tasks when show all button is clicked
    @FXML
    void showAllItems(ActionEvent event) {
        tableView.setItems(toDoList);
    }

    //shows all completed tasks when completed button is clicked
    @FXML
    void showCompletedItems(ActionEvent event) {
        completedList.clear();
        for (Item t: toDoList) {
            if(t.getCompleted().equalsIgnoreCase("y"))
                completedList.add(t);
        }
        tableView.setItems(completedList);

    }

    //shows incomplete tasks when button is clicked
    @FXML
    void showIncompleteItems(ActionEvent event) {
        completedList.clear();
        for (Item t: toDoList) {
            if(t.getCompleted().equalsIgnoreCase("n"))
                completedList.add(t);
        }
        tableView.setItems(completedList);


    }

    //checks if textfield is populated for requirement #2
    public boolean notPopulatedCheck(String textField){
        if(textField.equals(""))
            return true;
        return false;
    }

    //checks if date format is correct for requirement #3
    public boolean dateFormatCheck(String dateText){
        return !dateText.matches("[0-9]{4}[-][0-9]{2}[-][0-9]{2}");
    }

    //checks if date exists for requirement #3
    public boolean dateExistCheck(int year, int month, int day){
        boolean flag = true;
        month = month - 1;

        Calendar date = new GregorianCalendar(year, month, day);
        if(year != date.get(Calendar.YEAR)){
            flag = false;
        }
        if(month != date.get(Calendar.MONTH)){
            flag = false;
        }
        if(day != date.get(Calendar.DAY_OF_MONTH)){
            flag = false;
        }
        return flag;
    }


    //allows user to choose the file they want to use
    private File chooseFile(){
        FileChooser chooser = new FileChooser();
        chooser.setTitle("File");
        return chooser.showOpenDialog(new Stage());
    }



}
