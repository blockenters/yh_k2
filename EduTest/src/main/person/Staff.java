package main.person;

public class Staff extends Person {
	
//	protected int num;
//	protected String name;
//	protected String dept;
//	protected String address;
	
	// Person 클래스의 함수들도 상속받았다.
	
	private String job;
	
	public void printJob() {
		System.out.println(name+" 교직원의 직무는 "+ job);
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	
	
}





