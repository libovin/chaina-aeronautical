package com.hiekn.china.aeronautical.service.impl;

import com.hiekn.boot.autoconfigure.base.exception.RestException;
import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.china.aeronautical.exception.ErrorCodes;
import com.hiekn.china.aeronautical.model.bean.Conference;
import com.hiekn.china.aeronautical.model.bean.Dataset;
import com.hiekn.china.aeronautical.model.bean.Institution;
import com.hiekn.china.aeronautical.model.bean.Periodical;
import com.hiekn.china.aeronautical.model.bean.Publisher;
import com.hiekn.china.aeronautical.model.bean.Website;
import com.hiekn.china.aeronautical.model.vo.DatasetFile;
import com.hiekn.china.aeronautical.model.vo.DatasetQuery;
import com.hiekn.china.aeronautical.repository.DatasetRepository;
import com.hiekn.china.aeronautical.service.DatasetService;
import com.hiekn.china.aeronautical.service.ImportAsyncService;
import com.hiekn.china.aeronautical.util.DataBeanUtils;
import com.hiekn.china.aeronautical.util.QueryUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service("datasetService")
public class DatasetServiceImpl implements DatasetService {

    @Autowired
    private DatasetRepository datasetRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ImportAsyncService importAsyncService;

    @Override
    public RestData<Dataset> findAll(DatasetQuery bean) {
        Dataset targe = new Dataset();
        Map<String, Object> map = QueryUtils.trastation(bean, targe);
        Example<Dataset> example = Example.of((Dataset) map.get("bean"));
        List<Dataset> datasetList = datasetRepository.findAll(example);
        for (Dataset dataset : datasetList) {
            long count = mongoTemplate.count(new Query(), dataset.getTypeKey());
            dataset.setCount(count);
        }
        return new RestData<>(datasetList, datasetRepository.count(example));
    }

    @Override
    public Dataset findOne(String id) {
        return datasetRepository.findOne(id);
    }

    @Override
    public void delete(String id) {
        Dataset dataset = datasetRepository.findOne(id);
        String collectionName = dataset.getTypeKey();
        mongoTemplate.dropCollection(collectionName);
        datasetRepository.delete(id);
    }

    @Override
    public Dataset modify(String id, Dataset dataset) {
        Dataset targe = datasetRepository.findOne(id);
        String[] stringArr = DataBeanUtils.getNullProperty(dataset);
        BeanUtils.copyProperties(dataset, targe, stringArr);
        return datasetRepository.save(targe);
    }

    public Dataset findFirstByTypeKey(String typeKey) {
        return datasetRepository.findFirstByTypeKey(typeKey);
    }

    @Override
    public Dataset add(DatasetFile datasetFile) {
        Dataset dataset = new Dataset();
        dataset.setTable(datasetFile.getTable());
        dataset.setKey(datasetFile.getKey());
        dataset.setTypeKey(datasetFile.getTable() + "_" + datasetFile.getKey());
        dataset.setName(datasetFile.getName());
        if (datasetRepository.existsDatasetByTypeKey(dataset.getTypeKey())) {
            throw RestException.newInstance(ErrorCodes.NAME_EXIST_ERROR);
        }
        Dataset dataset1 = datasetRepository.save(dataset);
        mongoTemplate.createCollection(dataset1.getTypeKey());
        if (datasetFile.getFileInfo() != null) {
            File file = new File("temp" + System.currentTimeMillis());
            try {
                FileUtils.copyInputStreamToFile(datasetFile.getFileIn(), file);
                String suffix = datasetFile.getFileInfo().getFileName();
                if (suffix.endsWith(".xlsx")) {
                    importAsyncService.importExcelToDataset(getTableClass(dataset.getTable()), file, dataset.getTypeKey());
                } else if (suffix.endsWith(".xml")) {
                    importAsyncService.importXmlToDataset(getTableClass(dataset.getTable()), file, dataset.getTypeKey());
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return dataset1;
    }

    private Class getTableClass(String table) {
        if (Objects.equals("conference", table)) {
            return Conference.class;
        } else if (Objects.equals("institution", table)) {
            return Institution.class;
        } else if (Objects.equals("periodical", table)) {
            return Periodical.class;
        } else if (Objects.equals("publisher", table)) {
            return Publisher.class;
        } else if (Objects.equals("website", table)) {
            return Website.class;
        }
        return null;
    }


}
