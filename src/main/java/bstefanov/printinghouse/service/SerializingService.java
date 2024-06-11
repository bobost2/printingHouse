package bstefanov.printinghouse.service;

import bstefanov.printinghouse.data.audit.AuditableRecord;
import bstefanov.printinghouse.data.audit.FinalReport;

import java.io.*;
import java.math.RoundingMode;

public class SerializingService {
    public void saveDayReport(String path, FinalReport report) {
        try {
            FileOutputStream fos = new FileOutputStream(path);
            ObjectOutputStream outputStream = new ObjectOutputStream(fos);
            outputStream.writeObject(report);
            outputStream.close();
            fos.close();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void saveDayReportTxt(String path, FinalReport report) {
        try {
            FileWriter fileWriter = new FileWriter(path);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write("Printing House Report:\n");
            bufferedWriter.write("====================================\n");

            for (AuditableRecord record : report.getAudit()) {
                bufferedWriter.write(record.recordDetails() + " | "
                        + record.moneyGainedOrLost().setScale(2, RoundingMode.HALF_UP) + "$\n");
            }

            bufferedWriter.write("====================================\n");

            bufferedWriter.write("Total profit: "
                    + report.getProfit().setScale(2, RoundingMode.HALF_UP) + "$\n");
            bufferedWriter.write("Expected profit: "
                    + report.getExpectedProfit().setScale(2, RoundingMode.HALF_UP) + "$\n");
            bufferedWriter.write("Total losses: "
                    + report.getLosses().setScale(2, RoundingMode.HALF_UP) + "$\n");
            bufferedWriter.write("Total: "
                    + report.getTotal().setScale(2, RoundingMode.HALF_UP) + "$\n");

            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public FinalReport loadDayReport(String path) {
        try {
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream inputStream = new ObjectInputStream(fis);
            return (FinalReport) inputStream.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }
}
