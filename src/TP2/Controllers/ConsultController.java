package TP2.Controllers;


import com.jfoenix.controls.JFXComboBox;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.StringConverter;
import TP2.Models.*;
import TP2.Services.Service;

import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;


public class ConsultController implements Initializable {

    Service service;

    @FXML
    private TableView<Tab1> tableStudent;

    @FXML
    private TableView<Tab2> tabAbs;

    @FXML
    private TableView<Tab3> tableEleve;

    @FXML
    private JFXComboBox classeCombo, ComboClass, classroomcombo;

    @FXML
    private JFXComboBox coursCombo, ComboCourse;

    @FXML
    private TableColumn<Tab1,String> studentName;

    @FXML
    private TableColumn<Tab1, String> studentStatus;

    @FXML
    private TableColumn<Tab1,String> studentNote, studentDate;

    @FXML
    private TableColumn<Tab2,String> colStdtN;

    @FXML
    private TableColumn<Tab2, Boolean> colAbs;

    @FXML
    private TableColumn<Tab3,String> nameCol, classeCol, courseCol, noteCol;

    @FXML
    private TableColumn<Tab3, String> dateCol;

    @FXML
    private TableColumn<Tab3, Boolean> abCol;

    @FXML
    private DatePicker datePicker;


    @FXML
    private TextField nameOfStudent, firstnameinput, lastnameinput, courseinput, classroominput;


    ArrayList<Classe> classes;
    ArrayList<Cours> courses;
    ArrayList<Student> students;
    ArrayList<Absence> absences;

    public static Integer coursId,Idcours;



    public ConsultController() throws ParseException {

        service = new Service();

        getClasses();

        getAbsence();


    }

    private void getClasses() {
        classes = service.getClasses();
    }

    private void getAbsence() throws ParseException {
        absences = service.getAbsences();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        updateClasses();


    }

    private void updateClasses() {
        classeCombo.getItems().clear();
        ComboClass.getItems().clear();
        classroomcombo.getItems().clear();

        for (int i=0; i< classes.size();i++){

            classeCombo.getItems().add(classes.get(i).name);
            ComboClass.getItems().add(classes.get(i).name);
            classroomcombo.getItems().add(classes.get(i).name);
        }
    }

    @FXML
    private void classeComboAction(ActionEvent event) {



        if (classeCombo.getValue() != null){
            courses = service.getCoursByClasse(classes.get(classeCombo.getSelectionModel().getSelectedIndex()).classe_id);

            ObservableList<String> data = FXCollections.observableArrayList();
            for (int i=0; i< courses.size();i++){
                data.add(courses.get(i).name);
            }
            try {
                coursCombo.setItems(data);

            }catch (Exception e){

            }
        }


    }

    @FXML
    private void classeComboAction2(ActionEvent event) {

        if (ComboClass.getValue() != null){
            courses = service.getCoursByClasse(classes.get(ComboClass.getSelectionModel().getSelectedIndex()).classe_id);
            ComboCourse.getItems().clear();

            for (int i=0; i< courses.size();i++){

                ComboCourse.getItems().add(courses.get(i).name);
            }
        }




    }




