package controller;

import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.options;
import static spark.Spark.path;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.ipAddress;

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

		// before("/*", (req, res) -> {
		// System.out.println("Done before");
		// res.header("Access-Control-Allow-Origin", "*");
		// res.header("Access-Control-Allow-Methods", "DELETE, PUT");
		// res.status(200);
		// });
		//
		// get("/api/1/category/*", (req, res) -> {
		// res.type("application/json");
		// res.status(200);
		// String id = "";// req.params(":id");
		// Gson gson = new GsonBuilder().create();
		// if (id.contains("")) {
		// String jsonArray = gson.toJson(cat.getCategoryNames());
		// return jsonArray;
		// } else {
		// String jsonArray = gson.toJson("test");
		// return jsonArray;
		// }
		//
		// });
		//
		// // get("/api/1/question/:id", (req, res) -> {
		// //
		// // String id = req.params(":id"); // find out what/who they want to know
		// // about
		// // res.type("application/json"); // set response to json
		// // res.header("Access-Control-Allow-Origin", "*"); // Prevent errors due
		// // to requests from foreign origin
		// // res.status(200);
		// // System.out.println("Request received"); // Hey, we got the request
		// // Gson gson = new GsonBuilder().create();
		// // String jsonArray = gson.toJson("");
		// // return jsonArray;
		// //
		// // });
		//
		// options("/api/1/category/:id", (req, res) -> {
		// String id = req.params(":id");
		// System.out.println(id);
		// System.out.println(req.body());
		// System.out.println(req.requestMethod());
		// int result = cat.removeCategory(id);
		// System.out.println(result);
		// if (result < 0) {
		// res.status(404);
		// System.out.println("Not found");
		// return "asdasd";
		// }else if(result > 0){
		// res.status(200);
		// }
		// return "ok";
		//
		//
		// });
		//
		// post("/api/1/category/:id", (req, res) -> {
		// res.type("application/json");
		// res.status(200);
		// String id = req.params(":id");
		// Gson gson = new GsonBuilder().create();
		//
		// String jsonArray = gson.toJson(cat.addCat(id));
		// return jsonArray;
		// });

		path("/api/1", () -> {

			before("/*", (req, res) -> {
				boolean first = true;
				res.header("Access-Control-Allow-Origin", "*");
				res.header("Access-Control-Allow-Methods", "*");
				res.status(200);
				// res.type("application/json");
				if (first) {
					first = false;
				} else {
					System.out.println();
				}
				System.out.println("---------------------------------------------------");
				System.out.println("Received api call");
				System.out.println("---------------------------------------------------");

			});
			path("/category", () -> {
				get("", (req, res) -> {
					System.out.println("API Call received was of the type: GET");
					System.out.println();
					String id = "";
					req.params(":id");
					res.status(200);
					Gson gson = new GsonBuilder().create();
					if (id.equals("")) {
						id = "All Categories";
						System.out.println("GET category: " + id);
						String jsonArray = gson.toJson(cat.getCategoryNames());

						if (jsonArray != null) {
							res.status(200);
							System.out.println(jsonArray);
							return jsonArray;

						}
					}
					res.status(200);
					System.out.println("a");
					return " ";
				});

				post("", (req, res) -> {
					System.out.println("API Call received was of the type: POST");
					System.out.println();
					System.out.println(req.body());
					String catToAdd = req.body();
					if (cat.addCat(catToAdd) == 1) {
						res.type("application/json");
						String id = "";
						System.out.println("POST category: " + id);
						System.out.println();
						res.status(201);
						return "success";
					}else {
						res.status(409);
						return "conflict";
					}
				});
				options("", (req, res) -> {
					System.out.println(
							"API Call received was of the type: " + req.headers("Access-Control-Request-Method"));
					System.out.println();
					String id = req.params((":id"));
					if (req.headers("Access-Control-Request-Method").equals("DELETE")) {
						System.out.println("DELETE category: " + id);
					}
					if (req.headers("Access-Control-Request-Method").equals("PUT")) {
						System.out.println("PUT category: " + id);
					}
					res.type("application/json");
					return "";
				});
			});
			path("/question/*", () -> {
				get("/:id", (req, res) -> {
					System.out.println("API Call received was of the type: GET");
					System.out.println();
					String id = req.params((":id"));
					System.out.println("GET question: " + id);
					System.out.println();
					return "";
				});

				post("/:id", (req, res) -> {
					System.out.println("API Call received was of the type: POST");
					System.out.println();
					String id = req.params((":id"));
					System.out.println("POST question: " + id);
					System.out.println();
					return "";
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
		});
	}
}