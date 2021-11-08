import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/*
 *  UCF COP3330 Fall 2021 Application Assignment 1 Solution
 *  Copyright 2021 Diego Figueroa
 */

public class Item {
    private StringProperty name = new SimpleStringProperty();
    private StringProperty description = new SimpleStringProperty();
    private StringProperty dueDate = new SimpleStringProperty();
    private StringProperty completed = new SimpleStringProperty();

    
    public Item (String name, String descrip, String date, String completed){
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(descrip);
        this.dueDate = new SimpleStringProperty(date);
        this.completed = new SimpleStringProperty(completed);
        
    }
    //returns name
    public String getName(){
        
        return name.get();
    }

    public StringProperty nameProperty(){
        return name;
    }

    //returns description
    public String getDescription(){
        return description.get();
    }

    public StringProperty nameDescription(){
        return name;
    }

    //returns due date
    public String getDueDate(){

        return dueDate.get();
    }

    public StringProperty nameDueDate(){
        return name;
    }

    //returns if task is completed
    public String getCompleted(){

        return completed.get();
    }

    public StringProperty nameCompleted(){
        return completed;
    }

    //sets task description to a new task
    public void setName(String x){
        name.set(x);
    }

    public void setDescription(String x){
        description.set(x);
    }

    //sets task due date to a new due date
    public void setDueDate(String x){
        dueDate.set(x);
    }

    //sets task completion to new completion boolean
    public void setCompleted(String x){
        completed.set(x);
    }


}
