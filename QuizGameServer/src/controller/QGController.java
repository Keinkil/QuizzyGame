package controller;

import static spark.Spark.before;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.options;
import static spark.Spark.path;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.put;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import category.Category;
import qAndA.Question;

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
		QuestionController qc = new QuestionController();

		Question a1 = new Question("1 + 1 = ?");
		qc.addQuestion(a1);
		Question a2 = new Question("3 + 6 = ?");
		qc.addQuestion(a2);
		Question a3 = new Question("Är en cirkel rund?");
		qc.addQuestion(a3);
		Question a4 = new Question("Är en kub rund?");
		qc.addQuestion(a4);
		cat.getCat("Algebra").addQuestion(a1);
		cat.getCat("Algebra").addQuestion(a2);
		cat.getCat("Geometri").addQuestion(a3);
		cat.getCat("Geometri").addQuestion(a4);

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
						System.out.println("RIGHT");
						return "{\"status\": \"ok\"}";
					} else {
						res.status(404);
						System.out.println("WRONG");
						return "not ok";
					}
				});

				delete("/:id", (req, res) -> {
					System.out.println("API Call received was of the type: PUT");
					System.out.println();
					String id = req.params((":id"));
					System.out.println("DELETE category: " + id);

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
					return "";
				});
			});
			path("/question", () -> {

				get("/:id", (req, res) -> {
					System.out.println("API Call received was of the type: GET");
					String id = req.params(":id");
					if (id != null && !id.isEmpty()) {
						System.out.println("GET category: " + id);
						Gson gson = new GsonBuilder().create();
						String jsonArray = gson.toJson(cat.getCat(id).getQuestions());
						// String jsonArray = gson.toJson(qc.getQuestionBasedOnCategory(id));
						if (jsonArray != null) {
							res.status(200);
							System.out.println("Requested data:" + jsonArray);
							System.out.println();
							return jsonArray;
						} else {
							res.status(200);
							System.out.println("empty");
							return "{ \"status\": \"empty\" }";
						}
					} else {
						res.status(503);
						return "Request question failed, server returned " + res.status();
					}
				});

				post("", (req, res) -> {
					System.out.println("API Call received was of the type: POST");
					System.out.println();
					res.status(503);
					return res.status();
				});

				// Ska det göras mer här, läs: If a PUT-request alters the correct answer...
				put("/:id", (req, res) -> {
					System.out.println("API Call received was of the type: PUT");
					int id = Integer.parseInt(req.params(":id"));
					String reqb = req.body();
					System.out.println(reqb);
					reqb = reqb.replaceAll("\"", "");
					String[] test2 = reqb.split(",");
					for (String s : test2) {
						System.out.println(s);
					}
					for (int i = 0; i < test2.length; i++) {
						if (i == 0) {
							if (qc.changeQuestion(id, test2[i]) == 1) {
								res.status(200);
							} else {
								res.status(404);
							}
						} else {
							Question q = qc.getQ(id);
							if (cat.getCat(test2[i]) != null) {
								cat.getCat(test2[i]).addQuestion(q);
							}
						}
					}
					System.out.println("Try to PUT new question with id " + id + " and question is " + test2[0]);
					return res.status();
				});

				delete("/:id", (req, res) -> {
					String id = req.params(":id");
					System.out.println("API Call recieved was of the type: DELETE");
					System.out.println("Try to DELETE question with id " + id);
					int qid = Integer.parseInt(id);
					Question temp = qc.getQ(qid);
					if (qc.removeQuestion(qid) == 1) {
						res.status(200);
						for (Category c : cat.getAllCats()) {
							c.removeQuestion(temp);
						}
						return res.status();
					} else {
						res.status(503);
					}
					return res.status();
				});

				options("/:id", (req, res) -> {
					return "";
				});
			});

			path("/answer?q=", () -> {
				get(":id", (req, res) -> {
					String id = req.params(":id");
					System.out.println("API Call recieved was of the type: GET answer");
					System.out.println("Try to GET answer connected to question id " + id);
					res.status(503);
					return res.status();
				});

				post(":id", (req, res) -> {
					// res.type("application/json");
					String id = req.params(":id");
					String answer = req.body();
					System.out.println("API Call recieved was of the type: POST answer");
					System.out.println("Try to POST answer to question with id " + id + " with the answer " + answer);
					res.status(503);
					return "POST ANSWER " + res.status();
				});

				put(":id", (req, res) -> {
					String id = req.params(":id");
					String answer = req.body();
					System.out.println("API Call recieved was of the type: PUT answer");
					System.out.println("Try to PUT modified answer with id " + id + " and new answer " + answer);
					res.status(503);
					return res.status();
				});

				delete("/:id", (req, res) -> {
					String id = req.params(":id");
					System.out.println("API Call recieved was of the type: DELETE answer");
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