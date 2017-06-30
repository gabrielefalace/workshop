package com.falace.workshop.controllers;


import com.falace.workshop.model.Talk;
import com.falace.workshop.model.Track;
import com.falace.workshop.services.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

@Controller
public class SchedulerController {


    private static final int LIGHTNING_DURATION = 5;

    private SchedulerService schedulerService;

    @Autowired
    public SchedulerController(SchedulerService service) {
        this.schedulerService = service;
    }


    @PostMapping("/schedule")
    public String scheduleWorkshop(@RequestParam MultipartFile talksFile, Model m) {
        try {
            List<Talk> talks = extractWorkshopTalks(talksFile);
            List<Track> solution = schedulerService.schedule(talks);
            m.addAttribute("solution", solution);
            return "solution";
        } catch (IOException ioe) {
            return "error";
        }
    }

    //FIXME too much logic in private methods. cannot be tested in isolation.

    private List<Talk> extractWorkshopTalks(MultipartFile talksFile) throws IOException {
        String completeData = new String(talksFile.getBytes());
        List<Talk> result = new ArrayList<>();
        asList(completeData.split("\\r?\\n")).forEach((fileRow -> {
            String talkTitle = fileRow.substring(0, fileRow.lastIndexOf(" "));
            int talkDuration = extractDuration(fileRow);
            result.add(new Talk(talkTitle, talkDuration));
        }));
        return result;
    }

    private int extractDuration(String fileRow) {
        String lastWord = fileRow.substring(fileRow.lastIndexOf(" ") + 1);
        int duration = LIGHTNING_DURATION;
        if (!"lightning".equals(lastWord)) {
            duration = Integer.parseInt(lastWord.substring(0, lastWord.indexOf("min")));
        }
        return duration;
    }

}
