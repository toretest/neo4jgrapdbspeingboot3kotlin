package net.toregard.neo4jgrapdbspeingboot3kotlin.routes.infrastructur.adapter

import kotlinx.coroutines.reactive.awaitFirstOrNull
import net.toregard.neo4jgrapdbspeingboot3kotlin.routes.domain.Routes
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/routes/api")
class RouteCrudAPI(private val routesService: RoutesService) {

    @PostMapping("/route")
    suspend fun saveRoute(@RequestBody routes: Routes): Routes {
        return routesService.saveRoute(routes)
    }

    @GetMapping("/route/{id}")
    suspend fun getRoute(@PathVariable id: String): Routes? {
        return routesService.getRoute(id)
    }
}

@Repository
interface RoutesRepository : ReactiveMongoRepository<Routes, String> {
    //suspend fun findByCompanyId(companyId: Int): List<Routes>
}

@Service
class RoutesService(private val routesRepository: RoutesRepository) {
    suspend fun saveRoute(routes: Routes): Routes {
        return routesRepository.save(routes).awaitFirstOrNull()!!
    }

    suspend fun getRoute(id: String): Routes? {
        return routesRepository.findById(id).awaitFirstOrNull()
    }
}





