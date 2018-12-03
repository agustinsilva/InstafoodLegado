package ar.com.instafood.fragments.order

import android.content.Context
import android.preference.PreferenceManager

class PreferenceUtils {

    companion object {

        private const val TIMER_STATE_ID = "com.resocoder.timer.timer_state"
        private const val SCORED_LOCKED = "com.resocoder.scored_locked"

        fun getTimerLength(): Int{
            return 1
        }

        fun getTimerState(context: Context): TimerState {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            val ordinal = preferences.getInt(TIMER_STATE_ID, 0)
            return TimerState.values()[ordinal]
        }

        fun setTimerState(state: TimerState, context: Context){
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            val ordinal = state.ordinal
            editor.putInt(TIMER_STATE_ID, ordinal)
            editor.apply()
        }

        fun setScoreLocked(locked: Boolean, context: Context){
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putBoolean(SCORED_LOCKED, locked)
            editor.apply()
        }

        fun getScoreLocked(context: Context): Boolean {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getBoolean(SCORED_LOCKED, false)
        }

    }

}