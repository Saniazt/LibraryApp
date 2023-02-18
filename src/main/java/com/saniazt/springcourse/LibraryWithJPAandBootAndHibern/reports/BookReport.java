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

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.auditing.CurrentDateTimeProvider;
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
        Workbook workbook = new HSSFWorkbook();

        try {
            Class.forName(DB_DRIVER);
            conn = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);

            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM book");

            Sheet sheet = workbook.createSheet("Data");
            int rowNum = 0;

            while (rs.next()) {
                Row row = sheet.createRow(rowNum++);
                int colNum = 0;
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    Cell cell = row.createCell(colNum++);
                    cell.setCellValue(rs.getString(i));
                }
            }
            LocalDateTime myDateObj = LocalDateTime.now();
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm");
            String formattedDate = myDateObj.format(myFormatObj);

            FileOutputStream outputStream = new FileOutputStream(System.getProperty("user.home") + "/Desktop/data "+formattedDate+".xls");
            workbook.write(outputStream);
            workbook.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
