
public class Child extends Parent {
	
	// �� Ŭ������, Parent Ŭ������ ������ �ִ�
	// �Ӽ�����, ��ӹ��� ���̴�.
	
	// name
	// age
	// money
		
	String hobby;
	
	Child(){
		System.out.println("Child ������ ȣ���");
	}	
	
	
	// setMoney()
	// getMoney()
	// print()
	
	// Method Overriding �޼ҵ� �������̵�
	// ����ϴ� Ŭ��������!! �� �ڽ�Ŭ��������
	// �θ� Ŭ������ ����ִ� �Լ� �̸��� �״�� ����ϵ�!!!
	// ���� ���ϴ´�� ���븸 �ٲ㼭 ����ϴ°�!!
	void print() {
		super.print();
		System.out.println("hobby : " + hobby);
	}
	
	// Method Overroading :
	// �ϳ��� Ŭ���� �ȿ���!!! �Ȱ��� �̸��� �Լ��� ������ ����°�!!
	
	
}




