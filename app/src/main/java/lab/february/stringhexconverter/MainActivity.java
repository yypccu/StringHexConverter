package lab.february.stringhexconverter;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.math.BigInteger;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();
    private final boolean DEBUG = true;

    private EditText mStringInputEditText;
    private EditText mHexInputEditText;
    private EnhancedTextWatcher mHexInputTextWatcher;
    private EnhancedTextWatcher mStringInputTextWatcher;
    private CheckBox mCheckBox;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStringInputEditText = findViewById(R.id.stringInputEditText);
        mHexInputEditText = findViewById(R.id.hexInputEditText);

        mStringInputTextWatcher = new EnhancedTextWatcher() {
            @Override
            public String convertedText() {
                // TODO don't transform to unicode

                String hexCode = String.format("%x", new BigInteger(1, mStringInputEditText.getText().toString().getBytes()));
                return mCheckBox.isChecked() ? hexCode.replaceAll("..(?!$)", "$0 ") : hexCode;
            }
        };
        mStringInputTextWatcher.setResponder(mHexInputEditText);

        mHexInputTextWatcher = new EnhancedTextWatcher() {
            @Override
            public String convertedText() {
                // TODO auto append space while typing hex code

                if (mHexInputEditText.length() != 0) {
                    String hexCode = mHexInputEditText.getText().toString();
                    StringBuilder hexCodeBuilder = new StringBuilder(
                            mCheckBox.isChecked() ? hexCode.replaceAll(" ", "") : hexCode);

                    if (hexCodeBuilder.length() % 2 == 1) {
                        hexCodeBuilder.append('0');
                    }

                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < hexCodeBuilder.length(); i += 2) {
                        try {
                            stringBuilder.append((char) Integer.parseInt(
                                    hexCodeBuilder.substring(i, i + 2), 16));
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
        mHexInputTextWatcher.setResponder(mStringInputEditText);

        mCheckBox = findViewById(R.id.checkBox);
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String hexCode = mHexInputEditText.getText().toString();
                    mHexInputEditText.setText(hexCode.replaceAll("..(?!$)", "$0 "));
                } else {
                    String hexCode = mHexInputEditText.getText().toString();
                    mHexInputEditText.setText(hexCode.replaceAll(" ", ""));
                }
            }
        });

        MobileAds.initialize(this);
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mStringInputEditText.addTextChangedListener(mStringInputTextWatcher);
        mHexInputEditText.addTextChangedListener(mHexInputTextWatcher);
    }

    @Override
    protected void onPause() {
        super.onPause();

        mStringInputEditText.removeTextChangedListener(mStringInputTextWatcher);
        mHexInputEditText.removeTextChangedListener(mHexInputTextWatcher);
    }
}
