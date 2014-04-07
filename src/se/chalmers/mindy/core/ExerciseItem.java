package se.chalmers.mindy.core;

import android.graphics.Color;

public class ExerciseItem implements Comparable {
	
	private String name, description;
	private Color typeColor;
	
	public ExerciseItem(String name, String description, Color typeColor){
		this.name = name;
		this.description= description;
		this.typeColor = typeColor;
	}
	
	public int compareTo(Object another) {
		if(another == null)
			return 1;

		try{
			another = (ExerciseItem)another;
		}catch(Exception e){
			return -2;
		}
		
		if(this.getDescription().equals("Mindfulness") && ((ExerciseItem) another).getDescription().equals("Positiv Psykologi") || this.getDescription().equals("Mindfulness") && ((ExerciseItem) another).getDescription().equals("Studieteknik") || this.getDescription().equals("Positiv Psykologi") && ((ExerciseItem) another).getDescription().equals("Studieteknik"))
			return 1;
		
		if(this.getDescription().equals("Positiv Psykologi") && ((ExerciseItem) another).getDescription().equals("Mindfulness") || this.getDescription().equals("Studieteknik") && ((ExerciseItem) another).getDescription().equals("Mindfulness") || this.getDescription().equals("Studieteknik") && ((ExerciseItem) another).getDescription().equals("Postiv Psykologi"))
			return -1;
		
		return 0;
	}
	
	public String getName(){
		return name;
	}
	
	public String getDescription(){
		return description;
	}
	
	public Color getTypeColor(){
		return typeColor;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public void setTypeColor(Color typeColor){
		this.typeColor = typeColor;
	}


}
