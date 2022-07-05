package main.person;

public class Professor extends Person {
	
//	protected int num;
//	protected String name;
//	protected String dept;
//	protected String address;
	
	// Person 클래스의 함수들도 상속받았다.
	
	private String[] subjects;
	
	public void printSubj() {
		System.out.println( name + " 교수의 개설 과목");
		for (int i = 0; i < subjects.length ; i++) {
			System.out.println( subjects[i] );
		}
	}

	public String[] getSubjects() {
		return subjects;
	}

	public void setSubjects(String[] subjects) {
		this.subjects = subjects;
	}
	
	

}
