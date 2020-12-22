package statistic

interface EventsStatistic {
    fun incEvent(name: String)
    fun getEventStatisticByName(name: String): Double
    fun getAllEventsStatistic(): Map<String, Double>
    fun printStatistic()
}