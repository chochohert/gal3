package student2;


import java.text.DecimalFormat;
import java.util.Currency;
import java.util.Locale;

// ȭ�� ǥ�� Ŭ����
class GetWon {
	private int number;
	public GetWon(int number) {
		this.number = number;
	}
	String changer() {
		String decimal = new DecimalFormat("#,###,###,###").format(number);
		String won = Currency.getInstance(Locale.KOREA).getSymbol();
		String result = won + decimal;
		return result;
	}
}
