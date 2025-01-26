# doc

## swagger

http://localhost:8080/swagger-ui.html and the API documentation at http://localhost:8080/api-docs.

## annotation supported

db.serverStatus().metrics.ttl
db.routes.getIndexes()
db.routes.createIndex({ "createdAt": 1 }, { expireAfterSeconds: 60 })




@Document: Marks a class as a MongoDB document. This annotation is used to indicate that the class should be mapped to a MongoDB collection.

@Id: Marks a field as the primary key. This annotation is used to specify the field that will be used as the unique identifier for the document.

@Field: Specifies the name of the field in the MongoDB document. This annotation is used to map a class field to a specific field name in the MongoDB document.

@Indexed: Marks a field to be indexed. This annotation is used to create an index on the specified field to improve query performance.
```kotlin
// mongodb driver 5.x
@Indexed(expireAfter = "60s") // 60 seconds
val createdAt: Instant = Instant.now()

```
1m = 60s  
@Indexed(expireAfter = "PT1M") // ISO-8601 duration for 1 minut
@Indexed(expireAfter = "#{T(java.time.Duration).ofMinutes(1)}") // Spring Expression Language (SpEL) for 1 minute
@Value("\${my.custom.property}")
@Indexed(expireAfter = "\${my.custom.property}")


@CompoundIndex: Defines a compound index on multiple fields. This annotation is used to create an index on a combination of fields to improve query performance.

@GeoSpatialIndexed: Marks a field to be indexed with a geospatial index. This annotation is used to create a geospatial index on the specified field for location-based queries.

@Transient: Marks a field to be ignored by the persistence framework. This annotation is used to indicate that the field should not be persisted to the database.

@DBRef: Marks a field as a reference to another document. This annotation is used to create a reference to another document in a different collection.

@Version: Marks a field for optimistic locking. This annotation is used to implement optimistic locking by maintaining a version field in the document.

@PersistenceConstructor: Marks a constructor to be used by the persistence framework. This annotation is used to specify the constructor that should be used to create instances of the class from the database.

@Sharded: Marks a collection to be sharded. This annotation is used to indicate that the collection should be sharded across multiple servers.

@TextIndexed: Marks a field to be indexed with a text index. This annotation is used to create a text index on the specified field for full-text search.

@Collation: Specifies collation settings for a collection or query. This annotation is used to define collation settings, such as locale and case sensitivity, for a collection or query.

@TimeSeries: Marks a collection as a time series collection. This annotation is used to indicate that the collection should be treated as a time series collection.

@Meta: Adds metadata to a query. This annotation is used to add metadata, such as hints and options, to a query.

@ReadPreference: Specifies the read preference for a query. This annotation is used to define the read preference, such as primary or secondary, for a query.

@WriteConcern: Specifies the write concern for a query or collection. This annotation is used to define the write concern, such as acknowledgment level, for a query or collection.

@Hint: Adds a hint to a query. This annotation is used to provide a hint to the query optimizer.

@Language: Specifies the language for a text index. This annotation is used to define the language for a text index.

@CountQuery: Specifies a count query for a repository method. This annotation is used to define a custom count query for a repository method.

@DeleteQuery: Specifies a delete query for a repository method. This annotation is used to define a custom delete query for a repository method.

@ExistsQuery: Specifies an exists query for a repository method. This annotation is used to define a custom exists query for a repository method.

@FindQuery: Specifies a find query for a repository method. This annotation is used to define a custom find query for a repository method.

@UpdateQuery: Specifies an update query for a repository method. This annotation is used to define a custom update query for a repository method.



## save 

```json


```

