/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.ui.Form;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Subject;
import com.mycompany.myapp.services.SubjectService;
import java.util.ArrayList;

/**
 *
 * @author Mrad
 */
public class ActionsMatiereForm extends Form{
    Form current;
    public ActionsMatiereForm(Resources res, Subject s) {
        super("Matiere",BoxLayout.y());
        System.out.println(s.toString());
     

    }
    
}