    public void findAb(ActionEvent actionEvent) throws ParseException {
        getAbsence();

        students = service.getStudentsByClasse(classes.get(classeCombo.getSelectionModel().getSelectedIndex()).classe_id);

        ObservableList<Tab1> data = FXCollections.observableArrayList();

//        studentName.setCellValueFactory(new PropertyValueFactory<Tab1,String>("studentName"));
//        studentStatus.setCellValueFactory(new PropertyValueFactory<Tab1,Integer>("status"));
//        studentNote.setCellValueFactory(new PropertyValueFactory<Tab1,String>("note"));


        studentName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Tab1, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Tab1, String> p) {
                return p.getValue().studentName;
            }
        });
        studentStatus.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Tab1, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Tab1, String> p) {
                return p.getValue().status;
            }

        });

        studentNote.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Tab1, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Tab1, String> p) {
                return p.getValue().note;
            }
        });
        studentDate.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Tab1, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Tab1, String> p) {
                return p.getValue().Date;
            }
        });






        for (int i=0; i< students.size();i++){


            //ArrayList<Cours> cours = service.getCoursByClasse()
            data.add(
                    new Tab1(students.get(i).name, getStatusAbByStudent(students.get(i).student_id, coursId) , getDateAbByStudent(students.get(i).student_id,coursId),getNoteAbByStudent(students.get(i).student_id,coursId))
            );

        }


        tableStudent.setItems(data);






    }


    public String getClasseById(Integer classeid){
        for (int i = 0; i<classes.size();i++){
            if (classes.get(i).classe_id == classeid){
                return classes.get(i).name;
            }
        }
        return "";
    }

    public Date getDateAbByStudent(Integer studentid, Integer coursId){
        for (int i = 0; i<absences.size();i++){
            if (absences.get(i).student_id == studentid  && absences.get(i).cours_id == coursId){
                return absences.get(i).date;
            }
        }
        return new Date();
    }

    public Integer getStatusAbByStudent( Integer studentid, Integer coursId){
        for (int i = 0; i<absences.size();i++){
            if (absences.get(i).student_id == studentid && absences.get(i).cours_id == coursId){
                return absences.get(i).status;
            }
        }
        return -1;
    }

    public Integer getStatusAbByStudent( Integer studentid, Integer coursId, Date date){
        for (int i = 0; i<absences.size();i++){
            if (absences.get(i).student_id == studentid && absences.get(i).cours_id == coursId && absences.get(i).date.equals(date)){
                return absences.get(i).status;
            }
        }
        return -1;
    }

    public String getNoteAbByStudent( Integer studentid, Integer coursId){
        for (int i = 0; i<absences.size();i++){
            if (absences.get(i).student_id == studentid && absences.get(i).cours_id == coursId){
                return absences.get(i).justif;
            }
        }
        return "";
    }





    public void findStudent(ActionEvent actionEvent) throws ParseException {

        getAbsence();


        nameCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Tab3, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Tab3, String> p) {
                return p.getValue().studentName;
            }
        });

        classeCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Tab3, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Tab3, String> p) {
                return p.getValue().Classe;
            }
        });

        courseCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Tab3, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Tab3, String> p) {
                return p.getValue().Cours;
            }
        });

        noteCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Tab3, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Tab3, String> p) {
                return p.getValue().Note;
            }
        });

        dateCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Tab3, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Tab3, String> p) {
                return  p.getValue().date;
            }
        });

        abCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Tab3, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Tab3, Boolean> p) {
                return p.getValue().status;
            }
        });



        ObservableList<Tab3> data = FXCollections.observableArrayList();
        if(!nameOfStudent.getText().isEmpty()){
            ArrayList<Student> students = service.getStudentsByName(nameOfStudent.getText());
            for (int i=0; i< students.size();i++){
                ArrayList<Cours> cours = service.getCoursByClasse(students.get(i).classe_id);
                for (int c=0; c<cours.size();c++){
                    if (getStatusAbByStudent(students.get(i).student_id,cours.get(c).cours_id) != -1){

                        data.add(
                                new Tab3(students.get(i).student_id,
                                        students.get(i).name,
                                        getClasseById(students.get(i).classe_id) ,
                                        cours.get(c).cours_id,
                                        cours.get(c).name,
                                        getDateAbByStudent(students.get(i).student_id,cours.get(c).cours_id),
                                        getStatusAbByStudent(students.get(i).student_id,cours.get(c).cours_id),
                                        getNoteAbByStudent(students.get(i).student_id,cours.get(c).cours_id)
                        ));
                    }

                }
            }
            tableEleve.setItems(data);
        }


        noteCol.setCellFactory(new Callback<TableColumn<Tab3, String>, TableCell<Tab3, String>>() {
            @Override
            public TableCell<Tab3, String> call(TableColumn<Tab3, String> tab3StringTableColumn) {
                return new TextFieldTableCell<Tab3,String>(new StringConverter<String>() {
                    @Override
                    public String toString(String s) {


                        return s;
                    }


                    @Override
                    public String fromString(String s) {
                        return s;
                    }
                });
            }
        });

        noteCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Tab3, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Tab3, String> tab3StringCellEditEvent) {


                Integer studentid = tableEleve.getItems().get(tableEleve.getSelectionModel().getSelectedIndex()).studentID;
                Integer coursId = tableEleve.getItems().get(tableEleve.getSelectionModel().getSelectedIndex()).coursId;


                int abId = getAbsenceId(studentid,coursId,getDateAbByStudent(studentid,coursId));
                String url = "absences/"+abId;
                Map<String, String> parameters = new HashMap<>();

                parameters.put("justif", tab3StringCellEditEvent.getNewValue());

                try {
                    if(service.sendPost(url, parameters,"PUT")== 200){

                        System.out.println("Puted");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }






    public void selectFom(ActionEvent actionEvent) throws ParseException {


        getAbsence();

        colStdtN.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Tab2, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Tab2, String> p) {
                return p.getValue().studentName;
            }
        });

        colAbs.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Tab2, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Tab2, Boolean> p) {
                return p.getValue().absence;
            }
        });


        LocalDate localDate = datePicker.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);

        ArrayList<Student> students = service.getStudentsByClasse(classes.get(ComboClass.getSelectionModel().getSelectedIndex()).classe_id);
        tabAbs.getItems().clear();

        ObservableList<Tab2> data = FXCollections.observableArrayList();


        for (int i=0; i< students.size();i++){

            data.add(
                    new Tab2(students.get(i).name, getStatusAbByStudent(students.get(i).student_id,Idcours,date) )
            );

        }


        tabAbs.setItems(data);
        colAbs.setCellFactory(new Callback<TableColumn<Tab2, Boolean>, TableCell<Tab2, Boolean>>() {
            @Override
            public TableCell<Tab2, Boolean> call(TableColumn<Tab2, Boolean> tab2BooleanTableColumn) {
                return new CheckBoxTableCell<Tab2,Boolean>(){
                    @Override
                    public void updateItem(Boolean o, boolean b) {
                        super.updateItem(o, b);


                        if (o!=null && !b){
                            try {
                                absences = service.getAbsences();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            int studentId = students.get(this.getIndex()).student_id;
                            int abId = getAbsenceId(studentId,Idcours, date);
                            String url = "absences";

                            if(o){
                                if (abId == 0){
                                    //Post

                                    Map<String, String> parameters = new HashMap<>();
                                    parameters.put("status", String.valueOf(1));
                                    //parameters.put("justif", "");
                                    DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                                    parameters.put("date", df.format(date));
                                    parameters.put("student_id", String.valueOf(studentId));
                                    parameters.put("cour_id", String.valueOf(Idcours));




                                    try {
                                        if(service.sendPost(url, parameters,"POST")== 201){

                                            System.out.println("Posted");
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                            }
                            else {
                                 if (abId !=0){
                                     try {
                                         if (service.sendDelete(url,abId) == 204){
                                             System.out.println("Deleted");
                                         }
                                     } catch (Exception e) {
                                         e.printStackTrace();
                                     }
                                 }

                            }
                            System.out.println(this.getIndex());
                        }

                    }


                };
            }
        });

    }

    private Integer getAbsenceId(int indexStudent, int coursesid, Date date) {
        for (int i =0;i<absences.size();i++){
            if (absences.get(i).date.equals(date)&&absences.get(i).cours_id.equals(coursesid)&&absences.get(i).student_id.equals(indexStudent)){
                return  absences.get(i).absence_id;
            }
        }
        return 0;
    }

    public void coursCom(ActionEvent actionEvent) {
        ArrayList<Cours> cours = service.getCours();
        if (coursCombo.getValue() != null){

            for (int i =0; i<cours.size();i++){
                if (coursCombo.getValue().equals(cours.get(i).name)){
                    coursId = cours.get(i).cours_id;
                }
            }
        }

    }

    public void courCom2(ActionEvent actionEvent) {
        ArrayList<Cours> cours = service.getCours();
        if (ComboCourse.getValue() != null){

            for (int i =0; i<cours.size();i++){
                if (ComboCourse.getValue().equals(cours.get(i).name)){
                    Idcours = cours.get(i).cours_id;
                }
            }
        }
    }

    public void savestudent(ActionEvent actionEvent) {
        String name = firstnameinput.getText() + " "+ lastnameinput.getText();

        String url = "students";
        Map<String, String> parameters = new HashMap<>();
        parameters.put("name", name);
        parameters.put("classe_id", String.valueOf(classes.get(classroomcombo.getSelectionModel().getSelectedIndex()).classe_id));


        try {
            if(service.sendPost(url, parameters,"POST")== 200){

                System.out.println("Posted");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void savecourse(ActionEvent actionEvent) {
        String url = "courses";
        Map<String, String> parameters = new HashMap<>();
        parameters.put("name", courseinput.getText());
        parameters.put("classe_id", String.valueOf(classes.get(classroomcombo.getSelectionModel().getSelectedIndex()).classe_id));


        try {
            if(service.sendPost(url, parameters,"POST")== 200){

                System.out.println("Posted");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void bntclass(ActionEvent actionEvent) {

        String url = "classes";
        Map<String, String> parameters = new HashMap<>();
        parameters.put("name", classroominput.getText());


        try {
            if(service.sendPost(url, parameters,"POST")== 200){

                System.out.println("Posted");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        getClasses();
        updateClasses();
    }
}

