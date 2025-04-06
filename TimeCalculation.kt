// TimeCalculation.kt
package com.example.hector_new

import java.util.concurrent.TimeUnit

object TimeCalculation {

    fun calculateTimeStats(times: List<Long>): UserTimeStats {
        if (times.isEmpty()) {
            return UserTimeStats("User", "00:00", "00:00", "00:00")
        }

        val sortedTimes = times.sorted()
        val bestTime = sortedTimes.first()
        val worstTime = sortedTimes.last()
        val averageTime = sortedTimes.average().toLong()

        return UserTimeStats(
            "User",
            formatTime(worstTime),
            formatTime(averageTime),
            formatTime(bestTime)
        )
    }

    private fun formatTime(millis: Long): String {
        val minutes = TimeUnit.MILLISECONDS.toMinutes(millis)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(minutes)
        return String.format("%02d:%02d", minutes, seconds)
    }

    fun calculateSingleTime(startTime: Long, endTime: Long): String {
        return formatTime(endTime - startTime)
    }
}
