package se.chalmers.mindy.core;

import android.graphics.Color;

public class ExerciseItem implements Comparable {
	
	private String name, type;
	private Color typeColor;
	
	public ExerciseItem(String name, String type, Color typeColor){
		setName(name);
		setType(type);
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

		if(this.getName().equals(((ExerciseItem) another).getName()) && this.getType().equals(((ExerciseItem) another).getType()))
			return 0;
		
		if(this.getType() == "Mindfulness" && ((ExerciseItem) another).getType() == "Positiv Psykologi" || this.getType() == "Mindfulness" && ((ExerciseItem) another).getType() == "Studieteknik" || this.getType() == "Positiv Psykologi" && ((ExerciseItem) another).getType() == "Studieteknik")
			return 1;
		
		if(this.getType() == "Positiv Psykologi" && ((ExerciseItem) another).getType() == "Mindfulness" || this.getType() == "Studieteknik" && ((ExerciseItem) another).getType() == "Mindfulness" || this.getType() == "Studieteknik" && ((ExerciseItem) another).getType() == "Positiv Psykologi")
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
