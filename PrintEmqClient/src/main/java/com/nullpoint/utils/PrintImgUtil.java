package com.nullpoint.utils;

import javax.print.*;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.event.PrintJobEvent;
import javax.print.event.PrintJobListener;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class PrintImgUtil {
    public static void main(String[] args) {
        File file = new File("D:\\svn_ck\\产品文档\\后勤管理系统-v2.0\\SF-CSIM-PRINTER-SDK-V1.1.1-190705\\SF-CSIM-PRINTER-SDK-V1.1.1-190705\\丰密运单打印规则\\150丰密规格.png");

        // String printerName = "D203 (HP LaserJet M1536dnf MFP)";//打印机名包含字串

        String printerName = "Lenovo LJ2200";//打印机名包含字串

//        drawImage(printerName,"D:\\svn_ck\\产品文档\\后勤管理系统-v2.0\\SF-CSIM-PRINTER-SDK-V1.1.1-190705\\SF-CSIM-PRINTER-SDK-V1.1.1-190705\\丰密运单打印规则\\150丰密规格.png",210,false);
//        drawImage(printerName,"C:\\Users\\CeerDecy\\Documents\\Tencent Files\\1748788674\\FileRecv\\MobileFile\\85540994a927d99.jpg",
//                210,false);
        drawImage(printerName, "C:\\Users\\CeerDecy\\Desktop\\2021_04_01\\限选主题.docx",
                210, false);

    }

    public static void drawImage(String printerName, String fileName, int h, boolean aleartPrint) {
        if ((fileName == null) || (fileName.trim() == "")) {
            throw new RuntimeException("文件名为空");
        }

        try {
            DocFlavor dof = null;

            if (fileName.endsWith(".gif")) {
                dof = DocFlavor.INPUT_STREAM.GIF;

            } else if (fileName.endsWith(".jpg")) {
                dof = DocFlavor.INPUT_STREAM.JPEG;

            } else if (fileName.endsWith(".png")) {
                dof = DocFlavor.INPUT_STREAM.PNG;

            } else if (fileName.endsWith(".pdf")) {
                dof = DocFlavor.INPUT_STREAM.PDF;
            }

            PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();

            pras.add(OrientationRequested.PORTRAIT);

            pras.add(new Copies(1));

            DocAttributeSet das = new HashDocAttributeSet();

            das.add(new MediaPrintableArea(0, 0, 100, h, 1000));

            FileInputStream fin = new FileInputStream(fileName);

            Doc doc = new SimpleDoc(fin, dof, das);

            print(printerName, fileName, aleartPrint, dof, pras, doc);

            fin.close();

        } catch (IOException ie) {
            ie.printStackTrace();

        } catch (PrintException pe) {
            pe.printStackTrace();

        }

    }

    private static void print(String printerName, String fileName, boolean aleartPrint, DocFlavor dof, PrintRequestAttributeSet pras, Doc doc) throws PrintException {
//PrintService service = PrintServiceLookup.lookupDefaultPrintService();//默认打印机

// 定位打印服务

        PrintService service = null;

        if (printerName != null) {
//获得本台电脑连接的所有打印机

            PrintService[] printServices = PrinterJob.lookupPrintServices();

            if (printServices == null || printServices.length == 0) {
                System.out.print("打印失败，未找到可用打印机，请检查。");

                return;

            }

//匹配指定打印机

            for (int i = 0; i < printServices.length; i++) {
                if (printServices[i].getName().contains(printerName)) {
                    service = printServices[i];

                    break;

                }

            }

            if (service == null) {
                System.out.print("打印失败，未找到名称为" + printerName + "的打印机，请检查。");

                return;

            }

        }

        if (aleartPrint) {
            PrintService[] printServices = PrintServiceLookup.lookupPrintServices(dof, pras);

            service = ServiceUI.printDialog(null, 400, 400, printServices, service, dof, pras);

        }

        if (service != null) {
            DocPrintJob job = service.createPrintJob();

            job.addPrintJobListener(new PrintJobListener() {
                public void printJobRequiresAttention(PrintJobEvent arg0) {
                    System.out.println("printJobRequiresAttention");

                }

                public void printJobNoMoreEvents(PrintJobEvent arg0) {
                    System.out.println("打印机已接收");

                }

                public void printJobFailed(PrintJobEvent arg0) {
                    System.out.println("打印机无法完成作业,必须重新提交");

                }

                public void printJobCompleted(PrintJobEvent arg0) {
                    System.out.println("打印结束");

                }

                public void printJobCanceled(PrintJobEvent arg0) {
                    System.out.println("作业已被用户或者程序取消");

                }

                public void printDataTransferCompleted(PrintJobEvent arg0) {
                    System.out.println("数据已成功传输打印机");

                }

            });

            try {
                job.print(doc, pras);

            } catch (PrintException pe) {
                pe.printStackTrace();

            }

        } else {
            if (aleartPrint) {
                throw new RuntimeException("打印机未连接,请选择打印机");

            }

            throw new RuntimeException("请设置默认打印机");

        }

    }
}
