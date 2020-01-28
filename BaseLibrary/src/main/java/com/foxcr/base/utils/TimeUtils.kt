package com.foxcr.base.utils

import android.annotation.SuppressLint
import android.content.Context
import com.foxcr.base.R
import java.text.SimpleDateFormat
import java.util.*

object TimeUtils {
    fun QQFormatTime(context: Context, time: Long): String? {
        val resources = context.resources
        val date = Date()
        date.time = time
        return if (isSameYear(date)) { //同一年 显示MM-dd HH:mm
            val simpleDateFormat =
                SimpleDateFormat("HH:mm", Locale.getDefault())
            if (isSameDay(date)) { //同一天 显示HH:mm
                val minute = minutesAgo(time)
                if (minute < 60) { //1小时之内 显示n分钟前
                    if (minute <= 2) { //一分钟之内，显示刚刚
                        simpleDateFormat.format(date)
                    } else {
                        minute.toString() + " " + resources.getString(R.string.preMinute)
                    }
                } else {
                    simpleDateFormat.format(date)
                }
            } else {
                if (isYesterday(date)) { //昨天，显示昨天+HH:mm
                    resources.getString(R.string.yesterday) + " " + simpleDateFormat.format(
                        date
                    )
                } else if (isSameWeek(date)) { //本周,显示周几+HH:mm
                    var weekday: String? = null
                    if (date.day == 1) {
                        weekday = resources.getString(R.string.mon)
                    }
                    if (date.day == 2) {
                        weekday = resources.getString(R.string.Tue)
                    }
                    if (date.day == 3) {
                        weekday = resources.getString(R.string.Wed)
                    }
                    if (date.day == 4) {
                        weekday = resources.getString(R.string.Thu)
                    }
                    if (date.day == 5) {
                        weekday = resources.getString(R.string.Fri)
                    }
                    if (date.day == 6) {
                        weekday = resources.getString(R.string.Sat)
                    }
                    if (date.day == 0) {
                        weekday = resources.getString(R.string.Sun)
                    }
                    weekday + " " + simpleDateFormat.format(date)
                } else { //同一年 显示MM-dd HH:mm
                    val sdf =
                        SimpleDateFormat("MM-dd HH:mm", Locale.CHINA)
                    sdf.format(date)
                }
            }
        } else { //不是同一年 显示完整日期yyyy-MM-dd HH:mm
            val sdf =
                SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA)
            sdf.format(date)
        }
    }

    /**
     * 几分钟前
     *
     * @param time
     * @return
     */
    fun minutesAgo(time: Long): Int {
        return ((System.currentTimeMillis() - time) / 60000).toInt()
    }


    /**
     * 与当前时间是否在同一周
     * 先判断是否在同一年，然后根据Calendar.DAY_OF_YEAR判断所得的周数是否一致
     *
     * @param date
     * @return
     */
    fun isSameWeek(date: Date?): Boolean {
        return if (isSameYear(date)) {
            val calendar = Calendar.getInstance()
            calendar.time = date
            val a = calendar[Calendar.DAY_OF_YEAR]
            val now = Date()
            val calendar1 = Calendar.getInstance()
            calendar1.time = now
            val b = calendar1[Calendar.DAY_OF_WEEK]
            a == b
        } else {
            false
        }
    }

    /**
     * 是否是当前时间的昨天
     * 获取指定时间的后一天的日期，判断与当前日期是否是同一天
     *
     * @param date
     * @return
     */
    fun isYesterday(date: Date?): Boolean {
        val yesterday = getNextDay(date, 1)
        return isSameDay(yesterday)
    }

    /**
     * 判断与当前日期是否是同一天
     *
     * @param date
     * @return
     */
    fun isSameDay(date: Date?): Boolean {
        return isEquals(date, "yyyy-MM-dd")
    }

    /**
     * 判断与当前日期是否是同一月
     *
     * @param date
     * @return
     */
    fun isSameMonth(date: Date?): Boolean {
        return isEquals(date, "yyyy-MM")
    }

    /**
     * 判断与当前日期是否是同一年
     *
     * @param date
     * @return
     */
    fun isSameYear(date: Date?): Boolean {
        return isEquals(date, "yyyy")
    }


    /**
     * 格式化Date，判断是否相等
     *
     * @param date
     * @return 是返回true，不是返回false
     */
    private fun isEquals(date: Date?, format: String): Boolean { //当前时间
        val now = Date()
        @SuppressLint("SimpleDateFormat") val sf =
            SimpleDateFormat(format)
        //获取今天的日期
        val nowDay = sf.format(now)
        //对比的时间
        val day = sf.format(date)
        return day == nowDay
    }

    /**
     * 获取某个date第n天的日期
     * n<0 表示前n天
     * n=0 表示当天
     * n>1 表示后n天
     *
     * @param date
     * @param n
     * @return
     */
    fun getNextDay(date: Date?, n: Int): Date? {
        var date = date
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DAY_OF_MONTH, n)
        date = calendar.time
        return date
    }

    fun stampToDate(s: String): String? {
        val res: String
        val simpleDateFormat =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val lt: Long = s.toLong()
        val date = Date(lt)
        res = simpleDateFormat.format(date)
        return res
    }
}