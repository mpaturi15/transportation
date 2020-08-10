package com.macys.mst.transportation.functional.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.macys.mst.artemis.reports.StepDetail;
import com.macys.mst.transportation.functional.util.CommonUtils;

public class DBMethods {

	/** The logger. */
	private static Logger logger = Logger.getLogger(DBMethods.class.getName());
	private static Map<String, Connection> connectionMap = new HashMap<String, Connection>();
	static ResultSet resultSet = null;
	static PreparedStatement sqlStatement = null;

	public static PreparedStatement getSqlStatement() {
		return sqlStatement;
	}

	public static void setSqlStatement(PreparedStatement sqlStatement2) {
		sqlStatement = sqlStatement2;
	}

	public static Map<String, Connection> establishDBConnections(String schema) throws Exception {
		if (!connectionMap.containsKey(schema) || connectionMap.get(schema) == null || connectionMap.get(schema).isClosed()) {
			Connection conn = DBInitilizer.dbConnection(schema);
			//Connection conn = DBInitilizer.getConnection("db", schema);
			//Connection conn = DBInitilizer.getDbConnections(schema);
			connectionMap.put(schema, conn);
		} else {
			logger.info("Connection is already established ... ");
		}
		return connectionMap;
	}

	public static void closeDBConnections() {
		logger.info("Closing DB Connections ... ");
		connectionMap.forEach((k, v) -> {
			try {
				logger.info("Schema : " + k + " Connection : " + v);
				v.close();
			} catch (Exception e) {
				logger.info(e.getMessage());
			}
		});

	}

	public static ResultSet dbResultSet(String query, String schema) throws SQLException, Exception {
		resultSet = null;
		try {
			Connection connection = establishDBConnections(schema).get(schema);
			logger.info("Query: " + query);
			PreparedStatement sqlStatement = connection.prepareStatement(query);
			resultSet = sqlStatement.executeQuery();
			setSqlStatement(sqlStatement);
		} catch (SQLException exception) {
			logger.info("Exception in result set :" + exception.getMessage());
			{
				logger.error("Exception came for " + exception.getMessage());
				try {
					Connection connection = establishDBConnections(schema).get(schema);
					PreparedStatement sqlStatement = connection.prepareStatement(query);
					resultSet = sqlStatement.executeQuery();
					setSqlStatement(sqlStatement);
				} catch (Exception ex) {
					logger.info("Additional Ex: " + ex.getMessage());
				}

			}
		}
		return resultSet;
	}

	public static void deleteOrUpdateDataBase(String query, String schema) {
		try {
			logger.info(query);
			Connection connection = establishDBConnections(schema).get(schema);
			PreparedStatement sqlStatement = connection.prepareStatement(query);
			sqlStatement.executeUpdate();
			connection.commit();
			sqlStatement.close();
		} catch (Exception exception) {
			logger.info("2nd time : " + query);
			logger.info(exception.getMessage());
			StepDetail.addDetail("Exception in DB : " + exception, false);
		}

	}

	/*
	 * Get a Single String Value
	 */
	public static String getDBValueInString(String query, String schema) {
		String dbValue = null;
		ResultSet rs = null;
		try {
			rs = dbResultSet(query, schema);
			while (rs.next()) {
				dbValue = rs.getString(1);
				break;
			}
			rs.close();
			getSqlStatement().close();
		} catch (Exception exception) {
			logger.info("the Db values exception is " + exception.getMessage());
		}
		return dbValue;
	}

	/*
	 * Get a Single String Value
	 */
	public static List<String> getDBValueInList(String query, String schema) throws Exception {
		List<String> dbValues = new ArrayList<String>();
		ResultSet rs = null;
		try {
			rs = dbResultSet(query, schema);
			while (rs.next()) {
				String dbValue = rs.getString(1);
				dbValues.add(dbValue);
				// break;
			}
			rs.close();
			getSqlStatement().close();
		} catch (Exception exception) {
			logger.info("the Db values exception is " + exception.getMessage());
		}
		return dbValues;
	}

	public static Map<String, String> getDBValuesInMap(String query, String dbSchema) throws Exception {
		logger.info("Query Is : " + query);
		LinkedHashMap sub_Map = new LinkedHashMap();
		ResultSet result_Set = dbResultSet(query, dbSchema);

		while (result_Set.next()) {
			sub_Map.put(result_Set.getString(1), result_Set.getString(2));
		}

		return sub_Map;
	}

	/* Get list values in SQL query format */
	public static String getValuesForSQl(List<String> list) {
		List<String> sl = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			sl.add("'" + list.get(i) + "'");
		}
		String deptsSqlOrder = sl.toString().substring(1, sl.toString().length() - 1);
		return deptsSqlOrder;

	}

	public static List<Map<String, String>> getData(String query, String schema) throws ClassNotFoundException {
		List<Map<String, String>> AssignmentDetails = new ArrayList<Map<String, String>>();

		try {
			ResultSet result_Set = dbResultSet(query, schema);

			while (result_Set.next()) {
				Map<String, String> details = new HashMap<String, String>();
				details.clear();
				ResultSetMetaData rmd = result_Set.getMetaData();
				// The column count starts from 1
				for (int i = 1; i < rmd.getColumnCount() + 1; i++) {
					String name = rmd.getColumnName(i);
					// logger.info(name);
					details.put(name, result_Set.getString(name));
				}
				AssignmentDetails.add(details);
			}
			result_Set.close();
			getSqlStatement().close();
		} catch (SQLException e) {
			logger.error(e);

		} catch (Exception e) {
			logger.error(e);
		}
		return AssignmentDetails;
	}

}
