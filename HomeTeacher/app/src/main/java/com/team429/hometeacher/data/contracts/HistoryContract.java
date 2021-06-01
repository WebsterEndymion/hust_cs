package com.team429.hometeacher.data.contracts;

import android.provider.BaseColumns;

/**
 * 历史Table原型
 * @author Alaric
 */

public final class HistoryContract {
    private HistoryContract(){}

    public static final class HistoryEntry implements BaseColumns{
        public static final String TABLE_NAME = "history";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_CORRECT_NUM = "correct_num";
        public static final String COLUMN_DONE_NUM = "done_num";
        public static final String COLUMN_TARGET_NUM = "target_num";
    }
}
