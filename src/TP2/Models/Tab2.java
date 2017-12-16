package TP2.Models;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class Tab2 {

    public SimpleStringProperty studentName;
    public SimpleBooleanProperty absence;

    public Tab2(String studentName, Integer absence){
        this.studentName = new SimpleStringProperty(studentName);
        this.absence = new SimpleBooleanProperty(absence==1? true:false);
    }
}
