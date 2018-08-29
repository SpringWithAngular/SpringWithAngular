# Spring Boot

https://chat.whatsapp.com/8xRhwFJaPlL58xdwK9l20W

Spring Boot e Gitlab
---

* Baixar o Projeto via Spring Initializr
* Configurar o Intellij IDEA
* Run Server
* Clean install Maven
* Rodar o projeto
* IndexController
* @SpringBootApplication
* Criar um projeto no gitlab
* Subir o projeto na master
* Criar a branch develop
* Usar a nomenclatura do gitflow
* Gerar a tag do dia

Spring Data
---

* Especificação JPA
* Entidades
* Interfaces Spring Data Repository
* Spring Data na prática

MVC e Criação de APIs REST
---

* MVC
* Controller
* Criação de um endpoint
* Integração do endpoint com o Repository
* Postman

Spring Security e autenticação OAuth2
---

* Spring Security
* Autenticação OAuth2
* AccessToken
* Proteger os endpoints
* Configurações produtivas no Postman
* Link do postman https://www.getpostman.com/collections/b67949213dfce62c938d

Criptografia de senhas e ignorar campos
---

* The modernized password storage 
* createDelegatingPasswordEncoder
* @JsonIgnore
* Bcrypt


	@Bean
	public CorsFilter corsFilter() {

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("GET");
		config.addAllowedMethod("PUT");
		config.addAllowedMethod("POST");
		config.addAllowedMethod("OPTIONS");
		config.addAllowedMethod("DELETE");
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}


