package etc;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class inputNumberOnly extends PlainDocument {

	public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		if (str == null)
			return;
		if (str.charAt(0) >= '0' && str.charAt(0) <= '9')
			super.insertString(offset, str, attr);
	}

}