# RapidString

**RapidString** is a faster Scala string interpolation replacement.  It is inspired by Fastering (https://github.com/Atry/fastring).  Some code is copied from Fastring.


## How to use it

`RapidString` uses [string interpolation](http://docs.scala-lang.org/sips/pending/string-interpolation.html) syntax.

    import com.vpon.rapidstring.RapidString._
    def poem() {
      val f = "forgotten"
      val m = "machinery"
      rapid"My mouth is snow slowly caking that stiff pigeon. My mouth, the intricately moist $m of a plant. I have $f if I ever had a mouth"
    }

## It's extremely fast

[TODO] benchmark


## Installation

Put these lines in your `build.sbt` if you use [Sbt](http://www.scala-sbt.org/):

    libraryDependencies += "com.vpon" %% "rapidstring" % "0.1.2"

See http://mvnrepository.com/artifact/com.vpon/rapidstring_2.11/0.1.2 if you use [Maven](http://maven.apache.org/)
or other build systems.

Note that `RapidString` requires [Scala](http://www.scala-lang.org/) `2.11`.
