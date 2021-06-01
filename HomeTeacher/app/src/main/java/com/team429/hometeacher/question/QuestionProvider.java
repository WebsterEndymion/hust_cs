package com.team429.hometeacher.question;

import android.util.Pair;
import com.team429.hometeacher.data.RuntimeData;

import java.sql.ResultSet;


/**
 * The type Question provider.
 * 用于生成问题和回调逻辑
 *
 * @author Alaric
 */
public class QuestionProvider {
    private static final int ERROR_TRY_EVENT = 6; // 每五题触发一次历史错题

    /**
     * Instantiates a new Question provider.
     */
    public QuestionProvider() {

    }


    /**
     * 生成问题
     *
     * @return the question
     */
    public Question generate() {
        ResultSet today = null;
        try {
                today = RuntimeData.getHistoryToday();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        /*today.moveToFirst();
        int num = today.getInt(3);
        today.close();
        if (num % ERROR_TRY_EVENT == 5) {
            Cursor errorLists = RuntimeData.Database.query(
                    ErrorBookContract.ErrorBookEntry.TABLE_NAME,
                    null,
                    "date == ?",
                    new String[]{RuntimeData.getDateString()},
                    null, null, ErrorBookContract.ErrorBookEntry.COLUMN_ERR_TIMES + " DESC");
            if (errorLists.getCount() > 0) {
                errorLists.moveToFirst();
                int id = errorLists.getInt(0);
                int errorTimes = errorLists.getInt(4);
                String q = errorLists.getString(2);
                String a = errorLists.getString(3);
                errorLists.close();
                return new Question(
                        "(错题本)\r\n"+q,
                        a,
                        question -> {
                            ContentValues values = new ContentValues();
                            values.put(ErrorBookContract.ErrorBookEntry.COLUMN_ERR_TIMES, errorTimes + 1);
                            RuntimeData.Database.update(ErrorBookContract.ErrorBookEntry.TABLE_NAME, values, "_id = ?", new String[]{String.valueOf(id)});
                            RuntimeData.Done.setValue(RuntimeData.Done.getValue() + 1);
                        },
                        question -> {
                            if (errorTimes == 1) {
                                RuntimeData.Database.delete(ErrorBookContract.ErrorBookEntry.TABLE_NAME, "_id = ?", new String[]{String.valueOf(id)});
                            } else {
                                ContentValues values = new ContentValues();
                                values.put(ErrorBookContract.ErrorBookEntry.COLUMN_ERR_TIMES, errorTimes - 1);
                                RuntimeData.Database.update(ErrorBookContract.ErrorBookEntry.TABLE_NAME, values, "_id = ?", new String[]{String.valueOf(id)});
                            }

                            RuntimeData.Correct.setValue(RuntimeData.Correct.getValue() + 1);
                            RuntimeData.Done.setValue(RuntimeData.Done.getValue() + 1);
                        }
                );
            }

        }*/
        Pair<String, String> questionPair = MathQuestionGenerator.generate(RuntimeData.Difficulty.getValue());
        return new Question(questionPair.first,
                questionPair.second,
                question -> {
                    RuntimeData.insert(questionPair.first, questionPair.second, 1);//历史题目记录
                    RuntimeData.Done.setValue(RuntimeData.Done.getValue() + 1);
                },
                question -> {
                    RuntimeData.Correct.setValue(RuntimeData.Correct.getValue() + 1);
                    RuntimeData.Done.setValue(RuntimeData.Done.getValue() + 1);
                });
    }
}