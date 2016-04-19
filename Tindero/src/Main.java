import java.util.ArrayList;

public class Main {
	
	public static void main(String[] args)
	{
		ArrayList<Observer> temp;
		Freelancer joel = new Freelancer("joel");
		Freelancer drake = new Freelancer("drake");
		Freelancer kimk = new Freelancer("kim k");
		Employer kanye = new Employer("kanye");
		
		// empty observers for kanye
		temp = kanye.getObserver();
		for(Observer t: temp)
		{
			User user = (User) t;
			System.out.println(user.getUsername());
		}
		
		System.out.println("Joel, Drake, Kim K applied to Kanye");
		System.out.println("List of Kanye's Observers: ");
		// Joel and drake will apply to Kanye
		joel.apply(kanye);
		drake.apply(kanye);
		kimk.apply(kanye);
		temp = kanye.getObserver();
		for(Observer t: temp)
		{
			User user = (User) t;
			System.out.println("  " + user.getUsername());
		}
		
		System.out.println("\n\nKanye is not interested with Joel. He declined joel.");
		System.out.println("List of Kanye's Observers: ");
		// Joel and drake will apply to Kanye
		kanye.removeObserver(joel);
		temp = kanye.getObserver();
		for(Observer t: temp)
		{
			User user = (User) t;
			System.out.println("  " + user.getUsername());
		}
	}

}
