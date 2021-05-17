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
public class Question {
    private int id;
    private String question;
    private String proposition1;
     private String proposition2;
      private String proposition3;
       private String proposition4;
    
       private String bonnereponse;

    public Question() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getProposition1() {
        return proposition1;
    }

    public void setProposition1(String proposition1) {
        this.proposition1 = proposition1;
    }

    public String getProposition2() {
        return proposition2;
    }

    public void setProposition2(String proposition2) {
        this.proposition2 = proposition2;
    }

    public String getProposition3() {
        return proposition3;
    }

    public void setProposition3(String proposition3) {
        this.proposition3 = proposition3;
    }

    public String getProposition4() {
        return proposition4;
    }

    public void setProposition4(String proposition4) {
        this.proposition4 = proposition4;
    }

    public String getBonnereponse() {
        return bonnereponse;
    }

    public void setBonnereponse(String bonnereponse) {
        this.bonnereponse = bonnereponse;
    }
    
 
    
}
