package controller;

import java.util.ArrayList;

import category.Category;

public class CatController {

	private ArrayList<Category> category = new ArrayList<Category>();
	
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

}
