package controller;

import java.util.ArrayList;
import java.util.HashMap;

import category.Category;
import qAndA.Question;

public class CatController {

	private ArrayList<Category> category = new ArrayList<Category>();
	String cat = "";
	Question q;

	public CatController() {
		category.add(new Category("Trigonometri"));
		category.add(new Category("Geometri"));
		category.add(new Category("Algebra"));

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

	// LYKKE TEST
	// public void showQuestion(String cat) {
	// if (getCategoryNames().equals(cat)) {
	// category.get(0).getQuestion();
	// }
	// }

	public Question addQuestion(Question q) {

		for (int i = 0; i < category.size(); i++)

			if (category.get(i).getName().equals(q.getCategory()))
				System.out.println("whad iiiiis this i add?? ");

		return q;

	}

}
