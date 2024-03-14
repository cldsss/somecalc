package com.calc.controller;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.List;

public class TreeController {
    public static JTree generateTree(List<List<String>> treeLists, int digit) {
        if (treeLists == null) return null;
        if (digit == treeLists.size()) {
            DefaultMutableTreeNode headRoot;
            if (treeLists.get(digit - 1).size() == 3) {
                headRoot = new DefaultMutableTreeNode(treeLists.get(digit - 1).get(2), false);
            } else {
                headRoot = new DefaultMutableTreeNode(treeLists.get(digit - 1).get(3), false);
            }
            DefaultTreeModel treeModel1 = new DefaultTreeModel(headRoot, true);
            JTree calculatorTree = new JTree(treeModel1);
            return calculatorTree;
        }
        DefaultMutableTreeNode currentRoot = new DefaultMutableTreeNode(treeLists.get(treeLists.size() - 1).get(1));
        DefaultMutableTreeNode headRoot = currentRoot;

        for (int i = treeLists.size() - 1; i > digit; i--) {
            DefaultMutableTreeNode sign = new DefaultMutableTreeNode(treeLists.get(i - 1).get(1));
            currentRoot.add(sign);
            if (treeLists.get(i).size() == 3) {
                currentRoot.add(new DefaultMutableTreeNode(treeLists.get(i).get(0), false));
            } else currentRoot.add(new DefaultMutableTreeNode(treeLists.get(i).get(2), false));
            currentRoot = sign;
        }
        currentRoot.add(new DefaultMutableTreeNode(treeLists.get(digit).get(0), false));
        if (treeLists.get(digit).size() == 4)
            currentRoot.add(new DefaultMutableTreeNode(treeLists.get(digit).get(2), false));

        DefaultTreeModel treeModel1 = new DefaultTreeModel(headRoot, true);
        JTree calculatorTree = new JTree(treeModel1);
        if (calculatorTree != null) {
            for (int i = 0; i < calculatorTree.getRowCount(); i++) {
                calculatorTree.expandRow(i);
            }
        }
        return calculatorTree;
    }
}

