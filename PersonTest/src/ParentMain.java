
public class ParentMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub		
		Parent p = new Parent();
		p.name = "�θ�";
		p.age = 50;
		p.setMoney(1000000000);
		
		p.print();
		System.out.println("���� : "+p.getMoney()+"��");
		
		// ��ӹ��� Child Ŭ������ �޸𸮿� ����(��ü ����)
		Child c = new Child();
		c.name = "�ڽ�";
		c.age = 20;
		c.hobby = "����";		
		c.setMoney(1000000);
		System.out.println(c.getMoney());
		c.print();
		
		
	}

}





