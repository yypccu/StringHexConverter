package lab.february.stringhexconverter;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.math.BigInteger;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();
    private final boolean DEBUG = true;

    private EditText mStringInputEditText;
    private EditText mHexInputEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStringInputEditText = findViewById(R.id.stringInputEditText);
        mHexInputEditText = findViewById(R.id.hexInputEditText);

        EnhancedTextWatcher stringInputTextWatcher = new EnhancedTextWatcher() {
            @Override
            public String convertedText() {
                return String.format("%x", new BigInteger(1, mStringInputEditText.getText().toString().getBytes()));
            }
        };
        stringInputTextWatcher.setResponder(mHexInputEditText);
        mStringInputEditText.addTextChangedListener(stringInputTextWatcher);

        EnhancedTextWatcher hexInputTextWatcher = new EnhancedTextWatcher() {
            @Override
            public String convertedText() {
                if (mHexInputEditText.length() != 0) {
                    StringBuilder hexCode = new StringBuilder(mHexInputEditText.getText().toString());
                    if (hexCode.length() % 2 == 1) {
                        hexCode.append('0');
                    }

                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < hexCode.length(); i += 2) {
                        try {
                            stringBuilder.append((char) Integer.parseInt(hexCode.substring(i, i + 2), 16));
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                            stringBuilder.append("¿");
                        }
                    }
                    stringBuilder.trimToSize();
                    return stringBuilder.toString();
                } else {
                    return "";
                }
            }
        };
        hexInputTextWatcher.setResponder(mStringInputEditText);
        mHexInputEditText.addTextChangedListener(hexInputTextWatcher);
    }
}
