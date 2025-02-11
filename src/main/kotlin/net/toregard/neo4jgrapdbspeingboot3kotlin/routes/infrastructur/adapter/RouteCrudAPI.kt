package net.toregard.neo4jgrapdbspeingboot3kotlin.routes.infrastructur.adapter

import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.index.CompoundIndexes
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*
import java.time.Instant

@RestController
@RequestMapping("/routes/api")
class RouteCrudAPI(private val routeService: RouteService) {

    @PostMapping("/route")
    suspend fun saveRoute(@RequestBody route: Route): Route {
        return routeService.saveRoute(route)
    }

    @GetMapping("/route/{routeId}")
    suspend fun getRoute(@PathVariable routeId: String): Route? {
        return routeService.getRoute(routeId)
    }
}

@Repository
interface RouteRepository : ReactiveMongoRepository<Route, String> {
    suspend fun findByCompanyId(companyId: Int): List<Route>
}

@Service
class RouteService(private val routeRepository: RouteRepository) {
    suspend fun saveRoute(route: Route): Route {
        return routeRepository.save(route).awaitFirstOrNull()!!
    }

    suspend fun getRoute(routeId: String): Route? {
        return routeRepository.findById(routeId).awaitFirstOrNull()
    }
}

@Document(collection = "routes")
@CompoundIndexes(
    CompoundIndex(name = "id_name_idx", def = "{'id': 1, 'name': 1}")
)
data class Route (
    @Id
    val id: String,
    //@Indexed(expireAfter = "60s") // 10 hours
    //val createdAt: Instant = Instant.now(),
    //@Indexed(expireAfter = "\${di.collections.routes.deletedAfter}")
    val createdAt: Instant = Instant.now(),
    val companyId: Int,
    val name: String,
    val modules: List<Module>

)

data class Module(
    @Id
    val id: String,
    val name: String,
    val type: String,
    val description: String,
    val additionalAttributes: MutableMap<String, Any> = mutableMapOf()
)




