# Microservicios con Spring Boot, Eureka, API Gateway y Config Server

Este proyecto demuestra una arquitectura mínima de microservicios con:

- ✅ Spring Cloud Config Server
- ✅ Eureka Service Discovery
- ✅ Spring Cloud Gateway (API Gateway)
- ✅ Múltiples microservicios (Product, Order)
- ✅ Configuración centralizada en un repositorio local

---

## 🔧 Estructura del proyecto

```
microservicesExample/
├── config-repo/                 # Configuraciones centralizadas (.properties)
├── config-server/               # Spring Cloud Config Server
├── eureka-server/               # Eureka Discovery Server
├── api-gateway/                 # API Gateway
├── product-service/             # Microservicio de productos
└── order-service/               # Microservicio de pedidos
```

---

## 📦 Dependencias clave por módulo

### ✅ API Gateway (`pom.xml`)
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-gateway</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-config</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-bootstrap</artifactId>
</dependency>
```

**❗ Importante:** _No incluir `spring-boot-starter-web` en el gateway._

---

## 🔧 Configuración por microservicio

### ✅ `bootstrap.properties` en cada microservicio
```properties
spring.application.name=nombre-del-servicio
spring.cloud.config.uri=http://localhost:8888
spring.config.import=configserver:
```

### ✅ `config-repo/{nombre-del-servicio}.properties`
```properties
server.port=puerto
spring.application.name=nombre-del-servicio
eureka.client.service-url.defaultZone=http://localhost:8761/eureka
```

---

## 🌐 Enrutamiento en el API Gateway (config-repo/api-gateway.properties)

```properties
server.port=8080
spring.application.name=api-gateway
eureka.client.service-url.defaultZone=http://localhost:8761/eureka

spring.cloud.gateway.discovery.locator.enabled=false

spring.cloud.gateway.routes[0].id=product-service
spring.cloud.gateway.routes[0].uri=lb://product-service
spring.cloud.gateway.routes[0].predicates[0]=Path=/products/**

spring.cloud.gateway.routes[1].id=order-service
spring.cloud.gateway.routes[1].uri=lb://order-service
spring.cloud.gateway.routes[1].predicates[0]=Path=/orders/**
```

---

## 🚀 Cómo ejecutar

1. Iniciar el `config-server`
2. Iniciar el `eureka-server`
3. Iniciar `product-service` y `order-service`
4. Iniciar el `api-gateway`
5. Visitar:
   - `http://localhost:8080/products`
   - `http://localhost:8080/orders`
   - `http://localhost:8761` (Eureka dashboard)

---

## ✅ Verificación rápida

| Componente       | Puerto | URL                        |
|------------------|--------|----------------------------|
| Config Server    | 8888   | http://localhost:8888      |
| Eureka Server    | 8761   | http://localhost:8761      |
| API Gateway      | 8080   | http://localhost:8080      |
| Product Service  | 8081   | http://localhost:8081      |
| Order Service    | 8082   | http://localhost:8082      |

---

## 🧼 Limpieza de errores comunes

- 🔥 **No usar spring-cloud-starter-gateway-mvc**
- ❌ No mezclar Web y WebFlux en Gateway
- ❗ Siempre reiniciar servicios tras editar config-repo