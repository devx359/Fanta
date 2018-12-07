package projSpecificUtilities;

import java.util.Map;

import com.jayway.jsonpath.JsonPath;
import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;
import com.rethinkdb.net.Cursor;

import config.PathUtility;

public class Rethink_query {
	Connection conn;
	RethinkDB r;
	String node_tsk_id = null;
	String task_code = null;
	String reques_id = null;
	String user_code = null;
	String response_status = "Submit";
	String Reqstatus = null;

	public Rethink_query() {
		r = RethinkDB.r;
		// conn =
		// r.connection().hostname("104.196.151.173").port(28015).authKey("passW0rd#123").connect();
		conn = r.connection().hostname(PathUtility.RethinkUrl).port(PathUtility.RethinkPort)
				.authKey(PathUtility.RethinkPassword).connect();
	}

	public void tableDelete(String jobCode) {
		try {
			r.db(PathUtility.RethinkDB).table("task_request").filter(r.hashMap("job_code", jobCode)).delete().run(conn);
			r.db(PathUtility.RethinkDB).table("main_task_list").filter(r.hashMap("job_code", jobCode)).delete()
					.run(conn);
			r.db(PathUtility.RethinkDB).table("node_wise_task_data").filter(r.hashMap("job_code", jobCode)).delete()
					.run(conn);
			//r.db(PathUtility.RethinkDB).table("user_task").filter(r.hashMap("job_code", jobCode)).delete().run(conn);
			//r.db(PathUtility.RethinkDB).table("task_response").filter(r.hashMap("job_code", jobCode)).delete().run(conn);
			r.db(PathUtility.RethinkDB).table("analytics").filter(r.hashMap("job_code", jobCode)).delete().run(conn);
			r.db(PathUtility.RethinkDB).table("task_ready_counter").filter(r.hashMap("job_code", jobCode)).delete()
					.run(conn);
			r.db(PathUtility.RethinkDB).table("user_task_log").filter(r.hashMap("job_code", jobCode)).delete()
					.run(conn);

			System.out.println("All Rethinks tables 1)main_task_list 2)task_request 3)node_wise_task_data 4)analytics 5)task_ready_counter 6)user_task_log for job code: "+jobCode+" truncated");
		} catch (Exception e) {
			System.out.println("Rethink table truncate issue " + e);
		}

	}

	public Object task_request_node_task_id_extract2(String jobcode, String node, String usercode) {

		Object result = null;
		String Reqstatus = "dumb";
		int time = 0;

		while (!Reqstatus.equalsIgnoreCase("dispatched") && (time < 60000)) { // wait max 1 minute for task to be
																				// dispatched
			// System.out.println("Checking Task request table for any dispatched task..");

			try {

				Cursor cur2 = r
						.db(PathUtility.RethinkDB).table("task_request").filter(r.hashMap("user_code", usercode)
								.with("job_code", jobcode).with("node_id", node).with("status", "dispatched"))
						.run(conn);

				for (Object doc2 : cur2) {

					Map map1 = (Map) doc2;
					Reqstatus = map1.get("status").toString();
					System.out.println("reqstatus " + Reqstatus);
					if (Reqstatus.equalsIgnoreCase("dispatched")) {
						node_tsk_id = map1.get("node_task_id").toString();
					}

					result = doc2;

				}
				System.out.println("Waiting 3sec for task to be dispatched in task Request table..");
				Thread.sleep(3000);
				time = time + 3000;

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Unable to get node_tsk_id from task_request table");

			}

		}
		if (time >= 60000) {
			System.out.println("No Tasks were dispatched");
		}

		return result;
	}

