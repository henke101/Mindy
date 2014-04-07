package se.chalmers.mindy.core;

import android.graphics.Color;

public class ExerciseItem   {
	
	private String name, description;
	private Color typeColor;
	
	public ExerciseItem(String name, String description, Color typeColor){
		this.name = name;
		this.description= description;
		this.typeColor = typeColor;
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
