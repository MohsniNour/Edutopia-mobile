/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.mycompany.entities.Co_Studying;
import com.mycompany.services.CoStudyingServices;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.ext.filechooser.FileChooser;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.CN;
import com.codename1.ui.CheckBox;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.codename1.ui.Graphics;
import com.codename1.ui.RadioButton;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.ImageIO;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 *
 * @author rayen
 */
public class AddCoStudying extends BaseForm {

    Form current;

    String path = "";
    String imgpath = "";

    protected String saveFileToDevice(String hi, String ext) throws IOException {
        URI uri;
        try {
            uri = new URI(hi);
            String path = uri.getPath();
            int index = hi.lastIndexOf("/");
            hi = hi.substring(index + 1);
            imgpath = hi;
            return hi;
        } catch (URISyntaxException ex) {
        }
        int index = hi.lastIndexOf("/");
        hi = hi.substring(index + 1);
        imgpath = hi;
        return hi;
    }

    public AddCoStudying(Resources res) {

        super("Ajouter contenu", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        getContentPane().setScrollVisible(false);

        tb.addSearchCommand(s -> {
        });

        /**
         * *************************************
         */
        Tabs swipe = new Tabs();
        Label s1 = new Label();
        Label s2 = new Label();

        addTab(swipe, s1, res.getImage("kkj.png"), "", "", res);

        swipe.setUIID("Container");
        swipe.getContentPane().setUIID("Container");
        swipe.hideTabs();

        ButtonGroup bg = new ButtonGroup();
        int size = Display.getInstance().convertToPixels(1);
        Image unselectedWalkthru = Image.createImage(size, size, 0);
        Graphics g = unselectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAlpha(100);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        Image selectedWalkthru = Image.createImage(size, size, 0);
        g = selectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        RadioButton[] rbs = new RadioButton[swipe.getTabCount()];
        FlowLayout flow = new FlowLayout(CENTER);
        flow.setValign(BOTTOM);
        Container radioContainer = new Container(flow);
        for (int iter = 0; iter < rbs.length; iter++) {
            rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
            rbs[iter].setPressedIcon(selectedWalkthru);
            rbs[iter].setUIID("Label");
            radioContainer.add(rbs[iter]);
        }
        rbs[0].setSelected(true);
        swipe.addSelectionListener((i, ii) -> {
            if (!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });

        Component.setSameSize(radioContainer, s1, s2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));
        ButtonGroup barGroup = new ButtonGroup();

        RadioButton liste = RadioButton.createToggle("Home", barGroup);
        liste.setUIID("SelectBar");
        liste.getAllStyles().setBorder(Border.createEmpty());
        liste.getAllStyles().setTextDecoration(Style.TEXT_DECORATION_UNDERLINE);

        RadioButton mesListes = RadioButton.createToggle("Contenu", barGroup);
        mesListes.setUIID("SelectBar");
        mesListes.getAllStyles().setBorder(Border.createEmpty());
        mesListes.getAllStyles().setTextDecoration(Style.TEXT_DECORATION_UNDERLINE);

        RadioButton partage = RadioButton.createToggle("Ajouter", barGroup);
        partage.setUIID("SelectBar");
        partage.getAllStyles().setBorder(Border.createEmpty());
        partage.getAllStyles().setTextDecoration(Style.TEXT_DECORATION_UNDERLINE);

        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");

        mesListes.addActionListener((e) -> {
            InfiniteProgress ip = new InfiniteProgress();
            final Dialog ipDlg = ip.showInifiniteBlocking();
            new ListeCoStudying(res).show();
            refreshTheme();
        });

        partage.addActionListener((e) -> {
            InfiniteProgress ip = new InfiniteProgress();
            final Dialog ipDlg = ip.showInifiniteBlocking();
            new AddCoStudying(res).show();
            refreshTheme();
        });

        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(3, liste, mesListes, partage),
                FlowLayout.encloseBottom(arrow)
        ));

        partage.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(partage, arrow);
        });
        bindButtonSelection(mesListes, arrow);
        bindButtonSelection(liste, arrow);
        bindButtonSelection(partage, arrow);
        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });

        /**
         * *********************************
         */
        TextField description = new TextField("", "Entrer une description");
        description.setUIID("TextFieldBalck");
        addStringValue("Description", description);

