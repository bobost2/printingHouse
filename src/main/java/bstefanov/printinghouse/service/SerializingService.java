package bstefanov.printinghouse.service;

import bstefanov.printinghouse.data.audit.FinalReport;

import java.io.*;

public class SerializingService {
    public void saveDayReport(String path, FinalReport report) {
        try {
            FileOutputStream fos = new FileOutputStream(path);
            ObjectOutputStream outputStream = new ObjectOutputStream(fos);
            outputStream.writeObject(report);
            outputStream.close();
            fos.close();
        } catch (IOException ex) {
            // TODO: Write new exception
            System.err.println(ex.getMessage());
        }
    }

    public FinalReport loadDayReport(String path) {
        try {
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream inputStream = new ObjectInputStream(fis);
            return (FinalReport) inputStream.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            // TODO: Write new exception
            System.err.println(ex.getMessage());
        }
        return null;
    }
}
