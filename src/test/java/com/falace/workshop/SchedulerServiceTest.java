package com.falace.workshop;

import com.falace.workshop.model.Talk;
import com.falace.workshop.model.Track;
import com.falace.workshop.services.SchedulerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.List;

import static com.falace.workshop.services.SchedulerService.MEET_YOUR_COLLEAGUES;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class SchedulerServiceTest {

    @Autowired
    private SchedulerService service;

    @Test
    public void testScheduler() {
        List<Talk> talks = TalksFixtureProvider.createTalks();

        List<Track> schedule = service.schedule(talks);

        assertThat(schedule, hasSize(2));

        List<Talk> firstTrack = schedule.get(0).getTalks();
        assertThat(firstTrack, hasSize(13));
        assertThat(firstTrack.get(12).getName(), equalTo(MEET_YOUR_COLLEAGUES));

        List<Talk> secondTrack = schedule.get(1).getTalks();
        assertThat(secondTrack, hasSize(7));
        assertThat(secondTrack.get(6).getName(), equalTo(MEET_YOUR_COLLEAGUES));
    }

    @Configuration
    static class ContextConfiguration {
        @Bean
        public SchedulerService schedulerService() {
            return new SchedulerService();
        }
    }


}


