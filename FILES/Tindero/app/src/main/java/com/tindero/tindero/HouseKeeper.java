package com.tindero.tindero;

public class HouseKeeper extends SkillDecorator {
	protected Skill skill;

	public HouseKeeper(Skill skill) { this.skill = skill; }

	@Override
	public String getDescription() {
		return skill.getDescription() + "HK ";
	}
}