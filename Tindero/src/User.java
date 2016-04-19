
public abstract class User {
	private String username;
	private String password;
	private String contactNum;
	
	public abstract void editProfile();
	
	public void setUsername(String s)
	{
		this.username = s;
	}
	
	public String getUsername()
	{
		return username;
	}
}
