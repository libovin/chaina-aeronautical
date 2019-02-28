package com.hiekn.china.aeronautical.knowledge;

import com.hiekn.china.aeronautical.model.bean.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherKgService {

    private final static String schema = "publisher";

    @Autowired
    private KgBaseService kgBaseService;

    public Publisher findOne(String kgName, Long id) {
        return kgBaseService.findOne(kgName, schema, id, Publisher.class);
    }

    public List<Publisher> find(String kgName, Pageable pageable) {
        return kgBaseService.find(kgName, schema, pageable, Publisher.class);
    }

    public Long count(String kgName) {
        return kgBaseService.count(kgName, schema);
    }

    public void delete(String kgName, Long id) {
        kgBaseService.delete(kgName, schema, id);
    }

    public Publisher insert(String kgName, Publisher publisher) {
       return kgBaseService.insert(kgName, schema, publisher);
    }

    public Publisher modify(String kgName, Long id, Publisher publisher) {
       return kgBaseService.modify(kgName, schema, id, publisher);
    }
}
