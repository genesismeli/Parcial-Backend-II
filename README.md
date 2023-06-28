# Parcial-Backend-II
# Resumen
Este proyecto consta de varios componentes principales: dos microservicios (ms-bills y
ms-users), un Gateway (ms-gateway) y un Discovery Service (ms-discovery). Cada uno de
estos componentes tiene una función específica y se comunica con los demás para
proporcionar una aplicación completa y segura. El proyecto utiliza Keycloak como su
sistema de gestión de identidad y acceso. Keycloak es un producto de código abierto que
permite la autenticación y autorización de usuarios, proporcionando características como el
inicio de sesión único (Single Sign-On, SSO), la gestión de identidades y la gestión de
permisos.
# Componentes
# Microservicio "Bills"
El microservicio "Bills" es responsable de la gestión de las facturas de los clientes en el
sistema de comercio electrónico. Este microservicio proporciona una API REST que expone
las funcionalidades para interactuar con las facturas.
# Endpoints:
GET /bills/all: Este endpoint devuelve una lista de todas las facturas en el sistema. Este
endpoint requiere que el usuario esté autenticado y tenga el rol "USER".
.Configuración: La configuración para este microservicio se realiza a través de un archivo de
propiedades. Las propiedades incluyen la configuración del servidor, la seguridad de Spring,
la base de datos H2, JPA y la configuración del cliente de Eureka y Keycloak.
.GET/bills/findBy : Este endpoint permite qué un usuario autenticado pueda buscar facturas
por ID de usuario.
.Configuración: La configuración para este microservicio se realiza a través de un archivo de
propiedades. Las propiedades incluyen la configuración del servidor, la seguridad de Spring,
la base de datos H2, JPA y la configuración del cliente de Eureka y Keycloak.
.Configuración del servidor: El microservicio se ejecuta en el puerto 8081 y tiene un contexto
de servlet en /api/v1/.
.Configuración de Seguridad Keycloak:
Este proyecto utiliza Keycloak para la autenticación y autorización de usuarios. Los tokens
emitidos por Keycloak son tokens JWT, y el proyecto utiliza una configuración personalizada
para decodificar estos tokens y convertirlos en objetos de autenticación utilizables en el
sistema.
. Clase KeyCloakJwtAuthenticationConverter
Esta clase implementa la interfaz Converter y se utiliza para convertir un Jwt en un
AbstractAuthenticationToken. Esto permite al sistema de seguridad de Spring interpretar
correctamente los tokens JWT emitidos por Keycloak. Esta clase realiza varias funciones:
Extrae los roles y los grupos del token JWT que se encuentran en varios lugares dentro del
token, como el acceso a recursos (resource_access), el acceso al reino (realm_access) y el
auditor (aud).
Crea instancias de GrantedAuthority a partir de los roles extraídos. Los roles se convierten
en objetos GrantedAuthority que se utilizan en el sistema de seguridad de Spring para
representar las autoridades concedidas a un usuario autenticado.
Combina las autoridades concedidas por defecto (si las hay) con las autoridades extraídas
del token JWT.
.Clase OAuth2ResourceServerSecurityConfiguration
Esta clase es una clase de configuración de Spring que configura la seguridad de la
aplicación. Establece la política de creación de sesiones en
SessionCreationPolicy.STATELESS, lo que significa que el servidor no mantendrá ninguna
información de sesión entre las solicitudes. Además, configura el servidor con la
configuración de seguridad de Keycloak.
.Configuración de seguridad de Keycloak
La configuración de seguridad de Keycloak incluye la configuración del servidor, la base de
datos, JPA, Eureka y el Gateway.
Configuración de la base de datos:
El microservicio utiliza una base de datos H2 en memoria para el almacenamiento de datos.
La base de datos se configura para permanecer abierta hasta que la JVM se cierre.
Configuración de JPA:
El microservicio utiliza JPA para la persistencia de datos con Hibernate como proveedor. Se
configura para mostrar las consultas SQL y actualizar automáticamente el esquema de la
base de datos para reflejar las entidades JPA.
Configuración de Eureka:
El microservicio se registra con un servidor Eureka en http://localhost:8761/eureka.
 



