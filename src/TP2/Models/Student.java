package TP2.Models;

public class Student {
    public Integer student_id;
    public String name;

    public Integer classe_id;


    public Student(Integer student_id, String name, Integer classe_id){
        this.student_id = student_id;
        this.name = name;

        this.classe_id = classe_id;
    }
}

