package com.calc.view;

import com.calc.controller.TreeController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class OutputPanel extends JPanel {
    private JTextField txtScreenOutput = new JTextField();
    private JScrollPane scrollPane;
    private JScrollPane scrollPaneTree;
    private Font textAreaFont = new Font("Dialog", Font.PLAIN, 28);

    public OutputPanel(String result, List<List<String>> treeLists, int digit) {
        setOutputPanelLocationSize();
        txtScreenOutput.setFont(textAreaFont);
        txtScreenOutput.setText(result);
        scrollPaneTree = new JScrollPane(TreeController.generateTree(treeLists, digit));
        scrollPaneTree.scrollRectToVisible(txtScreenOutput.getVisibleRect());
        scrollPaneTree.setSize(240, 290);
        scrollPaneTree.setLocation(5, 52);
        generateScrollPanelOutputScreen();
        add(scrollPaneTree);
    }

    private void setOutputPanelLocationSize() {
        setLayout(null);
        setSize(250, 380);
        setLocation(0, 4);
    }

    private void generateScrollPanelOutputScreen() {
        scrollPane = new JScrollPane(txtScreenOutput, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.scrollRectToVisible(txtScreenOutput.getVisibleRect());
        scrollPane.setSize(240, 50);
        scrollPane.setLocation(5, 0);
        add(scrollPane);
    }
}
