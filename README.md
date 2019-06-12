#### To notice (room for improvement)

##### Almost all issues are as consequence of multi-module project setup and Kotlin - which both very desirable!

1. Tests do not run. As simple as it sounds - with kotlin setup tests are not detected at all. at least it was so for `0.15.0` for sure and for `0.16.0` (almost) for sure, even though:
    ```kotlin
    quarkus {
        setSourceDir("$projectDir/src/main/kotlin")
        resourcesDir() += file("$projectDir/src/main/resources")
        setOutputDirectory("$buildDir/classes/kotlin/main")
    }
    ```
   
    you get
  
    ```log
    org.junit.jupiter.api.extension.TestInstantiationException
    : TestInstanceFactory [io.quarkus.test.junit.QuarkusTestExtension]
    failed to instantiate test class [com.example.marvel.web.rest.jakarta.EmployeeServiceOperationsNamespaceTest]
    : The test class
    class com.example.marvel.web.rest.jakarta.EmployeeServiceOperationsNamespaceTest
    is not located in any of the directories [classes/java/test, /test-classes, bin/test]
    ```
    There is a Test [com.example.marvel.web.rest.jakarta.EmployeeServiceOperationsNamespaceTest](runtime-jakarta/src/test/kotlin/com/example/marvel/web/rest/jakarta/EmployeeServiceOperationsNamespaceTest.kt)
    You can uncomment and try. 
