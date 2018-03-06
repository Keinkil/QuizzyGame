package qAndA;

import java.util.HashMap;

import category.Category;

public class Question {
	private HashMap<String, String> map = new HashMap<String, String>(); 
	private String question = "";
	private String name = "";
//	private String answer = "";
	
	public Question(String cat, String q){
		this.question = q;
		this.name = cat;
		map.put(cat, q);
//		this.answer = a;
	}
	
//	public Question(String q){
//		this.question = q;
////		this.catId = cat;
////		map.put(cat, q);
////		this.answer = a;
//	}
	
	public String getQuestion(){
		return question;
	}
	
	public void setQuestion(String q){
		this.question = q;
	}
	
	public void setCategory(String cat){
		this.name = cat;
	}
	
	
	public String getCategory(){
		return name;
	}
//	public String getAnswer(){
//		return answer;
//	}
//	
//	public void setAnswer(String a){
//		this.answer = a;
//	}


}
