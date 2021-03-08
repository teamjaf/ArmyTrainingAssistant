package com.eb.basictrainingprep.model;

public class Model_VideosButtons {
    String btnName;
    String links;

    public Model_VideosButtons(String btnName, String links) {
        this.btnName = btnName;
        this.links = links;
    }

    public Model_VideosButtons() {

    }

    public String getBtnName() {
        return btnName;
    }

    public void setBtnName(String btnName) {
        this.btnName = btnName;
    }

    public String getLinks() {
        return links;
    }

    public void setLinks(String links) {
        this.links = links;
    }
}
