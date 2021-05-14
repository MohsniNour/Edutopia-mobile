/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

/**
 *
 * @author Amine
 */
public class Course {
    private int id;
    private String name;
    private String Description;
    private String nom_Subject;

    public Course() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getNom_Subject() {
        return nom_Subject;
    }

    public void setNom_Subject(String nom_Subject) {
        this.nom_Subject = nom_Subject;
    }
    
    
    
}
