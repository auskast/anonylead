package com.edmunds.anonylead.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Created with IntelliJ IDEA. User: ehlee Date: 8/17/12 Time: 10:27 AM To change this template use File | Settings |
 * File Templates.
 */
public class TableView implements Viewable{
    private VerticalPanel panel;
    private final FlexTable leadEmailTable = new FlexTable();
    private final FlexTable tempEmailTable = new FlexTable();

    public TableView(){
        panel = new VerticalPanel();
    }

        public void setView(){
            //Refresh Button
            Button refresh = new Button("Refresh Tables");
            panel.add(refresh);

            //Lead Email
            VerticalPanel leadEmailPanel = new VerticalPanel();
            Label leadEmail = new Label("leadEmail table");
            leadEmail.setStyleName("h2");

            leadEmailPanel.add(leadEmail);
            leadEmailPanel.add(getLeadEmailTable());

            panel.add(leadEmailPanel);

            //Temp Email
            VerticalPanel tempEmailPanel = new VerticalPanel();
            Label tempEmail = new Label("tempEmail table");
            tempEmail.setStyleName("h2");

            tempEmailPanel.add(tempEmail);
            tempEmailPanel.add(getTempEmailTable());

            panel.add(tempEmailPanel);

            refresh.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent event) {
                    panel.clear();
                    refresh();
                }
            });
        }

    public VerticalPanel getView(){
          return panel;
    }

    public void refresh(){
        setView();
    }

    private Label getTitle(String text) {
        Label label = new Label(text);
        label.setStyleName("h2");
        return label;
    }

    private FlexTable getLeadEmailTable() {
        leadEmailTable.setStyleName("leadTable");

        leadEmailTable.setWidget(0, 0, getTitle("Email Address"));
        leadEmailTable.setWidget(0, 1, getTitle("First Name"));
        leadEmailTable.setWidget(0, 2, getTitle("Last Name"));
        leadEmailTable.setWidget(0, 3, getTitle("Digest Type"));
        leadEmailTable.setWidget(0, 4, getTitle("Temporary Email Address"));

        leadEmailTable.setText(1, 0, "elee86@gmail.com");


        return leadEmailTable;
    }


    private FlexTable getTempEmailTable() {
        tempEmailTable.setStyleName("leadTable");

        tempEmailTable.setWidget(0, 0, getTitle("Temp Email Address)"));
        tempEmailTable.setWidget(0, 1, getTitle("Short"));
        tempEmailTable.setWidget(0, 2, getTitle("Medium"));
        tempEmailTable.setWidget(0, 3, getTitle("Long"));

        return tempEmailTable;
    }
//
}
