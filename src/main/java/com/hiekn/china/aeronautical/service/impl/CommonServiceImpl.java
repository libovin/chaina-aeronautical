package com.hiekn.china.aeronautical.service.impl;

import com.hiekn.china.aeronautical.repository.ConferenceRepository;
import com.hiekn.china.aeronautical.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("chinaAirService")
public class CommonServiceImpl implements CommonService {

    @Autowired
    private ConferenceRepository conferenceRepository;

}
