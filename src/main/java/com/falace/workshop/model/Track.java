package com.falace.workshop.model;

import java.util.ArrayList;
import java.util.List;

public class Track {

    private List<Talk> talks = new ArrayList<>();

    public void add(Talk talk){
        talks.add(talk);
    }

    public void remove(Talk talk){
        talks.remove(talk);
    }

    public List<Talk> getTalks() {
        return talks;
    }

    public void setTalks(List<Talk> talks) {
        this.talks = talks;
    }
}
