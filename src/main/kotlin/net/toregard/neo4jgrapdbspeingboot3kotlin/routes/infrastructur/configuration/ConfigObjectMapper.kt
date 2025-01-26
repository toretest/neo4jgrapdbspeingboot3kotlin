package net.toregard.neo4jgrapdbspeingboot3kotlin.routes.infrastructur.configuration

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import net.toregard.neo4jgrapdbspeingboot3kotlin.routes.infrastructur.adapter.Module
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.reactive.config.WebFluxConfigurer


@Configuration
class ConfigObjectMapper : WebFluxConfigurer {

    @Bean
    fun objectMapper(): ObjectMapper = jacksonObjectMapper().apply {
        registerModule(JavaTimeModule())
        val module = SimpleModule()
            .addDeserializer(Module::class.java, ModuleDeserializer())
            .addSerializer(Module::class.java, ModuleSerializer())
        registerModule(module)
    }

    @Bean
    fun customJackson2HttpMessageConverter(objectMapper: ObjectMapper): MappingJackson2HttpMessageConverter {
        return MappingJackson2HttpMessageConverter(objectMapper)
    }

    override fun configureHttpMessageCodecs(configurer: org.springframework.http.codec.ServerCodecConfigurer) {
        configurer.defaultCodecs().jackson2JsonEncoder(org.springframework.http.codec.json.Jackson2JsonEncoder(objectMapper()))
        configurer.defaultCodecs().jackson2JsonDecoder(org.springframework.http.codec.json.Jackson2JsonDecoder(objectMapper()))
    }
}

class ModuleDeserializer : JsonDeserializer<Module>() {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): Module {
        val mapper = p.codec as ObjectMapper
        val node: JsonNode = mapper.readTree(p)

        val id = node.get("id").asText()
        val name = node.get("name").asText()
        val type = node.get("type").asText()
        val description = node.get("description").asText()

        val additionalAttributes = mutableMapOf<String, Any>()
        node.fields().forEachRemaining { (key, value) ->
            if (key !in listOf("id", "name", "type", "description")) {
                additionalAttributes[key] = mapper.readValue(value.toString())
            }
        }

        return Module(id, name, type, description, additionalAttributes)
    }
}

class ModuleSerializer : JsonSerializer<Module>() {
    override fun serialize(module: Module, gen: JsonGenerator, serializers: SerializerProvider) {
        gen.writeStartObject()
        gen.writeStringField("id", module.id)
        gen.writeStringField("name", module.name)
        gen.writeStringField("type", module.type)
        gen.writeStringField("description", module.description)
        module.additionalAttributes.forEach { (key, value) ->
            gen.writeObjectField(key, value)
        }
        gen.writeEndObject()
    }
}