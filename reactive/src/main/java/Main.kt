import com.mongodb.rx.client.Success
import io.netty.handler.codec.http.HttpMethod
import io.reactivex.netty.protocol.http.server.HttpServer
import rx.Observable
import java.util.*
import javax.management.RuntimeErrorException

/*
 *  API:
 *  - POST user/?currency
 *  - POST good/?price?currency
 *  - GET view_good/?user_id?good_id
 *  - GET all_users
 *  - GET all_goods
 */

val dao = Dao("mongodb://localhost:27017", "shop")

fun main() {
    HttpServer.newServer(8080)
        .start { req, resp ->
            val query: String = req.decodedPath.substring(1)
            val method = req.httpMethod
            val response: Observable<String> = processQuery(method, query, req.queryParameters)
            return@start resp.writeString(response)
        }
        .awaitShutdown()
}

fun processQuery(
    method: HttpMethod,
    query: String,
    queryParameters: Map<String, MutableList<String>>
): Observable<String> {
    return when (query) {
        "user" -> when (method) {
            HttpMethod.POST -> createUser(fromString(queryParameters["currency"]?.get(0)) ?: defaultCurrency)
            else -> throw RuntimeException("Unexpected method $method for query $query")
        }
        "good" -> when (method) {
            HttpMethod.POST -> {
                val value = queryParameters["price"]?.get(0)?.toInt() ?: throw RuntimeException("Bad request")
                val currencyType = fromString(queryParameters["currency"]?.get(0)) ?: defaultCurrency
                addGood(currencyType, value)
            }
            else -> throw RuntimeException("Unexpected method $method for query $query")
        }
        "view" -> when(method) {
            HttpMethod.GET -> {
                val user_id = queryParameters["user_id"]?.get(0) ?: throw RuntimeException("Bad request")
                val good_id = queryParameters["good_id"]?.get(0) ?: throw RuntimeException("Bad request")
                showGood(UUID.fromString(user_id), UUID.fromString(good_id))
            }
            else -> throw RuntimeException("Unexpected method $method for query  $query")
        }
        "all_users" -> {
            if (method != HttpMethod.GET)
                throw RuntimeException("Unexcepted method $method for query $query")
            getAllUser()
        }
        "all_goods" -> {
            if (method != HttpMethod.GET)
                throw RuntimeException("Unexcepted method $method for query $query")
            getAllGoods()
        }
        else -> throw RuntimeException("Unexpected query $query")
    }
}

fun createUser(currencyType: CurrencyType): Observable<String> {
    val id = UUID.randomUUID()
    return dao.createUser(User(id, currencyType)).map { success ->
        if (success == Success.SUCCESS) "Created id=$id" else "Failed"
    }
}

fun addGood(currencyType: CurrencyType, value: Int): Observable<String> {
    val id = UUID.randomUUID()
    return dao.addGood(Good(id, value, currencyType)).map { success ->
        if (success == Success.SUCCESS) "Created id=$id" else "Failed"
    }
}

fun showGood(user_id: UUID, good_id: UUID): Observable<String> {
    return dao.getUser(user_id).map(User::preferredCurrencyType).flatMap { preferredCurrency ->
           dao.getGood(good_id).map { good ->
               "Your good: id=${good.id}, value=${convert(good.currency, good.price, preferredCurrency)} in $preferredCurrency"
           }
    }
}

fun getAllUser(): Observable<String> {
    return dao.getAllUsers().map { user -> "$user\n" }
}

fun getAllGoods(): Observable<String> {
    return dao.getAllGood().map { good -> "$good\n"}
}