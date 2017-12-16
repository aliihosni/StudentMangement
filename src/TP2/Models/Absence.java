package TP2.Models;


import java.util.Date;

public class Absence {

    public Integer absence_id;
    public Integer status;
    public String justif;
    public Date date;


    public Integer student_id;
    public Integer cours_id;

    public Absence(Integer absence_id,Integer status, String justif,Date date, Integer student_id,Integer cours_id){
        this.absence_id = absence_id;
        this.status = status;
        this.justif = justif;
        this.date= date;

        this.student_id = student_id;
        this.cours_id = cours_id;
    }
}
