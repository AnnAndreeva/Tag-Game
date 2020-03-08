package com.example.tag_game.data;

import android.provider.BaseColumns;

public final class ResultsContract {
    public ResultsContract() {
    }

    public static final class ResultsTable implements BaseColumns {
        public final static String TABLE_NAME = "results";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_NAME = "name";
        public final static String COLUMN_TIME = "time";

    }
}
