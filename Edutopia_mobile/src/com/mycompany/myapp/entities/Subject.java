/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

import java.util.Date;



/**
 *
 * @author Mrad
 */
public class Subject {
    private int id;
    private String id_Subject;
    private int id_class;
    private int id_teacher;
    private int created_by;
    private int update_by;
    private Date created_date;
    private Date update_date;
    
    public Subject(){
        
    }

    public int getId() {
        return id;
    }

    public String getId_Subject() {
        return id_Subject;
    }

    public int getId_class() {
        return id_class;
    }

    public int getId_teacher() {
        return id_teacher;
    }

    public int getCreated_by() {
        return created_by;
    }

    public int getUpdate_by() {
        return update_by;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public Date getUpdate_date() {
        return update_date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId_Subject(String id_Subject) {
        this.id_Subject = id_Subject;
    }

    public void setId_class(int id_class) {
        this.id_class = id_class;
    }

    public void setId_teacher(int id_teacher) {
        this.id_teacher = id_teacher;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }

    public void setUpdate_by(int update_by) {
        this.update_by = update_by;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public void setUpdate_date(Date update_date) {
        this.update_date = update_date;
    }

    @Override
    public String toString() {
        return "Subject{" + "id=" + id + ", id_Subject=" + id_Subject + ", id_class=" + id_class + ", id_teacher=" + id_teacher + ", created_by=" + created_by + ", update_by=" + update_by + ", created_date=" + created_date + ", update_date=" + update_date + '}';
    }
    
    
    
}
