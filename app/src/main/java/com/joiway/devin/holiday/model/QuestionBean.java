package com.joiway.devin.holiday.model;

/**
 * save the answer and question
 */
public class QuestionBean {
    private int mQuestionId;
    private boolean isTrue;

    public QuestionBean(int questionId,boolean isTrue){
        this.mQuestionId = questionId;
        this.isTrue = isTrue;
    }

    public int getQuestionId() {
        return mQuestionId;
    }

    public void setQuestionId(int questionId) {
        mQuestionId = questionId;
    }

    public boolean isTrue() {
        return isTrue;
    }

    public void setTrue(boolean aTrue) {
        isTrue = aTrue;
    }
}
