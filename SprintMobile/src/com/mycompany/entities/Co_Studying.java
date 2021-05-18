/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entities;

/**
 *
 * @author rayen
 */
public class Co_Studying {

    private int id;
    private String description;
    private String file;
    private String type;
    private String niveau;
    private int rating;
    private String id_student;

    public Co_Studying(int id, String description, String file, String type, String level, int rating, String id_student) {
        this.id = id;
        this.description = description;
        this.file = file;
        this.type = type;
        this.niveau = level;
        this.rating = rating;
        this.id_student = id_student;
    }

    public Co_Studying(String description, String file, String type, String level, int rating, String id_student) {
        this.description = description;
        this.file = file;
        this.type = type;
        this.niveau = level;
        this.rating = rating;
        this.id_student = id_student;
    }

    public Co_Studying(String description, String type, String niveau, int rating, String id_student) {
        this.description = description;
        this.type = type;
        this.niveau = niveau;
        this.rating = rating;
        this.id_student = id_student;
    }

    public Co_Studying() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getId_student() {
        return id_student;
    }

    public void setId_student(String id_student) {
        this.id_student = id_student;
    }
    
    
}
