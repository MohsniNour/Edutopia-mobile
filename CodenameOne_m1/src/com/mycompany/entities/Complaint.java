/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entities;

import java.util.Date;

/**
 *
 * @author Sabrina
 */
public class Complaint {
    private int id;
    private String object;
    private String description;
    private String status;
    private  Date createdDate;
    private int createdBy;

    
    public Complaint() {
    }
   
    public Complaint(int id, String object, String description, String status, Date createdDate, int createdBy) {
        this.id = id;
        this.object = object;
        this.description = description;
        this.status = status;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
    }

    public Complaint(String object, String description, String status, Date createdDate, int createdBy) {
        this.object = object;
        this.description = description;
        this.status = status;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }
    
    
    
    
    
    
    
}
