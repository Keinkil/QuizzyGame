package qAndA;

public class Question {
	private String question = "";
	private static int counter = 1;
	private int id = 0;
	
	public Question(String q){
		this.question = q;
		this.id = counter;
		counter++;
	}
	
	public Question getQuestion(){
		return this;
	}
	
	public void setQuestion(String q){
		this.question = q;
	}
	
	public String getName() {
		return question;
	}
	
//	public void setCategory(String cat){
//		this.category = cat;
//	}
//	
//	
//	public String getCategory(){
//		return category;
//	}
	
	public int getId() {
		return id;
	}


}
