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

package com.vpon.rapidstring


import language.experimental.macros
import scala.reflect.macros.whitebox.Context


object RapidString {

  object RapidStringContext {

    private def newLocalRapidClass(c: Context, escapeFunction: String => String)(arguments: Seq[c.Expr[Any]]):c.Expr[String] = {
      import c.universe._
      val Apply(Select(Apply(_, List(Apply(_, partTrees))), _), _) =
        c.macroApplication
      assert(partTrees.length == arguments.length + 1)
      if (arguments.isEmpty) {
        val Literal(Constant(lastPart: String)) = partTrees.last
        c.Expr(q"""${escapeFunction(lastPart)}""")

      } else {
        val initial = q"""(new java.lang.StringBuilder())"""
        val appendTrees = 0.until(arguments.length).foldLeft[Tree](initial) { (prefixTree, i) =>
          val arg = arguments(i)
          val Literal(Constant(part: String)) = partTrees(i)
          part match {
            case escaped if escaped.nonEmpty =>
              q"""(..$prefixTree).append(${escapeFunction(part)}).append($arg)"""
            case _ =>
              q"""(..$prefixTree).append($arg)"""
          }
        }
        val Literal(Constant(lastPart: String)) = partTrees.last
        lastPart match {
          case p if p.nonEmpty =>
            c.Expr(q"""(..$appendTrees).append(${escapeFunction(p)}).toString()""")
          case _ =>
            c.Expr(q"""(..$appendTrees).toString()""")
        }
      }
    }

    final def rapid_impl(c: Context)(arguments: c.Expr[Any]*): c.Expr[String] =
      newLocalRapidClass(c, StringContext.treatEscapes)(arguments)

  }


  implicit final class RapidStringContext(val stringContext: StringContext) extends AnyVal {
    import RapidStringContext._

    final def rapid(arguments: Any*) = macro rapid_impl
  }

}
