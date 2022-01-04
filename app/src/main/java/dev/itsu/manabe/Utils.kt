package dev.itsu.manabe

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.res.ResourcesCompat
import org.threeten.bp.LocalDate
import java.util.*

object Utils {

    val MODULES = mapOf(
        "春A" to listOf("2021/4/8", "2021/5/18"),
        "春B" to listOf("2021/5/20", "2021/6/23"),
        "春C" to listOf("2021/7/1", "2021/7/29"),
        "秋A" to listOf("2021/10/1", "2021/11/9"),
        "秋B" to listOf("2021/11/11", "2021/12/21"),
        "秋C" to listOf("2021/1/6", "2021/2/15"),
    )

    val TIMETABLES = mapOf(
        1 to listOf("8:40", "9:55"),
        2 to listOf("10:10", "11:25"),
        3 to listOf("12:15", "13:30"),
        4 to listOf("13:45", "15:00"),
        5 to listOf("15:15", "16:30"),
        6 to listOf("16:45", "18:00")
    )

    private val DAY_OF_WEEKS = listOf(
        "月", "火", "水", "木", "金", "土", "日"
    )

    fun limitToColorDrawable(context: Context, limit: Long): Drawable {
        val day = 86400000
        val delta = limit - System.currentTimeMillis()
        return when {
            delta <= day -> ResourcesCompat.getDrawable(
                context.resources,
                R.drawable.background_red,
                context.theme
            )!!
            delta <= day * 3 -> ResourcesCompat.getDrawable(
                context.resources,
                R.drawable.background_yellow,
                context.theme
            )!!
            delta <= day * 7 -> ResourcesCompat.getDrawable(
                context.resources,
                R.drawable.background_green,
                context.theme
            )!!
            else -> ResourcesCompat.getDrawable(
                context.resources,
                R.drawable.background_thumbnail,
                context.theme
            )!!
        }
    }

    fun getHoldingLecture(): Int {
        val min = 1000 * 60
        val current = Calendar.getInstance()

        if (current.get(Calendar.DATE) in listOf(Calendar.SUNDAY, Calendar.SATURDAY)) return -1

        TIMETABLES.forEach { (key, value) ->
            val start = value[0].split(":")
            val end = value[1].split(":")
            if (start[0].toInt() * min * 60 + start[1].toInt() * min <
                    current.get(Calendar.HOUR_OF_DAY) * min * 60 + current.get(Calendar.MINUTE) &&
                    current.get(Calendar.HOUR_OF_DAY) * min * 60 + current.get(Calendar.MINUTE) <
                    end[0].toInt() * min * 60 + end[1].toInt() * min) {
                return key
            }
        }
        return -1
    }

    fun isHoldingClass(seasons: Map<String, List<String>>, times: Map<String, List<Int>>): Pair<String, Int>? {
        val today = LocalDate.now() // LocalDate.parse("2021/11/05", DateTimeFormatter.ofPattern("yyyy/MM/dd"))
        var result: Pair<String, Int>? = null

        seasons.forEach season@{ season, alphabets ->
            alphabets.forEach { alphabet ->
                val startSplit = MODULES[season + alphabet]!![0].split("/")
                val startDate = LocalDate.of(startSplit[0].toInt(), startSplit[1].toInt(), startSplit[2].toInt())
                val endSplit = MODULES[season + alphabet]!![1].split("/")
                val endDate = LocalDate.of(endSplit[0].toInt(), endSplit[1].toInt(), endSplit[2].toInt())

                if (!(startDate.isAfter(today) || endDate.isBefore(today))) {
                    times.forEach time@{ dow, numbers ->
                        if (dow != DAY_OF_WEEKS[today.dayOfWeek.ordinal]) return@time
                        numbers.forEach number@{ number ->
                            if (TIMETABLES[number] == null) return@number

                            val startTimeSplit = TIMETABLES[number]!![0].split(":")
                            val startTime = today.atTime(startTimeSplit[0].toInt(), startTimeSplit[1].toInt())
                            val endTimeSplit = TIMETABLES[number]!![1].split(":")
                            val endTime = today.atTime(endTimeSplit[0].toInt(), endTimeSplit[1].toInt())
                            val nowTime = today.atTime(Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE))

                            if (!(startTime.isAfter(nowTime) || endTime.isBefore(nowTime))) {
                                result = Pair(dow, number)
                                return@time
                            }
                        }
                    }
                }
                if (result != null) return@season
            }
        }

        return result
    }

}

infix fun <R> R.ifNull(func: () -> R) = this ?: func()

infix fun <R> R.ifNotNull(func: (R) -> R): R = if (this != null) func(this) else this