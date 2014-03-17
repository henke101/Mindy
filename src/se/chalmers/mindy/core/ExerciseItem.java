package se.chalmers.mindy.core;

import android.graphics.Color;

public class ExerciseItem implements Comparable {
	
	private String name, type;
	private Color typeColor;
	
	public ExerciseItem(String name, String description, Color typeColor){
		setName(name);
		setType(description);
		setTypeColor(typeColor);
	}
	
	public int compareTo(Object another) {
		if(another == null)
			return 1;

		try{
			another = (ExerciseItem)another;
		}catch(Exception e){
			return -2;
		}
		
		if(this.getType().equals("Mindfulness") && ((ExerciseItem) another).getType().equals("Positiv Psykologi") || this.getType().equals("Mindfulness") && ((ExerciseItem) another).getType().equals("Studieteknik") || this.getType().equals("Positiv Psykologi") && ((ExerciseItem) another).getType().equals("Studieteknik"))
			return 1;
		
		if(this.getType().equals("Positiv Psykologi") && ((ExerciseItem) another).getType().equals("Mindfulness") || this.getType().equals("Studieteknik") && ((ExerciseItem) another).getType().equals("Mindfulness") || this.getType().equals("Studieteknik") && ((ExerciseItem) another).getType().equals("Postiv Psykologi"))
			return -1;
		
		return 0;
	}
	
	public String getName(){
		return name;
	}
	
	public String getType(){
		return type;
	}
	
	public Color getTypeColor(){
		return typeColor;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setType(String type){
		this.type = type;
	}
	
	public void setTypeColor(Color typeColor){
		this.typeColor = typeColor;
	}


}
