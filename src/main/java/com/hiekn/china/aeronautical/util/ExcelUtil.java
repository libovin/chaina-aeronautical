package com.hiekn.china.aeronautical.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Sax方式解析
 * @author Administrator
 *
 */
public class ExcelUtil extends DefaultHandler implements Runnable {

    private File file;
    private HttpSession session;
    private String prefixStr;  //一次任务的标识
    private SharedStringsTable sst;
    private String lastContents;
    private boolean nextIsString;
    private int sheetIndex = -1;
    private List<String> rowlist = new ArrayList<String>();   //存储当前的行记录
    private List<String> phoneList = new ArrayList<>();		//存储手机号的List
    private int curRow = 0;
    private int countList; //list计数器用于线程解决
    private int total;
    private static final int oneList = 60000; //一次解决的线程
    private static final int oneTimes = 3000;
    //默认数据格式为字符串
    private CellDataType nextDataType = CellDataType.NUMBER;
    private final DataFormatter formatter = new DataFormatter();
    private short formatIndex;
    private String formatString;
    private StylesTable stylesTable; //单元格
    private boolean isTElement;


    public ExcelUtil() {

    }
    //构造器传入Service,Session,file
    public ExcelUtil(HttpSession session,File f,String prefixStr){
        this.session = session;
        this.file = f;
        this.prefixStr = prefixStr;
    }

    //定义可能存在的数据类型
    enum CellDataType {
        BOOL, ERROR, FORMULA, INLINESTR, SSTINDEX, NUMBER, DATE, NULL
    }


    /**
     * 启动读取
     * */
    @Override
    public void run() {
        try {
            process(file);
        } catch (java.lang.Exception e) {
            session.setAttribute(prefixStr+"isSuccessed",false);
            session.setAttribute("isNotImport",false);
            session.setAttribute(prefixStr+"ok", true); //安全校验，任务结束
        }
    }

    /**
     * 读取所有工作簿的入口方法
     * @param
     * @throws OpenXML4JException
     * @throws IOException
     * @throws SAXException
     * @throws Exception
     */
    public  void process(File file) throws java.lang.Exception, java.lang.Exception  {
        OPCPackage pkg = OPCPackage.open(file, PackageAccess.READ);
        XSSFReader 	r = new XSSFReader(pkg);
        SharedStringsTable sst = r.getSharedStringsTable();
        XMLReader parser = fetchSheetParser(sst);
        Iterator<InputStream> sheets = r.getSheetsData();
        while (sheets.hasNext()) {
            curRow = 0;
            sheetIndex++;
            InputStream sheet = sheets.next();
            InputSource sheetSource = new InputSource(sheet);
            parser.parse(sheetSource);
            sheet.close();
        }
        //第一批数据list或者最后一批不足一万在这里执行---线程计数器,切分list数据，多线程批量上传
        if (countList>oneTimes) {
            int size = phoneList.size();
            int count = (size/oneTimes)+1;
            total+=size;
            session.setAttribute(prefixStr+"Total", total);//统计读取到的总记录数
            ThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(count);
            CountDownLatch countDownLatch = new CountDownLatch(count);
            List<String> subList = null;
            for (int i = 0; i < count; i++) {
                if ((i+1)!=count) {
                    int startIndex = i*oneTimes;
                    int endIndex = (i+1)*oneTimes;
                    subList = phoneList.subList(startIndex,endIndex);
                }else{
                    int startIndex = i*oneTimes;
                    subList = phoneList.subList(startIndex, size);
                }

                ExecuteSave executeSave = new ExecuteSave(subList,countDownLatch,session,prefixStr);
                executor.execute(executeSave); //执行当前线程
            }

            countDownLatch.await();//计数器为完毕--主线程等待，
            executor.shutdown();
        }else if(phoneList.size()>0&&phoneList.size()<=3000){ //不足3000,进行记录
            total+=phoneList.size();
            session.setAttribute(prefixStr+"Total", total);
            int success = Integer.parseInt(session.getAttribute(prefixStr+"Succ").toString());
            session.setAttribute(prefixStr+"Succ", success+=phoneList.size());
        }
        session.setAttribute("isNotImport",false); //结束上传任务
        session.setAttribute(prefixStr+"ok", true);//安全再校验
    }

    /**
     * 该方法自动被调用，每读一行调用一次，在方法中写自己的业务逻辑即可
     * @param sheetIndex 工作簿序号
     * @param curRow 处理到第几行
     * @param rowList 当前数据行的数据集合
     * @throws InterruptedException
     * @throws Exception
     */
    public void optRow(int sheetIndex, int curRow, List<String> rowList)  {
        try {
            //第一行跳过
            if (curRow==0) {
                return ;
            }

            //判断当前的手机号是否为大陆手机号,并添加手机号
	      /*  for(String phone : rowList) {
	        	System.out.println(phone);
	        	if (phone.length()!=11) {
					return ;
				}
				phoneList.add(phone);
				countList++;
			}*/
            if (rowList.get(0).length()==11) {
                phoneList.add(rowList.get(0));
                countList++;
            }else {
                return;
            }
            //当前量达六万开启线程池，线程计数器,切分list数据，多线程批量上传
            if (countList==oneList) {
                int size = phoneList.size();
                int count = (size/oneTimes)+1;
                total+=size;
                ThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(count);
                CountDownLatch countDownLatch = new CountDownLatch(count);
                List<String> subList = null;
                for (int i = 0; i < count; i++) {
                    if ((i+1)!=count) {
                        int startIndex = i*oneTimes;
                        int endIndex = (i+1)*oneTimes;
                        subList = phoneList.subList(startIndex,endIndex);
                    }else{
                        int startIndex = i*oneTimes;
                        subList = phoneList.subList(startIndex, size);
                    }

                    ExecuteSave executeSave = new ExecuteSave(subList,countDownLatch,session,prefixStr);
                    executor.execute(executeSave); //执行当前线程
                }

                countDownLatch.await();//计数器为完毕--主线程等待，
                executor.shutdown();
                executor=null;  //便于快速回收，计数器置于0重新计算
                countDownLatch=null;
                countList=0;
                phoneList.clear();
            }
        } catch (InterruptedException e) {
            //记录异常日志
        }
    }

