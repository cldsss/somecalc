package com.calc.controller;

import com.calc.view.MainFrame;
import com.calc.view.OutputPanel;

import javax.swing.*;

public class InputPanelController {

    public static void inputSimbol(JButton button, JTextField txtScreenInput) {
        txtScreenInput.requestFocus();
        StringBuffer expression = new StringBuffer(txtScreenInput.getText());
        int idexOfCursor = txtScreenInput.getCaretPosition();
        expression.insert(idexOfCursor, button.getText());
        txtScreenInput.setText(String.valueOf(expression));
        txtScreenInput.setCaretPosition(idexOfCursor + 1);

    }

    public static void updateOutputPanel(MainFrame mainFrame, int digit, String result, Calculator reader) {
        if (!result.equals("")) {
            mainFrame.remove(mainFrame.getOutputPanel());
            mainFrame.setOutputPanel(new OutputPanel(result, reader.getTreeLists(), digit));
            mainFrame.add(mainFrame.getOutputPanel());
            mainFrame.getOutputPanel().updateUI();
        }
    }

    public static void clearAll(MainFrame mainFrame, int digit, String result, Calculator reader) {
        reader.clearLists();
        mainFrame.remove(mainFrame.getOutputPanel());
        mainFrame.setOutputPanel(new OutputPanel("", null, 0));
        mainFrame.add(mainFrame.getOutputPanel());
        mainFrame.getOutputPanel().updateUI();
    }
}