	public Object task_request_node_task_id_extract(String Request_id) {

		Object result = null;
		Reqstatus = "pending";

		try {
			System.out.println("reth1");
			Cursor cur2 = r.db(PathUtility.RethinkDB).table("task_request").filter(r.hashMap("request_id", Request_id))
					.run(conn);

			for (Object doc2 : cur2) {

				Map map1 = (Map) doc2;
				Reqstatus = map1.get("status").toString();
				System.out.println("reqstatus " + Reqstatus);
				if (Reqstatus.equalsIgnoreCase("dispatched")) {
					node_tsk_id = map1.get("node_task_id").toString();
				}

				result = doc2;

			}

			while (Reqstatus.equalsIgnoreCase("pending"))

			{

				Cursor cur1 = r.db(PathUtility.RethinkDB).table("task_request")
						.filter(r.hashMap("request_id", Request_id)).run(conn);
				System.out.println("reth4");
				for (Object doc1 : cur1) {
					System.out.println("reth4a");
					Map map = (Map) doc1;

					Reqstatus = map.get("status").toString();
					System.out.println("reqstatus " + Reqstatus);
					if (Reqstatus.equalsIgnoreCase("dispatched")) {
						node_tsk_id = map.get("node_task_id").toString();
					}

					result = doc1;
					System.out.println(
							"Querying for Task request status to change for Request_ID: " + Request_id + "...");

				}

			}

		} catch (Exception e) {

			System.out.println("Unable to get node_tsk_id from task_request table");
			// result=false;

		}

		return result;

	}

