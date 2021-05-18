/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.mycompany.entities.Co_Studying;
import com.codename1.components.FloatingHint;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.services.CoStudyingServices;


/**
 *
 * @author rayen
 */
public class UpdateCoStudying extends BaseForm {

    Form current;

    public UpdateCoStudying(Resources res, Co_Studying co) {

        super("Modifier Co_Studying", BoxLayout.y());

        Toolbar tb = new Toolbar(true);

        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("Container");

        setTitle("Modifier Co_Studying");
        getContentPane().setScrollVisible(false);

        //  super.addSideMenu (res);
        TextField id = new TextField(String.valueOf(co.getId()), "Id Co-Studing", 20, TextField.ANY);
        TextField rating = new TextField(String.valueOf(co.getRating()), "Rating  ", 20, TextField.ANY);
        TextField type = new TextField(String.valueOf(co.getType()), "Type ", 20, TextField.ANY);
        TextField niveau = new TextField(String.valueOf(co.getNiveau()), "Niveau ", 20, TextField.ANY);
        TextField description = new TextField(String.valueOf(co.getDescription()), "Description ", 20, TextField.ANY);
   
        ComboBox partCombo = new ComboBox();
        partCombo.addItem("a");

        id.setUIID("NewTopLine");
        rating.setUIID("NewTopLine");
        type.setUIID("NewTopLine");
        niveau.setUIID("NewTopLine");
        description.setUIID("NewTopLine");

        id.setSingleLineTextArea(true);
        rating.setSingleLineTextArea(true);
        type.setSingleLineTextArea(true);
        niveau.setSingleLineTextArea(true);
        description.setSingleLineTextArea(true);

        Button btnModifier = new Button("Modifier");
        // btnModifier.setUIID("button");

        btnModifier.addPointerPressedListener(l
                -> {
            co.setType(type.getText());
            co.setRating(Integer.parseInt(rating.getText()));
            co.setDescription(description.getText());
            co.setNiveau(niveau.getText());

            // appel fct modif service
            if (CoStudyingServices.getInstance().updateCos(co)) {
                new ListeCoStudying(res).show();
            }
        }
        );
        Button btnAnnler = new Button("Annuler");
        btnAnnler.addActionListener(e -> {
            new ListeCoStudying(res).show();
        });
        Label a = new Label("");
        Label b = new Label("");
        Label c = new Label("");
        Label d = new Label("");
        Label f = new Label();
        Container content = BoxLayout.encloseY(
                a, f,
                new FloatingHint(id),
                createLineSeparator(),
                new FloatingHint(type),
                createLineSeparator(),
                new FloatingHint(description),
                createLineSeparator(),
                new FloatingHint(rating),
                createLineSeparator(),
                new FloatingHint(niveau),
                createLineSeparator(),
                // partCombo,
                createLineSeparator(),
                b, c,
                createLineSeparator(),
                d,
                btnModifier,
                btnAnnler
        );
        add(content);
        show();
    }
}
