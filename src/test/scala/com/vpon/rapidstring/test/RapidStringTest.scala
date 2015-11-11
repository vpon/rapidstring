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

  "A rapid string" should "interpolate complex html string" in {
    val html = "12345678901234567890"
    val url = "https://192.168.0.1267/tracking?type=0&c=1"
    val script = """document.write("hello world");"""
    val tracking = """<img href="http://target.vpon.com" />"""
    val idx = 10
    rapid"""${html.substring(0, idx)}<img border="0" width="1" height="1" style="display: none;" src="$url"/><script>$script$tracking</script>#IMP_TRACK#${html.substring(idx)}""" should be (
      s"""${html.substring(0, idx)}<img border="0" width="1" height="1" style="display: none;" src="$url"/><script>$script$tracking</script>#IMP_TRACK#${html.substring(idx)}"""
    )
  }

  "A rapid string" should "interpolate complex url string" in {
    val url = "https://192.168.0.1267/tracking?type=0&c=1"
    val ext = """document.write("hello world");"""
    rapid"${url}?t=0&ext=${ext}" should be (
      s"${url}?t=0&ext=${ext}"
      )
  }

  "filled method" should "fill left padding with spefied characters" in {
    val i1 = 19
    val i2 = -120
    val l1 = 1020L
    val l2 = -1330L

    rapid"numbers: $i1,$i2,${i1.filled(5, ' ')},${i2.filled(5, ' ')}; $l1,$l2,${l1.filled(9, ' ')},${l2.filled(9, ' ')}" should be(
      f"numbers: $i1,$i2,$i1% 5d,$i2% 5d; $l1,$l2,$l1% 9d,$l2% 9d")

    rapid"numbers: $i1,$i2,${i1.filled(5, '0')},${i2.filled(5, '0')}; $l1,$l2,${l1.filled(9, '0')},${l2.filled(9, '0')}" should be(
      f"numbers: $i1,$i2,$i1%05d,$i2%05d; $l1,$l2,$l1%09d,$l2%09d")
  }

  "rapid" should "escape string" in {
    val url = "https://192.168.0.1267/tracking?type=0&c=1"
    rapid"${url}\nt=10\next=23" should be (
      s"${url}\nt=10\next=23")
  }
}
