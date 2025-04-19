package com.example.compitrackr;

import java.util.Date;

public class ContestLog {

    private String platform;
    private String contestName;
    private Date date;
    private int rank;
    private int oldRating;
    private int newRating;
    private int problemsSolvedCount;

    // Constructor
    public ContestLog(String platform, String contestName, Date date, int rank, int oldRating, int newRating, int problemsSolvedCount) {
        this.platform = platform;
        this.contestName = contestName;
        this.date = date;
        this.rank = rank;
        this.oldRating = oldRating;
        this.newRating = newRating;
        this.problemsSolvedCount = problemsSolvedCount;
    }

    // Getters and Setters
    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getContestName() {
        return contestName;
    }

    public void setContestName(String contestName) {
        this.contestName = contestName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getOldRating() {
        return oldRating;
    }

    public void setOldRating(int oldRating) {
        this.oldRating = oldRating;
    }

    public int getNewRating() {
        return newRating;
    }

    public void setNewRating(int newRating) {
        this.newRating = newRating;
    }

    public int getProblemsSolvedCount() {
        return problemsSolvedCount;
    }

    public void setProblemsSolvedCount(int problemsSolvedCount) {
        this.problemsSolvedCount = problemsSolvedCount;
    }

    @Override
    public String toString() {
        return "ContestLog{" +
                "platform='" + platform + '\'' +
                ", contestName='" + contestName + '\'' +
                ", date=" + date +
                ", rank=" + rank +
                ", oldRating=" + oldRating +
                ", newRating=" + newRating +
                ", problemsSolvedCount=" + problemsSolvedCount +
                '}';
    }
}


