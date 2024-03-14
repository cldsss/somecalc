package com.calc.view;

import com.calc.controller.Calculator;
import com.calc.controller.InputPanelController;

import javax.swing.*;
import java.awt.*;

public class InputPanel extends JPanel {
    //private OutputPanel outputPanel;
    private JTextField txtScreenInput = new JTextField();
    private Font buttonsFont = new Font("Dialog", Font.PLAIN, 19);
    private Font textAreaFont = new Font("Dialog", Font.PLAIN, 28);
    private JScrollPane scrollPane;
    private JRadioButton openFunctionRadioBtn = new JRadioButton("Open power functions");
    private String result;
    private Calculator reader = new Calculator();
    private int digit = 0;
    //number buttons:
    private JButton btn0 = new JButton("0");
    private JButton btn1 = new JButton("1");
    private JButton btn2 = new JButton("2");
    private JButton btn3 = new JButton("3");
    private JButton btn4 = new JButton("4");
    private JButton btn5 = new JButton("5");
    private JButton btn6 = new JButton("6");
    private JButton btn7 = new JButton("7");
    private JButton btn8 = new JButton("8");
    private JButton btn9 = new JButton("9");

    //operator buttons:
    private JButton btnAddition = new JButton("+");
    private JButton btnSubtraction = new JButton("-");
    private JButton btnMultiplication = new JButton("*");
    private JButton btnDivision = new JButton("/");
    private JButton btnSqrt = new JButton("sqrt");
    private JButton btnRemainderDivision = new JButton("%");
    private JButton btnInversion = new JButton("1/x");
    private JButton btnEqual = new JButton("=");
    private JButton btnLeft = new JButton("<");
    private JButton btnRight = new JButton(">");
    private JButton btnClear = new JButton("C");
    private JButton btnSquare = new JButton("x^2");
    private JButton btnСube = new JButton("x^3");
    private JButton btnExponentiation = new JButton("x^y");
    private JButton btnDot = new JButton(".");
    private JButton btnLeftBracked = new JButton("(");
    private JButton btnRightBracked = new JButton(")");
    private MainFrame mainFrame;

    public InputPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(null);
        setSize(350, 380);
        setLocation(250, 4);

