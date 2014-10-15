# Bug demonstration

This is a demonstration of [this issue in the macwire library](https://github.com/adamw/macwire/issues/21) related to  [this issue in the scala compiler](https://issues.scala-lang.org/browse/SI-8904)

macwire 0.7.1 and scala 2.11.2 (same issue occurs with scala 2.10.4)

## compilation works fine as can be seen here:

```
brokenMacwireParsing  sbt
[info] Loading global plugins from /Users/joe/.sbt/0.13/plugins
[info] Loading project definition from /Users/joe/Documents/git/brokenMacwireParsing/project
[info] Set current project to brokenMacwireParsing (in build file:/Users/joe/Documents/git/brokenMacwireParsing/)
> clean
[success] Total time: 0 s, completed Oct 10, 2014 5:04:00 PM
> compile
[info] Updating {file:/Users/joe/Documents/git/brokenMacwireParsing/}brokenmacwireparsing...
[info] Resolving jline#jline;2.12 ...
[info] Done updating.
[info] Compiling 1 Scala source to /Users/joe/Documents/git/brokenMacwireParsing/target/scala-2.11/classes...
[debug] Trying to find parameters to create new instance of: [com.example.Hello]
   [debug] Trying to find value [service] of type: [com.example.Service]
      [debug] Looking in the enclosing method
      [debug] Looking in the enclosing class/trait
      [debug] Checking def: [$init$]
      [debug] Checking val: [service]
         [debug] Found a match!
      [debug] Checking val: [hello]
      [debug] Found single value: [service] of type [com.example.Service]
   [debug] Generated code: new Hello(service)
[success] Total time: 5 s, completed Oct 10, 2014 5:04:07 PM
```

## but the sbt publish command complains

upon `sbt publish`, macwire is unable to recognise the `lazy val service: Service = MyService` exists.

```
publish
[info] Packaging /Users/joe/Documents/git/brokenMacwireParsing/target/scala-2.11/brokenmacwireparsing_2.11-1.0-sources.jar ...
[info] Done packaging.
[info] Packaging /Users/joe/Documents/git/brokenMacwireParsing/target/scala-2.11/brokenmacwireparsing_2.11-1.0.jar ...
[info] Main Scala API documentation to /Users/joe/Documents/git/brokenMacwireParsing/target/scala-2.11/api...
[info] Wrote /Users/joe/Documents/git/brokenMacwireParsing/target/scala-2.11/brokenmacwireparsing_2.11-1.0.pom
[info] Done packaging.
[info] :: delivering :: brokenmacwireparsing#brokenmacwireparsing_2.11;1.0 :: 1.0 :: release :: Fri Oct 10 17:04:11 CEST 2014
[info]  delivering ivy file to /Users/joe/Documents/git/brokenMacwireParsing/target/scala-2.11/ivy-1.0.xml
[debug] Trying to find parameters to create new instance of: [com.example.Hello]
   [debug] Trying to find value [service] of type: [com.example.Service]
      [debug] Looking in the enclosing method
      [debug] Looking in the enclosing class/trait
      [debug] Checking def: [$init$]
      [debug] Checking val: [hello]
      [debug] Looking in parents
      [debug] Checking parent: [AnyRef]
[error] /Users/joe/Documents/git/brokenMacwireParsing/src/main/scala/com/example/Hello.scala:18: Cannot find a value of type: [com.example.Service]
[error]   lazy val hello: Hello = wire[Hello]
[error]                               ^
   [debug] Generated code: new Hello(null)
[info] No documentation generated with unsuccessful compiler run
[error] one error found

```

The offending code is this:

```
  /**
  * some comment what this service does.
  */
  lazy val service: Service = MyService
```

the same, without the `/** block comment */`, or a `// line comment` behaves normally.

```
  //some comment what this service does.
  lazy val service: Service = MyService
```

This seems to be a really weird bug? What is the difference between compilation and compilation for publishing?
