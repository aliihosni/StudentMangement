package TP2.Models;

public class Cours {
    public Integer cours_id;
    public String name;


    public Integer classe_id;

    public Cours(Integer cours_id, String name, Integer classe_id){
        this.cours_id= cours_id;
        this.name = name;

        this.classe_id= classe_id;
    }
}
