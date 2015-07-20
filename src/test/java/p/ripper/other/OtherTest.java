package p.ripper.other;

public class OtherTest {
	public static void main(String[] args) {
		int firstEnglish = (int) 'A';
		for (int i = firstEnglish; i <= (firstEnglish + 25); i++) {
			char x = (char) i;
			System.out.print(String.valueOf(x));
		}
	}

}
