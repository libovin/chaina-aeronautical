package com.hiekn.china.aeronautical.util;

import com.hiekn.boot.autoconfigure.base.exception.RestException;
import com.hiekn.china.aeronautical.exception.ErrorCodes;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class PoiUtils {
    public static List<Map<String, Object>> importXls(InputStream fileIn, String name) {

        Workbook wb = null;
        Sheet sheet = null;
        Row row = null;
        List<Map<String, Object>> list = null;
        String cellData = null;
        wb = readExcel(fileIn, name);
        if (wb != null) {
            //用来存放表中数据
            list = new ArrayList<Map<String, Object>>();
            //获取第一个sheet
            sheet = wb.getSheetAt(0);
            //获取最大行数
            int rownum = sheet.getLastRowNum();
            //获取第一行
            row = sheet.getRow(0);
            if (row == null) {
                throw RestException.newInstance(ErrorCodes.EXCEL_ROW_NULL_ERROR);
            }
            //获取最大列数
//            int colnum = row.getPhysicalNumberOfCells();
            int colnum = row.getLastCellNum();
            List<String> columns = new ArrayList<>();
            if (rownum >= 1) {
                row = sheet.getRow(0);
                if (row != null) {
                    for (int j = 0; j < colnum; j++) {
                        cellData = (String) getCellFormatValue(row.getCell(j));
                        columns.add(cellData.trim());
                    }
                }
            }
            for (int i = 1; i <= rownum; i++) {
                Map<String, Object> map = new LinkedHashMap<String, Object>();
                row = sheet.getRow(i);
                if (row != null) {
                    boolean flag = false;
                    for (int j = 0; j < colnum && j < columns.size(); j++) {

                        cellData = (String) getCellFormatValue(row.getCell(j));
                        if (cellData != null && !cellData.isEmpty()) {
                            flag = true;

                        }

                        if (cellData != null) {
                            cellData = cellData.trim();
                        }

                        map.put(columns.get(j), cellData);
                    }
                    if (flag) {
                        list.add(map);
                    }
                } else {
                    continue;
                }
            }
        }
        return list;
    }

    //读取excel
    public static Workbook readExcel(InputStream fileIn, String name) {
        Workbook wb = null;
        InputStream is = fileIn;
        try {
            if (name.endsWith(".xls")) {
                return wb = new HSSFWorkbook(is);
            } else if (name.endsWith(".xlsx")) {
                return wb = new XSSFWorkbook(is);
            } else {
                return wb = null;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw RestException.newInstance(ErrorCodes.UPLAD_FILE_ERROR);
        } finally {
            try {
                fileIn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return wb;
    }

    public static Object getCellFormatValue(Cell cell) {
        Object cellValue = null;
        if (cell != null) {
            //判断cell类型
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC: {
                    //判断cell是否为日期格式
                    if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                        //转换为日期格式YYYY-mm-dd
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        cellValue = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
                    } else {
                        //数字
                        double dob = cell.getNumericCellValue();
                        if (whitDecimalPointIntCheck(dob)) {
                            cellValue = (int) dob + "";
                        } else {
//                            BigDecimal bigDecimal = new BigDecimal(dob);
//                            cellValue = String.valueOf(bigDecimal.longValue());
                            cellValue = dob + "";

                        }
                    }
                    break;
                }
                case Cell.CELL_TYPE_FORMULA: {
                    //判断cell是否为日期格式
                    if (DateUtil.isCellDateFormatted(cell)) {
                        //转换为日期格式YYYY-mm-dd
                        cellValue = cell.getDateCellValue();
                    } else {
                        //数字
                        cellValue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }
                case Cell.CELL_TYPE_STRING: {
//                    cellValue = cell.getRichStringCellValue().getString();
                    cellValue = cell.getStringCellValue();
                    break;
                }
                default:
                    cellValue = "";
            }
        } else {
            cellValue = "";
        }
        return cellValue;
    }

    private static boolean whitDecimalPointIntCheck(Object str) {
        if (str == null)
            return false;
        if (!Pattern.matches("^\\d+(\\.(0)+)?$", str.toString()))
            return false;
        return true;
    }


}
