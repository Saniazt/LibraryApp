package com.saniazt.springcourse.LibraryWithJPAandBootAndHibern.reports;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

@Component
public class BookReport {

    private static final String DB_DRIVER = "org.postgresql.Driver";
    private static final String DB_CONNECTION = "jdbc:postgresql://localhost:5432/library_app_jpa_hiber";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "postgres";

    public void createReport() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        Workbook workbook = new XSSFWorkbook();

        try {
            Class.forName(DB_DRIVER);
            conn = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);

            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM book");

            Sheet sheet = workbook.createSheet("Data");
            int rowNum = 0;

            Row headerRow = sheet.createRow(rowNum++);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("Title");
            headerRow.createCell(2).setCellValue("Author");
            headerRow.createCell(3).setCellValue("taken_at");
            headerRow.createCell(4).setCellValue("person_id");


            while (rs.next()) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(rs.getLong("id"));
                row.createCell(1).setCellValue(rs.getString("title"));
                row.createCell(2).setCellValue(rs.getString("author"));
                row.createCell(3).setCellValue(rs.getString("taken_at"));
                row.createCell(4).setCellValue(rs.getString("person_id"));

            }

            LocalDateTime myDateObj = LocalDateTime.now();
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm");
            String formattedDate = myDateObj.format(myFormatObj);

            String filePath = System.getProperty("user.home") + "/Desktop/data_" + formattedDate + ".xlsx";
            FileOutputStream outputStream = new FileOutputStream(filePath);
            workbook.write(outputStream);
            workbook.close();
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

