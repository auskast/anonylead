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
import java.util.Arrays;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class MySampleApplication implements EntryPoint {

    public static final String width = "330";
    private static final String SELECTED_BUTTON_STYLE = "buttonSelected";
    Button[] buttons = new Button[]{new Button("User View"), new Button("Table View"), new Button("Demo View")};
    Viewable[] views = new Viewable[]{new UserView(), new TableView(), new DemoView()};

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        showOptions();

        for(Viewable view : views) {
            view.setView();
        }

        buttonClicked(0);
    }

    private void showOptions() {
        HorizontalPanel optionPanel = new HorizontalPanel();

        for(Button button : buttons) {
            optionPanel.add(button);


        }

            for(final Button button : buttons) {
            optionPanel.add(button);

        }

        RootPanel.get("buttonArea").add(optionPanel);

        buttons[0].addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                buttonClicked(0);
            }
        });


        buttons[1].addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                buttonClicked(1);
            }
        });

        buttons[2].addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                buttonClicked(2);
            }
        });
    }

    private void buttonClicked(int index ) {
        //Set button style
        buttons[index].setStyleName(SELECTED_BUTTON_STYLE);

        //Select View
        showView(views[index].getView());

        for(int i = 0; i < buttons.length; i++) {
            if(index == i) {
                continue;
            }
            buttons[i].removeStyleName(SELECTED_BUTTON_STYLE);
            hideView(views[i].getView());
        }

    }


    private void showView(Widget widget) {
        RootPanel.get("slot2").add(widget);
    }

    private void hideView(Widget widget) {
        RootPanel.get("slot2").remove(widget);
    }

}
