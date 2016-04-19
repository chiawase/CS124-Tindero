import java.util.ArrayList;

public class Employer extends User implements Subject{
	private ArrayList<Observer> freelancer;
	
	public Employer(String username)
	{
		setUsername(username);
		freelancer = new ArrayList<Observer>();
	}

	@Override
	public void editProfile() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerObserver(Observer o) {
		freelancer.add(o);
		
	}

	// delete someone
	@Override
	public void removeObserver(Observer o) {
		int i = freelancer.indexOf(o);
		if(i >= 0)
		{
			freelancer.remove(i);
		}
		
	}

	@Override
	public void notifyObserver(Observer o) {
		// TODO Auto-generated method stub
		
	}
	
	public ArrayList<Observer> getObserver()
	{
		return freelancer;
	}

}
