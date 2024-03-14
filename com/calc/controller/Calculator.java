package com.calc.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Calculator {
    private List<List<String>> treeLists;
    private List<String> stateList;
    private String currentExpression;
    StringBuilder mainString;
    private StringBuilder cstring = new StringBuilder();//for user string storage
    private boolean plusSign = true;//saving result sign
    private int closeBracket, openBracket;//saving start and finish substring positions
    private int finishPos = 0, startPos = 0;//saving start and finish expression part positions
    private boolean basicBracket = false;

    public List<List<String>> getTreeLists() {
        return treeLists;
    }

    public List<String> getStateList() {
        return stateList;
    }

    public String readLine(String line) {
        if (line != "") {
            treeLists = new ArrayList();
            stateList = new ArrayList<>();
            stateList.add(line);
            calculate(line);
            if (plusSign) {
                System.out.println("Result: " + cstring);
            } else {
                cstring.insert(0, '-');
                System.out.println("Result: " + cstring);
            }
            plusSign = true;
            if (treeLists.get(treeLists.size() - 1).size() == 3) {
                return treeLists.get(treeLists.size() - 1).get(2);
            } else return treeLists.get(treeLists.size() - 1).get(3);

        }
        return "";
    }

    private int valueCalc(String input) {
        int slots = 0;
        for (int i = 0; i < input.length(); i++) {
            if ((input.charAt(i) == '+') || (input.charAt(i) == '-') ||
                    (input.charAt(i) == '*') || (input.charAt(i) == '/') ||
                    (input.charAt(i) == ')') || (input.charAt(i) == '(') || (input.charAt(i) == '%')) {
                slots++;
            }
        }
        return slots;
    }

    private void calculate(String input) {
        int[] charsPos = new int[valueCalc(input) + 2];
        float a = 0, b = 0;         //for parse parts
        Float c = null;             //result between parts
        cstring.delete(0, cstring.length());        //cleaning
        cstring.append(input);

        for (int i = 0; i < cstring.length(); i++) {
            if (cstring.charAt(i) == '(') {
                openBracket = i;
            }
            if (cstring.charAt(i) == 's') {
                basicBracket = false;
                calculateSubString(i, 3, 4, 1);
                sqrtFunction(cstring);
                updateLine(mainString);
            } else if (cstring.charAt(i) == '^') {
                basicBracket = false;

                String subStrDegree = "";
                int brackedCounter = 0;
                for (int j = i + 1; j < cstring.length(); j++) {
                    if (cstring.charAt(j) == '(') {
                        brackedCounter++;
                    }
                    if (cstring.charAt(j) == ')') {
                        brackedCounter--;
                    }
                    if (brackedCounter == 0) {
                        String currentSubString = cstring.substring(i + 2, j);

                        cstring.delete(i + 1, j + 1);
                        StringBuilder mainString = new StringBuilder(cstring);
                        calculate(currentSubString);
                        mainString.insert(i + 1, cstring.toString());
                        cstring.delete(0, cstring.length());
                        cstring.insert(0, mainString.toString());
                        try {
                            Float.parseFloat(currentSubString);
                        } catch (Exception e) {
                            stateList.add(cstring.toString());
                        }
                        subStrDegree += cstring.charAt(i + 1);
                        for (int k = i + 2; k < cstring.length(); k++) {
                            if ((cstring.charAt(k) == '+') || (cstring.charAt(k) == '-') ||
                                    (cstring.charAt(k) == '*') || (cstring.charAt(k) == '/') ||
                                    (cstring.charAt(k) == '(') || (cstring.charAt(k) == ')') || (cstring.charAt(k) == '%')) {
                                break;
                            }
                            subStrDegree += cstring.charAt(k);
                        }
                        break;
                    }

                }
                calculateSubString(i, 0, 1, subStrDegree.length());

                if (!plusSign && subStrDegree.charAt(0) != '-') {
                    subStrDegree = "-" + subStrDegree;
                }
                exponentiation(cstring, subStrDegree);
                updateLine(mainString);
            } else if (cstring.charAt(i) == ')' && (i == cstring.length() - 1 || (cstring.charAt(i + 1) != 's' && cstring.charAt(i + 1) != '^')))//for locating max priority
            {
                basicBracket = true;
                calculateSubString(i, 0, 0, 1);
                updateLine(mainString);
            }
        }

        for (int i = 0; i < cstring.length(); i++) {
            if (cstring.charAt(i) == '*') {
                mulDivPer(i, charsPos, '*');
                break;
            }
            if (cstring.charAt(i) == '/') {
                mulDivPer(i, charsPos, '/');
                break;
            }
            if (cstring.charAt(i) == '%') {
                mulDivPer(i, charsPos, '%');
                break;
            }

        }
        for (int i = 0; i < cstring.length(); i++) {
            if ((cstring.charAt(i) == '*') || (cstring.charAt(i) == '/') || (cstring.charAt(i) == '%')) {
                calculate(cstring.toString());
            }
        }

        for (int i = 0; i < cstring.length(); i++) {
            if ((cstring.charAt(i) == '+') && (plusSign)) {
                findExpression(charsPos, i);
                a = Float.parseFloat(cstring.substring(startPos, i));
                b = Float.parseFloat(cstring.substring(i + 1, finishPos));
                currentExpression = cstring.substring(startPos, finishPos);
                cstring.delete(startPos, finishPos);
                c = a + b;
                treeLists.add(newList(a, "+", b, c));
                cstring.insert(startPos, c.toString());
                checkExpression(cstring.toString(), currentExpression, c.toString());
                break;
            }
            if ((cstring.charAt(i) == '+') && (!plusSign)) {
                findExpression(charsPos, i);
                a = Float.parseFloat(cstring.substring(0, i));
                b = Float.parseFloat(cstring.substring(i + 1, finishPos));
                currentExpression = cstring.substring(startPos, finishPos);
                cstring.delete(0, finishPos);
                if (b > a) {
                    c = b - a;
                    treeLists.add(newList(a * -1, "+", b, c));
                    plusSign = true;
                } else {
                    c = a - b;
                    treeLists.add(newList(a * -1, "+", b, c * -1));
                }
                cstring.insert(0, c.toString());
                checkExpression(cstring.toString(), currentExpression, c.toString());
                break;
            }
            if ((cstring.charAt(i) == '-') && (plusSign)) {
                if (i == 0) {
                    break;
                }
                findExpression(charsPos, i);
                b = Float.parseFloat(cstring.substring(i + 1, finishPos));
                a = Float.parseFloat(cstring.substring(0, i));
                currentExpression = cstring.substring(startPos, finishPos);
                cstring.delete(0, finishPos);
                if (b > a) {
                    c = b - a;
                    treeLists.add(newList(a, "-", b, c * -1));
                    plusSign = false;
                } else {
                    c = a - b;
                    treeLists.add(newList(a, "-", b, c));
                }
                cstring.insert(0, c.toString());
                checkExpression(cstring.toString(), currentExpression, c.toString());
                break;
            }
            if ((cstring.charAt(i) == '-') && (!plusSign)) {
                if (i == 0) {
                    break;
                }
                findExpression(charsPos, i);
                a = Float.parseFloat(cstring.substring(0, i));
                b = Float.parseFloat(cstring.substring(i + 1, finishPos));
                cstring.delete(0, finishPos);
                c = a + b;
                treeLists.add(newList(a * -1, "-", b, c * -1));
                cstring.insert(0, c.toString());
                checkExpression(cstring.toString(), currentExpression, c.toString());
                break;
            }
        }
        for (int i = 0; i < cstring.length(); i++) {
            if ((cstring.charAt(i) == '+') || ((cstring.charAt(i) == '-') && (i != 0))) {
                calculate(cstring.toString());
            }
        }
    }

    private StringBuilder exponentiation(StringBuilder number, String degree) {
        double num = Double.parseDouble(number.toString());
        double deg = Double.parseDouble(degree);
        number.delete(0, number.length());
        number.insert(0, Math.pow(num, deg));
        treeLists.add(newList(num, "^" + deg, Double.parseDouble(number.toString())));
        return number;
    }

    private StringBuilder sqrtFunction(StringBuilder number) {
        float a = Float.parseFloat(number.toString());
        number.delete(0, number.length());
        number.insert(0, Math.sqrt(a));
        treeLists.add(newList(a, "sqrt", Float.parseFloat(number.toString())));
        return number;
    }

    private List<String> newList(float a, String sign, float b, float c) {
        List<String> currentList = Arrays.asList(String.valueOf(a), sign, String.valueOf(b), String.valueOf(c));
        System.out.println(currentList);
        return currentList;
    }

    private List<String> newList(double a, String sign, double c) {
        List<String> currentList = Arrays.asList(String.valueOf(a), sign, String.valueOf(c));
        System.out.println(currentList);
        return currentList;
    }

    private List<String> newList(float a, String sign, float c) {
        List<String> currentList = Arrays.asList(String.valueOf(a), sign, String.valueOf(c));
        System.out.println(currentList);
        return currentList;
    }

    private void checkExpression(String line, String currentExpression, String newLine) {
        if (basicBracket) {
            try {
                Float.parseFloat(line);
            } catch (Exception e) {
                stateList.add(stateList.get(stateList.size() - 1).replace(currentExpression, newLine));
                //stateList.add(line);
            }
        } else stateList.add(stateList.get(stateList.size() - 1).replace(currentExpression, newLine));
    }

    private void updateLine(StringBuilder mainString) {
        mainString.insert(openBracket, cstring.toString());
        cstring.delete(0, cstring.length());
        cstring.insert(0, mainString.toString());
        stateList.add(cstring.toString());
        calculate(cstring.toString());
    }

    private void mulDivPer(int i, int[] charsPos, char sign) {
        float a = 0, b = 0;
        Float c = null;
        charsPos[0] = 0;
        for (int j = 0, k = 1; j < cstring.length() - 1; j++) {
            if ((cstring.charAt(j) == '+') || (cstring.charAt(j) == '-') ||
                    (cstring.charAt(j) == '*') || (cstring.charAt(j) == '/') || (cstring.charAt(j) == '%') ||
                    (cstring.charAt(j) == '(') || (cstring.charAt(j) == ')')) {
                charsPos[k] = j + 1;
                k++;
            }
            charsPos[charsPos.length - 1] = cstring.length() + 1;
        }
        for (int k = 0; k < charsPos.length; k++) {

            if (charsPos[k] == i + 1) {
                startPos = charsPos[k - 1];
                finishPos = charsPos[k + 1] - 1;
            }
        }

        a = Float.parseFloat(cstring.substring(startPos, i));
        b = Float.parseFloat(cstring.substring(i + 1, finishPos));
        currentExpression = cstring.substring(startPos, finishPos);
        cstring.delete(startPos, finishPos);
        if (sign == '*') {
            c = a * b;
            treeLists.add(newList(a, "*", b, c));
        } else if (sign == '/') {
            c = a / b;
            treeLists.add(newList(a, "/", b, c));
        } else if (sign == '%') {
            c = a / 100 * b;
            treeLists.add(newList(a, "%", b, c));
        }
        cstring.insert(startPos, c.toString());
        checkExpression(cstring.toString(), currentExpression, c.toString());
        calculate(cstring.toString());
    }

    private void findExpression(int[] charsPos, int i) {
        charsPos[0] = 0;
        for (int j = 0, k = 1; j < cstring.length() - 1; j++) {
            if ((cstring.charAt(j) == '+') || (cstring.charAt(j) == '-') ||
                    (cstring.charAt(j) == '*') || (cstring.charAt(j) == '/') ||
                    (cstring.charAt(j) == '(') || (cstring.charAt(j) == ')') || (cstring.charAt(j) == '%')) {
                charsPos[k] = j + 1;
                k++;
            }
            charsPos[charsPos.length - 1] = cstring.length() + 1;
        }
        for (int k = 0; k < charsPos.length; k++) {

            if (charsPos[k] == i + 1) {
                startPos = charsPos[k - 1];
                finishPos = charsPos[k + 1] - 1;
            }
        }
    }

    private void calculateSubString(int i, int firstNumber, int secondNumber, int thirdNumber) {
        closeBracket = i + firstNumber;
        String subString = "";
        subString = cstring.substring(openBracket + 1, closeBracket - secondNumber);
        cstring.delete(openBracket, closeBracket + thirdNumber);
        mainString = new StringBuilder(cstring);
        calculate(subString);
    }

    public void clearLists() {
        treeLists.clear();
        stateList.clear();
    }
}