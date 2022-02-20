package com.techfocus.chirra.saf.db;

import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class MSACCESS_DataBaseWork {
	static Connection connectionObj = null;
	static Statement statementObj = null;
	static ResultSet resultSetObj = null;
	static boolean isDatabaseExists = false;
	static boolean isTableCreated = false;

	public static boolean createDataBase(String dataBaseName) {
		String dataBasePath = MSACCESS_DataBaseConstants.MSACCESS_DATABASE_PATH + "/" + dataBaseName;
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		} catch (ClassNotFoundException cnfex) {
			System.err.println("Unable to config the  Class.forName");
			return false;
		}
		try {
			String dbURL = "jdbc:ucanaccess://" + dataBasePath + ";showSchema=true";
			connectionObj = DriverManager.getConnection(dbURL);
			DatabaseMetaData meta = connectionObj.getMetaData();
			ResultSet res = meta.getCatalogs();
			while (res.next()) {
				String value = res.getString("TABLE_CAT");
				if (value.trim().length() >= 1)
					isDatabaseExists = true;
			}
			res.close();
		} catch (Exception e) {
			System.err.println("Database " + dataBaseName + " is not exists and Database creation in progress...");
		}
		if (!isDatabaseExists) {
			try {
				Database db = DatabaseBuilder.create(Database.FileFormat.V2010, new File(dataBasePath));
				System.out.println("Database created successfully: " + dataBasePath);
				return true;
			} catch (Exception e) {
				System.err.println("Unable to create the Database " + dataBasePath);
				return false;
			}
		} else {
			System.out.println("DataBase exists in path : " + dataBasePath);
			return true;
		}
	}

	public static boolean createTable(String tableName, String tableSchema) throws SQLException {
		String dataBasePath = MSACCESS_DataBaseConstants.MSACCESS_DATABASE_PATH + "/"
				+ MSACCESS_DataBaseConstants.MSACCESS_DATABASE_NAME;
		isTableCreated = false;
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		} catch (ClassNotFoundException cnfex) {
			System.err.println("Unable to config the  Class.forName");
			return false;
		}
		String dbURL = "jdbc:ucanaccess://" + dataBasePath + ";showSchema=true";
		connectionObj = DriverManager.getConnection(dbURL);
		DatabaseMetaData metadata = connectionObj.getMetaData();
		String[] types = { "TABLE" };
		ResultSet resultSet = metadata.getTables(null, null, "%", types);
		ArrayList<String> tableNames_al = new ArrayList<String>();
		while (resultSet.next()) {
			String tableName_temp = resultSet.getString(3);
			String tableCatalog = resultSet.getString(1);
			String tableSchema_temp = resultSet.getString(2);
			System.out.println(
					"Table : " + tableName + ", nCatalog : " + tableCatalog + ", Schema : " + tableSchema_temp);
			tableNames_al.add(tableName);
		}
		if (tableNames_al.contains(tableName)) {
			System.out.println("Table " + tableName + " already exists");
			return true;
		} else {
			System.out.println("Table name " + tableName + " not existsed, Table cration in Process...");
			Statement stm = connectionObj.createStatement();
			stm.execute(tableSchema);
			isTableCreated = true;
			connectionObj.close();
			System.out.println("Table " + tableName + " created successfully!");
		}
		return true;
	}

	public static void insertRecordIntoTable() throws SQLException {
		String dbURL = "jdbc:ucanaccess://" + MSACCESS_DataBaseConstants.MSACCESS_DATABASE_PATH + "/"
				+ MSACCESS_DataBaseConstants.MSACCESS_DATABASE_NAME + ";showSchema=true";
		Connection connectionObj = DriverManager.getConnection(dbURL);
		String sql = "insert into " + MSACCESS_DataBaseConstants.MSACCESS_REPORTS_TABLE_NAME + " (Col_1, Col_2, Col_3) "
				+ "values ('ABC', 'XYZ', 10)";
		System.out.println("sql: " + sql);
		PreparedStatement pstmt = connectionObj.prepareStatement(sql);
		pstmt.executeUpdate();
		pstmt.close();
		connectionObj.close();
		System.out.println("Test Insertion - Done!!!!!!!!!!!");
	}

	public static ArrayList<String> getTableData() throws SQLException {
		String dbURL = "jdbc:ucanaccess://" + MSACCESS_DataBaseConstants.MSACCESS_DATABASE_PATH + "/"
				+ MSACCESS_DataBaseConstants.MSACCESS_DATABASE_NAME + ";showSchema=true";
		String tableName = MSACCESS_DataBaseConstants.MSACCESS_REPORTS_TABLE_NAME;
		String sql = "select * from " + tableName;
		System.out.println("sql : " + sql);
		connectionObj = DriverManager.getConnection(dbURL);
		statementObj = connectionObj.createStatement();
		resultSetObj = statementObj.executeQuery(sql);
		ArrayList<String> scenarioNames_al = new ArrayList<String>();
		while (resultSetObj.next()) {
			String scenarioName = resultSetObj.getString(1);
			System.out.println("scenarioName : " + scenarioName);
		}
		try {
			connectionObj.close();
		} catch (Exception e) {
		}
		return scenarioNames_al;
	}

	public static LinkedHashMap<String, String> getTableColumnNameDataTypes() throws SQLException {
		String dbURL = "jdbc:ucanaccess://" + MSACCESS_DataBaseConstants.MSACCESS_DATABASE_PATH + "/"
				+ MSACCESS_DataBaseConstants.MSACCESS_DATABASE_NAME + ";showSchema=true";
		String tableName = MSACCESS_DataBaseConstants.MSACCESS_REPORTS_TABLE_NAME;
		connectionObj = DriverManager.getConnection(dbURL);
		statementObj = connectionObj.createStatement();
		ResultSet rsColumns = null;
		DatabaseMetaData meta = connectionObj.getMetaData();
		rsColumns = meta.getColumns(null, null, tableName, null);
		LinkedHashMap<String, String> columnNameDataType_LHM = new LinkedHashMap<String, String>();
		while (rsColumns.next())
			columnNameDataType_LHM.put(rsColumns.getString("COLUMN_NAME"), rsColumns.getString("TYPE_NAME"));
		try {
			connectionObj.close();
		} catch (Exception e) {
		}
		System.out.println("columnNameDataType_LHM : " + columnNameDataType_LHM);
		return columnNameDataType_LHM;
	}

	public static void main(String[] args) throws SQLException {
		// Create Database if not exists
		// createDataBase(MSACCESS_DataBaseConstants.MSACCESS_DATABASE_NAME);
		// Create Table if not exists
		// createTable(MSACCESS_DataBaseConstants.MSACCESS_REPORTS_TABLE_NAME,
		// MSACCESS_DataBaseConstants.MSACCESS_REPORTS_TABLE_SCHEMA);
		// Insert record into table
		// insertRecordIntoTable();
		// Table data retrieve
		// getTableData();
		// Get Table column names and Data types
		// getTableColumnNameDataTypes();
	}
}