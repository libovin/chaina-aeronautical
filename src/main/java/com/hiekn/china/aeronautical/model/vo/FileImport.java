package com.hiekn.china.aeronautical.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import java.io.InputStream;

@Data
@ApiModel
public class FileImport {

    @ApiParam(value = "file")
    @FormDataParam("filename")
    FormDataContentDisposition fileInfo;

    @ApiParam(value = "file")
    @FormDataParam("filename")
    FormDataBodyPart formDataBodyPart;

    @ApiParam(value = "file")
    @FormDataParam("filename")
    InputStream fileIn;
}
