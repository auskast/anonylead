package com.edmunds.anonylead.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Created with IntelliJ IDEA. User: ehlee Date: 8/17/12 Time: 10:32 AM To change this template use File | Settings |
 * File Templates.
 */
public class DemoView implements Viewable {
    public VerticalPanel panel;

    public DemoView(){
        panel = new VerticalPanel();
    }

    public void setView(){
        //Get temp email
        HorizontalPanel getTempEmailPanel = new HorizontalPanel();
        Label getTempLabel = new Label("Get temporary email from real email");
        getTempLabel.setStyleName("h2");
        final TextBox realEmail = new TextBox();
        final Button submitForTempEmail = new Button("Submit");

        getTempEmailPanel.add(getTempLabel);
        getTempEmailPanel.add(realEmail);
        getTempEmailPanel.add(submitForTempEmail);

        //Get Real Email
        HorizontalPanel getRealEmailPanel = new HorizontalPanel();
        Label getRealLabel = new Label("Get real email from temporary email");
        getRealLabel.setStyleName("h2");
        final TextBox tempEmail = new TextBox();
        final Button submitForRealEmail = new Button("Submit");

        getRealEmailPanel.add(getRealLabel);
        getRealEmailPanel.add(tempEmail);
        getRealEmailPanel.add(submitForRealEmail);

        panel.add(getTempEmailPanel);
        final Label result1 = new Label();
        panel.add(result1);

        panel.add(getRealEmailPanel);
        final Label result2 = new Label();
        submitForTempEmail.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                result1.setText("The temporary email for '" + tempEmail.getText() + "' is: ");
                result2.setText("");
            }
        });

        submitForRealEmail.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                result2.setText("The real email for '" + tempEmail.getText() + "' is: ");
                result1.setText("");
            }
        });

    }

    public VerticalPanel getView(){
        return panel;
    }


}
