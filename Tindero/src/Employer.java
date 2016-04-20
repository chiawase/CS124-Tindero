import java.util.ArrayList;

public class Employer extends User implements Subject, Observer{
	private ArrayList<Subject> freelancerSubject;
	private ArrayList<Observer> freelancerObserver;
	
	public Employer(String username)
	{
		setUsername(username);
		freelancerObserver = new ArrayList<Observer>();
		freelancerSubject = new ArrayList<Subject>();
	}

	@Override
	public void editProfile() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerObserver(Observer o) {
		freelancerObserver.add(o);
		
	}

	// delete someone
	@Override
	public void removeObserver(Observer o) {
		int i = freelancerObserver.indexOf(o);
		if(i >= 0)
		{
			freelancerObserver.remove(i);
		}
		
	}

	@Override
	public void notifyObserver(Observer o) {
		// TODO Auto-generated method stub
		
	}
	
	public ArrayList<Observer> getObserver()
	{
		return freelancerObserver;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	// show interest for a Freelancer
	public void showInterest(Subject e) {
		freelancerSubject.add(e);
		e.registerObserver(this);
		
	}

}
