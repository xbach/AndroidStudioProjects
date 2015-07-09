package cz.xbach.a.geoquiz;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
// import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;


public class QuizActivity extends ActionBarActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_CHEAT = "cheatPersist";

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPreviousButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;
    private TextView mAnswerTextView;

    private TrueFalse[] mQuestionBank = new TrueFalse[] {
            new TrueFalse(R.string.q_oceans, true),
            new TrueFalse(R.string.q_mideast, false),
            new TrueFalse(R.string.q_africa, false),
            new TrueFalse(R.string.q_americas, true),
            new TrueFalse(R.string.q_asia, true),
            new TrueFalse(R.string.q_1, false),

    };

    private int mCurrentIndex = 0;

    private boolean mIsCheater;

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getQuestion();
        mQuestionTextView.setText(question);
        // Logging the mCurrentIndex
        // Log.d(TAG, "Current position: " + mCurrentIndex);
        // mIsCheater = false;
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();

        int messageResId = 0;

        if (mIsCheater) {
            messageResId = R.string.judgment_toast;
        } else {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }

        // Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
        // Replacing the Toast with TextView
        mAnswerTextView = (TextView)findViewById(R.id.answer_text_view);
        mAnswerTextView.setText(messageResId);
    }

    private void incrementQuestion() {
        mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
        // Clear Previous Answer
        mAnswerTextView.setText("");
        updateQuestion();
        mIsCheater = false;
    }

    private void decreaseQuestion() {
        if (mCurrentIndex == 0) {
            mCurrentIndex = 4 % mQuestionBank.length;
        } else {
            mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
        }

        mAnswerTextView.setText("");
        updateQuestion();
        mIsCheater = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementQuestion();
            }
        });

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);

            int checkCheatSaved;
            checkCheatSaved = savedInstanceState.getInt(KEY_CHEAT, 0);
            if (checkCheatSaved == 1) {
                mIsCheater = true;
            }
        }


        mTrueButton = (Button)findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mFalseButton = (Button)findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mNextButton = (Button)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementQuestion();
            }
        });

        mPreviousButton = (Button)findViewById(R.id.prev_button);
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseQuestion();
            }
        });

        mCheatButton = (Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){
                // Start new Cheat Activity, tell OS Activity Manager through startActivity and Intent
                // Activity needs to be defined in the manifest
                Intent i = new Intent(QuizActivity.this, CheatActivity.class);
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
                // Sending Information through Intent.Extra
                i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);
                startActivityForResult(i, 0);

            }
        });

        // Show first question
        updateQuestion();


    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;

        }
        mIsCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);

        // Saving Cheater across Instances
        int CheaterInstanceState;
        if (mIsCheater) {
            CheaterInstanceState = 1;
            // Log.d(TAG, "INSTANCE:" + CheaterInstanceState);
            }
        else {CheaterInstanceState = 0;}

        savedInstanceState.putInt(KEY_CHEAT, CheaterInstanceState);
    }
}
