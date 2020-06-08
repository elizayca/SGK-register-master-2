package test;
import org.junit.jupiter.api.BeforeAll;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

import controller.MainController;
import javafx.collections.ObservableList;
import library.Registers;
import org.dbunit.DBTestCase;
import org.dbunit.DatabaseTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static sun.nio.cs.Surrogate.is;

public class DbUnit extends DatabaseTestCase {
    public static final String TABLE_LOGIN = "project_sgk";
    public FlatXmlDataSet loadedDataSet;
    MainController controller;


    public DbUnit(String name) {
        super(name);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "com.mysql.cj.jdbc.Driver");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:mysql://localhost:3306/proje_sgk?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "root");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "1794eliz");
    }

    @Override
     protected IDatabaseConnection getConnection() throws Exception {
        Connection conn;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/proje_sgk?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "1794eliz");
            return new DatabaseConnection(conn);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        loadedDataSet = new FlatXmlDataSet(this.getClass().getClassLoader()
                .getResourceAsStream("dbunit_test_data.xml"));
        return loadedDataSet;
    }


  /*  protected DatabaseOperation getSetUpOperation() throws Exception {
        return DatabaseOperation.REFRESH;
    }

    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DatabaseOperation.NONE;
    }*/



     public void testById() throws Exception {

        ObservableList<Registers> list =controller.getRegistersList();
      controller.insert(1,"eliz","Yilmaz","computer","ello@gmail.com", "12-01-2018","15.06.2020");
        assertEquals(15, list.size());
    }


}