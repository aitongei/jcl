package com.example.myapplication;

/**
 * @author jcl
 */
// OuterItem.java

import java.util.ArrayList;

public class OuterItem {
    private String outerText;
    private ArrayList<String> innerItems;

    public OuterItem(String outerText) {
        this.outerText = outerText;
    }

    public String getOuterText() {
        return outerText;
    }

    public ArrayList<String> getInnerItems() {
        return innerItems;
    }

    public void setInnerItems(ArrayList<String> innerItems) {
        this.innerItems = innerItems;
    }
}
