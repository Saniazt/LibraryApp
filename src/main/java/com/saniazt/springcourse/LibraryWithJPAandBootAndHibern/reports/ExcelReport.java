package com.saniazt.springcourse.LibraryWithJPAandBootAndHibern.reports;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReport {
    public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/library_app_jpa_hiber", "postgres", "postgres");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM book");
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("book");
            Row row = sheet.createRow(0);
            Cell cell = row.createCell(0);
            cell.setCellValue("Title");
            int i = 0;
            while (rs.next()) {
                Row row1 = sheet.createRow(i+1);
                Cell cell1 = row1.createCell(0);
                cell1.setCellValue(rs.getString("Title"));
                i++;
            }
            FileOutputStream out = new FileOutputStream(new File("ExportDatabaseTableToExcel.xlsx"));
            workbook.write(out);
            out.close();
            System.out.println("Exported successfully...");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
