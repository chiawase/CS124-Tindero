package com.tindero.tindero;

import java.util.ArrayList;

public class Freelancer extends User implements Subject, Observer {
	private ArrayList<Subject> employerSubject;
	private ArrayList<Observer> employerObserver;
	private ArrayList<String> employerObserverNames;
	
	public Freelancer(String id, String name, String pass, String fName, String type, String contactNum, String emailAddress, String desc) {
		setId(id);
		setUsername(name);
		setPassword(pass);
		setFullName(fName);
		setUserType(type);
		setContactNum(contactNum);
		setEmailAddress(emailAddress);
		setDescription(desc);

		employerSubject = new ArrayList<>();
		employerObserver = new ArrayList<>();
		employerObserverNames = new ArrayList<>();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	// Apply for a specific Employer
	public void apply(Subject e) {
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
		if(i >= 0) {
			employerObserver.remove(i);
		}
	}

	@Override
	public void notifyObserver(Observer o) {
		// TODO Auto-generated method stub
		
	}
	
	public ArrayList<Observer> getObserver() {
		return employerObserver;
	}

	public ArrayList<String> getObserverNames() { return employerObserverNames; }

	public void addObserverName(String name) {
		employerObserverNames.add(name);
	}
}