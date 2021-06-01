package com.team429.hometeacher.data.contracts;

import android.provider.BaseColumns;
/**
 * 错题本Table原型
 * @author Alaric
 */

public final class ErrorBookContract {
    private ErrorBookContract(){ }

    public static final class ErrorBookEntry implements BaseColumns{
        public static final String TABLE_NAME = "error_book";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_ERR_TIMES = "err_times";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_ANSWER = "answer";
    }
}
