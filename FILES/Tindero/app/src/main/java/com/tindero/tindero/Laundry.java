package com.tindero.tindero;

public class Laundry extends SkillDecorator {
	protected Skill skill;

	public Laundry(Skill skill)	{ this.skill = skill; }

	@Override
	public String getDescription() {
		return skill.getDescription() + "LA ";
	}
}