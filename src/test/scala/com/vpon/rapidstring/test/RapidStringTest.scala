/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vpon.rapidstring.test

import org.scalatest._
import com.vpon.rapidstring.RapidString._

class ExampleSpec extends FlatSpec with Matchers {

  "A rapid string" should "concate sequence of strings" in {
    val c = Seq("qwer", "asdf", "zxcv", "tyuio")

    rapid"qwer,asdf,zxcv,tyuio" should be(c.mkString(","))
  }

  "A rapid string" should "replace integer and sequence of strings" in {
    val a = 1
    val c = Seq(Seq("sss", "dd"), Seq("asdf"))

    rapid"a $a aa ${c.mkString(",")}" should be(s"a $a aa ${c.mkString(",")}")
  }

  "A rapid string" should "replace inner string interpolations" in {
    val a = 1

    rapid"baz $a foo ${rapid"inner$a"} bar" should be(f"baz $a foo inner$a bar")
  }

  "A rapid string" should "replace inner statements" in {
    val a = 1

    rapid"baz $a foo ${
      (for (i <- 0 until 5) yield {
        s"i=$i"
      }).mkString
    } bar" should be (
    s"baz $a foo ${
      (for (i <- 0 until 5) yield {
        s"i=$i"
      }).mkString
    } bar")
  }

  "A rapid string" should "replace inner statements with inner interpolations" in {
    def a = 1

    rapid"baz $a foo ${
      (for (i <- 0 until 5) yield {
        rapid"i=$i"
      }).mkString
    } bar" should be(
    s"baz $a foo ${
      (for (i <- 0 until 5) yield {
        s"i=$i"
      }).mkString
    } bar")
  }

  "A rapid string" should "interpolate multilines string" in {
    val f = "forgotten"
    val m = "machinery"

    rapid"""My mouth is snow slowly caking that stiff pigeon.
      |My mouth, the intricately moist $m of a plant.
      |I have $f if I ever had a mouth. . . .
    """.stripMargin should be (
    s"""My mouth is snow slowly caking that stiff pigeon.
      |My mouth, the intricately moist $m of a plant.
      |I have $f if I ever had a mouth. . . .
    """.stripMargin)
  }
}
