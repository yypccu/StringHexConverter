package lab.february.stringhexconverter;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import java.math.BigInteger;

public abstract class EnhancedTextWatcher implements TextWatcher {

    private TextView mResponderTextView;
    private boolean sourceIsTyping = false;

    public void setResponder(TextView responder) {
        mResponderTextView = responder;
    }

    public void setIsTyping(boolean sourceIsTyping) {
        this.sourceIsTyping = sourceIsTyping;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // Do Nothing
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (sourceIsTyping && mResponderTextView != null && !mResponderTextView.isFocused()) {
            mResponderTextView.setText(convertedText());
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        // Do Nothing
    }

    public abstract String convertedText();
}
