package com.example.onboarding.Pojo;

import java.io.Serializable;
import java.util.List;

public class Profile implements Serializable {
    String id, studentID, rewards, name, notification, studentImage, pastWorkshop, step;
    List<Task> pastTask;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getRewards() {
        return rewards;
    }

    public void setRewards(String rewards) {
        this.rewards = rewards;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getStudentImage() {
        return studentImage;
    }

    public void setStudentImage(String studentImage) {
        this.studentImage = studentImage;
    }

    public List<Task> getPastTask() {
        return pastTask;
    }

    public void setPastTask(List<Task> pastTask) {
        this.pastTask = pastTask;
    }

    public String getPastWorkshop() {
        return pastWorkshop;
    }

    public void setPastWorkshop(String pastWorkshop) {
        this.pastWorkshop = pastWorkshop;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id='" + id + '\'' +
                ", studentID='" + studentID + '\'' +
                ", rewards='" + rewards + '\'' +
                ", name='" + name + '\'' +
                ", notification='" + notification + '\'' +
                ", studentImage='" + studentImage + '\'' +
                ", pastWorkshop='" + pastWorkshop + '\'' +
                ", step='" + step + '\'' +
                ", pastTask=" + pastTask +
                '}';
    }
}
