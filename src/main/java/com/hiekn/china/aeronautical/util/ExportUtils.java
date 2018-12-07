package com.hiekn.china.aeronautical.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.ArrayList;
import java.util.List;

public class ExportUtils {

    public static List<String> getHeadData(Class cls) {
        List<String> list = new ArrayList<>();
        return list;
    }

    public static void addOneRowOfHeadDataToExcel(Row row, List<String> headByRowNum) {
        if (headByRowNum != null && headByRowNum.size() > 0) {
            for (int i = 0; i < headByRowNum.size(); i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(headByRowNum.get(i));
            }
        }
    }
}
