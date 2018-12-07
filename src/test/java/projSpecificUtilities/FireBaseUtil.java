package projSpecificUtilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.google.api.core.ApiFuture;
import com.google.api.gax.paging.Page;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.WriteResult;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import config.PathUtility;

public class FireBaseUtil {
	Firestore db;
	DocumentReference docRef;
	FirestoreOptions fsoptions;

	public FireBaseUtil() {
		try {			
			GoogleCredentials credentials = GoogleCredentials
					.fromStream(new FileInputStream(".\\TestData\\googleCreds.json"))
					.createScoped(Arrays.asList("https://www.googleapis.com/auth/cloud-platform"));
			Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

			System.out.println("Buckets:");
			Page<Bucket> buckets = storage.list();
			for (Bucket bucket : buckets.iterateAll()) {
				System.out.println(bucket.toString());
			}
			// System.out.println(PathUtility.firebaseProjectID);
			/*fsoptions = FirestoreOptions.newBuilder().setTimestampsInSnapshotsEnabled(true).build();
			Firestore firestore = fsoptions.getService();*/
			
			FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(credentials)
					.setProjectId("platform-203105").build();

			FirebaseApp.initializeApp(options);
			db = FirestoreClient.getFirestore();
			System.out.println("FireBase Connection established");
		} catch (Exception e) {
			System.out.println("FireBase Connection Failed: " + e);
			e.printStackTrace();
		}
	}
	
	public boolean initiateFirestore(String nodeTaskId,String userCode,long port)
	{
		try {
			docRef = db.collection("tasks").document(nodeTaskId);
			
			Map<String, Object> data = new HashMap<>();
			data.put("userCode", "indrajit.b@imerit.net");
			data.put("port",1540554643929L ); 
			data.put("PLATFORM_API", "https://itestapi.imerit.net:32845/");
			data.put("PLATFORM_TASK_REQUEST", "impp/imerit/platform/task/send/to/operator/0");
			data.put("PLATFORM_TASK_RESPONSE", "impp/imerit/platform/operator/task/submit/0");
			data.put("PLATFORM_TASK_RESPONSE_DIFFERENTIAL", "impp/imerit/platform/operator/task/submit/1");
			
			ApiFuture<WriteResult> result =docRef.update(data);
			System.out.println("initiateFirestore Update time : " + result.get().getUpdateTime()+" nodeTaskId: "+nodeTaskId+" userCode: "+userCode);
			return true ;
		} catch (Exception e) {
			System.out.println("initiateFirestore Failed: "+e);
			e.printStackTrace();
			return false;
		} 
	}
	
	public void setStatus(String submitType,Boolean next)
	{
		try {
			Map<String, Object> data = new HashMap<>();
			data.put("submit_status", submitType);
			data.put("next",next ); 
			
			ApiFuture<WriteResult> result =docRef.update(data);
			System.out.println("Status Update time : " + result.get().getUpdateTime());
		} catch (Exception e) {
			System.out.println("setStatus Firestore Failed: "+e);
			e.printStackTrace();
		} 
	}
	
	public void submit(int frameno,String comment,int duration)
	{
	
		try {
			Map<String, Object> psubmit_data = new HashMap<>();
			psubmit_data.put("Frame_no", frameno);
			psubmit_data.put("comment",comment ); 
			psubmit_data.put("duration",duration);
			psubmit_data.put("judgement", "{\"object\":{\"timestamp\":1537017121792,\"metainfo\":{\"image\":{\"height\":320,\"width\":320,\"url\":\"https://s3-ap-southeast-1.amazonaws.com/imerit-solution/client/medtronic/polyp/polyp_a%2Fpolyp_a/aorsrA3300_2014408.png\"},\"annotation\":{\"enabled_count\":1}},\"annotateddata\":[{\"attribute\":[],\"class\":[[\"type\",\"polyp_a\"]],\"coordinates\":[{\"x\":150,\"y\":250},{\"x\":150,\"y\":250},{\"x\":150,\"y\":250}],\"deleted\":true,\"id\":\"aiG6m1534779669719\",\"mapelem\":[\"attribute\",\"class\"],\"object_type\":\"polygon\"},{\"attribute\":[],\"class\":[[\"type\",\"polyp_a\"]],\"coordinates\":[{\"x\":150,\"y\":250},{\"x\":150,\"y\":250}],\"deleted\":false,\"id\":\"fNb711534779706566\",\"mapelem\":[\"class\",\"attribute\"],\"object_type\":\"polygon\"}],\"formateddata\":[{\"id\":\"aiG6m1534779669719\",\"object_type\":\"polygon\",\"coordinates\":[{\"x\":150,\"y\":250},{\"x\":150,\"y\":250},{\"x\":150,\"y\":250},{\"x\":150,\"y\":250}],\"deleted\":false,\"attribute\":{},\"class\":{\"type\":\"polyp_a\"}}]}}");
			
			
			ApiFuture<WriteResult> res2 = docRef.collection("submit_data").document("key1").collection("annotations").document(Integer.toString(frameno)).set(psubmit_data);
			System.out.println("PSUBMIT Update time : " + res2.get().getUpdateTime());
		} catch (Exception e) {
			System.out.println("submit Firestore Failed: "+e);
			e.printStackTrace();
		}
	}

}
