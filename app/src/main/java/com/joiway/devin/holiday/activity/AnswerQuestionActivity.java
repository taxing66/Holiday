package com.joiway.devin.holiday.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.joiway.devin.holiday.R;
import com.joiway.devin.holiday.model.QuestionBean;
import com.joiway.devin.holiday.tools.ToastTool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 德华 on 2016/7/21.
 * this the controller for answer the question game
 */
public class AnswerQuestionActivity extends AbsActivity {
    private TextView tvQuestion;
    private ImageView ivLeft;
    private ImageView ivRight;
    private Button btnYes;
    private Button btnNo;
    private List<QuestionBean> mQuestionBeanList;
    private int mCurrentIndex;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_question);
        tvQuestion = (TextView) findViewById(R.id.tv_question);
        ivRight = (ImageView) findViewById(R.id.iv_right);
        ivLeft = (ImageView) findViewById(R.id.iv_left);
        btnNo = (Button) findViewById(R.id.btn_no);
        btnYes = (Button) findViewById(R.id.btn_yes);
        tvQuestion.setOnClickListener(onClick);
        ivLeft.setOnClickListener(onClick);
        ivRight.setOnClickListener(onClick);
        btnNo.setOnClickListener(onClick);
        btnYes.setOnClickListener(onClick);

        initQuestion();
    }

    public View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tv_question:
                    displayNextQuestion();
                    break;
                case R.id.iv_left:
                    displayUpQuestion();
                    break;
                case R.id.iv_right:
                    displayNextQuestion();
                    break;
                case R.id.btn_yes:
                    checkAnswer(true);
                    break;
                case R.id.btn_no:
                    checkAnswer(false);
                    break;
            }
        }
    };

    private void checkAnswer(boolean inputAnswer) {
        if (mQuestionBeanList.get(mCurrentIndex).isTrue()&&inputAnswer){
            Toast.makeText(this,"yeah!you are right!",Toast.LENGTH_SHORT).show();
        }else if((!mQuestionBeanList.get(mCurrentIndex).isTrue())&&!inputAnswer){
            Toast.makeText(this,"yeah!you are right!",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this,"pit!you are wrong!",Toast.LENGTH_SHORT).show();

        }
    }

    private void displayUpQuestion() {
        mCurrentIndex = (mCurrentIndex-1)%mQuestionBeanList.size();
        tvQuestion.setText(mQuestionBeanList.get(mCurrentIndex).getQuestionId());
    }

    private void displayNextQuestion() {
        mCurrentIndex=(mCurrentIndex+1)%mQuestionBeanList.size();
        tvQuestion.setText(mQuestionBeanList.get(mCurrentIndex).getQuestionId());
    }

    private void initQuestion() {
        mQuestionBeanList = new ArrayList<>();
        mQuestionBeanList.add(new QuestionBean(R.string.question_one,true));
        mQuestionBeanList.add(new QuestionBean(R.string.question_two,false));
        mQuestionBeanList.add(new QuestionBean(R.string.question_three,true));
        mQuestionBeanList.add(new QuestionBean(R.string.question_four,false));
        mQuestionBeanList.add(new QuestionBean(R.string.question_five,true));

    }
}
