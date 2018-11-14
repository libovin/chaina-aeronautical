package com.hiekn.china.aeronautical.util;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;

/**
 * CSV操作(导出和导入)
 */
public class CSVUtils {


    /**
     * 导入
     *
     * @return
     */
//    public static List<Map<String, Object>> importCsv(InputStream fileIn) {
//        List<Map<String, Object>> dataList = new ArrayList<>();
//        BufferedReader bRead = null;
//        try {
//            bRead = new BufferedReader(new InputStreamReader(fileIn, "gbk"));
//            CSVReader csvReader = new CSVReader(bRead);
//            ArrayList<String> title = new ArrayList<>();
//            String[] strs = csvReader.readNext();
//            if (strs != null && strs.length > 0) {
//                for (String str : strs)
//                    title.add(str);
//            }
//            List<String[]> list = csvReader.readAll();
//            for (String[] ss : list) {
//                Map<String, Object> map = new LinkedHashMap<>();
//                for (int j = 0; j < ss.length && j < title.size(); j++) {
//                    map.put(title.get(j), ss[j]);
//                }
//                dataList.add(map);
//            }
//            csvReader.close();
//
//
//        } catch (Exception e) {
//            return new ArrayList<>();
//        }
//        return dataList;
//    }

    /**
     * 导出
     *
     * @return
     */
    public static boolean outPutCsv(List<Map<String, Object>> data, List<String> schame, String pathNmae) throws IOException {

         OutputStreamWriter osw = null;
         CSVWriter writer = null;
        try {
            osw = new OutputStreamWriter(new FileOutputStream(new File(pathNmae)), "gbk");
            writer = new CSVWriter(osw);
            String[] schameArr = new String[schame.size()];
            schameArr = schame.toArray(schameArr);
            writer.writeNext(schameArr);
            CSVWriter finalWriter = writer;
            data.forEach(s -> {
                String[] entries = new String[schame.size()];
                for (int i = 0; i < schame.size(); i++) {
                    entries[i] = s.get(schame.get(i)).toString();
                }
                finalWriter.writeNext(entries);
            });


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            osw.close();
            writer.close();
        }

        return true;
    }


}