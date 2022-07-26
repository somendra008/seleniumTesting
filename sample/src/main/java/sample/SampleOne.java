package sample;

public class SampleOne {

	public static void main(String[] args) {
		
		try {
		int i = 1/0;	
		}
		
		catch(Exception e) {
			System.out.println("Exception caught");
		}
		
		try {
			System.out.println("welcome");
		}
		catch(Exception e) {
			System.out.println("Exception two caught");
		}
	}
}
