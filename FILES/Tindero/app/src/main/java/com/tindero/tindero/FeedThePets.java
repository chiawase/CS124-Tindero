package com.tindero.tindero;

public class FeedThePets extends SkillDecorator {
	protected Skill skill;
	
	public FeedThePets(Skill skill)	{ this.skill = skill; }

	@Override
	public String getDescription() {
		return skill.getDescription() + "FTP ";
	}
}