    //获取一个parser解析对象
    public XMLReader fetchSheetParser(SharedStringsTable sst) throws SAXException {
        XMLReader parser = XMLReaderFactory
                .createXMLReader("org.apache.xerces.parsers.SAXParser");
        this.sst = sst;
        parser.setContentHandler(this);
        return parser;
    }


    /**
     * 读取一个标签元素的开始
     */
    public void startElement(String uri, String localName, String name,
                             Attributes attributes) throws SAXException {
        // 判断当前的元素标签为c，则是单元格
        if (name.equals("c")) {
            // 判断c标签属性的t属性，s代表存在值
            String cellType = attributes.getValue("t");
            if (cellType != null && cellType.equals("s")) {
                nextIsString = true;
            } else {
                nextIsString = false;
            }
        }
        // 当元素为t标签时  ，也存在文本内容
        if ("t".equals(name)) {
            isTElement = true;
        } else {
            isTElement = false;
        }
        //设置数据类型
        setNextDataType(attributes);
        // 置空
        lastContents = "";
    }

    /**获取实际的值*/
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        // 得到单元格内容的值
        lastContents += new String(ch, start, length);
    }

    /**读取一个标签元素的结束*/
    public void endElement(String uri, String localName, String name)
            throws SAXException {

        // 根据SST的索引值的到单元格的真正要存储的字符串
        // 这时characters()方法可能会被调用多次
        if (nextIsString  &&  StringUtils.isNotEmpty(lastContents) && StringUtils.isNumeric(lastContents)) {
            int idx = Integer.parseInt(lastContents);
            lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString();
        }
        // t元素也包含字符串
        if (isTElement) {
            // 将单元格内容加入rowlist中，在这之前先去掉字符串前后的空白符
            String value = lastContents.trim();
            rowlist.add(value);
            isTElement = false;
        }
        if ("v".equals(name)) {
            // v => 单元格的值，如果单元格是字符串则v标签的值为该字符串在SST中的索引
            String value = this.getDataValue(lastContents.trim(), "");
            if (value!="") {
                rowlist.add(value);
            }
        } else {
            // 如果标签名称为 row ，这说明已到行尾，调用 optRows() 方法
            if (name.equals("row")) {
                optRow(sheetIndex, curRow, rowlist);
                rowlist.clear();
                curRow++;
            }
        }
    }




    /**
     * 设置数据类型
     */
    public void setNextDataType(Attributes attributes) {

        formatIndex = -1;
        formatString = null;
        String cellType = attributes.getValue("t");
        String cellStyleStr = attributes.getValue("s");
        String columData = attributes.getValue("r");

        //判断当前元素单元格 的数据类型
        if ("b".equals(cellType)) {
            nextDataType = CellDataType.BOOL;
        } else if ("e".equals(cellType)) {
            nextDataType = CellDataType.ERROR;
        } else if ("inlineStr".equals(cellType)) {
            nextDataType = CellDataType.INLINESTR;
        } else if ("s".equals(cellType)) {
            nextDataType = CellDataType.SSTINDEX;
        } else if ("str".equals(cellType)) {
            nextDataType = CellDataType.FORMULA;
        }

        //获取单元格的样式并设置
        if (cellStyleStr != null) {
            int styleIndex = Integer.parseInt(cellStyleStr);  //获取样式的索引--short类型
            XSSFCellStyle style = stylesTable.getStyleAt(styleIndex);  //到样式表获取XSSFCellStyle格式对象
            formatIndex = style.getDataFormat();  //
            formatString = style.getDataFormatString();
            if (formatString == null) {
                nextDataType = CellDataType.NULL;
                formatString = BuiltinFormats.getBuiltinFormat(formatIndex);
            }
        }
    }



    /**
     * 处理数据类型
     * @param value 单元格的值（这时候是一串数字）
     * @param thisStr  一个空字符串
     */
    @SuppressWarnings("deprecation")
    public String getDataValue(String value, String thisStr) {
        switch (nextDataType) {
            case INLINESTR:
                XSSFRichTextString rtsi = new XSSFRichTextString(value.toString());
                thisStr = rtsi.toString();
                rtsi = null;
                break;
            case SSTINDEX:
                String sstIndex = value.toString();
                try {
                    int idx = Integer.parseInt(sstIndex);
                    XSSFRichTextString rtss = new XSSFRichTextString(sst.getEntryAt(idx));
                    thisStr = rtss.toString();
                    rtss = null;
                } catch (NumberFormatException ex) {
                    thisStr = value.toString();
                }
                break;
            case NUMBER:
                DecimalFormat dFormat = new DecimalFormat("#");
                thisStr = dFormat.format(Double.parseDouble(value));
                thisStr = thisStr.replace("_", "").trim();
                break;
            default:
                thisStr = " ";
                break;
        }
        return thisStr;
    }
}

