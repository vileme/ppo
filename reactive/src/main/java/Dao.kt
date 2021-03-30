import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.MongoClients;
import com.mongodb.rx.client.Success
import com.mongodb.client.model.Filters.eq
import rx.Observable
import java.util.*

public class Dao(url: String, dbName: String) {
    private val client = MongoClients.create(url)
    private val db = client.getDatabase(dbName)

    fun createUser(user: User): Observable<Success> {
        return db.getCollection("users")
            .insertOne(user.toDoc())
    }

    fun getUser(user_id: UUID): Observable<User> {
        return db.getCollection("users")
            .find(eq("id", user_id.toString()))
            .first()
            .map(::User)
    }

    fun getAllUsers(): Observable<User> {
        return db.getCollection("users").find().toObservable().map(::User)
    }

    fun addGood(good: Good): Observable<Success> {
        return db.getCollection("goods")
            .insertOne(good.toDoc())
    }

    fun getGood(good_id: UUID): Observable<Good> {
        return db.getCollection("goods")
            .find(eq("id", good_id.toString()))
            .first()
            .map(::Good)
    }

    fun getAllGood(): Observable<Good> {
        return db.getCollection("goods").find().toObservable().map(::Good)
    }
}