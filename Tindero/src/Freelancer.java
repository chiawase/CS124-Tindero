import java.util.ArrayList;

public class Freelancer extends User implements Observer{
	private double rating;
	private Skill skill;
	private ArrayList<Subject> employer;
	
	public Freelancer(String username)
	{
		setUsername(username);
		this.skill = null;
		employer = new ArrayList<Subject>();
	}

	@Override
	public void editProfile() {
		// TODO Auto-generated method stub
		
	}
	
	public void setSkill(Skill skill)
	{
		this.skill = skill;
	}
	
	public String getSkillDescription()
	{
		return skill.getDescription();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	// Apply for a specific Employer
	public void apply(Subject e){
		employer.add(e);
		e.registerObserver(this);
	}
}
