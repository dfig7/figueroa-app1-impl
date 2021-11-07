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
                ).setName(t.getNewValue())
        );
        dateCol.setCellValueFactory(
                new PropertyValueFactory<>("dueDate")
        );
        dateCol.setCellFactory(TextFieldTableCell.forTableColumn());
        dateCol.setOnEditCommit(
                t -> (t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setName(t.getNewValue())
        );
        completedCol.setCellValueFactory(
                new PropertyValueFactory<>("completed")
        );
        completedCol.setCellFactory(TextFieldTableCell.forTableColumn());
        completedCol.setOnEditCommit(
                t -> (t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setName(t.getNewValue())
        );
        tableView.setItems(toDoList);
    }

    @FXML
    void addList(ActionEvent event) {
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
    }

    @FXML
    void clearFullList(ActionEvent event) {
        toDoList.clear();
        completedList.clear();
        incompleteList.clear();
    }

    @FXML
    void deleteSelectedItem(ActionEvent event) {

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



}
