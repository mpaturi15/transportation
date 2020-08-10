package com.macys.mst.transportation.functional.db;

import com.macys.mst.artemis.config.FileConfig;
import com.macys.mst.artemis.config.GetPasswordCyberArk;
import com.macys.mst.artemis.db.DBConnections;
import com.macys.mst.transportation.functional.execdrivers.ExecutionConfig;

import org.apache.log4j.Logger;

import java.sql.*;

public class DBInitilizer {

	public static String cyberarksafe = FileConfig.getInstance().getStringConfigValue("cyberark.safe");
	public static String cyberarkappid = FileConfig.getInstance().getStringConfigValue("cyberark.appid");

	static PreparedStatement sqlStatement = null;
	static ResultSet resultSet = null;
	private static Logger logger = Logger.getLogger(DBInitilizer.class.getName());
	public static Connection connection = null;

	public static Connection getConnection(String dbType, String schema) {
		Connection conn = DBConnections.getinstance(dbType, schema).dbConnection();
		return conn;
	}

	public static Connection dbConnection(String schema) {
		try {
				String driver = FileConfig.getInstance().getStringConfigValue("db." + schema + ".driver.classname");
				String uri = FileConfig.getInstance().getStringConfigValue("db." + schema + ".connecturi");
				String username = FileConfig.getInstance().getStringConfigValue("db." + schema + ".userName");
				// String password=FileConfig.getInstance().getStringConfigValue("db."+schema+".password");
				String passwordobj = FileConfig.getInstance().getStringConfigValue("db." + schema + ".pwdobjectid");
				//String password = GetPasswordCyberArk.getpassword(cyberarksafe, cyberarkappid, passwordobj);
				Class.forName(driver);
				connection = DriverManager.getConnection(uri, username, passwordobj);

		} catch (SQLException | ClassNotFoundException e) {
			logger.info(e);
		}
		return connection;
	}

	public static Connection getDbConnection(String schema) {
		try {
			if (connection != null && !connection.isClosed()) {
				logger.info("Connection is NOT NULL");
				return connection;
			}

			String driver = FileConfig.getInstance().getStringConfigValue("db.driver.classname");
			String uri = FileConfig.getInstance().getStringConfigValue("db." + schema + ".connecturi");
			String username = FileConfig.getInstance().getStringConfigValue("db." + schema + ".userName");
			String password = FileConfig.getInstance().getStringConfigValue("db." + schema + ".password");
			Class.forName(driver);
			connection = DriverManager.getConnection(uri, username, password);
		} catch (ClassNotFoundException | SQLException var5) {
			logger.info(var5);
		}

		return connection;
	}

	public static Connection getDbConnections(String dbSchema){
		String driverClass =  FileConfig.getInstance().getStringConfigValue("db.driver.classname");
		String connUrl  =  String.format(FileConfig.getInstance().getStringConfigValue("db.connecturi"),dbSchema);
		String userName =  FileConfig.getInstance().getStringConfigValue("db.userName");
		String passWord = ExecutionConfig.getPassword();
		Connection conn = DBConnections.getinstance(dbSchema,driverClass,connUrl,userName,passWord).dbConnection();
		return conn;
	}



	public static void closeDBResources() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (sqlStatement != null) {
				sqlStatement.close();
			}
			if (connection != null) {
				connection.close();
				logger.info("##############################--- Connection Closed -- ######################");
			}

		} catch (SQLException se) {
			logger.error(se);
		}
	}

}
