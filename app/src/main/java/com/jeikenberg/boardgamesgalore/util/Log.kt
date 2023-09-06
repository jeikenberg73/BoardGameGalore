package com.jeikenberg.boardgamesgalore.util

import android.util.Log

class Log {
    companion object {
        /**
         * Set this flag to enable or disable the debugging Log tags.
         * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;TRUE =
         * Tag are enabled.
         * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FALSE =
         * Tag are disabled.
         */
        private const val LOG = true

        /**
         * [android.util.Log.i]
         *
         * @param tag
         * tag Used to identify the source of a log message. It usually
         * identifies the class or activity where the log call occurs.
         * msg.
         * @param string
         * The message you would like logged.
         */
        fun i(tag: String?, string: String?) {
            if (LOG) Log.i(tag, string!!)
        }

        /**
         * [android.util.Log.i]
         *
         * @param tag
         * tag Used to identify the source of a log message. It usually
         * identifies the class or activity where the log call occurs.
         * msg.
         * @param string
         * The message you would like logged.
         */
        fun i(tag: String?, string: String?, e: Throwable?) {
            if (LOG) Log.i(tag, string!!)
        }

        /**
         * [android.util.Log.e]
         *
         * @param tag
         * tag Used to identify the source of a log message. It usually
         * identifies the class or activity where the log call occurs.
         * msg.
         * @param string
         * The message you would like logged.
         */
        fun e(tag: String?, string: String?) {
            if (LOG) Log.e(tag, string!!)
        }

        /**
         * [android.util.Log.e]
         *
         * @param tag
         * tag Used to identify the source of a log message. It usually
         * identifies the class or activity where the log call occurs.
         * msg.
         * @param string
         * The message you would like logged.
         */
        fun e(tag: String?, string: String?, e: Throwable?) {
            if (LOG) Log.e(tag, string!!)
        }

        /**
         * [android.util.Log.d]
         *
         * @param tag
         * tag Used to identify the source of a log message. It usually
         * identifies the class or activity where the log call occurs.
         * msg.
         * @param string
         * The message you would like logged.
         */
        fun d(tag: String?, string: String?) {
            if (LOG) Log.d(tag, string!!)
        }

        /**
         * [android.util.Log.d]
         *
         * @param tag
         * tag Used to identify the source of a log message. It usually
         * identifies the class or activity where the log call occurs.
         * msg.
         * @param string
         * The message you would like logged.
         */
        fun d(tag: String?, string: String?, e: Throwable?) {
            if (LOG) Log.d(tag, string!!)
        }

        /**
         * [android.util.Log.v]
         *
         * @param tag
         * tag Used to identify the source of a log message. It usually
         * identifies the class or activity where the log call occurs.
         * msg.
         * @param string
         * The message you would like logged.
         */
        fun v(tag: String?, string: String?) {
            if (LOG) Log.v(tag, string!!)
        }

        /**
         * [android.util.Log.v]
         *
         * @param tag
         * tag Used to identify the source of a log message. It usually
         * identifies the class or activity where the log call occurs.
         * msg.
         * @param string
         * The message you would like logged.
         */
        fun v(tag: String?, string: String?, e: Throwable?) {
            if (LOG) Log.v(tag, string!!)
        }

        /**
         * [android.util.Log.w]
         *
         * @param tag
         * tag Used to identify the source of a log message. It usually
         * identifies the class or activity where the log call occurs.
         * msg.
         * @param string
         * The message you would like logged.
         */
        fun w(tag: String?, string: String?) {
            if (LOG) Log.w(tag, string!!)
        }

        /**
         * [android.util.Log.w]
         *
         * @param tag
         * tag Used to identify the source of a log message. It usually
         * identifies the class or activity where the log call occurs.
         * msg.
         * @param string
         * The message you would like logged.
         */
        fun w(tag: String?, string: String?, e: Throwable?) {
            if (LOG) Log.w(tag, string!!)
        }
    }
}