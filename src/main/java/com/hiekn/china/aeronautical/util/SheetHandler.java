package com.hiekn.china.aeronautical.util;

import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.usermodel.XSSFComment;

import java.util.HashMap;
import java.util.Map;


public abstract class SheetHandler implements XSSFSheetXMLHandler.SheetContentsHandler {


    private boolean firstCellOfRow = false;
    private int currentRow = -1;
    private int currentCol = -1;
    private Map<Integer, String> titleMap;
    private Map<String, Object> row = new HashMap<>();

    public Map<String, Object> getRow() {
        return this.row;
    }

    @Override
    public void startRow(int rowNum) {
        firstCellOfRow = true;
        currentRow = rowNum;
        currentCol = -1;
        if (currentRow == 0) {
            titleMap = new HashMap<>();
        }
        row.clear();
    }

    abstract public void endRow(int rowNum);

    @Override
    public void cell(String cellReference, String formattedValue, XSSFComment comment) {
        if (firstCellOfRow) {
            firstCellOfRow = false;
        }
        if (cellReference == null) {
            cellReference = new CellAddress(currentRow, currentCol).formatAsString();
        }
        int thisCol = (new CellReference(cellReference)).getCol();
        int missedCols = thisCol - currentCol - 1;
        for (int i = 0; i < missedCols; i++) {
            // missedCols
        }
        currentCol = thisCol;
        if (currentRow == 0) {
            titleMap.put(currentCol, formattedValue);
        } else {
//            Object o;
//            try {
//                o = Double.parseDouble(formattedValue);
//            } catch (NumberFormatException e) {
//                o = formattedValue;
//            }
            row.put(titleMap.get(currentCol), formattedValue);
        }
    }

    @Override
    public void headerFooter(String text, boolean isHeader, String tagName) {
    }
}
