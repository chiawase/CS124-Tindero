import java.util.ArrayList;

public class Freelancer extends User implements Subject, Observer{
	private double rating;
	private Skill skill;
	private ArrayList<Subject> employerSubject;
	private ArrayList<Observer> employerObserver;
	
	public Freelancer(String username)
	{
		setUsername(username);
		this.skill = null;
		employerSubject = new ArrayList<Subject>();
		employerObserver = new ArrayList<Observer>();
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
		employerSubject.add(e);
		e.registerObserver(this);
	}
	
	
	// employers who are interested with the Freelancer
	@Override
	public void registerObserver(Observer o) {
		employerObserver.add(o);	
	}

	// decline 
	@Override
	public void removeObserver(Observer o) {
		int i = employerObserver.indexOf(o);
		if(i >= 0)
		{
			employerObserver.remove(i);
		}
		
	}

	@Override
	public void notifyObserver(Observer o) {
		// TODO Auto-generated method stub
		
	}
	
	public ArrayList<Observer> getObserver()
	{
		return employerObserver;
	}
}
