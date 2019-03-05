package com.hiekn.china.aeronautical;

import com.hiekn.china.aeronautical.knowledge.ConferenceKgService;
import com.hiekn.china.aeronautical.model.bean.Conference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

    @Autowired
    private ConferenceKgService conferenceKgService;

    private static String KG_DB_NAME = "abcd_1234_7349l1owxwqjqw3a";

    @Test
    public void insert() {
        Conference conference = new Conference();
        conference.setName("会议");
        conference.setSecondaryName("zzzzz会议");
        conference.setShortName("ShortName会议");
        conference.setSeriesName("会议SeriesName");
        conference.setMeetingCycle("MeetingCycle会议");
        conference.setMeetingSession("MeetingSession会议");
        conference.setMeetingTheme("MeetingTheme会议");
        conference.setOrganizer("Organizer会议");
        conference.setCoOrganizer("CoOrganizer会议");
        conference.setVenue("Venue会议");
        conference.setSpecialClassified("SpecialClassified会议");
        conferenceKgService.insert(KG_DB_NAME, conference);
    }

    @Test
    public void delete() {
        conferenceKgService.delete(KG_DB_NAME, 15l);
    }

    @Test
    public void update() {
        Conference conference = new Conference();
        conference.setName("会议");
        conference.setSecondaryName("zzzzz");
        conference.setShortName("ShortName");
        conference.setSeriesName("SeriesName");
        conference.setMeetingCycle("MeetingCycle");
        conference.setMeetingSession("MeetingSession");
        conference.setMeetingTheme("MeetingTheme");
        conference.setOrganizer("Organizer");
        conference.setCoOrganizer("CoOrganizer");
        conference.setVenue("Venue");
        conference.setSpecialClassified("SpecialClassified");
        conferenceKgService.modify(KG_DB_NAME, 15l, conference);
    }

    @Test
    public void s() {
        System.out.println(conferenceKgService.findAll(KG_DB_NAME));
    }
}
