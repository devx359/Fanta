package projSpecificUtilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import config.PathUtility;

public class Dbconnection {
	public Connection con = null;
	Statement stmt;
	ResultSet rs;

	public Dbconnection() {
		String host = PathUtility.mysqlhostname;
		String port = PathUtility.mysqlport;
		String username = PathUtility.mysqlusername;
		String password = PathUtility.mysqlpassword;
		String db = PathUtility.mysqldbname;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("jdbc:mysql:" + host + ":" + port + "/" + db + "," + username + "," + password);
			con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + db, username, password);
			System.out.println("Database connected");
		} catch (Exception e) {
			System.out.println("Database connection failed " + e);
		}
	}

	public HashMap export_task(String nodeTaskId) {
		HashMap<String, String> result = new HashMap<String, String>();
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("select * from impp_export_task where node_task_id='" + nodeTaskId + "'");
			while (rs.next()) {
				result.put("task_code", rs.getString(2));
				result.put("node_task_id", rs.getString(3));
				result.put("job_code", rs.getString(4));
				result.put("node_id", rs.getString(5));
				result.put("task_type", rs.getString(6));
				result.put("task_data", rs.getString(7));
				result.put("submit_status", rs.getString(8));
				result.put("submit_data", rs.getString(9));
				result.put("request_time", rs.getString(10));
				result.put("distribute_time", rs.getString(11));
				result.put("ack_time", rs.getString(12));
				result.put("submit_time", rs.getString(13));
				result.put("user_agent", rs.getString(15));
			}

		} catch (Exception e) {
			System.out.println("export_task " + e);
			e.printStackTrace();
		}

		return result;
	}
	
	public HashMap export_task_using_taskCode(String taskCode) {
		HashMap<String, String> result = new HashMap<String, String>();
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("select * from impp_export_task where task_code='" + taskCode + "'");
			while (rs.next()) {
				result.put("task_code", rs.getString(2));
				result.put("node_task_id", rs.getString(3));
				result.put("job_code", rs.getString(4));
				result.put("node_id", rs.getString(5));
				result.put("task_type", rs.getString(6));
				result.put("task_data", rs.getString(7));
				result.put("submit_status", rs.getString(8));
				result.put("submit_data", rs.getString(9));
				result.put("request_time", rs.getString(10));
				result.put("distribute_time", rs.getString(11));
				result.put("ack_time", rs.getString(12));
				result.put("submit_time", rs.getString(13));
				result.put("user_agent", rs.getString(15));
			}

		} catch (Exception e) {
			System.out.println("export_task " + e);
			e.printStackTrace();
		}

		return result;
	}

	public HashMap export_task_count(String job_code) {
		HashMap<String, String> result = new HashMap<String, String>();
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("select count(*) from impp_export_task where job_code='" + job_code + "'");
			while (rs.next()) {
				result.put("count", Integer.toString(rs.getInt(1)));

			}

		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}

		return result;
	}
//returns task status
	public HashMap engagement_task_table(String engagementCode, String taskcode) {
		HashMap<String, String> result = new HashMap<String, String>();
		try {
			stmt = con.createStatement();
			// System.out.println("select * from `impp_task_"+engagementCode+"` where
			// task_code='" + taskcode + "'");
			rs = stmt.executeQuery(
					"select * from `impp_task_" + engagementCode + "` where task_code='" + taskcode + "' order by id limit 1");
			while (rs.next()) {
				result.put("task_status", rs.getString(13));

			}

		} catch (Exception e) {
			System.out.println("engagement_task_table " + e);
			e.printStackTrace();

		}

		return result;
	}
	//Uploaded task count
	public HashMap engagement_Uploaded_task_count(String engagementCode, String jobCode) {
		
		HashMap<String, String> result = new HashMap<String, String>();
		try {
			stmt = con.createStatement();
			
			rs = stmt.executeQuery(
					"select  count(distinct task_code) from `impp_task_" + engagementCode + "` where job_code='" + jobCode + "'");
			while (rs.next()) {
				result.put("count", Integer.toString(rs.getInt(1)));
				//System.out.println(rs.getInt(1));
			}

		} catch (Exception e) {
			System.out.println("engagement_task_table " + e);
			e.printStackTrace();

		}

		return result;
	}
	//Total task count
	public HashMap engagement_Total_task_count(String engagementCode, String jobCode) {
		
		HashMap<String, String> result = new HashMap<String, String>();
		try {
			stmt = con.createStatement();
			String query="select count(*) from `impp_task_" + engagementCode + "` where job_code='" + jobCode + "' and is_active=1";
			//System.out.println(query);
			
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				result.put("count", Integer.toString(rs.getInt(1)));

			}

		} catch (Exception e) {
			System.out.println("engagement_task_table " + e);
			e.printStackTrace();

		}

		return result;
	}
	
	public HashMap engagement_task_table_orderbyIdDesc(String engagementCode, String taskcode) {
		HashMap<String, String> result = new HashMap<String, String>();
		try {
			stmt = con.createStatement();
			// System.out.println("select * from `impp_task_"+engagementCode+"` where
			// task_code='" + taskcode + "'");
			rs = stmt.executeQuery(
					"select * from `impp_task_" + engagementCode + "` where task_code='" + taskcode + "' order by id desc limit 1");
			while (rs.next()) {
				result.put("task_status", rs.getString(13));

			}

		} catch (Exception e) {
			System.out.println("engagement_task_table " + e);
			e.printStackTrace();

		}

		return result;
	}
//Used for querying engmanent main table using task_master_id
	public HashMap engagement_task_table_query_usingTaskMasterId(String engagementCode, String taskMasterId) {
		HashMap<String, String> result = new HashMap<String, String>();
		try {
			stmt = con.createStatement();
			
			rs = stmt.executeQuery(
					"select * from `impp_task_" + engagementCode + "` where task_master_id='" + taskMasterId + "'");
			while (rs.next()) {
				result.put("task_status", rs.getString(13));
			//	System.out.println("mysql task status: "+rs.getString(13));

			}

		} catch (Exception e) {
			System.out.println("engagement_task_table " + e);
			e.printStackTrace();

		}

		return result;
	}

	
	
	public Boolean deleteTaskFromMysql(String EngagementCode, String jobcode, Boolean DeleteLog) {
		try {
			stmt = con.createStatement();
			String maintasksql = "delete from " + PathUtility.mysqldbname + ".`impp_task_" + EngagementCode
					+ "` where job_code='" + jobcode + "' and id>0";
			String exporttable = "delete from " + PathUtility.mysqldbname + ".impp_export_task where job_code='"
					+ jobcode + "' and id>0";
			String logtable = "delete   FROM  " + PathUtility.mysqldbname + ".`impp_user_task_log` where job_code='"
					+ jobcode + "' and id>0";

			System.out.println("maintasksql " + maintasksql);
			System.out.println("exporttable " + exporttable);
			System.out.println("logtable " + logtable);
			// rs = stmt.executeQuery("select * from `impp_task_"+engagementCode+"` where
			// task_code='" + taskcode + "'");
			if (DeleteLog == true) {
				stmt.executeUpdate(logtable);
			}
			stmt.executeUpdate(maintasksql);
			stmt.executeUpdate(exporttable);
			
			System.out.println("Delete Success");

		} catch (Exception e) {
			System.out.println("Mysql delete Operation failed " + e);
			e.printStackTrace();
			return false;

		}

		return true;
	}

}
