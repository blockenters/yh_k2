
public class Parent {
	
	String name;
	int age;
	private int money;
	
	Parent(){
		System.out.println("Parent 생성자 호출됨");
	}
	
	void print() {
		System.out.println("name : " + name);
		System.out.println("age : " + age);
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}
	
	
	

}
