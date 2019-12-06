package com.example.onboarding.Pojo;

public class Info {
    String infoId,infoName,infoDetail,infoLink;

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    public String getInfoName() {
        return infoName;
    }

    public void setInfoName(String infoName) {
        this.infoName = infoName;
    }

    public String getInfoDetail() {
        return infoDetail;
    }

    public void setInfoDetail(String infoDetail) {
        this.infoDetail = infoDetail;
    }

    public String getInfoLink() {
        return infoLink;
    }

    public void setInfoLink(String infoLink) {
        this.infoLink = infoLink;
    }

    @Override
    public String toString() {
        return "Info{" +
                "infoId='" + infoId + '\'' +
                ", infoName='" + infoName + '\'' +
                ", infoDetail='" + infoDetail + '\'' +
                ", infoLink='" + infoLink + '\'' +
                '}';
    }
}
