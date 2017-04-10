![RelamIO Logo](https://realm.io/assets/svg/general_logo.svg)


Este es un ejemplo basico de la implementacion de RealmIO presentado en la seguna Android Meeting en El Salvador por [Moises Portillo](http://moisesportillo.com/).


## RealmIO

Realm es un gestor de base de datos que puede servir como un reemplazo de SQLite en el caso de Android o Core Data para iOS.
Su facil implementacion y uso la hacen una libreria bastante util.

####Instalacion

**Paso 1**: Agregue la dependencia de la ruta de acceso de clase al archivo build.gradle de nivel de proyecto.

	buildscript {
	    repositories {
	        jcenter()
	    }
	    dependencies {
	        classpath "io.realm:realm-gradle-plugin:3.1.1"
	    }
	}
	
**Paso 2**: Apply the realm-android plugin to the top of the application level build.gradle file.

	apply plugin: 'realm-android'


Para poder apoyarse mejor del uso de esta fantiastica libreria no dude en revisar la [documentacion completa](https://realm.io/docs/java/latest/) ya que es la mejor referencia para su uso.