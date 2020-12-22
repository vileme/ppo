package statistic

import clock.Clock
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*


class EventsStatisticImpl(private var clock: Clock) : EventsStatistic {
    private val eventAmount: MutableMap<String, Int> = HashMap()
    private val eventQueue: Queue<Pair<String, Instant>> = LinkedList()

    private fun eventCanBeRemoved(nowTime: Instant): Boolean {
        if (eventQueue.isEmpty()) return false
        return ChronoUnit.HOURS.between(
            eventQueue.element().second,
            nowTime
        ) >= 1
    }

    private fun removeOldEvents(nowTime: Instant) {
        while (eventCanBeRemoved(nowTime)) {
            val removingEvent = eventQueue.poll().first
            val amount = eventAmount.getOrDefault(removingEvent, 0)
            when {
                amount == 0 -> return
                amount > 1 -> eventAmount[removingEvent] = amount - 1
                else -> eventAmount.remove(removingEvent)
            }
        }
    }

    override fun incEvent(name: String) {
        val now: Instant = clock.now()
        removeOldEvents(now)
        eventQueue.add(Pair(name, now))
        eventAmount[name] = eventAmount.getOrDefault(name, 0) + 1
    }

    override fun getEventStatisticByName(name: String): Double {
        val now: Instant = clock.now()
        removeOldEvents(now)
        return eventAmount.getOrDefault(name, 0) / 60.0
    }

    override fun getAllEventsStatistic(): Map<String, Double> {
        val now: Instant = clock.now()
        removeOldEvents(now)
        val statisticMap: MutableMap<String, Double> = HashMap()
        for ((key, value) in eventAmount) {
            statisticMap[key] = value / 60.0
        }
        return statisticMap
    }

    override fun printStatistic() {
        println("Events statistic:")
        for ((event, statistic) in getAllEventsStatistic()) {
            print("event=\"$event\", rpm=$statistic\n")
        }
    }
}