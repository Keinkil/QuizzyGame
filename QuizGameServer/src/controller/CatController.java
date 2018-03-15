package controller;

import java.util.ArrayList;

import category.Category;
import qAndA.Answer;
import qAndA.Question;

public class CatController {

	private ArrayList<Category> category = new ArrayList<Category>();
	String cat = "";
	Question q;

	public CatController() {
		category.add(new Category("Geometri"));
		category.add(new Category("Algebra"));
		category.add(new Category("Procent"));
		category.add(new Category("Logaritmer"));
	}

	public int catExists(String cat) {
		for (Category c : category) {
			if (c.getName().equals(cat)) {
				return category.indexOf(c);
			}
		}
		return -1;

	}

	public ArrayList<String> getCategoryNames() {
		ArrayList<String> catNames = new ArrayList<String>();
		for (Category c : category) {
			catNames.add(c.getName());
		}
		return catNames;
	}

	public boolean renameCategory(String cat, String name) {
		int index = catExists(cat);
		if (index != -1) {
			Category reCat = category.get(index);
			reCat.setName(name);
			return true;
		}
		return false;
	}

	public int removeCategory(String cat) {
		System.out.println("hur många kategorier " + category.size());
		for (int i = 0; i < category.size(); i++) {

			if (category.get(i).getName().equals(cat)) {
				System.out.println("Category getName " + category.get(i).getName());
				System.out.println("Id namn " + cat);
				category.remove(i);
				return 1;

			}

		}
		return -1;
	}

	public int addCat(String cat) {
		System.out.println(cat);
		if (removeCategory(cat) == -1) {
			category.add(new Category(cat));
			return 1;
		}

		return -1;

	}
	
	public void addQToCat(Question q, String name) {
		for(Category c : category) {
			if(c.getName().equals(name)) {
				c.addQuestion(q);
			}
		}
	}
	
	public Category getCat(String name) {
		for(Category c : category) {
			if(c.getName().equals(name)) {
				return c;
			}
		}
		return null;
	}
	
	public ArrayList<Category> getAllCats() {
		return category;
	}
	
	public ArrayList<Question> getAllQuestions(){
		ArrayList<Question> allQuestions = new ArrayList<Question>();;		
		for(Category category : getAllCats()){
			allQuestions.addAll(category.getQuestions());
		}
		return allQuestions;
	}
	
	public Answer getAnswer(int id){
		for(Category category : getAllCats()){
			for(Question question : category.getQuestions()){
				for(Answer answer : question.getAnswers()){
					if(answer.getId() == id){
						return answer;
					}
				}
			}	
		}
		return null;
	}
	
	public Question getQuestion(int id){		
		for(Category category : getAllCats()){
			for(Question question : category.getQuestions()){
				if(question.getId() == id){
					return question;
				}
			}
		}
		return null;
	}
	
	public int removeAnswer(int id){
		for(Category category : getAllCats()){
			for(Question question : category.getQuestions()){
				for (int i = 0; i < question.getAnswers().size(); i++) {
					if (question.getAnswers().get(i).getId() == id) {
						System.out.println("RemovedAnswer: " + question.getAnswers().get(i).getName());
						System.out.println("Id answer " + question.getAnswers().get(i).getId());
						question.getAnswers().remove(i);
						return 1;
					}
				}
			}
		}
		return -1;
	}
	
	public int changeAnswer(int id, String newAnswer) {
		int returnValue = -1;
		
		Answer answer = getAnswer(id);
		if (answer != null) {
			System.out.println("Changed Question with id " + id + " from "
					+ answer.getName() + " to " + newAnswer);
			answer.setAnswer(newAnswer);
		}
		return returnValue;
	}

}