        txtScreenInput.setFont(textAreaFont);
        scrollPane = new JScrollPane(txtScreenInput, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.scrollRectToVisible(txtScreenInput.getVisibleRect());
        scrollPane.setSize(332, 50);
        scrollPane.setLocation(0, 0);

        openFunctionRadioBtn.setSize(152, 20);
        openFunctionRadioBtn.setFocusPainted(false);
        openFunctionRadioBtn.setLocation(0, 264);
        add(openFunctionRadioBtn);
        addButton(0, 52, btn7);
        addButton(52, 52, btn8);
        addButton(104, 52, btn9);
        addButton(0, 104, btn4);
        addButton(52, 104, btn5);
        addButton(104, 104, btn6);
        addButton(0, 156, btn1);
        addButton(52, 156, btn2);
        addButton(104, 156, btn3);
        addButton(52, 208, btn0);
        addButton(0, 208, btnDot);
        addButton(104, 208, btnEqual);
        addButton(156, 104, btnLeftBracked);
        addButton(208, 104, btnRightBracked);
        addButton(156, 52, btnLeft);
        addButton(208, 52, btnRight);
        addButton(156, 156, btnMultiplication);
        addButton(208, 156, btnDivision);
        addButton(156, 208, btnAddition);
        addButton(208, 208, btnSubtraction);
        addButton(260, 52, btnInversion);
        addButton(260, 104, btnSqrt);
        addButton(260, 156, btnRemainderDivision);
        addButton(260, 208, btnClear);
        add(scrollPane);

        btn7.addActionListener(actionEvent -> InputPanelController.inputSimbol(btn7, txtScreenInput));
        btn6.addActionListener(actionEvent -> InputPanelController.inputSimbol(btn6, txtScreenInput));
        btn5.addActionListener(actionEvent -> InputPanelController.inputSimbol(btn5, txtScreenInput));
        btn4.addActionListener(actionEvent -> InputPanelController.inputSimbol(btn4, txtScreenInput));
        btn3.addActionListener(actionEvent -> InputPanelController.inputSimbol(btn3, txtScreenInput));
        btn2.addActionListener(actionEvent -> InputPanelController.inputSimbol(btn2, txtScreenInput));
        btn1.addActionListener(actionEvent -> InputPanelController.inputSimbol(btn1, txtScreenInput));
        btn0.addActionListener(actionEvent -> InputPanelController.inputSimbol(btn0, txtScreenInput));
        btn8.addActionListener(actionEvent -> InputPanelController.inputSimbol(btn8, txtScreenInput));
        btn9.addActionListener(actionEvent -> InputPanelController.inputSimbol(btn9, txtScreenInput));
        btnLeftBracked.addActionListener(actionEvent -> InputPanelController.inputSimbol(btnLeftBracked, txtScreenInput));
        btnRightBracked.addActionListener(actionEvent -> InputPanelController.inputSimbol(btnRightBracked, txtScreenInput));
        btnRemainderDivision.addActionListener(actionEvent -> InputPanelController.inputSimbol(btnRemainderDivision, txtScreenInput));
        btnDivision.addActionListener(actionEvent -> InputPanelController.inputSimbol(btnDivision, txtScreenInput));
        btnMultiplication.addActionListener(actionEvent -> InputPanelController.inputSimbol(btnMultiplication, txtScreenInput));
        btnSubtraction.addActionListener(actionEvent -> InputPanelController.inputSimbol(btnSubtraction, txtScreenInput));
        btnAddition.addActionListener(actionEvent -> InputPanelController.inputSimbol(btnAddition, txtScreenInput));

        btnClear.addActionListener(actionEvent -> {
            txtScreenInput.setText("");
            //reader.setTreeLists(null);
            InputPanelController.clearAll(this.mainFrame, 0, "", reader);
        });

        btnSquare.addActionListener(actionEvent -> txtScreenInput.setText("(" + txtScreenInput.getText() + ")^(2)"));
        btnСube.addActionListener(actionEvent -> txtScreenInput.setText("(" + txtScreenInput.getText() + ")^(3)"));
        btnExponentiation.addActionListener(actionEvent -> txtScreenInput.setText("(" + txtScreenInput.getText() + ")^()"));
        btnSqrt.addActionListener(actionEvent -> txtScreenInput.setText("(" + txtScreenInput.getText() + ")sqrt"));
        btnInversion.addActionListener(actionEvent -> txtScreenInput.setText("(" + txtScreenInput.getText() + ")^(-1)"));
        btnDot.addActionListener(actionEvent -> InputPanelController.inputSimbol(btnDot, txtScreenInput));
        openFunctionRadioBtn.addActionListener(actionEvent -> {
            if (openFunctionRadioBtn.isSelected()) {
                addButton(0, 290, btnSquare);
                addButton(72, 290, btnСube);
                addButton(144, 290, btnExponentiation);
            } else {
                remove(btnSquare);
                remove(btnСube);
                remove(btnExponentiation);
            }
            updateUI();
        });

        btnEqual.addActionListener(actionEvent -> {
            digit = 0;
            result = reader.readLine(txtScreenInput.getText());
            InputPanelController.updateOutputPanel(this.mainFrame, digit, result, reader);
        });
        btnRight.addActionListener(actionEvent -> {
            if (!reader.getStateList().isEmpty()) {
                if (digit != 0) digit--;
                txtScreenInput.setText(reader.getStateList().get(digit));
                InputPanelController.updateOutputPanel(this.mainFrame, digit, result, reader);
            }
        });

        btnLeft.addActionListener(actionEvent -> {
            if (!reader.getStateList().isEmpty()) {
                if (reader.getTreeLists().size() != digit) digit++;
                if (digit == reader.getTreeLists().size()) {
                    txtScreenInput.setText(result);
                } else {
                    txtScreenInput.setText(reader.getStateList().get(digit));
                }
                InputPanelController.updateOutputPanel(this.mainFrame, digit, result, reader);
            }
        });
    }

    private void addButton(int x, int y, JButton button) {
        if (button.getText().equals("1/x") || button.getText().equals("%") || button.getText().equals("sqrt")
                || button.getText().equals("C") || button.getText().equals("x^2") ||
                button.getText().equals("x^3") || button.getText().equals("x^y")) {
            button.setSize(70, 50);
        } else {
            button.setSize(50, 50);
        }
        button.setFocusPainted(false);
        button.setFont(buttonsFont);
        button.setLocation(x, y);
        add(button);
    }

}
