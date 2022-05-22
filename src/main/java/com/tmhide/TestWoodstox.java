package com.tmhide;

import com.ctc.wstx.exc.WstxParsingException;
import org.codehaus.stax2.*;
import org.codehaus.stax2.XMLStreamWriter2;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.logging.Logger;

public class TestWoodstox {

    public static void main(String[] args) throws XMLStreamException {
        System.out.println("start");

        File xml =  new File("src/test/java/base-example-large1.xml");

        XMLInputFactory2 inputFactory = (XMLInputFactory2)XMLInputFactory2.newFactory();
        XMLOutputFactory2 outputFactory = (XMLOutputFactory2)XMLOutputFactory2.newFactory();
        //factory.configureForSpeed();
        inputFactory.configureForLowMemUsage();
        XMLStreamReader2 reader = inputFactory.createXMLStreamReader(xml);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XMLStreamWriter2 writer = (XMLStreamWriter2)outputFactory.createXMLStreamWriter(out);
        try {
            while (reader.hasNext()) {
                reader.next();
                if (reader.isStartElement()) {
                    reader.getLocalName();
                }
            }
        } catch(WstxParsingException e) {
            System.out.println("Invalid XML format");
            throw e;
        } finally {
            reader.close();
        }
    }


}
