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
import java.util.Calendar;
import java.util.GregorianCalendar;

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
    private Button showIncompletedButton;

    private final ObservableList<Item> toDoList = FXCollections.observableArrayList();
    private final ObservableList<Item> completedList = FXCollections.observableArrayList();
    private final ObservableList<Item> incompleteList = FXCollections.observableArrayList();

    @FXML
    public void initialize(){
        taskCol.setCellValueFactory(
                new PropertyValueFactory<Item, String>("name")
        );
        taskCol.setCellFactory(TextFieldTableCell.forTableColumn());
        taskCol.setOnEditCommit(
                t -> ((Item) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setName(t.getNewValue())
                /*
                t -> (t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setName(t.getNewValue())
*/
        );
        descriptionCol.setCellValueFactory(
                new PropertyValueFactory<>("description")
        );
        descriptionCol.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionCol.setOnEditCommit(
                t -> ((Item) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setName(t.getNewValue())
        );
        dateCol.setCellValueFactory(
                new PropertyValueFactory<>("dueDate")
        );
        dateCol.setCellFactory(TextFieldTableCell.forTableColumn());
        dateCol.setOnEditCommit(
                t -> ((Item) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setName(t.getNewValue())
        );
        completedCol.setCellValueFactory(
                new PropertyValueFactory<>("completed")
        );
        completedCol.setCellFactory(TextFieldTableCell.forTableColumn());
        completedCol.setOnEditCommit(
                t -> ((Item) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setName(t.getNewValue())
        );
        tableView.setItems(toDoList);
    }

    @FXML
    void addList(ActionEvent event) {

        if(notPopulatedCheck(ItemNameInput.getText()) || notPopulatedCheck(DescriptionInput.getText())){
            errorText.setText("Name and Description field cannot be empty!");
            return;
        }
        else if(dueDateInput.getText().equals("") || dateFormatCheck(dueDateInput.getText())){
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
        /*
        taskCol.setCellValueFactory(
                new PropertyValueFactory<>("name")
        );
        dateCol.setCellValueFactory(
                new PropertyValueFactory<>("dueDate")
        );
        descriptionCol.setCellValueFactory(
                new PropertyValueFactory<>("description")
        );
        completedCol.setCellValueFactory(
                new PropertyValueFactory<>("completed")
        );
        tableView.setItems(toDoList);
         */
        toDoList.add(new Item(
                ItemNameInput.getText(),
                DescriptionInput.getText(),
                dueDateInput.getText(),
                completedInput.getText()
        ));
        ItemNameInput.clear();
        DescriptionInput.clear();
        dueDateInput.clear();
        completedInput.clear();
        errorText.setText("__");
    }

    @FXML
    void clearFullList(ActionEvent event) {
        toDoList.clear();
        completedList.clear();
        incompleteList.clear();
    }

    @FXML
    void deleteSelectedItem(ActionEvent event) {
        toDoList.removeIf(Item -> ItemNameInput.getText().equals(Item.getName()));
        completedList.removeIf(Item -> ItemNameInput.getText().equals(Item.getName()));
        incompleteList.removeIf(Item -> ItemNameInput.getText().equals(Item.getName()));

    }

    @FXML
    void saveListToFile(ActionEvent event) {

    }

    @FXML
    void showAllItems(ActionEvent event) {
        tableView.setItems(toDoList);
    }

    @FXML
    void showCompletedItems(ActionEvent event) {
        completedList.clear();
        for (Item t: toDoList) {
            if(t.getCompleted().equalsIgnoreCase("y"))
                completedList.add(t);
        }
        tableView.setItems(completedList);

    }

    @FXML
    void showIncompleteItems(ActionEvent event) {
        completedList.clear();
        for (Item t: toDoList) {
            if(t.getCompleted().equalsIgnoreCase("n"))
                completedList.add(t);
        }
        tableView.setItems(completedList);


    }

    public boolean notPopulatedCheck(String textField){
        if(textField.equals(""))
            return true;
        return false;
    }

    public boolean dateFormatCheck(String dateText){
        return !dateText.matches("[0-9]{4}[-][0-9]{2}[-][0-9]{2}");
    }

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





}
