package backend.master;

import javax.swing.*;
import javax.swing.text.*;

public class NumericTextField extends JTextField {
    private int column;
    public NumericTextField(int columns) {
        super(columns);
        ((AbstractDocument) this.getDocument()).setDocumentFilter(new IntegerDocumentFilter());
    }

    private static class IntegerDocumentFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (string == null) return;
            if (isNumeric(string)) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (text == null) return;
            if (isNumeric(text)) {
                super.replace(fb, offset, length, text, attrs);
            }
        }

        private boolean isNumeric(String text) {
            return text.matches("\\d*");
        }
    }

}