import org.bson.Document
import java.util.*
import kotlin.collections.Map
import kotlin.math.roundToInt

enum class CurrencyType {
    dollar, euro, rubles;
}

val defaultCurrency = CurrencyType.dollar

fun fromString(str: String?): CurrencyType? = when (str) {
    "dollar" -> CurrencyType.dollar
    "euro" -> CurrencyType.euro
    "rubles" -> CurrencyType.rubles
    else -> null
}

val currency_course: Map<Pair<CurrencyType, CurrencyType>, Double> = mapOf(
    Pair(Pair(CurrencyType.dollar, CurrencyType.euro), 0.94),
    Pair(Pair(CurrencyType.euro, CurrencyType.dollar), 1.07),
    Pair(Pair(CurrencyType.dollar, CurrencyType.rubles), 80.61),
    Pair(Pair(CurrencyType.rubles, CurrencyType.dollar), 0.012),
    Pair(Pair(CurrencyType.euro, CurrencyType.rubles), 86.25),
    Pair(Pair(CurrencyType.rubles, CurrencyType.euro), 0.012)
)


fun convert(srcCurrencyType: CurrencyType, srcValue: Int, dstCurrencyType: CurrencyType): Int {
    if (srcCurrencyType == dstCurrencyType)
        return srcValue
    val m = currency_course[Pair(srcCurrencyType, dstCurrencyType)] ?: throw RuntimeException("Logic error")
    return (srcValue * m).roundToInt()
}

class User(id: UUID, preferredCurrencyType: CurrencyType) {
    val id = id
        get() = field
    val preferredCurrencyType = preferredCurrencyType
        get() = field

    constructor(doc: Document) :
            this(
                UUID.fromString(doc.getString("id")),
                fromString(doc.getString("currency")) ?: defaultCurrency
            )

    fun toDoc(): Document {
        val doc = Document()
        doc["id"] = id
        doc["currency"] = preferredCurrencyType.toString()
        return doc
    }

    override fun toString(): String = "User: id=$id, preferredCurrencyType=$preferredCurrencyType"
}

class Good(id: UUID, price: Int, currency: CurrencyType) {
    val id = id
        get() =  field
    val price = price
        get() = field
    val currency = currency
        get() = field

    constructor(doc: Document) :
            this(
                UUID.fromString(doc.getString("id")),
                doc.getInteger("price"),
                fromString(doc.getString("currency")) ?: defaultCurrency
            )

    fun toDoc(): Document {
        val doc = Document()
        doc["id"] = id.toString()
        doc["currency"] = currency
        doc["price"] = price
        return doc
    }

    override fun toString(): String = "Good: id=$id, price=$id, currency=$currency"
}