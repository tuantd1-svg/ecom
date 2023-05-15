package com.example.napasecomgw.handler;
import org.jpos.iso.ISOSource;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;
import org.jpos.iso.ISOUtil;

import java.io.IOException;
import java.net.Socket;

public class SocketHandler implements Runnable {
    private Socket clientSocket;
    private ISOPackager isoPackager;

    private ISOMsg isoMsg;

    public SocketHandler(Socket clientSocket, ISOPackager isoPackager) {
        this.clientSocket = clientSocket;
        this.isoPackager = isoPackager;
    }

    @Override
    public void run() {
        try {
            // Đọc dữ liệu từ client socket
            byte[] buffer = new byte[2048];
            int bytesRead = clientSocket.getInputStream().read(buffer);
            // Gửi dữ liệu nhận được đến phương thức xử lý ISO message
            processISOMessage(buffer, bytesRead);

            // Đóng client socket sau khi hoàn thành xử lý
            clientSocket.close();
        } catch (IOException | ISOException e) {
            e.printStackTrace();
        }
    }

    private void processISOMessage(byte[] data, int length) throws ISOException {
        String isoMessage = ISOUtil.hexString(data, 0, length);
        System.out.println("Received ISO 8557 message: " + isoMessage);

        // Unpack ISO message
        ISOMsg isoMsg = new ISOMsg();
        isoMsg.setPackager(isoPackager);
        isoMsg.unpack(data);

        // Xử lý các trường dữ liệu trong ISO message
        String mti = isoMsg.getMTI();
        System.out.println("MTI: " + mti);

        if (mti.equals("0220")) {
            // Nếu MTI là "0220", gọi service khác để xử lý
        }
    }
}