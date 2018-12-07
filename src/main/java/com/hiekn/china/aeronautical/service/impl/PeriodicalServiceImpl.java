package com.hiekn.china.aeronautical.service.impl;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.china.aeronautical.model.bean.Periodical;
import com.hiekn.china.aeronautical.model.vo.FileImport;
import com.hiekn.china.aeronautical.model.vo.PeriodicalQuery;
import com.hiekn.china.aeronautical.model.vo.WordStatQuery;
import com.hiekn.china.aeronautical.repository.PeriodicalRepository;
import com.hiekn.china.aeronautical.service.ImportAsyncService;
import com.hiekn.china.aeronautical.service.PeriodicalService;
import com.hiekn.china.aeronautical.util.DataBeanUtils;
import com.hiekn.china.aeronautical.util.ExportUtils;
import com.hiekn.china.aeronautical.util.QueryUtils;
import com.mongodb.WriteResult;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.CloseableIterator;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("periodicalService")
public class PeriodicalServiceImpl implements PeriodicalService {


    @Autowired
    private PeriodicalRepository periodicalRepository;

    @Autowired
    private ImportAsyncService importAsyncService;

    public RestData<Periodical> findAll(PeriodicalQuery bean, String collectionName) {
        Pageable pageable;
        Periodical targe = new Periodical();
        Map<String, Object> map = QueryUtils.trastation(bean, targe);
        List<Sort.Order> orders = (List<Sort.Order>) map.get("sort");
        if (orders.size() > 0) {
            pageable = new PageRequest(bean.getPageNo() - 1, bean.getPageSize(), new Sort(orders));
        } else {
            pageable = new PageRequest(bean.getPageNo() - 1, bean.getPageSize());
        }
        Example<Periodical> example = Example.of((Periodical) map.get("bean"));
        Page<Periodical> p = periodicalRepository.findAll(example, pageable, collectionName);
        return new RestData<>(p.getContent(), p.getTotalElements());
    }

    public Periodical findOne(String id, String collectionName) {
        return periodicalRepository.findOne(id, collectionName);
    }

    public WriteResult delete(String id, String collectionName) {
        return periodicalRepository.delete(id, collectionName);
    }

    public Periodical modify(String id, Periodical periodical, String collectionName) {
        Periodical targe = periodicalRepository.findOne(id, collectionName);
        String[] stringArr = DataBeanUtils.getNullProperty(periodical);
        BeanUtils.copyProperties(periodical, targe, stringArr);
        return periodicalRepository.save(targe, collectionName);
    }

    public Periodical add(Periodical periodical, String collectionName) {
        return periodicalRepository.insert(periodical, collectionName);
    }

    public RestData<Periodical> wordStatistics(WordStatQuery wordStatQuery, String collectionName) {
        return periodicalRepository.wordStatistics(wordStatQuery, collectionName);
    }

    public Map<String, Object> importData(FileImport fileImport, String collectionName) {
        Map<String, Object> map = new HashMap<>(2);
        if (fileImport.getFileInfo() != null) {
            File file = new File("temp" + System.currentTimeMillis());
            try {
                FileUtils.copyInputStreamToFile(fileImport.getFileIn(), file);
                map.put("msg", "上传成功");
                map.put("code", "success");
                importAsyncService.importExcelToDataset(Periodical.class, file, collectionName);
            } catch (IOException e) {
                map.put("msg", "上传失败，无法上传文件");
                map.put("code", "fail");
            }
        } else {
            map.put("msg", "上传失败，文件不能为空");
            map.put("code", "fail");
        }
        return map;
    }

    public List<Map<String, Object>> checkStat(String key) {

        return null;
    }

    public void exportData(String collectionName, OutputStream output) {
        try {
            Workbook wb = new SXSSFWorkbook(100);
            CloseableIterator<Periodical> c = periodicalRepository.findAllByStream(collectionName);
            int index = 1;
            Sheet sheet = wb.createSheet();
            ExportUtils.addOneRow(sheet.createRow(0), DataBeanUtils.getFieldList(Periodical.class));
            while (c.hasNext()) {
                Periodical item = c.next();
                ExportUtils.addOneRow(sheet.createRow(index), DataBeanUtils.getFieldValueList(item));
                index++;
            }
            wb.write(output);
            c.close();
            output.close();
            wb.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
