package com.tmhide;

import com.ctc.wstx.exc.WstxParsingException;
import com.ctc.wstx.stax.WstxOutputFactory;
import org.codehaus.stax2.XMLEventReader2;
import org.codehaus.stax2.XMLInputFactory2;

import javax.xml.stream.*;
import javax.xml.stream.events.XMLEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class TestWoodstox3 {
    public static void main(String[] args) throws XMLStreamException, FileNotFoundException {
        long start = System.currentTimeMillis();
        long free =  Runtime.getRuntime().freeMemory() / (1024 * 1024);
        long total = Runtime.getRuntime().totalMemory() / (1024 * 1024);
        long max =   Runtime.getRuntime().maxMemory() / (1024 * 1024);
        long used =  total - free;
        System.out.println(String.format("free: %s, total: %s, max: %s, used: %s", free, total, max, used));

        File xml =  new File("src/test/java/base-example-large-200m.xml");

        XMLInputFactory inputFactory = XMLInputFactory.newFactory();
        XMLOutputFactory outputFactory = XMLOutputFactory.newFactory();
        //outputFactory.configureForRobustness();
        //inputFactory.configureForSpeed();
        //inputFactory.configureForLowMemUsage();
        //inputFactory.configureForXmlConformance();
        FileInputStream f = new FileInputStream(xml);
        XMLEventReader reader = inputFactory.createXMLEventReader(f);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XMLEventWriter writer = outputFactory.createXMLEventWriter(out);
        try {
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();

                if (event.isStartElement()) {
                    //StartElement elm = event.asStartElement();
                    //System.out.println(elm.getName().getLocalPart());
                }
                writer.add(event);
            }
            writer.flush();
        } catch(WstxParsingException e) {
            System.out.println("Invalid XML format");
            throw e;
        } finally {
            writer.close();
            reader.close();
        }

        //System.out.println(out.toString());

        long end = System.currentTimeMillis();
        System.out.println("Finished within: " + (end - start));
        start = System.currentTimeMillis();
        free =  Runtime.getRuntime().freeMemory() / (1024 * 1024);
        total = Runtime.getRuntime().totalMemory() / (1024 * 1024);
        max =   Runtime.getRuntime().maxMemory() / (1024 * 1024);
        used =  total - free;
        System.out.println(String.format("free: %s, total: %s, max: %s, used: %s", free, total, max, used));

        out = null;
        System.gc();

        main(new String[0]);
    }
}
/*
Invoice
    <cac:AccountingSupplierParty>
        <cac:Party>
            <cbc:EndpointID schemeID="0088">9482348239847239874</cbc:EndpointID>
            <cac:PartyIdentification>
                <cbc:ID>99887766</cbc:ID>
 */
