package projSpecificUtilities;

import static com.jayway.restassured.RestAssured.given;

import org.hamcrest.Matcher;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.filter.log.RequestLoggingFilter;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.matcher.ResponseAwareMatcher;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import com.mongodb.util.JSON;

import config.PathUtility;

public class TaskAdd {

	Rethink_query rethink;
	JsonParser parser = new JsonParser();
	String JsonRequest;

	@SuppressWarnings("unchecked")
	public void add(String UserCode, String Jobcode, int noOfTasks, String token, String job_name)
			throws ParseException {
		int count = 0;
		int tasksToBeAdded = noOfTasks;
		String img_path = PathUtility.task_img_path;

		RestAssured.baseURI = PathUtility.APIurl + "/impp/imerit/platform/task/add/0";

		String jreq = "{\"userCode\":\"" + UserCode + "\",\"jobCode\":\"" + Jobcode + "\",\"task\":[{\"img_path\":\""
				+ PathUtility.task_img_path + "\"},{\"config\":[]}],\"batchNum\":\"BT-1254\",\"orderSort\":5}";
		String psubm = "{\"userCode\":\"" + UserCode + "\",\"jobCode\":\"" + Jobcode
				+ "\",\"task\":[{\"rangedthumbs\":[{\"frame_no\":1,\"toBeDeleted\":false,\"fileSignName\":\""
				+ PathUtility.psubmitfile + "\"},{\"frame_no\":2,\"toBeDeleted\":false,\"fileSignName\":\""
				+ PathUtility.psubmitfile + "\"},{\"frame_no\":3,\"toBeDeleted\":false,\"fileSignName\":\""
				+ PathUtility.psubmitfile + "\"},{\"frame_no\":4,\"toBeDeleted\":false,\"fileSignName\":\""
				+ PathUtility.psubmitfile + "\"},{\"frame_no\":5,\"toBeDeleted\":false,\"fileSignName\":\""
				+ PathUtility.psubmitfile + "\"},{\"frame_no\":6,\"toBeDeleted\":false,\"fileSignName\":\""
				+ PathUtility.psubmitfile + "\"},{\"frame_no\":7,\"toBeDeleted\":false,\"fileSignName\":\""
				+ PathUtility.psubmitfile + "\"},{\"frame_no\":8,\"toBeDeleted\":false,\"fileSignName\":\""
				+ PathUtility.psubmitfile + "\"},{\"frame_no\":9,\"toBeDeleted\":false,\"fileSignName\":\""
				+ PathUtility.psubmitfile + "\"},{\"frame_no\":10,\"toBeDeleted\":false,\"fileSignName\":\""
				+ PathUtility.psubmitfile + "\"}]},"
				+ "{\"config\":{\"clientUrl\":\"\",\"ajaxOpTask\":\"\",\"annotation\":{\"color\":[\"#f61717\",\"#76e743\"],\"reload\":true,\"drawable\":{\"bbox\":{\"enable\":true,\"metaInfo\":{\"diagonal\":true},\"fill-opacity\":0,\"min-diagonal\":30,\"truncate-enable\":false,\"crosshair-enable\":true},\"polygon\":{\"enable\":false,\"metaInfo\":{\"vertex\":true},\"fill-opacity\":0,\"truncate-enable\":false,\"crosshair-enable\":false},\"keypoint\":{\"enable\":false,\"radius\":3,\"fill-opacity\":0,\"crosshair-enable\":false},\"crosshair-color\":\"red\"},\"prepopulateClassification\":true},\"constraint\":[{\"rule\":[{\"type\":\"annotation_comment\",\"value\":[1,2,3]}],\"entity\":[]}],\"grid_pixel\":30,\"redisOption\":0,\"backendApiUrl\":\"\",\"commentOptions\":[{\"text\":\"--Please select a comment--\",\"value\":\"\"},{\"text\":\"Image too Blurry\",\"value\":\"Image too Blurry\"},{\"text\":\"No Annotation Points\",\"value\":\"No Annotation Points\"}],\"clipboard_store\":\"CLIPBOARD\",\"config_data_url\":\"config/\",\"static_data_url\":\"data/\",\"initialImageLoad\":3}},{\"annotationClass\":{\"note\":false,\"attribute\":[{\"name\":\"occluded\",\"value\":\"binary\"}],\"classification\":{\"entity\":[{\"id\":1,\"name\":\"Pedestrians\",\"shape\":[\"bbox\"],\"Parent\":\"P0\",\"toplevel\":true},{\"id\":2,\"name\":\"Vehicles\",\"shape\":[\"bbox\"],\"Parent\":\"P0\",\"toplevel\":true}],\"classification\":\"type\"}}},{\"category\":\"batch0/invoice\"},{\"sub_category\":\"batch0%2Finvoice\"}]}";

		// System.out.println(psubm);
		if (job_name.equalsIgnoreCase("multi_psubmit_flow")) {
			JsonRequest = psubm;
		} else {
			JsonRequest = jreq;
		}

		RestAssured.useRelaxedHTTPSValidation();

		// API Execution...
		while (count < tasksToBeAdded) {
			Response res = null;
			try {
				res = given().header("Authorization", token)/*.filter(new RequestLoggingFilter())*/
						.contentType(ContentType.JSON).body(JsonRequest).when().contentType(ContentType.JSON).post().then()
						.contentType(ContentType.JSON).extract().response();
			} catch (Exception e1) {

				e1.printStackTrace();
				System.out.println("Task Add API Connection Issue: " + e1);
			}

			System.out.println("JSON Response " + res.asString() + " Status code " + res.statusLine());
			if (res.statusLine().toString().contains("200")) {

				System.out.println("task added for: " + UserCode + " JobCode: " + Jobcode);
			} else {
				System.out.println("task NOT added for: " + UserCode + " JobCode: " + Jobcode);

			}
			count++;
		}
	}

	public int QCjobstoReady(int qcPercentagearg, String JobCode, String nodeId, int noOfTaskToBeDone) {
		int result = 0;
		int BatchSize = 0;
		int noOfJobsToBeReady = 0;
		int optasksReady = 0;

		rethink = new Rethink_query();

		int qcPercentage = qcPercentagearg; // Set QC Percentage
		if (qcPercentage == 100) {
			BatchSize = 1;
		} else if (qcPercentage == 50) {
			BatchSize = 2;
		} else if ((qcPercentage % 20) == 0) {
			BatchSize = 5;
		} else {
			BatchSize = 10;
		}

		// Job to be ready per batch
		noOfJobsToBeReady = (BatchSize * qcPercentage) / 100;
		// Available OP tasks in ready state
		// optasksReady = rethink.main_task_list_status_count(JobCode, nodeId, "op",
		// "node_status", "ready");

		// If all OP tasks are done expected count of QC nodes in ready state
		noOfJobsToBeReady = (int) (Math.floor((noOfTaskToBeDone / BatchSize)) * noOfJobsToBeReady);

		return noOfJobsToBeReady;
	}

}
