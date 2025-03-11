package net.toregard.neo4jgrapdbspeingboot3kotlin.routes.domain

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.index.CompoundIndexes
import org.springframework.data.mongodb.core.mapping.Document

import java.time.Instant


@Document(collection = "routes")
@CompoundIndexes(
    CompoundIndex(name = "id_name_idx", def = "{'id': 1, 'name': 1}")
)
data class Routes(
    @Id
    val id: Id,
    val metadata: Metadata,
    val route: Route
)
data class Metadata(
    val source: String,
    val schemaName: String,
    val schemaVersion: String,
    val isMotherRoute: Boolean,
    val childRouteIds: List<String> = emptyList(),
    val allocatedModuleIds: List<String> = emptyList(),
    val splitPoints: List<String> = emptyList(),
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val date: String
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Route (
    @JsonProperty("_id")
    val id: String,
    val createdAt: Instant = Instant.now(),
    val companyId: Int,
    val regionID:  Int,
    val areaId: Int,
    val number: String,
    val name: String,
    val schema: String,
    val distributionType : Int,
    val vehicle: String,
    @JsonProperty("modules")
    val stopLocation: List<Module>
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Module(
    @JsonProperty("_id")
    val id: String,
    val name: String,
    val type: String,
    val additionalAttributes: MutableMap<String, Any> = mutableMapOf()
)
