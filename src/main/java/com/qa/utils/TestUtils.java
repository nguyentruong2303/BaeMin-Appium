package com.qa.utils;

import com.qa.base.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class TestUtils {
    public static final long WAIT = 10;

    public HashMap<String, String > parseStringXML(InputStream file) throws ParserConfigurationException, IOException, SAXException {
        HashMap<String, String> stringMap = new HashMap<String, String>();
        //Get document builder.
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        //Build document
        Document document = builder.parse(file);
        //Normalize the XML Structure, It's just too important
        document.getDocumentElement().normalize();
        //Here come the root node.
        Element root = document.getDocumentElement();
        // Get all element
        NodeList nList = document.getElementsByTagName("string");
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node node = nList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) node;
                //store each Element key value in map
                stringMap.put(element.getAttribute("name"),element.getTextContent());
            }
        }
        return stringMap;
    }
    public String dateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd-HH-mm-ss");
        Date date = new Date();
        return dateFormat.format(date);

    }
    //
    public void log(String txt) {
        BaseTest baseTest = new BaseTest();
        String msg = Thread.currentThread().getId() + ":" + baseTest.getPlatform()+ baseTest.getDeviceName()
                + ":" + Thread.currentThread().getStackTrace()[2].getClassName() + ":" + txt;
        System.out.println(msg);

        String strFile = "log" + File.separator + baseTest.getPlatform() + baseTest.getDeviceName() +
                File.separator + baseTest.getDateTime();
        File logFile = new File(strFile);
        if (!logFile.exists()) {
            logFile.mkdir();
        }
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(logFile + File.separator + "log.txt",true);

        }catch (Exception e) {
            e.printStackTrace();
        }
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println(msg);
        printWriter.close();
    }
    public Logger log() {
        return LogManager.getLogger(Thread.currentThread().getStackTrace()[2].getClassName());
    }
}
