package com.eb.basictrainingprep.model;

public class Ranks {

    String title;
    String abbrivation;
    String payGrade;
    String remarks;

    public Ranks(String title, String abbrivation, String payGrade, String remarks) {
        this.title = title;
        this.abbrivation = abbrivation;
        this.payGrade = payGrade;
        this.remarks = remarks;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbbrivation() {
        return abbrivation;
    }

    public void setAbbrivation(String abbrivation) {
        this.abbrivation = abbrivation;
    }

    public String getPayGrade() {
        return payGrade;
    }

    public void setPayGrade(String payGrade) {
        this.payGrade = payGrade;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