	public Object task_request(String Request_id) {

		Object result = null;

		try {
			Cursor cur1 = r.db(PathUtility.RethinkDB).table("task_request").filter(r.hashMap("request_id", Request_id))
					.run(conn);

			for (Object doc1 : cur1) {
				// System.out.println("Task Request Table " + doc1.toString() + " for " +
				// user_code);
				Map map = (Map) doc1;
				node_tsk_id = map.get("node_task_id").toString();
				result = doc1;

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("task_request Rethink query error or data not found");
		}

		return result;

	}

	// Node wise task data details
	public Object node_wise_task_data(String Request_id) {

		Object result = null;

		try {

			Cursor cur33 = r.db(PathUtility.RethinkDB).table("node_wise_task_data")
					.filter(r.hashMap("request_id", Request_id))

					.map(val -> val.toJson()).run(conn);

			for (Object doc1 : cur33) {
				result = doc1;
				String res = doc1.toString();
				task_code = JsonPath.read(res, "$.task_code");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("node_wise_task_data Rethink query error or data not found");
		}

		return result;
	}

	public Object main_task_list() {
		Object result = null;
		try {
			Cursor cur4 = r.db(PathUtility.RethinkDB).table("main_task_list").filter(r.hashMap("task_code", task_code))
					.map(val -> val.toJson()).run(conn);

			for (Object doc1 : cur4) {
				// System.out.println("Table main_task_list data " + doc1.toString() + " for " +
				// user_code);
				// Map map= (Map) doc1;
				// task_code=map.get("task_code").toString();
				// node_tsk_id=doc1.toString();
				result = doc1;

			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("main_task_list Rethink query error or data not found");
		}
		return result;
	}

	public Object main_task_list(String task_code) {
		Object result = null;
		try {
			Cursor cur4 = r.db(PathUtility.RethinkDB).table("main_task_list").filter(r.hashMap("task_code", task_code))
					.map(val -> val.toJson()).run(conn);
			if (cur4.hasNext() == false) {
				result = "no_data";
			} else {
				for (Object doc1 : cur4) {

					result = doc1;

				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("main_task_list Rethink query error or data not found");
		}
		return result;
	}

	public Object node_wise_task_data_usingNodeTaskId(String node_task_id) {
		Object result = null;
		try {
			Cursor cur4 = r.db(PathUtility.RethinkDB).table("node_wise_task_data")
					.filter(r.hashMap("node_task_id", node_task_id)).map(val -> val.toJson()).run(conn);
			if (cur4.hasNext() == false) {
				result = "no_data";
			} else {
				for (Object doc1 : cur4) {

					result = doc1;

				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("main_task_list Rethink query error or data not found");
		}
		return result;
	}

	public Object task_request_usingNodeTaskId(String node_task_id) {
		Object result = null;
		try {
			Cursor cur4 = r.db(PathUtility.RethinkDB).table("task_request")
					.filter(r.hashMap("node_task_id", node_task_id)).map(val -> val.toJson()).run(conn);
			if (cur4.hasNext() == false) {
				result = "no_data";
			} else {
				for (Object doc1 : cur4) {

					result = doc1;

				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("main_task_list Rethink query error or data not found");
		}
		return result;
	}

	public Object task_response_usingNodeTaskId(String node_task_id) {
		Object result = null;
		try {
			Cursor cur4 = r.db(PathUtility.RethinkDB).table("task_response")
					.filter(r.hashMap("node_task_id", node_task_id)).map(val -> val.toJson()).run(conn);
			if (cur4.hasNext() == false) {
				result = "no_data";
			} else {
				for (Object doc1 : cur4) {

					result = doc1;

				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("main_task_list Rethink query error or data not found");
		}
		return result;
	}

	// user_task table

	public Object user_task() {
		Object result = null;
		try {
			Cursor cur5 = r.db(PathUtility.RethinkDB).table("user_task").filter(r.hashMap("node_task_id", node_tsk_id))
					// .map(val -> val.toJson())
					.run(conn);

			for (Object doc1 : cur5) {

				result = doc1;

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("user_task Rethink query error or data not found");
		}
		return result;
	}

	// task response
	public Object task_response() {
		Object result = null;
		try {
			Cursor cur6 = r.db(PathUtility.RethinkDB).table("task_response")
					.filter(r.hashMap("node_task_id", node_tsk_id)).run(conn);

			for (Object doc1 : cur6) {
				result = doc1;

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("task_response Rethink query error or data not found");
		}
		return result;
	}

	public Object task_response_with_status_change_processed(String SubmitType) {
		Object result = null;
		response_status = "Submit";
		String statusToCheck = null;
		if (SubmitType.equalsIgnoreCase("p_submit")) {
			statusToCheck = "p_submit";
		} else {
			statusToCheck = "processed";
		}
		try {
			while (!response_status.equalsIgnoreCase(statusToCheck)) {
				Cursor cur6 = r.db(PathUtility.RethinkDB).table("task_response")
						.filter(r.hashMap("node_task_id", node_tsk_id)).run(conn);

				for (Object doc1 : cur6) {
					System.out.println("Querying task_response table for change of status for " + reques_id
							+ " node_tsk_id:" + node_tsk_id + "......");
					Map map = (Map) doc1;
					response_status = map.get("status").toString();
					result = doc1;
					Thread.sleep(5000);// wait for 5 seconds before checking again

				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("task_response_with_status_change_processed Rethink query error or data not found");
		}
		return result;
	}

	public Object task_response_with_status_change_processed_GiveUp() {
		Object result = null;
		response_status = "give_up";
		try {
			while (response_status.equalsIgnoreCase("give_up")) {
				Cursor cur6 = r.db(PathUtility.RethinkDB).table("task_response")
						.filter(r.hashMap("node_task_id", node_tsk_id)).run(conn);

				for (Object doc1 : cur6) {
					System.out.println("Querying task_response table for change of status for " + reques_id
							+ " node_tsk_id:" + node_tsk_id + "......");
					Map map = (Map) doc1;
					response_status = map.get("status").toString();
					result = doc1;
					Thread.sleep(5000);// wait for 5 seconds before checking again

				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("task_response_with_status_change_processed Rethink query error or data not found");

		}
		return result;
	}

	/*
	 * String OpOrQc pass :either OP or QC as value String nodeId pass :node id for
	 * example c6 or c10 String Requiredkey :pass the json key for example
	 * node_status,distribute_count etc String RequiredStatus :Desired status you
	 * need to find count of for example bufferer ,ready ,delivered
	 */
	public int main_task_list_status_count(String JobCode, String nodeId, int nodenumber, String Requiredkey,
			String RequiredStatus) {
		int result = 0;
		String status = null;
		int count = 0;
		int i = 0;
		/*
		 * if (OpOrQc.equalsIgnoreCase("qc")) { i = 1; }
		 */
		i = nodenumber; // This is just for json path used below

		try {
			Cursor cursor = r.db(PathUtility.RethinkDB).table("main_task_list").filter(r.hashMap("job_code", JobCode))
					.pluck("dependency_table").map(val -> val.toJson()).run(conn);

			for (Object doc : cursor) {
				// System.out.println(doc.toString());

				String str = doc.toString();
				status = JsonPath.read(str, "$.dependency_table.[" + i + "]." + nodeId + "." + Requiredkey);
				if (status.equalsIgnoreCase(RequiredStatus)) {
					count++;
				}

			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		result = count;
		return result;

	}

	// Counts Number of tasks in Rethink
	public int main_task_list_job_count(String JobCode_TaskCode_MasterTaskCode) {
		int count = 99;
		try {
			
			if(JobCode_TaskCode_MasterTaskCode.startsWith("JC-"))
			{
				String JobCode=JobCode_TaskCode_MasterTaskCode;
			Object cursor = r.db(PathUtility.RethinkDB).table("main_task_list").filter(r.hashMap("job_code", JobCode))
					.count().run(conn);

			count = Integer.parseInt(cursor.toString());
			}
			
			 if(JobCode_TaskCode_MasterTaskCode.startsWith("TC-"))
			{
				String task_code=JobCode_TaskCode_MasterTaskCode;
					Object cursor = r.db(PathUtility.RethinkDB).table("main_task_list").filter(r.hashMap("task_code", task_code))
							.count().run(conn);

					count = Integer.parseInt(cursor.toString());
					
			}
			
			 if(JobCode_TaskCode_MasterTaskCode.startsWith("TM-"))
			{
				String task_master_id=JobCode_TaskCode_MasterTaskCode;
				Object cursor = r.db(PathUtility.RethinkDB).table("main_task_list").filter(r.hashMap("task_master_id", task_master_id))
						.count().run(conn);

				count = Integer.parseInt(cursor.toString());
				}
			
				

		} catch (Exception e) {

			e.printStackTrace();
		}

		return count;

	}

	public String main_task_list_QCnode_status(String task_code, String nodeId) {

		String status = null;

		int i = 1;

		try {
			Cursor cursor = r.db(PathUtility.RethinkDB).table("main_task_list")
					.filter(r.hashMap("task_code", task_code)).pluck("dependency_table").map(val -> val.toJson())
					.run(conn);

			for (Object doc : cursor) {

				String str = doc.toString();
				status = JsonPath.read(str, "$.dependency_table.[" + i + "]." + nodeId + ".node_status");

			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		return status;

	}
	
	//you can pass node_task_id or task_code
	public String main_task_list_status_check_with_TMid(String taskcodeOrNodetaskId, String nodeId,int nodeserial) {

		String status = null;

		int i = nodeserial;

		try {
		if(taskcodeOrNodetaskId.startsWith("NT-", 1))
		{
			
			Cursor cur = r.db(PathUtility.RethinkDB).table("node_wise_task_data")
					.filter(r.hashMap("node_task_id", node_tsk_id)).pluck("task_code").map(val -> val.toJson())
					.run(conn);
			
			for (Object doc : cur) {
				System.out.println(doc.toString());
			//	Map map1 = (Map) doc;
				String str = doc.toString();
				task_code = JsonPath.read(str, "$.task_code" );
				}
			Cursor cursor = r.db(PathUtility.RethinkDB).table("main_task_list")
					.filter(r.hashMap("task_code", task_code)).pluck("dependency_table").map(val -> val.toJson())
					.run(conn);

			for (Object doc : cursor) {

				String str = doc.toString();
				status = JsonPath.read(str, "$.dependency_table.[" + i + "]." + nodeId + ".node_status");

			}
		}
		else 
		{
			this.task_code=taskcodeOrNodetaskId;
			Cursor cursor = r.db(PathUtility.RethinkDB).table("main_task_list")
					.filter(r.hashMap("task_code", task_code)).pluck("dependency_table").map(val -> val.toJson())
					.run(conn);

			for (Object doc : cursor) {

				String str = doc.toString();
				status = JsonPath.read(str, "$.dependency_table.[" + i + "]." + nodeId + ".node_status");

			}
		}
		} 
		catch (Exception e) {

			e.printStackTrace();
		}
		return status;

	}

	public String main_task_list_status_check_taskStatus(String task_code) {

		String status = null;

		try {
			Cursor cursor = r.db(PathUtility.RethinkDB).table("main_task_list")
					.filter(r.hashMap("task_code", task_code)).map(val -> val.toJson()).run(conn);

			for (Object doc : cursor) {
				System.out.println(doc.toString());
				// Map map1 = (Map) doc;
				String str = doc.toString();
				status = JsonPath.read(str, "$.task_status");
			}

		}

		catch (Exception e) {

			e.printStackTrace();
		}
		return status;

	}
}
