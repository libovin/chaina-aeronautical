package com.hiekn.china.aeronautical.util.xml;

import org.springframework.util.Assert;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class XmlUtils {

    private Map<String, Integer> map;

    private StringBuilder sb;

    private String beginTag;

    private final XmlHandler out;


    public XmlUtils(final String beginTag, XmlHandler handler) {
        Assert.notNull(handler, "ContentHandler must not be null!");
        this.beginTag = beginTag;
        this.out = handler;
    }

    /**
     *
     * @param file
     * @throws FileNotFoundException
     * @throws XMLStreamException
     */
    public void process(File file) throws FileNotFoundException, XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLEventReader reader = factory.createXMLEventReader(new FileReader(file));
        while (reader.hasNext()) {
            processXml(reader.nextEvent());
        }
    }

    /**
     *
     * @param xmlEvent
     */
    private void processXml(XMLEvent xmlEvent) {
        if (xmlEvent.isStartDocument()) {
            map = new HashMap<>();
        }
        if (xmlEvent.isStartElement()) {
            StartElement startElement = xmlEvent.asStartElement();
            QName name = startElement.getName();
            if (sb != null) {
                sb.append(xmlEvent.toString());
            }
            if (name.getLocalPart().equalsIgnoreCase(beginTag)) {
                int index = map.compute(name.getLocalPart(), (k, v) -> v == null ? 1 : v + 1);
                if (index == 1) {
                    sb = new StringBuilder();
                    sb.append(xmlEvent.toString());
                }
            }
        }
        if (xmlEvent.isCharacters()) {
            Characters characters = xmlEvent.asCharacters();
            if (!characters.isWhiteSpace()) {
                if (sb != null) {
                    sb.append(xmlEvent.toString());
                }
            }
        }
        if (xmlEvent.isEndElement()) {
            EndElement endElement = xmlEvent.asEndElement();
            if (sb != null) {
                sb.append(xmlEvent.toString());
            }
            QName name = endElement.getName();
            if (name.getLocalPart().equalsIgnoreCase(beginTag)) {
                int index = map.computeIfPresent(name.getLocalPart(), (k, v) -> v - 1);
                if (index == 0) {
                    String xml = sb.toString();
                    out.element(xml);
                    sb = null;
                }
            }
        }
        if (xmlEvent.isEndDocument()) {
            map = null;
        }
    }


}
