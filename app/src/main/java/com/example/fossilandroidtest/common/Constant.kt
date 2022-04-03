package com.example.fossilandroidtest.common

class Constant {
    companion object {
        const val EVERY_DAY = "Every Day"
        const val MONDAY = "Mon"
        const val TUESDAY = "Tue"
        const val WEDNESDAY = "Wed"
        const val THURSDAY = "Thu"
        const val FRIDAY = "Fri"
        const val SATURDAY = "Sat"
        const val SUNDAY = "Sun"
        const val REPEAT = "REPEAT"
        const val ALARM_NAME = "NAME"
        const val AlARM_RECEIVER_ACTION = "AlARM_RECEIVER_ACTION"

        const val DEFAULT_SECOND = 0
        const val DEFAULT_MIL_SECOND = 0
        const val PENDING_INTENT_FLAG = 0

        const val SNOOZE_MINUTE_DEFAULT = 2

        const val CHANNEL_ID = "ALARM_CHANNEL"
        const val NOTIFICATION_ID = 200
        const val CHANNEL_NAME = "ALARM_SERVICE_CHANNEL"

        const val LIST_ALARM_TITLE_SCREEN = "Alarm"
        const val ADD_ALARM_TITLE_SCREEN = "Add Alarm"

        const val DATABASE_NAME = "AlarmDatabase"

        const val DEFAULT_ALARM_NAME = "Hello"

        const val RUN_DAILY_MIL_TIME = (24 * 60 * 60 * 1000).toLong()

        const val DISMISS_ACTION = "Dismiss"
        const val SNOOZE_ACTION = "Snooze"

    }
}