package TP2.Models;

import javafx.beans.property.SimpleStringProperty;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tab1 {

    public SimpleStringProperty studentName;
    public SimpleStringProperty status;
    public SimpleStringProperty Date;
    public SimpleStringProperty note;


    public Tab1(String studentName, Integer status, Date date, String note){
        this.studentName = new SimpleStringProperty(studentName);
        String absence;
        if(status.equals(0)){
            absence = "Present";
        }else {
            absence = "Absent";
        }
        this.status = new SimpleStringProperty(absence);

        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Date = new SimpleStringProperty(df.format(date));

        this.note = new SimpleStringProperty(note);
    }
}
