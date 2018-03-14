package controller;

import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.options;
import static spark.Spark.path;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.delete;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import category.Category;

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

		path("/api/1", () -> {

			before("//*", (req, res) -> {
				boolean first = true;
				res.header("Access-Control-Allow-Origin", "*");
				res.header("Access-Control-Allow-Methods", "*");
				res.header("Access-Control-Allow-Methods", "PUT,DELETE,GET,POST");
				res.header("Access-Control-Allow-Headers", "content-type");
				res.header("Access-Control-Allow-Headers", "*");
				res.status(200);
				// res.type("application/json");
				if (first) {
					first = false;
				} else {
					System.out.println();
				}
				if (!(req.requestMethod().equals("OPTIONS"))) {
					System.out.println("---------------------------------------------------");
					System.out.println("Received api call");
					System.out.println("---------------------------------------------------");
				}
			});
			path("/category", () -> {
				get("", (req, res) -> {
					System.out.println("API Call received was of the type: GET");
					System.out.println();
					String id = "";
					req.params(":id");
					Gson gson = new GsonBuilder().create();
					if (id.equals("")) {
						id = "All Categories";
						System.out.println("GET category: " + id);
						String jsonArray = gson.toJson(cat.getCategoryNames());

						if (jsonArray != null) {
							res.status(200);
							System.out.println("Requested data:" + jsonArray);
							System.out.println();
							return jsonArray;

						}
					}
					res.status(200);
					System.out.println("empty");
					return "ok";
				});

				post("", (req, res) -> {
					System.out.println("API Call received was of the type: POST");
					System.out.println();
					String id = req.body();
					Category category = new Gson().fromJson(req.body(), Category.class);
					if (id != null) {
						id = id.replace("\"", "");
					}
					if (cat.addCat(category.getName()) == 1) {
						res.type("application/json");
						System.out.println("POST category: " + id);
						System.out.println("Success 201: Created");
						System.out.println();
						res.status(201);
						return "{\"status\": \"ok\"}";
					} else {
						System.out.println("POST category attempt: " + id);
						System.out.println("Error 409: Conflict");
						System.out.println();
						res.status(409);
						return "conflict";
					}
				});

				put("/:id", (req, res) -> {
					System.out.println("API Call received was of the type: PUT");
					System.out.println();
					String id = req.params((":id"));
					System.out.println("PUT category: " + id);

					String newId = req.body();
					if (newId != null) {
						newId = newId.replace("\"", "");
					}
					System.out.println("Attempt to rename category " + id + " to " + newId);
					if (cat.renameCategory(id, newId)) {
						res.status(200);
						System.out.println();
						return "{\"status\": \"ok\"}";
					} else {
						res.status(404);
						System.out.println();
						return "not ok";
					}
				});

				delete("/:id", (req, res) -> {
					System.out.println("API Call received was of the type: PUT");
					System.out.println();
					String id = req.params((":id"));
					System.out.println("PUT category: " + id);

					String newId = req.body();
					if (newId != null) {
						newId = newId.replace("\"", "");
					}
					System.out.println("Attempt to DELETE category " + id);
					if (cat.removeCategory(id) > 0) {
						res.status(200);
						System.out.println();
						return "deleted";
					} else {
						res.status(404);
						System.out.println();
						return "404";
					}
				});

				options("/:id", (req, res) -> {
					// for (String s : req.headers()) {
					// System.out.println("Header: " + s);
					// }
					// System.out.println("Content Type: " + req.contentType());
					// System.out.println(
					// "API Call received was of the type: " +
					// req.headers("Access-Control-Request-Method"));
					// System.out.println("parameters: " + req.params());
					// System.out.println("data: " + req.body());
					// System.out.println("url: " + req.url());
					// System.out.println();
					// String id = req.params((":id"));
					// if
					// (req.headers("Access-Control-Request-Method").equals("DELETE"))
					// {
					// System.out.println("DELETE category: " + id);
					// }
					// if
					// (req.headers("Access-Control-Request-Method").equals("PUT"))
					// {
					// System.out.println("PUT category: " + id);
					//
					// String newId = req.body();
					// if (newId != null) {
					// newId = newId.replace("\"", "");
					// }
					// System.out.println("Attempt to rename " + id + " to " +
					// newId);
					// if (cat.renameCategory(id, newId)) {
					// res.status(200);
					// return "ok";
					// } else {
					// res.status(404);
					// return "not ok";
					// }
					//
					// }
					return "";
				});
			});
			path("/question", () -> {
				get("/:id", (req, res) -> {
					System.out.println("API Call received was of the type: GET");
					System.out.println();
					String id = req.params(":id");
					try {
						int question = Integer.parseInt(id);
						res.status(503);
					return "Request question failed, server returned " + res.status();

					} catch (NumberFormatException e) {
						System.out.println("GET question: " + id);
						res.status(503);
						return res.status();
			
					}
					

				
				});

	
				post("", (req, res) -> {
					System.out.println("API Call received was of the type: POST");
					System.out.println();
					res.status(503);
					return res.status();
				});
				
				//Ska det göras mer här, läs: If a PUT-request alters the correct answer...
				put("/:id", (req, res) -> {
					System.out.println("API Call received was of the type: PUT");
					String id = req.params(":id");
					String newQ = req.body();
					System.out.println("Try to PUT new quetion with id " + id + " and question is " + newQ);
					res.status(503);
					return res.status();
				});
				
				delete("/:id", (req, res) -> {
					String id = req.params(":id");
					System.out.println("API Call recieved was of the type: DELETE");
					System.out.println("Try to DELETE question with id " + id);
					res.status(503);
					return res.status();
				});

				options("/:id", (req, res) -> {
					System.out.println(
							"API Call received was of the type: " + req.headers("Access-Control-Request-Method"));
					System.out.println();
					String id = req.params((":id"));
					if (req.headers("Access-Control-Request-Method").equals("DELETE")) {
						System.out.println("DELETE question: " + id);
					}
					if (req.headers("Access-Control-Request-Method").equals("PUT")) {
						System.out.println("PUT question: " + id);
					}
					res.type("application/json");
					System.out.println();
					return "";
				});
			});
			
			path("/answer?q=", () -> {
				get(":id", (req, res) -> {
					String id = req.params(":id");
					System.out.println("API Call recieved was of the type: GET answer" );
					System.out.println("Try to GET answer connected to question id " + id);
					res.status(503);
					return res.status();
				});
				
				post(":id", (req, res) -> {
//					res.type("application/json");
					String id = req.params(":id");
					String answer = req.body();
					System.out.println("API Call recieved was of the type: POST answer" );
					System.out.println("Try to POST answer to question with id " + id + " with the answer " + answer);
					res.status(503);
					return "POST ANSWER " + res.status();
				});
				
				put(":id", (req, res) -> {
					String id = req.params(":id");
					String answer = req.body();
					System.out.println("API Call recieved was of the type: PUT answer" );
					System.out.println("Try to PUT modified answer with id " + id + " and new answer " + answer);
					res.status(503);
					return res.status();
				});
				
				delete("/:id", (req, res) -> {
					String id = req.params(":id");
					System.out.println("API Call recieved was of the type: DELETE answer" );
					System.out.println("Try to DELETE answer with id " + id);
					res.status(503);
					return res.status();
				});
				
				options("/:id", (req, res) -> {
					System.out.println(
							"API Call received was of the type: " + req.headers("Access-Control-Request-Method"));
					System.out.println();
					String id = req.params((":id"));
					if (req.headers("Access-Control-Request-Method").equals("DELETE")) {
						System.out.println("DELETE answer: " + id);
					}
					if (req.headers("Access-Control-Request-Method").equals("PUT")) {
						System.out.println("PUT question: " + id);
					}
					res.type("application/json");
					System.out.println();
					return "";
				});
			});
		});
	}
}