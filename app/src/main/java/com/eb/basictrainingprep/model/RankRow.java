package com.eb.basictrainingprep.model;

public class RankRow {


    Ranks[] rankCat;
    String rankCatName;

    public RankRow(Ranks[] rankCat, String rankCatName) {
        this.rankCat = rankCat;
        this.rankCatName = rankCatName;
    }

    public Ranks[] getRankCat() {
        return rankCat;
    }

    public void setRankCat(Ranks[] rankCat) {
        this.rankCat = rankCat;
    }

    public String getRankCatName() {
        return rankCatName;
    }

    public void setRankCatName(String rankCatName) {
        this.rankCatName = rankCatName;
    }
}
