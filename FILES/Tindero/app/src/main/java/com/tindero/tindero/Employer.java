package com.tindero.tindero;

import java.util.ArrayList;

public class Employer extends User implements Subject, Observer {
	private ArrayList<Subject> freelancerSubject;
	private ArrayList<Observer> freelancerObserver;
	private ArrayList<String> freelancerObserverNames;
	
	public Employer(String id, String name, String pass, String fName, String type, String contactNum, String emailAddress, String desc) {
		setId(id);
		setUsername(name);
		setPassword(pass);
		setFullName(fName);
		setUserType(type);
		setContactNum(contactNum);
		setEmailAddress(emailAddress);
		setDescription(desc);

		freelancerObserver = new ArrayList<>();
		freelancerSubject = new ArrayList<>();
		freelancerObserverNames = new ArrayList<>();
	}

	@Override
	public void registerObserver(Observer o) {
		freelancerObserver.add(o);
		
	}

	// delete someone
	@Override
	public void removeObserver(Observer o) {
		int i = freelancerObserver.indexOf(o);
		if(i >= 0) {
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

	public ArrayList<String> getObserverNames () { return freelancerObserverNames; }

	public void addObserverName(String name) {
		freelancerObserverNames.add(name);
	}
}