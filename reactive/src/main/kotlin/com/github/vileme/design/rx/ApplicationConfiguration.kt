package com.github.vileme.design.rx

import com.github.vileme.design.rx.Currency.*
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import com.natpryce.konfig.ConfigurationProperties
import com.natpryce.konfig.Key
import com.natpryce.konfig.stringType
import org.springframework.context.annotation.Bean
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@EnableReactiveMongoRepositories
object ApplicationConfiguration : AbstractReactiveMongoConfiguration() {

    private val configuration = ConfigurationProperties.fromResource("defaults.properties")

    val conversions = mapOf(
        USD to mapOf(
            RUB to 75.83,
            EUR to 0.85,
        ),
        EUR to mapOf(
            RUB to 89.35,
            USD to 1.18,
        ),
        RUB to mapOf(
            USD to 0.013,
            EUR to 0.011,
        ),
    )

    @Bean fun mongoClient(): MongoClient = MongoClients.create(configuration[Key("mongo.url", stringType)])

    override fun getDatabaseName() = configuration[Key("mongo.database.name", stringType)]

    override fun reactiveMongoClient() = mongoClient()
}
