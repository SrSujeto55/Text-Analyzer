# Text-Analyzer
Analizador de Archivos de texto

# Requerimientos

Este proyecto se realizó usando Maven, por lo que es necesario que se tenga instalada una versión funcional de Maven para usar correctamente el programa.

# Uso

## Compilar programa

Dirígiete a la carpeta raiz de este repositorio y ejecuta la siguiente instrucción:

    mvn compile

## Instalar programa

Dirígiete a la carpeta raiz de este repositorio y ejecuta la siguiente instrucción:

    mvn install
  
 acto seguido se empezará a instalar el programa.
 
## Ejecutar programa

Para esto es necesario usar la siguiente sintaxis dentro de la carpeta raiz del programa:

    java -jar target/proyecto3 <Archivo(s)> / bandera: [-o <Nombre Directorio> | Sin bandera: ruta default]
    
la bandera -o te permite guardar la salida del programa en una ruta específica, si omites la bandera -o la salida del programa se escribirá en la 
carpeta default del directorio.
  
Si se intenta utilizar el programa con un archivo específico, entonces debes especificar la ruta de éste, además debe seguir la siguiente estructura, además

Si después de poner la bandera -o no se especifica bien un directorio sino mas bien un archivo, este se sobreescribirá
 
