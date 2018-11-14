package com.hiekn.china.aeronautical.service;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.china.aeronautical.model.bean.Periodical;
import com.hiekn.china.aeronautical.model.vo.PeriodicalQuery;
import com.mongodb.WriteResult;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface PeriodicalService {
    RestData<Periodical> findAll(PeriodicalQuery periodicalQuery, String collectionName);

    Periodical findOne(String id,String collectionName);

    WriteResult delete(String id, String collectionName);

    Periodical modify(String id, Periodical periodical,String collectionName);

    Periodical add(Periodical periodical,String collectionName);

    void wordStatistics(String collectionName);

    Map<String, Object> importData(FormDataContentDisposition fileInfo, InputStream fileIn, FormDataBodyPart formDataBodyPart);

    List<Map<String, Object>> checkStat(String key);
}
