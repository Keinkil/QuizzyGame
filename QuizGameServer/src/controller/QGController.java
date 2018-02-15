package controller;

import static spark.Spark.get;

import static spark.Spark.port;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * A controller class. Receives requests and acts upon them Uses Spark to handle
 * HTTP requests and Google Gson to return JSONs
 * 
 * @author Rikard Almgren
 * @version 1.0
 *
 */
public class QGController {
	
	/**
	 * Main method. Let's start the server
	 * 
	 * @param args
	 * @throws Exception
	 *             Something went wrong
	 */
	public static void main(String[] args) throws Exception {
		// Set Spark to listen on port 5000.
		port(5000);
		CatController cat = new CatController();
		
		get("/api/1/category/", (req, res) -> {
			System.out.println("get/api/1/category");
			res.type("application/json");
			res.header("Access-Control-Allow-Origin", "*");
			res.status(200);
			String id = "";//req.params(":id");
			Gson gson = new GsonBuilder().create();
			if (id.contains("")) {
				String jsonArray = gson.toJson(cat.getCategoryNames());
				return jsonArray;
			} else {
				String jsonArray = gson.toJson("test");
				return jsonArray;
			}

		});
		/*
		 * request for tweets, send an API call to twitter using TwittCalls
		 */
		get("/api/1/questions/:id", (req, res) -> {
			System.out.println("getting here");
			String id = req.params(""); // find out what/who they want to know about
			System.out.println("id -  " + id);
			System.out.println("get/api/questions/" + id);
			res.type("application/json"); // set response to json
			res.header("Access-Control-Allow-Origin", "*"); // Prevent errors due to requests from foreign origin
			res.status(200);
			System.out.println("Request received"); // Hey, we got the request
			Gson gson = new GsonBuilder().create();
			
			
			String[] testQuestions = new String[4];
			testQuestions[0] = "vad e tomten?";
			testQuestions[1] = "vad e påskharen?";
			testQuestions[2] = "vad e buse?";
			testQuestions[3] = "vad e spöken?";
			
			String jsonArray = gson.toJson(testQuestions);		
			
			return jsonArray;

		});
	}
	
	

}