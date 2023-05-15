package com.example.napasecomgw.handler;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOPackager;
import org.jpos.iso.packager.GenericPackager;

import java.io.InputStream;

public class PackagerFactory {

    private static final Logger logger = LoggerFactory.getLogger(PackagerFactory.class);

    public static ISOPackager getPackagerATM() {
        ISOPackager packager = null;
        try {
            String filename = " src/main/resources/iso8557/ecom8557.xml";
            InputStream is = PackagerFactory.class.getResourceAsStream(filename);
            packager = new GenericPackager(is);
        } catch (ISOException e) {
            e.printStackTrace();
            logger.error("getPackagerATM exception", e);
        }
        return packager;
    }

    public static ISOPackager getPackager() {
        ISOPackager packager = null;
        try {
            String filename = "iso87ascii.xml";
            InputStream is = PackagerFactory.class.getResourceAsStream(filename);
            packager = new GenericPackager(is);
        } catch (ISOException e) {
            e.printStackTrace();
            logger.error("getPackager exception", e);
        }
        return packager;
    }
}
