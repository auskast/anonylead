package com.edmunds.anonylead.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class MySampleApplication implements EntryPoint {

    private static final String width = "330";
    private static final String SELECTED_BUTTON_STYLE = "buttonSelected";
    private Button userButton, tableButton, demoButton;
    private final VerticalPanel entryPanel = new VerticalPanel();
    private final VerticalPanel tableView = new VerticalPanel();
    private final VerticalPanel demoView = new VerticalPanel();
    private final FlexTable leadEmailTable = new FlexTable();
    private final FlexTable tempEmailTable = new FlexTable();

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        showOptions();

        setUserView();
        setDemoView();
        setTableView();

        showView(entryPanel);

    }

    private void showOptions() {
        HorizontalPanel optionPanel = new HorizontalPanel();

        userButton = new Button("User View");
        userButton.setStyleName(SELECTED_BUTTON_STYLE);

        tableButton = new Button("Table View");

        demoButton = new Button("Demo View");

        optionPanel.add(userButton);
        optionPanel.add(tableButton);
        optionPanel.add(demoButton);
        RootPanel.get("buttonArea").add(optionPanel);

        userButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                userButtonClicked();
            }
        });


        tableButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {

                tableButtonClicked();
            }
        });

        demoButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                //To change body of implemented methods use File | Settings | File Templates.
                demoButtonClicked();
            }
        });
    }

    private void demoButtonClicked() {
        demoButton.setStyleName(SELECTED_BUTTON_STYLE);

        userButton.removeStyleName(SELECTED_BUTTON_STYLE);
        tableButton.removeStyleName(SELECTED_BUTTON_STYLE);

        showView(demoView);

        hideView(entryPanel);
        hideView(tableView);
    }

    private void userButtonClicked() {
        userButton.setStyleName(SELECTED_BUTTON_STYLE);

        demoButton.removeStyleName(SELECTED_BUTTON_STYLE);
        tableButton.removeStyleName(SELECTED_BUTTON_STYLE);

        showView(entryPanel);

        hideView(tableView);
        hideView(demoView);
    }

    private void tableButtonClicked() {
        tableButton.setStyleName(SELECTED_BUTTON_STYLE);

        demoButton.removeStyleName(SELECTED_BUTTON_STYLE);
        userButton.removeStyleName(SELECTED_BUTTON_STYLE);


        showView(tableView); // so new data populates each time

        hideView(entryPanel);
        hideView(demoView);
    }


    private void setUserView() {
        final TextBox firstNameField = new TextBox();
        firstNameField.setWidth(width);

        final TextBox lastNameField = new TextBox();
        lastNameField.setWidth(width);

        final TextBox emailField = new TextBox();
        emailField.setWidth(width);

        final ListBox expiration = new ListBox();
        expiration.addItem("1 day");
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


        button.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                if(label.getText().equals("")) {
                    MySampleApplicationService.App.getInstance().getMessage("Hello, World!", new MyAsyncCallback(
                            label));
                } else {
                    label.setText("");
                }
            }
        });

        final VerticalPanel formPanel = new VerticalPanel();
        formPanel.setWidth(width);

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


            }
        });

        entryPanel.add(formPanel);
        entryPanel.add(button);
        entryPanel.add(thankYou);
    }

    private void setTableView() {
        //Refresh Button
        Button refresh = new Button("Refresh Tables");
        tableView.add(refresh);

        //Lead Email
        VerticalPanel leadEmailPanel = new VerticalPanel();
        Label leadEmail = new Label("leadEmail table");
        leadEmail.setStyleName("h2");

        leadEmailPanel.add(leadEmail);
        leadEmailPanel.add(getLeadEmailTable());

        tableView.add(leadEmailPanel);

        //Temp Email
        VerticalPanel tempEmailPanel = new VerticalPanel();
        Label tempEmail = new Label("tempEmail table");
        tempEmail.setStyleName("h2");

        tempEmailPanel.add(tempEmail);
        tempEmailPanel.add(getTempEmailTable());

        tableView.add(tempEmailPanel);

        refresh.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                tableView.clear();
                setTableView();
            }
        });


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

    private void setDemoView() {
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

        demoView.add(getTempEmailPanel);
        final Label result1 = new Label();
        demoView.add(result1);

        demoView.add(getRealEmailPanel);
        final Label result2 = new Label();
        demoView.add(result2);

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


    private void showView(Widget widget) {
        RootPanel.get("slot2").add(widget);
    }

    private void hideView(Widget widget) {
        RootPanel.get("slot2").remove(widget);
    }

    private static class MyAsyncCallback implements AsyncCallback<String> {
        private Label label;

        public MyAsyncCallback(Label label) {
            this.label = label;
        }

        public void onSuccess(String result) {
            label.getElement().setInnerHTML(result);
        }

        public void onFailure(Throwable throwable) {
            label.setText("Failed to receive answer from server!");
        }
    }
}
