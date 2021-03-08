package com.eb.basictrainingprep.model;

public class ModelWarriorEthos {
    String btnName;
    int ImageId;
    String imgLink;

    public ModelWarriorEthos(String btnName, int imageId) {
        this.btnName = btnName;
        ImageId = imageId;
    }

    public ModelWarriorEthos() {

    }

    public String getBtnName() {
        return btnName;
    }

    public void setBtnName(String btnName) {
        this.btnName = btnName;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }
}
