## Java/Scala Coding Exercise

##### Author: _Mohamed EL HOSNI_

###### For any suggestions please email me at : [medhosni@gmail.com](emailto:medhosni@gmail.com) or open a Gitlab issue on [Gitlab Repo](https://gitlab.com/mohamed.elhosni/simple-files-search-cmd-line-tool)

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


### Time consideration
Without considering the time to write the **README** file, I think I've spent about 4 hours, because I started by writing the whole app in the main method and focus only on the main functionalities (what's I actually considering it not the best strategy to go fast -_- ), after succeeded running a full functional command line, I started a steps of refactoring code in some independent methods to facilitate unit tests.
Finally, I focus on the last part of treating `edge` use cases such: How to deal if some or all files doesn’t contain any of the searched words, as well as deeply thinking for `performance` and memory `optimization`: 
- What’s considering more efficient using lazy operators (stream, views) or eagers ones? 
- How to deal with loading a huge number of files in memory (Scala's Source, Java's NIO)?
- Is using parallelization in our case will really speed up the computation? 
- Why doesn’t benefit from Scala `future` for parallelization and delegate the computation to another `execution context`? 
 
 So, I try within the time limit to start dealing with the most _easy_ that has the _most improvement_.
