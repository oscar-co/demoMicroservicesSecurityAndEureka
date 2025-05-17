# Microservicios con Spring Boot, Eureka, API Gateway, Config Server y Seguridad JWT

Este proyecto demuestra una arquitectura de microservicios con seguridad integrada utilizando JWT (JSON Web Tokens) para proteger endpoints. Se apoya en los siguientes componentes:

- ✅ Spring Cloud Config Server
- ✅ Eureka Service Discovery
- ✅ Spring Cloud Gateway (API Gateway)
- ✅ Múltiples microservicios (Auth, Product, Order)
- ✅ Configuración centralizada en un repositorio local
- ✅ Seguridad con JWT implementada en auth-service y propagada a través del API Gateway

---

## 🔧 Estructura del proyecto

```
microservicesExample/
├── config-repo/                 # Configuraciones centralizadas (.properties)
├── config-server/               # Spring Cloud Config Server
├── eureka-server/               # Eureka Discovery Server
├── api-gateway/                 # API Gateway con rutas y validación de tokens
├── auth-service/                # Servicio de autenticación y emisión de JWT
├── product-service/             # Microservicio de productos (protegido con JWT)
└── order-service/               # Microservicio de pedidos
```

---

## 📆 Configuración centralizada

### `bootstrap.properties` en cada microservicio
```properties
spring.application.name=nombre-del-servicio
spring.cloud.config.uri=http://localhost:8888
spring.config.import=configserver:
```

### `config-repo/{nombre-del-servicio}.properties`
```properties
server.port=puerto
spring.application.name=nombre-del-servicio
eureka.client.service-url.defaultZone=http://localhost:8761/eureka
```

---

## 🌐 Enrutamiento en el API Gateway

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

spring.cloud.gateway.routes[2].id=auth-service
spring.cloud.gateway.routes[2].uri=lb://auth-service
spring.cloud.gateway.routes[2].predicates[0]=Path=/auth/**
```

---

## 🔐 Seguridad con JWT

- El `auth-service` expone un endpoint `/auth/login` para recibir credenciales y emitir tokens JWT.
- Los tokens se generan con `jjwt` y contienen el nombre del usuario.
- El `api-gateway` y los microservicios validan el JWT en las cabeceras `Authorization`.
- Las rutas protegidas requieren el token; `/auth/**` está exento para permitir login.

Ejemplo de login:
```http
POST /auth/login HTTP/1.1
Content-Type: application/json
{
  "username": "user",
  "password": "password"
}
```
Respuesta:
```json
{
  "token": "eyJhbGciOi..."
}
```

Peticiones posteriores deben incluir:
```http
Authorization: Bearer eyJhbGciOi...
```

---

## 🚀 Ejecución del proyecto

1. Iniciar `config-server`
2. Iniciar `eureka-server`
3. Iniciar `auth-service`
4. Iniciar `product-service` y `order-service`
5. Iniciar `api-gateway`
6. Acceder vía gateway:
   - `http://localhost:8080/products`
   - `http://localhost:8080/orders`
   - `http://localhost:8080/auth/login`
   - `http://localhost:8761` (dashboard de Eureka)

---

## 🔍 Verificación rápida

| Componente       | Puerto | URL                        |
|------------------|--------|----------------------------|
| Config Server    | 8888   | http://localhost:8888      |
| Eureka Server    | 8761   | http://localhost:8761      |
| API Gateway      | 8080   | http://localhost:8080      |
| Auth Service     | 8083   | http://localhost:8083      |
| Product Service  | 8081   | http://localhost:8081      |
| Order Service    | 8082   | http://localhost:8082      |

---

Este proyecto sirve como base para sistemas distribuidos seguros con Spring Cloud. Ideal para demostraciones profesionales y entrevistas técnicas.