# Configuración relevante para ms-discovery:
spring.application.name: Nombre de la aplicación, en este caso, es ms-discovery.
server.port: Puerto en el que se ejecuta el servidor, en este caso, es el puerto 8761.
# Configuración de Eureka:
eureka.instance.hostname: Define el nombre del host en el que se ejecuta el servidor
Eureka, que es localhost en este caso.
eureka.instance.prefer-ip-address: Esta propiedad, cuando se establece en true, le dice al
servidor Eureka que utilice la dirección IP del servicio en lugar del nombre del host cuando
registra el servicio.
# Configuración del cliente de Eureka:
eureka.client.fetch-registry: Cuando se establece en false, indica que este servidor no
necesita obtener el registro de Eureka de otros servidores Eureka.
eureka.client.register-with-eureka: Cuando se establece en false, indica que este servidor no
necesita registrarse con otros servidores Eureka. Esto tiene sentido aquí ya que este
servidor es el servidor de registro. Screenshot 2023-06-09 154218
MS-Gateway
Este proyecto representa la implementación de un Gateway para nuestra arquitectura de
microservicios utilizando Spring Cloud Gateway. El propósito principal de este servicio es
actuar como un punto de entrada único a nuestro sistema, manejando y enrutando las
solicitudes a los servicios correspondientes.
Configuración del servicio: La configuración del servicio se proporciona a través del archivo
application.yaml.
Configuración de Eureka: El servicio se registra con el servidor Eureka para participar en el
descubrimiento de servicios. También recupera la información del registro de Eureka para
conocer otros servicios disponibles.
# Configuración de Gateway:
El Gateway está configurado para enrutar las solicitudes a los
servicios correspondientes. Por ejemplo, todas las solicitudes que coinciden con la ruta
/api/v1/bills son enrutadas al servicio ms-bill. También se configura un filtro de retransmisión
de token para pasar los tokens de autenticación a los servicios enrutados.
Configuración de seguridad: La seguridad está habilitada para este servicio utilizando
OAuth2. El cliente OAuth2 se registra con la información necesaria para interactuar con el
servidor de autenticación, incluyendo el tipo de concesión de autorización, el proveedor, el
alcance, el ID del cliente, la URI de redirección y el secreto del cliente.
La configuración de seguridad también incluye la URI del emisor, que apunta al servidor
Keycloak para autenticar y autorizar a los usuarios.
# Clase SecurityConfig
Esta clase es una clase de configuración en Spring que se utiliza para establecer la
configuración de seguridad para el Gateway. La clase SecurityConfig se anota con
@Configuration, lo que indica que contiene métodos @Bean que pueden ser gestionados
por el contenedor de Spring. El método springSecurityFilterChain es un método @Bean que
configura la cadena de filtros de seguridad para el servidor. Esta cadena de filtros se aplica
a todas las peticiones que llegan al servidor. En este método, se configura el
ServerHttpSecurity para que autentique todas las peticiones
(anyExchange().authenticated()) y se habilita el inicio de sesión OAuth2 (oauth2Login()).
Finalmente, este método devuelve la cadena de filtros de seguridad construida (http.build()),
que será gestionada por Spring y aplicada a las peticiones entrantes. Por lo tanto, la clase
SecurityConfig y el método springSecurityFilterChain desempeñan un papel crucial en la
aplicación de la configuración de seguridad a las peticiones que llegan al Gateway.
# Configuración de Keycloak
Este proyecto utiliza Keycloak para la autenticación y autorización de usuarios. Para
configurar Keycloak en tu entorno local, sigue los pasos que se indican a continuación.
Paso 1: Crear un Client en Keycloak
Inicia sesión en la interfaz de administración de Keycloak.
Navega a la sección "Clients".
Haz clic en "Create".
Proporciona un nombre para tu client.
En el campo "Access Type", selecciona "confidential". Esta configuración es necesaria
porque el client es una aplicación del lado del servidor que puede mantener un secreto.
Haz clic en "Save" para guardar la configuración del client.
Paso 2: Crear un Rol en Keycloak
Navega a la sección "Roles".
Haz clic en "Add Role".
En el campo "Name", introduce "USER".
Haz clic en "Save" para guardar la configuración del rol.
Paso 3: Crear un Usuario en Keycloak
Navega a la sección "Users".
Haz clic en "Add user".
Completa los campos necesarios para crear un nuevo usuario.
Navega a la pestaña "Role Mappings".
Añade el rol "USER" al usuario.
Haz clic en "Save" para guardar la configuración del usuario. Una vez que hayas
completado estos pasos, tendrás un client, un rol y un usuario configurados en Keycloak.
Paso 3: Crear un grupo en Keycloak
Navega a la sección “Groups”
Haz clic en “Create group”
Unset
Completa con el nombre qué quieras para el grupo
Presiona “Create”
Presiona en la lista de grupos el nombre del grupo qué acabas de crear
Navega a la sección “Members”
Presiona “Add member”
Selecciona el/ los usuarios qué desees agregar
Presiona “save”
Ahora estás listo para integrar Keycloak con tu aplicación.
Despliegue de la Aplicación
Para desplegar la aplicación en tu entorno local, necesitas levantar varios servicios en un
orden específico. Sigue los pasos a continuación para levantar la aplicación.
Paso 1: Levantar Keycloak en Docker
Primero, necesitas levantar Keycloak en Docker. Asegúrate de que Docker esté instalado y
funcionando en tu máquina.
docker run -p 8080:8080 -e KEYCLOAK_ADMIN=admin -e
KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:21.1.1 start-dev
Paso 2: Levantar el Servicio de Discovery
El siguiente paso es levantar el servicio de Discovery.
Paso 3: Levantar el Gateway
Una vez que el servicio de Discovery esté funcionando, puedes levantar el Gateway.
Paso 4: Levantar los Microservicios
Finalmente, puedes levantar los microservicios
