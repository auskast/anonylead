package com.edmunds.anonylead.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Created with IntelliJ IDEA. User: ehlee Date: 8/17/12 Time: 10:22 AM To change this template use File | Settings |
 * File Templates.
 */
public class UserView implements Viewable{

    private VerticalPanel panel;

    public UserView(){
        panel = new VerticalPanel();
    }

    public void setView(){
        final TextBox firstNameField = new TextBox();
        firstNameField.setWidth(MySampleApplication.width);

        final TextBox lastNameField = new TextBox();
        lastNameField.setWidth(MySampleApplication.width);

        final TextBox emailField = new TextBox();
        emailField.setWidth(MySampleApplication.width);

        final ListBox expiration = new ListBox();
//        expiration.addItem("24 hours");
        expiration.addItem("15 seconds");
        expiration.addItem("7 days");
        expiration.addItem("30 days");
        expiration.setItemSelected(2, true);

        final CheckBox isDigest = new CheckBox();
        final ListBox digestTime = new ListBox();
        digestTime.addItem("1 hour");
        digestTime.addItem("8 hours");
        digestTime.addItem("24 hours");
        digestTime.setItemSelected(1, true);

        final Button button = new Button("Request Free Quote!");
        final Label label = new Label();


        final VerticalPanel formPanel = new VerticalPanel();
        formPanel.setWidth(MySampleApplication.width);

        //Header
        Label header = new Label("Get a Free Dealer Quote");
        header.setStyleName("header");
        formPanel.add(header);

        //Intro
        Label intro = new Label(
                "Get multiple free price quotes from local dealers in your area by using the form below, without compromising your\n"
                        + "    personal information.");
        formPanel.add(intro);

        //First Name
        VerticalPanel firstNamePanel = new VerticalPanel();
        firstNamePanel.add(new Label("First Name"));
        firstNamePanel.add(firstNameField);
        formPanel.add(firstNamePanel);

        //Last Name
        VerticalPanel lastNamePanel = new VerticalPanel();
        lastNamePanel.add(new Label("Last Name"));
        lastNamePanel.add(lastNameField);
        formPanel.add(lastNamePanel);

        //Email
        VerticalPanel emailPanel = new VerticalPanel();
        emailPanel.add(new Label("Email Address"));
        emailPanel.add(emailField);
        emailPanel.add(new Label("We will not share your email address with dealers!"));
        formPanel.add(emailPanel);

        //Expiration
        HorizontalPanel expirationPanel = new HorizontalPanel();
        expirationPanel.add(new Label("Please allow dealers to contact me for "));
        expirationPanel.add(expiration);
        formPanel.add(expirationPanel);

        //Digest
        HorizontalPanel digestPanel = new HorizontalPanel();
        digestPanel.add(new Label("Please group my emails together into a digest"));
        digestPanel.add(isDigest);


        final HorizontalPanel digestTimePanel = new HorizontalPanel();
        digestTimePanel.add(new Label("Please group and send me my emails every "));
        digestTimePanel.add(digestTime);
        digestTimePanel.add(digestPanel);

        formPanel.add(digestPanel);

        isDigest.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                if(isDigest.isChecked()) {
                    formPanel.add(digestTimePanel);
                } else {
                    formPanel.remove(digestTimePanel);
                }
            }
        });
        final Label thankYou = new Label();

        button.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String email = emailField.getText();
                int duration = expiration.getSelectedIndex();
                int digestPeriod = isDigest.isChecked() ? digestTime.getSelectedIndex() + 1 : 0;

                final StringBuilder url = new StringBuilder("api/anonyLead/submit").append("?")
                    .append("first=").append(firstName).append("&")
                    .append("last=").append(lastName).append("&")
                    .append("email=").append(email).append("&")
                    .append("duration=").append(duration).append("&")
                    .append("digest=").append(digestPeriod);
                final RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.POST, url.toString());
                try {
                    requestBuilder.sendRequest(null, new RequestCallback() {
                        public void onResponseReceived(Request request, Response response) {
                            thankYou.setText("Thank you! You will receive an email shortly including the new Edmunds " +
                                "Purchase Guide, put together just for you!");
                        }

                        public void onError(Request request, Throwable throwable) {
                            thankYou.setText("Error!");
                        }
                    });
                } catch(RequestException e) {
                    thankYou.setText(e.getMessage());
                }
            }
        });

        panel.add(formPanel);
        panel.add(button);
        panel.add(thankYou);
    }

    public VerticalPanel getView(){
        return panel;
    }

}
