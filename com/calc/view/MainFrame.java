package com.calc.view;

import javax.swing.*;

public class MainFrame extends JFrame {


    private InputPanel inputPanel = new InputPanel(this);
    private OutputPanel outputPanel = new OutputPanel("", null, 0);

    public OutputPanel getOutputPanel() {
        return outputPanel;
    }

    public void setOutputPanel(OutputPanel outputPanel) {
        this.outputPanel = outputPanel;
    }

    public MainFrame() {
        super("Calculator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setSize(600, 385);
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);
        add(inputPanel);
        add(outputPanel);
    }

}