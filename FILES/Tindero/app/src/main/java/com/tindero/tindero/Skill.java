package com.tindero.tindero;

public class Skill extends User {
	protected String description = "";

	public Skill() { description = ""; }

	@Override
	public String getDescription() { return description; }
}