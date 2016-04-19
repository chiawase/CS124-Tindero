
public class WaterThePlants extends SkillDecorator {
	protected Skill skill;
	
	public WaterThePlants(Skill skill)
	{
		this.skill = skill;
	}

	@Override
	public String getDescription() {
		return skill.getDescription() + ", can Water the Plants";
	}

}