//        TextField type = new TextField("", "Entrer un type"); 
//        type.setUIID("TextFieldBalck");
//        addStringValue("Type", type);
        ComboBox c = new ComboBox();
        List<String> listType = CoStudyingServices.getInstance().getAllType();
        for (String t : listType) {
            c.addItem(t);
        }
        listType.removeAll(listType);
        //listType.clear();

        description.setUIID("TextFieldBalck");
        addStringValue("Type", c);

        TextField niveau = new TextField("", "Entrer un niveau");
        niveau.setUIID("TextFieldBalck");
        addStringValue("Level", niveau);

        TextField rating = new TextField("", "Entrer le rating");
        rating.setUIID("TextFieldBalck");
        addStringValue("Rating", rating);

        TextField idStudent = new TextField("", "Entrer propriétaire");
        idStudent.setUIID("TextFieldBalck");
        addStringValue("Author", idStudent);

        CheckBox multiSelect = new CheckBox("Multi-Select");
        Button img1 = new Button("Choisir un fichier");
        img1.setUIID("TextFieldBlack");
        addStringValue("Fichier PDF", img1);
        img1.addActionListener((ActionEvent e) -> {
            if (FileChooser.isAvailable()) {
                FileChooser.setOpenFilesInPlace(true);
                FileChooser.showOpenDialog(multiSelect.isSelected(), ".pdf, .jpg, .jpeg, .png/plain", (ActionEvent e2) -> {
                    if (e2 == null || e2.getSource() == null) {
                        add("No file was selected");
                        revalidate();
                        return;
                    }
                    if (multiSelect.isSelected()) {
                        String[] paths = (String[]) e2.getSource();
                        for (String path : paths) {
                            System.out.println(path);
                            CN.execute(path);
                        }
                        return;
                    }

                    String file = (String) e2.getSource();
                    if (file == null) {
                        add("Aucun fichier n'a été sélectionné");
                        revalidate();
                    } else {
                        img1.setEnabled(false);
                        int index = file.lastIndexOf("/");
                        imgpath = file.substring(index + 1);
                        img1.setText(imgpath);
                        String imageFile = FileSystemStorage.getInstance().getAppHomePath() + "photo.png";
                        try (OutputStream os = FileSystemStorage.getInstance().openOutputStream(imageFile)) {
                            System.out.println(imageFile);
                        } catch (IOException err) {
                        }

                        String extension = null;
                        if (file.lastIndexOf(".") > 0) {
                            try {
                                extension = file.substring(file.lastIndexOf(".") + 1);
                                StringBuilder hi = new StringBuilder(file);
                                if (file.startsWith("file://")) {
                                    hi.delete(0, 7);
                                }
                                int lastIndexPeriod = hi.toString().lastIndexOf(".");
                                Log.p(hi.toString());
                                String ext = hi.toString().substring(lastIndexPeriod);
                                String hmore = hi.toString().substring(0, lastIndexPeriod - 1);
                                String namePic = saveFileToDevice(file, ext);
                                System.out.println(namePic);
                                path = namePic;
                                System.out.println("File  \n\n" + file);
                                revalidate();
                            } catch (IOException ex) {

                            }
                        }
                    }
                });
            }
        });

        Button btnAjouter = new Button("Add");
        addStringValue("", btnAjouter);
        btnAjouter.addActionListener((e) -> {

            try {
                if (description.getText() == "" || (((String) c.getSelectedItem()).length() == 0) || niveau.getText() == "" || rating.getText() == "" || idStudent.getText() == "") {
                    Dialog.show("veuillez verifier vos donnees", "", "annuler", "ok");
                } else {
                    InfiniteProgress ip = new InfiniteProgress();
                    final Dialog iDialog = ip.showInfiniteBlocking();
                    Co_Studying promo = new Co_Studying(
                            String.valueOf(description.getText()),
                            path,
                            String.valueOf(c.getSelectedItem()),
                            String.valueOf(niveau.getText()),
                            Integer.valueOf(rating.getText()),
                            String.valueOf(idStudent.getText())
                    );

                    System.out.println("data promo else gui" + promo);
                    // calling adding fct in the services class
                    CoStudyingServices.getInstance().addCos(promo);
                    iDialog.dispose(); //Remove LOADING after adding

                    //notification API
                    ToastBar.getInstance().setPosition(BOTTOM);
                    ToastBar.Status status = ToastBar.getInstance().createStatus();
                    status.setShowProgressIndicator(true);
                    status.setIcon(res.getImage("nour.png").scaledSmallerRatio(Display.getInstance().getDisplayWidth() / 10, Display.getInstance().getDisplayWidth() / 15));
                    status.setMessage("Contenu ajouté avec sucées");
                    status.setExpires(30000);  // only show the status for 3 seconds, then have it automatically clear
                    status.show();
                    //  iDialog.dispose(); //NAHIW LOADING BAED AJOUT
                    new ListeCoStudying(res).show();
                    refreshTheme();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                System.err.println("erreur!!! ");
            }

        });
    }

    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel")).add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }

    private void addTab(Tabs swipe, Label spacer, Image image, String string, String text, Resources res) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
        if (image.getHeight() < size) {
            image = image.scaledHeight(size);
        }
        if (image.getHeight() > Display.getInstance().getDisplayHeight() / 2) {
            image = image.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
        }
        ScaleImageLabel imageScale = new ScaleImageLabel(image);
        imageScale.setUIID("Container");
        imageScale.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        Label overLay = new Label("", "ImageOverlay");
        Container page1
                = LayeredLayout.encloseIn(
                        imageScale,
                        overLay,
                        BorderLayout.south(
                                BoxLayout.encloseY(
                                        new SpanLabel(text, "LargeWhiteText"),
                                        //nooo
                                        spacer
                                )
                        )
                );
        swipe.addTab("", res.getImage("kkj.png"), page1);
    }

    public void bindButtonSelection(Button btn, Label l) {
        btn.addActionListener(e -> {
            if (btn.isSelected()) {
                updateArrowPosition(btn, l);
            }
        }
        );
    }

    private void updateArrowPosition(Button btn, Label l) {
        l.getUnselectedStyle().setMargin(LEFT, btn.getX() + btn.getWidth() / 2 - l.getWidth() / 2);
        l.getParent().repaint();;
    }

}
