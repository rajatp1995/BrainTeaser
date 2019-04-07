package com.rajatp.brainteaser;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static java.util.Arrays.asList;

public class GameActivity extends AppCompatActivity {

    TextView timerD, scoreD, quesD, correctD, resultD;
    TextView t1, t2, t3;
    Button op1, op2, op3, op4;
    GridLayout answerOps;
    int gameLock = 0, checkLock = 0, v1, v2, gameCounter = 0, correctCheck = 0, correctAnswer = 0;
    List<String> correctChoices = new ArrayList<>();
    List<String> operators = new ArrayList<>(asList("+", "-", "*", "/"));
    Random rand = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        timerD = findViewById(R.id.timerDisp);
        scoreD = findViewById(R.id.scoreDisp);
        quesD = findViewById(R.id.quesDisp);
        correctD = findViewById(R.id.correctDisp);
        resultD = findViewById(R.id.resultDisp);
        answerOps = findViewById(R.id.answerOptions);

        t1 = findViewById(R.id.temp1);
        t2 = findViewById(R.id.temp2);
        t3 = findViewById(R.id.temp3);

        op1 = findViewById(R.id.op1);
        op2 = findViewById(R.id.op2);
        op3 = findViewById(R.id.op3);
        op4 = findViewById(R.id.op4);

        correctD.setVisibility(View.INVISIBLE);

