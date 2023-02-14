package com.saniazt.springcourse.LibraryWithJPAandBootAndHibern.reports;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import com.spire.data.table.DataTable;
import com.spire.data.table.common.JdbcAdapter;
import com.spire.xls.ExcelVersion;
import com.spire.xls.Workbook;
import com.spire.xls.Worksheet;

import java.sql.*;


public class ExcelReport {
    public static void main(String[] args) {
        //Create a Workbook object
        Workbook wb = new Workbook();

        //Get the first worksheet
        Worksheet sheet = wb.getWorksheets().get(0);

        //Create a DataTable object
        DataTable dataTable = new DataTable();

        //Connect to database
        String url = "jdbc:postgresql://localhost:5432/library_app_jpa_hiber";
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            try {
                Connection conn = DriverManager.getConnection(url,"postgres","postgres");
                Statement sta = conn.createStatement();

                //Select table from the database
                ResultSet resultSet = sta.executeQuery("select * from books");
                JdbcAdapter jdbcAdapter = new JdbcAdapter();

                //Export data from database to datatable
                jdbcAdapter.fillDataTable(dataTable, resultSet);

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //Write datatable to the worksheet
        sheet.insertDataTable(dataTable, true, 1, 1);

        //Auto fit column width
        sheet.getAllocatedRange().autoFitColumns();

        //Save to an Excel file
        wb.saveToFile("output/ExportToExcel.xlsx", ExcelVersion.Version2016);
    }
}
