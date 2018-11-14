package com.hiekn.china.aeronautical.util;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.hiekn.boot.autoconfigure.base.exception.JsonException;
import com.hiekn.boot.autoconfigure.base.exception.RestException;
import com.hiekn.china.aeronautical.exception.ErrorCodes;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

@Configuration
public class JsonUtils {

    private static Gson gson;

    private static final long TOOBIG = 0x6400000;

    @Autowired
    public void setGson(Gson gson) {
        JsonUtils.gson = gson;
    }

    public static <T> T fromJson(String json, Class<T> cls) {
		try {
			return gson.fromJson(json, cls);
		} catch (Exception e) {
			throw JsonException.newInstance();
		}
	}

	public static <T> T fromJson(String json, Type typeOfT) {
		try {
			return gson.fromJson(json, typeOfT);
		} catch (Exception e) {
			throw JsonException.newInstance();
		}
	}

	public static String toJson(Object obj) {
		try {
			return gson.toJson(obj);
		} catch (Exception e) {
			throw JsonException.newInstance();
		}
	}


	public static List<Map<String,Object>> importJson(FormDataContentDisposition fileInfo,InputStream fileIn,FormDataBodyPart formDataBodyPart){



		File file = formDataBodyPart.getEntityAs(File.class);

		Long size = file.length()/1024;
		if (size > TOOBIG) {
			throw new IllegalStateException("文件过大");
		}


    	List<String> jsonList = Lists.newArrayList();

    	String line = null;
		try {
			Reader reader = new InputStreamReader(fileIn, "UTF-8");
			BufferedReader br = new BufferedReader(reader);
			while((line=br.readLine())!=null){
				if("".equals(line.trim())){
					continue;
				}

				line = line.replaceAll("\\\\","\\\\\\\\");
				line = line.replaceAll("\\\\\\\"","\\\\\\\\\\\"");
				line = line.replaceAll("\n","\\n");
				line = line.replaceAll("\r","\\r");
				line = line.replaceAll("\t","\\t");
				jsonList.add(line);
			}
		} catch (Exception e) {
			throw RestException.newInstance(ErrorCodes.FILE_UPLOAD_ERROR);
		} finally {
			try {
				fileIn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		List<Map<String, Object>> jsonResultList = Lists.newArrayList();
		JSONObject jsonObject = new JSONObject();
		try {
			for(String json :jsonList){
				jsonObject = JSONObject.parseObject(json);
				jsonResultList.add(jsonObject);
			}
		} catch (Exception e) {
			throw RestException.newInstance(ErrorCodes.JSON_PARSE_ERROR);
		} finally {
		}
		return  jsonResultList;
	}

}