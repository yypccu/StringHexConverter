package lab.february.stringhexconverter;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import java.math.BigInteger;

public abstract class EnhancedTextWatcher implements TextWatcher {

    private TextView mResponderTextView;

    public void setResponder(TextView responder) {
        mResponderTextView = responder;
    }

    public void releaseResponder() {
        mResponderTextView = null;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // Do Nothing
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (mResponderTextView != null && !mResponderTextView.isFocused()) {
            mResponderTextView.setText(convertedText());
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        // Do Nothing
    }

    public abstract String convertedText();
}
