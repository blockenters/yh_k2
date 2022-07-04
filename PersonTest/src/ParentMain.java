
public class ParentMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub		
		Parent p = new Parent();
		p.name = "부모";
		p.age = 50;
		p.setMoney(1000000000);
		
		p.print();
		System.out.println("돈은 : "+p.getMoney()+"원");
		
		// 상속받은 Child 클래스를 메모리에 생성(객체 생성)
		Child c = new Child();
		c.name = "자식";
		c.age = 20;
		c.hobby = "게임";		
		c.setMoney(1000000);
		System.out.println(c.getMoney());
		c.print();
		
		
	}

}





