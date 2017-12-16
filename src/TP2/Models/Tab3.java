package TP2.Models;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tab3 {

    public Integer studentID;
    public SimpleStringProperty studentName;
    public SimpleStringProperty Classe;
    public Integer coursId;
    public SimpleStringProperty Cours;
    public SimpleStringProperty date;
    public SimpleBooleanProperty status;
    public SimpleStringProperty Note;

    public Tab3(Integer studentID, String studentName, String Classe, Integer coursId, String Cours, Date date, Integer status, String Note) throws ParseException {
        this.studentID = studentID;
        this.studentName = new SimpleStringProperty(studentName);
        this.Classe = new SimpleStringProperty(Classe);
        this.coursId = coursId;
        this.Cours = new SimpleStringProperty(Cours);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        this.date = new SimpleStringProperty(format.format(date));

        this.status = new SimpleBooleanProperty(status == 1?true:false);
        this.Note = new SimpleStringProperty(Note);
    }
}
