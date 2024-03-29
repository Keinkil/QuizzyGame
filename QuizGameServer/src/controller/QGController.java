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
import qAndA.Answer;
import qAndA.Question;

/**
 * A controller class. Receives requests and acts upon them Uses Spark to handle
 * HTTP requests and Google Gson to return JSONs
 * 
 */
public class QGController {

	/**
	 * Main method. Adds some dummy-questions and dummy-answers.
	 * Let's start the server
	 * @param args
	 * @throws Exception
	 *             Something went wrong
	 */
	public static void main(String[] args) throws Exception {
		// Set Spark to listen on port 5000.
		port(5000);
		CatController cat = new CatController();
		QuestionController qc = new QuestionController();
		Gson gson = new GsonBuilder().create();
		
	    Question q1 = new Question("Lös följande ekvation: 9+3x=15");
		qc.addQuestion(q1);
		Answer a1 = new Answer("x=2", true);
		q1.addAnswer(a1);
		
		Question q2 = new Question("Vad är x? 2⋅x+2=12");
		qc.addQuestion(q2);
		Answer a2 = new Answer("x=5", true);
		q2.addAnswer(a2);
		
		Question q3 = new Question("En cirkel har diametern 12cm, beräkna dess area");
		qc.addQuestion(q3);
		Answer a3 = new Answer("113,1 cm2", true);
		q3.addAnswer(a3);
		
		Question q4 = new Question("Hur mycket är 4m2 uttryckt i centimeter?");
		qc.addQuestion(q4);
		Answer a4 = new Answer("40000", true);
		q4.addAnswer(a4);
		
		Question q5 = new Question("Skriv om 3/4 i procentform");
		qc.addQuestion(q5);
		Answer a5 = new Answer("75%", true);
		q5.addAnswer(a5);
		
		Question q6 = new Question("Hur mycket är 10 % av 20 % av 3000 kr?");
		qc.addQuestion(q6);
		Answer a6 = new Answer("60 kr", true);
		q6.addAnswer(a6);
		
		Question q7 = new Question("Välj det uttryck som är detsamma som log(x⋅y)");
		qc.addQuestion(q7);
		Answer a7 = new Answer("log(x)+log(y)", true);
		q7.addAnswer(a7);
		
		Question q8 = new Question("Lös följande ekvation med logaritmer: 10x=50");
		qc.addQuestion(q8);
		Answer a8 = new Answer("x=1,7", true);
		q8.addAnswer(a8);
		
		cat.getCat("Algebra").addQuestion(q1);
		cat.getCat("Algebra").addQuestion(q2);
		cat.getCat("Geometri").addQuestion(q3);
		cat.getCat("Geometri").addQuestion(q4);
		cat.getCat("Procent").addQuestion(q5);
		cat.getCat("Procent").addQuestion(q6);
		cat.getCat("Logaritmer").addQuestion(q7);
		cat.getCat("Logaritmer").addQuestion(q8);

		/**
		 * PATH: base route
		 */
		path("/api/1", () -> {

			before("//*", (req, res) -> {
				boolean first = true;
				res.header("Access-Control-Allow-Origin", "*");
				res.header("Access-Control-Allow-Methods", "*");
				res.header("Access-Control-Allow-Methods", "PUT,DELETE,GET,POST");
				res.header("Access-Control-Allow-Headers", "content-type");
				res.header("Access-Control-Allow-Headers", "*");
				res.status(200);
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
			/**
			 * PATH: Category route
			 * GET: Get all categories
			 * POST: Post new category
			 * PUT: Change name of category
			 * DELETE: Delete e category
			 * OPTIONS: -
			 */
			path("/category", () -> {
				get("", (req, res) -> {
					System.out.println("API Call received was of the type: GET");
					System.out.println();
					String id = "";
					req.params(":id");
					if (id.equals("")) {
						id = "All Categories";
						System.out.println("GET category: " + id);
						String jsonArray = gson.toJson(cat.getCategoryNames());

						if (jsonArray != null) {
							res.status(200);
							System.out.println("Requested data:" + jsonArray);
							System.err.println(res.status());
							return jsonArray;

						}
					}
					res.status(200);
					System.out.println("empty");
					return res.status();
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
						return res.status();
					} else {
						System.out.println("POST category attempt: " + id);
						System.out.println("Error 409: Conflict");
						System.out.println();
						res.status(409);
						return res.status();
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
						return res.status();
					} else {
						res.status(404);
						return res.status();
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
						return res.status();
					} else {
						res.status(404);
						System.out.println();
						return res.status();
					}
				});

				options("/:id", (req, res) -> {
					return "";
				});
			});
			/**
			 * PATH: Question route
			 * GET: Get a specific question or get all questions
			 * POST: Post new question
			 * PUT: Change question
			 * DELETE: Delete a question
			 * OPTIONS: -
			 */
			path("/question", () -> {

				get("/:id", (req, res) -> {
					System.out.println("API Call received was of the type: GET");
					String id = req.params(":id");
					if (id != null && !id.isEmpty()) {
						System.out.println("GET category: " + id);
						String jsonArray = gson.toJson(cat.getCat(id).getQuestions());
						if (jsonArray != null) {
							res.status(200);
							System.out.println("Requested data:" + jsonArray);
							System.out.println();
							return jsonArray;
						} else {
							res.status(200);
							System.out.println("empty");
							return res.status();
						}
					} else {
						res.status(503);
						return "Request question failed, server returned " + res.status();
					}
				});
				
	
				get("/all/", (req, res) -> {
					System.out.println("API Call received was of the type: GET");
						String jsonArray = gson.toJson(cat.getAllQuestions());
						
						if (jsonArray != null) {
							res.status(200);
							System.out.println("Requested data:" + jsonArray);
							System.out.println();
							return jsonArray;
						} else {
							res.status(409);
							System.out.println("empty");
							return res.status();
						}
				});


				post("", (req, res) -> {
				System.out.println("API Call received was of the type: POST");
				System.out.println();
				String reqb = req.body();
				String[] test2 = reqb.split(",");
				int id = 0;
				Question q = null;
				for (String s : test2) {
					System.out.println(s);
				}
				for (int i = 0; i < test2.length; i++) {
					if(i == 0){
						if(test2[i].contains("base64")){
						res.status(503);
							return res.status();
						}
					q = qc.addQuestion(new Question(test2[i]));

					id = q.getId();
					} else if(i == 1) {
						Answer a = new Answer(test2[1], true);
						q.addAnswer(a);
					} else {
						q = qc.getQ(id);
						if (cat.getCat(test2[i]) != null) {
							cat.getCat(test2[i]).addQuestion(q);
					}
				
					
					} 
				
						res.status(201);
					
				}
				System.out.println("Try to POST new Question ");
				return res.status();
			});

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
								res.status(200);
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
			/**
			 * PATH: Answer route
			 * GET: Get all answers
			 * POST: Post new answer
			 * PUT: Change answer
			 * DELETE: Delete an answer
			 * OPTIONS: -
			 */
			path("/answer", () -> {
				get("/:id", (req, res) -> {
					String id = req.params(":id");
					System.out.println("API Call recieved was of the type: GET answer");
					System.out.println("Try to GET answer connected to question id " + id);
					Question question = null;
					try{
						int intId = Integer.parseInt(id);
						question = cat.getQuestion(intId);
					}catch (NumberFormatException e) {					
						res.status(400);
						System.out.println("Bad request");
						return res.status();
					}
					
					if(question == null){
						res.status(406);
						System.out.println("Couldn't find question");
						return res.status();
					}
				
					String jsonArray = gson.toJson(question.getAnswers());
					if (jsonArray != null) {
						res.status(200);
						System.out.println("Requested data:" + jsonArray);
						System.out.println();
						return jsonArray;
					} else {
						res.status(200);
						System.out.println("empty");
						return res.status();
					}
				});

				post("/:id", (req, res) -> {
					String id = req.params(":id");
					Question question = null;
					try{
						int intId = Integer.parseInt(id);
						question = cat.getQuestion(intId);
					}catch (NumberFormatException e) {					
						res.status(400);
						System.out.println("Bad request");
						return res.status();

					}
					
					Answer newAnswer = gson.fromJson(req.body(), Answer.class);
					System.out.println("API Call recieved was of the type: POST answer");
					if(newAnswer != null){
						System.out.println("Try to POST answer to question with id " + id + " with the answer " + newAnswer.getName());
						question.addAnswer(newAnswer);
						res.status(201);
						System.out.println("Ok");
						return res.status();
					}else{
						res.status(400);
						System.out.println("Bad request");
						return res.status();
					}					
				});

				put("/:id", (req, res) -> {
					String id = req.params(":id");
					int intId = 0;
					try{
						intId = Integer.parseInt(id);
					}catch (NumberFormatException e) {					
						res.status(400);
						System.out.println("Bad request");
						return res.status();
					}
					
					System.out.println(req.body());
					Answer editAnswer = gson.fromJson(req.body(), Answer.class);
					if(editAnswer.getName().equals("")){
						res.status(400);
						System.out.println("Bad request");
						return res.status();

					}else{
						System.out.println("Try to PUT answer with id " + id + " with the answer " + editAnswer.getName());
						cat.changeAnswer(intId, editAnswer.getName());
						res.status(200);
						System.out.println("Ok");
						return res.status();
					}					
				});

				delete("/:id", (req, res) -> {
					String id = req.params(":id");
					System.out.println("API Call recieved was of the type: DELETE answer");
					System.out.println("Try to DELETE answer with id " + id);
					int answerResult = -1;
					try{
						int intId = Integer.parseInt(id);
						answerResult = cat.removeAnswer(intId);
					}catch (NumberFormatException e) {					
						res.status(400);
						System.out.println("Bad request");
						return res.status();
					}
					
					if(answerResult == 1){
						res.status(200);
						System.out.println("Ok");
						return res.status();
						
					}else{
						res.status(400);
						System.out.println("Bad request");
						return res.status();
					}	
				});

				options("/:id", (req, res) -> {
					return "";
				});
			});
		});
	}
}