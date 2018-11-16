package com.hiekn.china.aeronautical.rest;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.boot.autoconfigure.base.model.result.RestResp;
import com.hiekn.china.aeronautical.model.bean.Dataset;
import com.hiekn.china.aeronautical.model.vo.DatasetQuery;
import com.hiekn.china.aeronautical.model.vo.DatesetFile;
import com.hiekn.china.aeronautical.service.DatasetService;
import com.monitorjbl.xlsx.StreamingReader;
import com.monitorjbl.xlsx.impl.StreamingSheet;
import com.monitorjbl.xlsx.impl.StreamingWorkbook;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.java.Log;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Iterator;

@Component
@Path("dataset")
@Api("数据集")
@Produces(MediaType.APPLICATION_JSON)
@Log
public class DatasetRestApi {

    @Autowired
    private DatasetService datasetService;

    @ApiOperation("数据集列表")
    @POST
    @Path("list")
    public RestResp<RestData<Dataset>> findAll(@Valid DatasetQuery datasetQuery) {
        return new RestResp<>(datasetService.findAll(datasetQuery));
    }

    @ApiOperation("数据集详情")
    @GET
    @Path("{id}")
    public RestResp<Dataset> findOne(@PathParam("id") String id) {
        return new RestResp<>(datasetService.findOne(id));
    }

    @ApiOperation("删除数据集")
    @DELETE
    @Path("{id}")
    public RestResp delete(@PathParam("id") String id) {
        //datasetService.delete(id);
        return new RestResp<>();
    }

    @ApiOperation("修改数据集")
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public RestResp<Dataset> modify(@PathParam("id") String id, @Valid Dataset dataset) {
        return new RestResp<>(datasetService.modify(id, dataset));
    }

    @ApiOperation("新增数据集")
    @POST
    @Path("add")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public RestResp<Dataset> add(@BeanParam DatesetFile datesetFile) throws Exception {
//        File file = new File("s" + System.currentTimeMillis());
//        FileUtils.copyInputStreamToFile(datesetFile.getFileIn(), file);
//        InputStream is = new FileInputStream(file);
//        StreamingReader reader = StreamingReader.builder()
//                .rowCacheSize(100)    // number of rows to keep in memory (defaults to 10)
//                .bufferSize(4096)     // buffer size to use when reading InputStream to file (defaults to 1024)
//                .sheetIndex(0)        // index of sheet to use (defaults to 0)
//                .open(datesetFile.getFileIn());            // InputStream or File for XLSX file (required)
        StreamingWorkbook workbook =(StreamingWorkbook) StreamingReader.builder().rowCacheSize(100).bufferSize(4096).open(datesetFile.getFileIn());

        Iterator<Sheet> sheets= workbook.sheetIterator();
        while (sheets.hasNext()){
            StreamingSheet sheet =(StreamingSheet) sheets.next();
            sheet.getPhysicalNumberOfRows();

        }

//        for (Row r : reader) {
//            for (Cell c : r) {
//                System.out.println(c.getStringCellValue());
//            }
//        }
//
//        List<Map<String, Object>> list = PoiUtils.importXls(datesetFile.getFileIn(),datesetFile.getFileInfo().getFileName());
        return new RestResp<>();
        //return new RestResp<>(datasetService.add(dataset));
    }

}
