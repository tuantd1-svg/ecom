package com.example.napasecomgw.client;


import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOUtil;
import org.jpos.iso.packager.GenericPackager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Component
public class ISO8553SocketClient {
    private final Logger logger = LoggerFactory.getLogger(ISO8553SocketClient.class);

    @Value("${napas.server.host}")
    private String serverHost;

    @Value("${napas.server.port}")
    private int serverPort;

    @Value("${iso.packager.file}")
    private String isoPackagerFile;

    public void startClient() {
        try (Socket socket = new Socket(serverHost, serverPort)) {
           System.out.println("Connected to Napas server :"+  serverHost+" : "+ serverPort);

            InputStream inputStream = socket.getInputStream();
            byte[] buffer = new byte[4096];

            while (true) {
                // Đọc gói tin ISO 8583 từ server
                int bytesRead = inputStream.read(buffer);
                System.out.println();
                if (bytesRead == -1) {
                    break; // Đã đọc hết dữ liệu từ server
                }

                byte[] isoMessage = Arrays.copyOf(buffer,bytesRead);

                // Xử lý gói tin ISO 8583
                processISOMessage(isoMessage);
            }
        } catch (IOException e) {
            logger.error("Error occurred while connecting to Napas server: {}", e.getMessage());
        }
    }

    public void processISOMessage(byte[] isoMessage) {
        try {
            // Load the ISO packager
             GenericPackager packager = new GenericPackager(isoPackagerFile);

            // Create a new ISOMsg instance using the packager
            ISOMsg isoMsg = new ISOMsg();
            System.out.println("ISO_MSG:"+ ISOUtil.hexString(isoMessage,0,isoMessage.length));
            System.out.println("String message : " + new String(isoMessage, StandardCharsets.UTF_8));
            System.out.println("MTI : "+ new String(Arrays.copyOfRange(isoMessage,0,4)));
            System.out.println("Account Number "+ new String(Arrays.copyOfRange(isoMessage,22,38)));
             isoMsg.setPackager(packager);

             //Unpack the ISO message from the byte array
              isoMsg.unpack(isoMessage);
//
//            // Extract the required fields from the ISO message
            String mti = isoMsg.getMTI();
            String cardNumber = isoMsg.getString(2);
            String processingCode = isoMsg.getString(3);
            String amount = isoMsg.getString(4);

            // Perform your custom processing logic here
            // Example: Print the extracted fields
            System.out.println("MTI: " + mti);
            System.out.println("Card Number: " + cardNumber);
            System.out.println("Processing Code: " + processingCode);
            System.out.println("Amount: " + amount);

            // Add more processing logic as needed

//        } catch (ISOException e) {
//            // Handle any ISOException that may occur during processing
//            e.printStackTrace();
//
//        }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}