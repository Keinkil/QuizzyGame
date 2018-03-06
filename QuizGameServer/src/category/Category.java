package category;

import java.util.ArrayList;
import java.util.HashMap;

import qAndA.Question;

public class Category {

	private String name = "";
//	private ArrayList<QuestionAndAnswer> questions = new ArrayList<QuestionAndAnswer>();
	
	public Category(String name) {
		this.name = name;
	
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	

	
	public String toString(){
		return name;
	}

}
