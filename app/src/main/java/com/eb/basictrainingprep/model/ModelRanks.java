package com.eb.basictrainingprep.model;

public class ModelRanks {

    String ranksTitle;
    String videoLink;

    public ModelRanks(String ranksTitle, String videoLink) {
        this.ranksTitle = ranksTitle;
        this.videoLink = videoLink;
    }

    public String getRanksTitle() {
        return ranksTitle;
    }

    public void setRanksTitle(String ranksTitle) {
        this.ranksTitle = ranksTitle;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }
}
