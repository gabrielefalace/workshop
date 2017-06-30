package com.falace.workshop.services;

import com.falace.workshop.model.Talk;
import com.falace.workshop.model.Track;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class SchedulerService {

    private static final LocalTime END_OF_WORKSHOP = LocalTime.of(17, 0);

    private static final LocalTime TWELVE = LocalTime.of(12, 0);

    private static final LocalTime ONE_PM = LocalTime.of(13, 0);

    private static final LocalTime NINE_AM = LocalTime.of(9, 0);

    public static final String MEET_YOUR_COLLEAGUES = "MEET YOUR COLLEAGUES";

    private static final LocalTime FOUR = LocalTime.of(16, 0);

    public List<Track> schedule(List<Talk> talks) {
        talks.sort(Comparator.comparing(Talk::getDuration));
        List<Track> workshop = new ArrayList<>();
        Track currentTrack = new Track();
        LocalTime nextFreeStartTime = NINE_AM;
        while (!talks.isEmpty()) {
            Optional<Talk> selectedTalk = findAssignableTalk(currentTrack, nextFreeStartTime, talks);
            if (selectedTalk.isPresent()) {
                LocalTime endTime = nextFreeStartTime.plusMinutes(selectedTalk.get().getDuration());
                LocalTime talkStartTime = endsDuringLunchBreak(endTime)? ONE_PM : nextFreeStartTime;
                selectedTalk.get().setStartTime(talkStartTime);
                nextFreeStartTime = talkStartTime.plusMinutes(selectedTalk.get().getDuration());
                currentTrack.add(selectedTalk.get());
            } else {
                closeTrackWithMeetColleaguesEvent(currentTrack, nextFreeStartTime);
                workshop.add(currentTrack);
                nextFreeStartTime = NINE_AM;
                currentTrack = new Track();
            }
        }
        return workshop;
    }

    private void closeTrackWithMeetColleaguesEvent(Track currentTrack, LocalTime currentTime) {
        Talk meetYourColleagues = new Talk(MEET_YOUR_COLLEAGUES, -1);
        meetYourColleagues.setStartTime(currentTime.isBefore(FOUR)? FOUR : currentTime);
        currentTrack.add(meetYourColleagues);
    }

    private Optional<Talk> findAssignableTalk(Track track, LocalTime currentTime, List<Talk> talks) {
        Optional<Talk> foundTalk = Optional.empty();
        if (currentTime.isBefore(TWELVE)) {
            foundTalk = findTalkEndingBefore(currentTime, talks, TWELVE);
        }
        if (!foundTalk.isPresent()) {
            foundTalk = findTalkEndingBefore(currentTime, talks, END_OF_WORKSHOP);
        }
        if (foundTalk.isPresent()) {
            talks.remove(foundTalk.get());
        }
        return foundTalk;
    }

    private Optional<Talk> findTalkEndingBefore(LocalTime startTime, List<Talk> talks, LocalTime limit) {
        return talks.stream().filter(t -> startTime.plusMinutes(t.getDuration()).isBefore(limit)).findFirst();
    }

    private boolean endsDuringLunchBreak(LocalTime endTime){
        return endTime.isAfter(TWELVE) && endTime.isBefore(ONE_PM);
    }

}
