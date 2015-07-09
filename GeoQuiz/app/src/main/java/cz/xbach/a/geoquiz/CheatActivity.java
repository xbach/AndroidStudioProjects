package cz.xbach.a.geoquiz;

import android.content.Intent;
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
    private int mAnswerShown;

    private TextView mAnswerTextView;
    private Button mShowAnswer;

    public static final String EXTRA_ANSWER_IS_TRUE = "cz.xbach.a.geoquiz.answer_is_true";
    public static final String EXTRA_ANSWER_SHOWN = "cz.xbach.a.geoquiz.answer_shown";

    private static final String KEY_CHEAT = "cz.xbach.a.geoquiz.cheatPersist";


    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
        if (isAnswerShown) {mAnswerShown = 1;}

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        // Log.d(TAG, "CheatActivity started");

        setAnswerShownResult(false);

        if (savedInstanceState != null) {
            int userCheated;
            userCheated = savedInstanceState.getInt(KEY_CHEAT, 0);
            if (userCheated == 1) {
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

    }

    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putInt(KEY_CHEAT, mAnswerShown);
    }

}
