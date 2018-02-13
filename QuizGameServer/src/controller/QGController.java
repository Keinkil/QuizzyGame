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
			res.type("application/json");
			res.header("Access-Control-Allow-Origin", "*");
			res.status(200);
			String id = "";//req.params(":id");
			System.out.println("test");
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
		get("/api/1/question/:id", (req, res) -> {

			String id = req.params(":id"); // find out what/who they want to know about
			res.type("application/json"); // set response to json
			res.header("Access-Control-Allow-Origin", "*"); // Prevent errors due to requests from foreign origin
			res.status(200);
			System.out.println("Request received"); // Hey, we got the request
			Gson gson = new GsonBuilder().create();
			String jsonArray = gson.toJson("");
			return jsonArray;

		});
	}
	
	

}