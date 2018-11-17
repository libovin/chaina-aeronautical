package com.hiekn.china.aeronautical.util;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.util.SAXHelper;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.springframework.util.Assert;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ExcelUtils {

    private final XSSFSheetXMLHandler.SheetContentsHandler sheetContentsHandler;

    private final DataFormatter formatter = new DataFormatter();

    public ExcelUtils(XSSFSheetXMLHandler.SheetContentsHandler handler) {
        Assert.notNull(handler, "ContentHandler must not be null!");
        this.sheetContentsHandler = handler;
    }

    public void process(File file) throws IOException, OpenXML4JException, SAXException {
        OPCPackage pkg = OPCPackage.open(file, PackageAccess.READ);
        try {
            ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(pkg);
            XSSFReader r = new XSSFReader(pkg);
            StylesTable styles = r.getStylesTable();
            XSSFReader.SheetIterator iter = (XSSFReader.SheetIterator) r.getSheetsData();
            while (iter.hasNext()) {
                processSheet(styles, strings, iter.next());
            }
        } finally {
            pkg.close();
            file.delete();
        }
    }

    private void processSheet(StylesTable styles, ReadOnlySharedStringsTable strings, InputStream sheetInputStream) throws IOException, SAXException {
        InputSource sheetSource = new InputSource(sheetInputStream);
        try {
            XMLReader sheetParser = SAXHelper.newXMLReader();
            ContentHandler handler = new XSSFSheetXMLHandler(styles, null, strings, sheetContentsHandler, formatter, false);
            sheetParser.setContentHandler(handler);
            sheetParser.parse(sheetSource);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException("SAX parser appears to be broken - " + e.getMessage());
        } finally {
            sheetInputStream.close();
        }
    }


}
