package se.chalmers.mindy.core;

import android.graphics.Color;

public class ExerciseItem   {
	
	private int name, description;
	private Color typeColor;
	
	public ExerciseItem(int name, int description, Color typeColor){
		this.name = name;
		this.description= description;
		this.typeColor = typeColor;
	}
	
	
	
	public int getName(){
		return name;
	}
	
	public int getDescription(){
		return description;
	}
	
	public Color getTypeColor(){
		return typeColor;
	}
	
	public void setName(int name){
		this.name = name;
	}
	
	public void setDescription(int description){
		this.description = description;
	}
	
	public void setTypeColor(Color typeColor){
		this.typeColor = typeColor;
	}






}
