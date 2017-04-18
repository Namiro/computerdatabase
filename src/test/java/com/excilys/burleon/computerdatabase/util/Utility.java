package com.excilys.burleon.computerdatabase.util;

import java.io.File;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;

import com.excilys.burleon.computerdatabase.service.util.PropertiesManager;

public final class Utility {

    /**
     *
     * @throws Exception
     *             Exception
     */
    public static void loadAndResetDatabase() {
        try {
            final IDataSet dataSet = new FlatXmlDataSetBuilder().build(new File("src/test/resources/dataset.xml"));
            PropertiesManager.load();
            final IDatabaseTester databaseTester = new JdbcDatabaseTester("com.mysql.jdbc.Driver",
                    PropertiesManager.config.getString("database"), PropertiesManager.config.getString("dbuser"),
                    PropertiesManager.config.getString("dbpassword"));
            databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
            databaseTester.setDataSet(dataSet);
            databaseTester.onSetup();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}
