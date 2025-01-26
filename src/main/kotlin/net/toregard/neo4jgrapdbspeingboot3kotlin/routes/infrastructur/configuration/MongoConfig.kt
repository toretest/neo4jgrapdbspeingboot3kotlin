package net.toregard.neo4jgrapdbspeingboot3kotlin.routes.infrastructur.configuration

import jakarta.annotation.PostConstruct
import net.toregard.neo4jgrapdbspeingboot3kotlin.routes.infrastructur.adapter.Route
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.ReactiveMongoTemplate

/**
 * Added this class to add created automatic removal after a time on collections.
 * Read from the apllicatiomm.yml file
 *
 * NB! Need to add to alle collections that should have this feature
 */

@Configuration
class MongoConfig(
    val reactiveMongoTemplate: ReactiveMongoTemplate,
    @Value("\${di.collections.routes.deletedAfter}") private val deletedAfter: String
) {

    @PostConstruct
    fun initIndexes() {
        val expireAfterSeconds = parseDurationToSeconds(deletedAfter)
        reactiveMongoTemplate.indexOps(Route::class.java).ensureIndex(
            org.springframework.data.mongodb.core.index.Index()
                .on("createdAt", org.springframework.data.domain.Sort.Direction.ASC)
                .expire(expireAfterSeconds)
        ).subscribe()
    }

    private fun parseDurationToSeconds(duration: String): Long {
        val timeUnit = duration.takeLast(1)
        val timeValue = duration.dropLast(1).toLong()
        return when (timeUnit) {
            "s" -> timeValue
            "m" -> timeValue * 60
            "h" -> timeValue * 3600
            "d" -> timeValue * 86400
            else -> throw IllegalArgumentException("Invalid time unit in duration: $duration")
        }
    }
}
