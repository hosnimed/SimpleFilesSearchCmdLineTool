## Java/Scala Coding Exercise

####***Author*** : _Mohamed EL HOSNI_
######***Contact*** :[medhosni@gmail.com](emailto:medhosni@gmail.com)

##How to build ?

#### Requirements :
(All the above should be defined in ```PATH```)
- Java 8
- Scala 2.12.8
- Sbt 1.2.8

To BUILD the project just go the the root project directory and execute from a command line :
```sh 
[PROJECT_ROOT_DIR]$sbt clean package
```

##How to test ?
To execute the TESTS just go the the root project directory and execute from a command line :

```sh
[PROJECT_ROOT_DIR]$sbt test
```

##How to run ?
To RUN the project just go the the root project directory and execute from a command line :
```sh 
[PROJECT_ROOT_DIR]$scala -classpath target/scala-2.12/simplesearcher_2.12-0.1.jar fr.adevinta.example.Searcher myDir
```