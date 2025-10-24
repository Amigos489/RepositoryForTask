class LargestNumber {
	public static void main(String[] args) {
		int n = new java.util.Random().nextInt(1000);
		if (n < 100) {
			n += 100;
		}
		System.out.println("Сгенерированное число: " + n);
		int MaxNum = 0;
		for (int i = 0; i < 3; ++i) {
			int ostn = n % 10;
			if (ostn > MaxNum) {
				MaxNum = ostn; 
			}
			n /= 10;
		}	
		System.out.println("Максимальная цифра: " + MaxNum);
	}
}