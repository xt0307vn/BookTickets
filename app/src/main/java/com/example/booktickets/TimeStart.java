package com.example.booktickets;

public class TimeStart {
    String time, room;

    public TimeStart(String time) {
        this.time = time;
    }

    public TimeStart(String time, String room) {
        this.time = time;
        this.room = room;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
