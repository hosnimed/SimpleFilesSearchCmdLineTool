## Java/Scala Coding Exercise

### How to build ?

##### _*Requirements*_ :
(All the above should be defined in ```PATH```)
- Java 8
- Scala 2.12.8
- Sbt 1.2.8

To BUILD the project just go the root project directory and execute from a command line :
```sh 
[PROJECT_ROOT_DIR]$sbt clean package
```

#### How to test ?
To execute the TESTS just go the root project directory and execute from a command line :

```sh
[PROJECT_ROOT_DIR]$sbt test
```

#### How to run ?
To RUN the project just go the root project directory and execute from a command line :
```sh 
[PROJECT_ROOT_DIR]$scala -classpath target/scala-2.12/simplesearcher_2.12-0.1.jar fr.adevinta.example.Searcher myDir
```

### Assumptions
I assume ignoring `duplicated` words in the search algorithm, for example to search for **to be or not to be** is the same as **to be or not**
Also considering two files `f1.txt` containing: **to be or not to be** and `f2.txt` containing : **to be** the result of the precedent search (**to be or not to be**) should be :
- f1.txt : 100 % 
- f2.txt : 50 %
