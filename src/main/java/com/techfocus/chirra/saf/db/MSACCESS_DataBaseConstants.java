package com.techfocus.chirra.saf.db;

public class MSACCESS_DataBaseConstants {

	public static String MSACCESS_RESULTS_PATH = "src/test/Results";
	public static String MSACCESS_RESULTS_SCREENSHOTS_PATH = "/Screenshots";
	public static String MSACCESS_DATABASE_PATH = "src/test/Results";
	public static final String MSACCESS_RESULTS_DATABASE_NAME = "SAF.MDB";
	public static final String MSACCESS_DATABASE_NAME = "SAF.MDB";
	public static final String MSACCESS_REPORTS_TABLE_NAME = "REPORTS";

	public static final String MSACCESS_REPORTS_TABLE_SCHEMA = "create table " + MSACCESS_REPORTS_TABLE_NAME + " ( "
			+ "testCaseId BIGINT," + "testCaseName varchar(500)," + "testStepName varchar(500),"
			+ "testStepDetails varchar(500)," + "testStepStatus varchar(10)," + "testStepExecutedAt DATETIME,"
			+ "testStepImagePath varchar(100)," + "testStepSno BIGINT)";

}