2. Multi-module project with with Quarkus is annoying. 
Recompilation never works here. Always an error. But also, simple restart didn't work either due to https://github.com/quarkusio/quarkus/issues/2413 (it didn't work in 100%!! of cases, and I then simplified Gradle build logic - it begin to sometime restart fine). By restarting fine I mean many times slower than some Spring-Boot application because Quarkus obviously does a lot of things at build time - which is intended for to allow hot-reload, but when the later do not work - it slows things down drastically. The more complex Gradle setup the more chances that simple restart (say by telling IDEA to restart the app) won't work.
*A single reproducer for the https://github.com/quarkusio/quarkus/issues/2413 is `./gradlew runtime-jakarta:quarkusDev` -> change sources -> `curl` to let Quarkus start to recompile -> you'll get "recompilation failed" -> now if you restart the app from IDEA you got https://github.com/quarkusio/quarkus/issues/2413*.
3. Changing Javadoc/Kdoc cause Quarkus to recompile on request as if that were method bodies, etc. at least with Kotlin (Could be this one is unresolvable, don't know - it is not big issue if recompilation would work great).
4. See all `FIXME:` and `TODO:` the most annoying one is https://github.com/quarkusio/quarkus/issues/2196.
Even though `beans.xml` in place and even writing jandex index with gradle plugin - nothing works - still getting "This method is normally overridden". (resulting message like in https://github.com/quarkusio/quarkus/issues/1717)
5. For some reason both `@kotlin.jvm.Transient` and `@javax.persistence.Transient` results in absence of the property in DB table for RDMS. Not sure it this intended and on which side (e.g. `persistence` spec, kotlin or quarkus...)
6. There was an exception saying `com.example.marvel.domain.model.jpa.record.RecordId` "is not found/indexed" or "should be declared as entity" - so we annotated it with `@javax.persistence.Embeddable` - which is not compliant with Hibernate (at least for `@javax.persistence.IdClass` which it is, not sure about compliance with JPA) and it worked. Would be great to avoid it.
7. Kotlin. Kotlin interop is very non-complete. Having `PanacheRepository*` as interfaces with default methods is great solution, `PanacheEntity` is understandably `abstract class` as it have a field, but what is the reason to leave `PanacheEntityBase` an abstract class. This limits developer experience as you can't have other logic accumulated elsewhere in other class as there could be only one `class`
you extend from. Should `PanacheEntityBase` become an interface?
8. Kotlin. If you make `@javax.persistence.Id` property a `val` Hibernate throws it can not set id for final field. In Kotlin this is common and for `data classes` with Spring-Boot( Data) you normally make it `val` even though in Java you probably would have a setter. Enhance at build time instead limiting developer experience with Kotlin?
9. JWT. Making Endpoint `RequestScopped` for to allow MP `JwtToken` injection seems limiting. There always was request scoped things you can inject in JAX-RS runtime (with `@Context` annotation) and it worked for years. Maybe for MP `JwtToken` and companions related to same concerns there could be possibility to `@Inject` into `@ApplicationScopped` still?
10. Kotlin. Extending `PanacheEntity*` gives much less as you can't access `static` method. All static methods in `Panache(Repository|Entity)*` needs more design with Kotlin in mind. Default methods seems to have less problems here. The only one comes in mind is that you can't override them unless annotate method with `@JvmDefault` but this is acceptable trade-off. Statics much less consumable from Kotlin. The best would be to write the extendable part of framework (e.g. `quarkus-hibernate-orm-panache`) in kotlin with full compatibility with Java. OkHttp recently did it. They rewrote the wrole thing in Kotlin while maintaining full interop with Java. I personally bumped the version to `4.0.0-rc-1` and it worked with no problem. See example in https://github.com/square/okhttp/blob/master/okhttp/src/main/java/okhttp3/RequestBody.kt. For 6-8 target classes in `io.quarkus.hibernate.orm.panache.*` package seems like a viable solution?
11. Kotlin. Recently (in `999-SNAPSHOT` I think) added `context-propagation` does not work for coroutines. This caused to throw away all `coroutines` supporting libs (`org.litote.kmongo:kmongo-coroutine`, `io.vertx:vertx-lang-kotlin-coroutines`, and especially `arrow-kt` as all those functional compositions only makes sense for async and parallel computations which could be expressed more concisely in imperative style (`arrow-kt` bindings), but they all rely on coroutines in its core. Hope this will change with `panache-rx-coroutines` even though list of supported JPA annotations/features is not impressive / big at all.
12. When add `io.quarkus:quarkus-reactive-pg-client` you got `io.reactiverse:reactive-pg-client` transitively. `io.reactiverse:reactive-pg-client` have kotlin package with extensions, which is misleading and Quarkus user should only use `smallrye-*-client` APIs for operations, I suppose.
13. While Jsonb writes our specifically structured `data class`es (see [com.example.marvel.domain.model.api.record.RecordDto](./business/src/main/kotlin/com/example/marvel/domain/model/api/recordcollection/algebra.kt)) just fine at runtime only inspecting `public` properties (which IS desired), produced OpenAPI spec file apparently inspects model through reflection with Jackson probably. Either way, the resulting JSON model examples reflects `delegate` property. Should there be whether way to provide configuration to underling Reader or something like that? Staring from `2.0.7` `swagger-core` provides both Gradle and Maven plugins, which both works brilliantly and has all handles I mentioned (for Jackson, for Readers, Filters, etc. AND for files to merge and affect the resuling `openapi.yaml`). So, far it is the best option on the market. You can see [api.yaml](./runtime-jakarta/src/main/webapp/api.yaml) with security declaration and meta-info and produced [openapi.yaml](./runtime-jakarta/src/main/webapp/openapi.yaml) file. You can do whatever to your endpoints and then just execute 
    ```log
    ./gradlew resolve
    ```
      in this project and rich openapi file will be written at build time, with no runtime-dependencies at all. Micronaut supports same kind of  merging additional info as [io.swagger.v3.plugins.gradle.tasks.ResolveTask.openApiFile](https://github.com/swagger-api/swagger-core/blob/027bf588042a6d60caaf85a5d72adca008b4da31/modules/swagger-gradle-plugin/src/main/java/io/swagger/v3/plugins/gradle/tasks/ResolveTask.java#L43)
 
14. Micronaut and Quarkus both doesn't go well with default time arguments and inheritance by delegation
we got 
    ```log
    Caused by: javax.enterprise.inject.AmbiguousResolutionException: Ambiguous dependencies for type com.example.marvel.web.grpc.EmployeeOperationsServiceNamespace and qualifiers [@Default]
        - java member: com.example.marvel.web.rest.jakarta.EmployeeOrchestrationResource#<init>()
        - declared on CLASS bean [types=[com.example.marvel.web.rest.jakarta.EmployeeOrchestrationResource, com.example.marvel.web.grpc.EmployeeOperationsServiceNamespace, kotlin.coroutines.CoroutineContext, java.lang.Object, com.example.marvel.web.rest.EmployeeResourceAdapter], qualifiers=[@Default, @Any], target=com.example.marvel.web.rest.jakarta.EmployeeOrchestrationResource]
        - available beans:
            - CLASS bean [types=[com.example.marvel.web.rest.jakarta.EmployeeOrchestrationResource, com.example.marvel.web.grpc.EmployeeOperationsServiceNamespace, kotlin.coroutines.CoroutineContext, java.lang.Object, com.example.marvel.web.rest.EmployeeResourceAdapter], qualifiers=[@Default, @Any], target=com.example.marvel.web.rest.jakarta.EmployeeOrchestrationResource]
            - CLASS bean [types=[com.example.marvel.web.rest.jakarta.EmployeeBlockingServiceNamespaceImpl, com.example.marvel.web.grpc.EmployeeOperationsServiceNamespace, io.quarkus.hibernate.orm.panache.PanacheRepositoryBase<com.example.marvel.domain.model.jpa.employee.EmployeeEntity, java.lang.Long>, java.lang.Object], qualifiers=[@Default, @Any], target=com.example.marvel.web.rest.jakarta.EmployeeBlockingServiceNamespaceImpl]
        at io.quarkus.arc.processor.Beans.resolveInjectionPoint(Beans.java:393)
        at io.quarkus.arc.processor.BeanInfo.init(BeanInfo.java:366)
        at io.quarkus.arc.processor.BeanDeployment.init(BeanDeployment.java:286)
        ... 14 more
    
    ```
15. !!! Method `EmployeeBlockingServiceNamespaceImpl::listForPeriodDemo` throws `MethodNotFound` upon call `EntityManager::createQuery().resultStream`

#### Notes:

 - If kapt fails with weird conditions: 

    ./gradlew clean kaptKotlin --rerun-tasks


 - All those `operator` overloading in `sealed class`es doesn't serve much value at the moment and more a design experiment.
