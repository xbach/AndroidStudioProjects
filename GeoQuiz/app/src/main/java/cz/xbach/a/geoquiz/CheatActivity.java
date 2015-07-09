package cz.xbach.a.geoquiz;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by xbach on 7/9/15.
 */
public class CheatActivity extends ActionBarActivity {

    // private static final String TAG = "CheatActivity";
    private boolean mAnswerIsTrue;
    private boolean mAnswerShown;

    private TextView mAnswerTextView;
    private TextView mAPIVersionTextView;
    private Button mShowAnswer;

    public static final String EXTRA_ANSWER_IS_TRUE = "cz.xbach.a.geoquiz.answer_is_true";
    public static final String EXTRA_ANSWER_SHOWN = "cz.xbach.a.geoquiz.answer_shown";

    private static final String KEY_CHEAT = "cz.xbach.a.geoquiz.cheatPersist";


    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
        mAnswerShown = true;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        // Log.d(TAG, "CheatActivity started");

        mAnswerShown = false;

        if (savedInstanceState != null) {
            mAnswerShown = savedInstanceState.getBoolean(KEY_CHEAT, false);
            if (mAnswerShown) {
                setAnswerShownResult(true);
            }
        }

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        mAnswerTextView = (TextView)findViewById(R.id.answerTextView);

        // Answer false at first, not shown until user presses the button


        mShowAnswer = (Button)findViewById(R.id.showAnswerButton);
        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAnswerIsTrue) {
                    mAnswerTextView.setText(R.string.true_button);
                } else {
                    mAnswerTextView.setText(R.string.false_button);
                }
                setAnswerShownResult(true);
            }
        });

        mAPIVersionTextView = (TextView)findViewById(R.id.APIVersionTextView);
        mAPIVersionTextView.setText(Build.VERSION.RELEASE);

    }

    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putBoolean(KEY_CHEAT, mAnswerShown);
    }

}