        new CountDownTimer(61000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished / 1000 < 10) {
                    String time = "0" + Long.toString(millisUntilFinished / 1000);
                    timerD.setText(time);
                    timerD.setTextColor(Color.parseColor("#FF0000"));
                    timerD.setTypeface(null, Typeface.BOLD);
                    Toast.makeText(GameActivity.this, Long.toString(millisUntilFinished / 1000) + " seconds left", Toast.LENGTH_SHORT).show();
                } else {
                    timerD.setText(Long.toString(millisUntilFinished / 1000));
                    timerD.setTypeface(null, Typeface.BOLD);
                }
            }

            public void onFinish() {
                gameLock = 1;
                declareScore();
            }
        }.start();
        startQuiz();
    }

    public void startQuiz() {
        if (gameLock == 0) {
            int temp = rand.nextInt(4);
            String operator = operators.get(temp);
            op1.setBackground(getResources().getDrawable(R.drawable.one));
            op2.setBackground(getResources().getDrawable(R.drawable.one));
            op3.setBackground(getResources().getDrawable(R.drawable.one));
            op4.setBackground(getResources().getDrawable(R.drawable.one));

            if (operator.equals("+")) generateChoices(1, "+");
            if (operator.equals("-")) generateChoices(2, "-");
            if (operator.equals("*")) generateChoices(3, "*");
            if (operator.equals("/")) generateChoices(4, "/");
        }
    }

    public void answerCheck(View view) {
        if (gameLock == 0) {
            if (checkLock == 0) {
                checkLock = 1;
                gameCounter++;
                String[] temp = view.getTag().toString().split("_");
                int answerOption = Integer.valueOf(temp[1]);

                if (correctChoices.get(answerOption).equals(Integer.toString(correctAnswer))) {
                    correctD.setVisibility(View.VISIBLE);
                    correctD.setText("Correct!");
                    view.setBackground(getResources().getDrawable(R.drawable.correct));
                    correctCheck++;
                } else {
                    correctD.setVisibility(View.VISIBLE);
                    correctD.setText("Incorrect!");
                    view.setBackground(getResources().getDrawable(R.drawable.wrong));
                }

                scoreD.setText(Integer.toString(correctCheck) + "/" + Integer.toString(gameCounter));
                new CountDownTimer(500, 500) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
                        correctD.setVisibility(View.INVISIBLE);
                        checkLock = 0;
                        startQuiz();
                    }
                }.start();
            }
        } else {
            Toast.makeText(GameActivity.this, "Time Over", Toast.LENGTH_SHORT).show();
        }
    }

    public void generateChoices(int op, String ops) {
        List<Integer> tempList = new ArrayList<>();
        tempList.clear();
        v1 = rand.nextInt(200 - 10 + 1) + 10;
        v2 = rand.nextInt(200 - 10 + 1) + 10;

        if (v1 == 0 || v2 == 0) generateChoices(op, ops);

        if (op == 1) {
            correctChoices.clear();
            correctAnswer = v1 + v2;
            int correctChoice = rand.nextInt(4);
            for (int i = 0; i < 4; i++) {
                if (i == correctChoice) {
                    correctChoices.add(Integer.toString(v1 + v2));
                } else {
                    int wrongAnswerMove = rand.nextInt(4);
                    if (wrongAnswerMove == 0) {
                        int wrongAnswer = rand.nextInt(50);
                        correctChoices.add(Integer.toString(v1 + v2 + wrongAnswer));
                    }
                    if (wrongAnswerMove == 1) {
                        int wrongAnswer = rand.nextInt(25);
                        correctChoices.add(Integer.toString(v1 + v2 - wrongAnswer));
                    }
                    if (wrongAnswerMove == 2) {
                        int tempNo = round(rand.nextInt(31));
                        correctChoices.add(Integer.toString(v1 + v2 - tempNo));
                    }
                    if (wrongAnswerMove == 3) {
                        int tempNo = round(rand.nextInt(31));
                        correctChoices.add(Integer.toString(v1 + v2 + tempNo));
                    }
                }
            }
        }

        if (op == 2) {
            correctChoices.clear();
            correctAnswer = v1 - v2;
            int correctChoice = rand.nextInt(4);
            for (int i = 0; i < 4; i++) {
                if (i == correctChoice) {
                    correctChoices.add(Integer.toString(correctAnswer));
                } else {
                    int wrongAnswerMove = rand.nextInt(4);
                    if (wrongAnswerMove == 0) {
                        int wrongAnswer = rand.nextInt(50);
                        correctChoices.add(Integer.toString(v1 - v2 + wrongAnswer));
                    }
                    if (wrongAnswerMove == 1) {
                        int wrongAnswer = rand.nextInt(25);
                        correctChoices.add(Integer.toString(v1 - v2 - wrongAnswer));
                    }
                    if (wrongAnswerMove == 2) {
                        int tempNo = round(rand.nextInt(31));
                        correctChoices.add(Integer.toString(v1 - v2 - tempNo));
                    }
                    if (wrongAnswerMove == 3) {
                        int tempNo = round(rand.nextInt(31));
                        correctChoices.add(Integer.toString(v1 - v2 + tempNo));
                    }
                }
            }
        }

        if (op == 3) {
            int v11 = rand.nextInt(40 - 5 + 1) + 5;
            int v21 = rand.nextInt(40 - 5 + 1) + 5;
            if (v11 == 0 || v21 == 0) generateChoices(3, ops);
            correctChoices.clear();
            correctAnswer = v11 * v21;
            quesD.setText(Integer.toString(v11) + " " + ops + " " + Integer.toString(v21));
            int correctChoice = rand.nextInt(4);
            for (int i = 0; i < 4; i++) {
                if (i == correctChoice) {
                    correctChoices.add(Integer.toString(correctAnswer));
                } else {
                    int wrongAnswerMove = rand.nextInt(2);
                    if (wrongAnswerMove == 0) {
                        int tempNo = round(rand.nextInt(31));
                        correctChoices.add(Integer.toString((v11 * v21) - tempNo));
                    }
                    if (wrongAnswerMove == 1) {
                        int tempNo = round(rand.nextInt(31));
                        correctChoices.add(Integer.toString((v11 * v21) + tempNo));
                    }
                }
            }
        }

        if (op == 4) {
            int v11, v21;
            int number1 = rand.nextInt(50 - 2 + 1) + 2;
            int number2 = rand.nextInt(30 - 2 + 1) + 2;
            v11 = number1 * number2;
            v21 = number2;
            correctChoices.clear();
            correctAnswer = v11 / v21;
            quesD.setText(Integer.toString(v11) + " " + ops + " " + Integer.toString(v21));
            int correctChoice = rand.nextInt(4);
            for (int i = 0; i < 4; i++) {
                if (i == correctChoice) {
                    correctChoices.add(Integer.toString(correctAnswer));
                } else {
                    int wrongAnswerMove = rand.nextInt(2);
                    if (wrongAnswerMove == 0) {
                        int tempNo = round(rand.nextInt(31));
                        correctChoices.add(Integer.toString((v11 / v21) - tempNo));
                    }
                    if (wrongAnswerMove == 1) {
                        int tempNo = round(rand.nextInt(31));
                        correctChoices.add(Integer.toString((v11 / v21) + tempNo));
                    }
                }
            }
        }

        for (int k = 0; k < 4; k++) {
            tempList.add(Integer.valueOf(correctChoices.get(k)));
        }

        Set<Integer> set = new HashSet<>(tempList);
        if (set.size() < tempList.size()) generateChoices(op, ops);

        if (op != 3 && op != 4) {
            quesD.setText(Integer.toString(v1) + " " + ops + " " + Integer.toString(v2));
        }
        op1.setText(correctChoices.get(0));
        op2.setText(correctChoices.get(1));
        op3.setText(correctChoices.get(2));
        op4.setText(correctChoices.get(3));
    }

    public void declareScore() {
        correctD.setVisibility(View.INVISIBLE);
        timerD.animate().translationXBy(-50).alpha(0).setDuration(2000);
        scoreD.animate().translationXBy(50).alpha(0).setDuration(2000);
        quesD.animate().alpha(0).setDuration(2000);
        t1.animate().translationXBy(-50).alpha(0).setDuration(2000);
        t3.animate().translationXBy(50).alpha(0).setDuration(2000);
        t2.animate().alpha(0).setDuration(2000);
        answerOps.animate().rotationYBy(360).alpha(0).setDuration(3000);

        resultD.setText("You've answered " + correctCheck + " questions correctly out of total " + gameCounter + " questions.");
        resultD.setBackground(getResources().getDrawable(R.drawable.two));
        resultD.setPadding(10, 50, 10, 50);
        resultD.setAlpha(0);
        resultD.setVisibility(View.VISIBLE);
        resultD.animate().alpha(1).setDuration(2000);
    }

    public int round(int n) {
        int a = (n / 10) * 10;
        int b = a + 10;
        return (n - a > b - n) ? b : a;
    }
}
