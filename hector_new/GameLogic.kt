// GameLogic.kt
package com.example.hector_new

import kotlin.random.Random

fun generateValidNumber(): Pair<String, String> {
    val operators = listOf("+", "-", "*", "/")
    while (true) {
        val numStr = (100000..999999).random().toString()
        val validExpressions = mutableListOf<String>()

        val combinations = mutableListOf<List<String>>()
        fun generateCombinations(index: Int, current: MutableList<String>) {
            if (index == numStr.length - 1) {
                combinations.add(current.toList())
                return
            }
            for (op in operators) {
                current.add(op)
                generateCombinations(index + 1, current)
                current.removeAt(current.size - 1)
            }
        }
        generateCombinations(0, mutableListOf())

        for (ops in combinations) {
            val expr = numStr.mapIndexed { index, digit ->
                digit.toString() + if (index < ops.size) ops[index] else ""
            }.joinToString("")
            try {
                if (evaluate(expr) == 100.0) {
                    validExpressions.add(expr)
                }
            } catch (e: Exception) {
            }
        }

        if (validExpressions.isNotEmpty()) {
            return Pair(numStr, validExpressions.random())
        }
    }
}

fun evaluate(expression: String): Double {
    return try {
        object {
            var pos = -1
            var ch = 0

            fun nextChar() {
                ch = if (++pos < expression.length) expression[pos].toInt() else -1
            }

            fun eat(charToEat: Int): Boolean {
                while (ch.toChar() == ' ') nextChar()
                if (ch == charToEat) {
                    nextChar()
                    return true
                }
                return false
            }

            fun parse(): Double {
                nextChar()
                val x = parseExpression()
                if (pos < expression.length) throw RuntimeException("Unexpected: " + ch.toChar())
                return x
            }

            fun parseExpression(): Double {
                var x = parseTerm()
                while (true) {
                    if (eat('+'.toInt())) x += parseTerm()
                    else if (eat('-'.toInt())) x -= parseTerm()
                    else return x
                }
            }

            fun parseTerm(): Double {
                var x = parseFactor()
                while (true) {
                    if (eat('*'.toInt())) x *= parseFactor()
                    else if (eat('/'.toInt())) x /= parseFactor()
                    else return x
                }
            }

            fun parseFactor(): Double {
                if (eat('+'.toInt())) return parseFactor()
                if (eat('-'.toInt())) return -parseFactor()

                var x: Double
                val startPos = pos
                if (eat('('.toInt())) {
                    x = parseExpression()
                    eat(')'.toInt())
                } else if (ch >= '0'.toInt() && ch <= '9'.toInt()) {
                    while (ch >= '0'.toInt() && ch <= '9'.toInt()) nextChar()
                    x = expression.substring(startPos, pos).toDouble()
                } else {
                    throw RuntimeException("Unexpected: " + ch.toChar())
                }
                return x
            }
        }.parse()
    } catch (e: ArithmeticException) {
        throw RuntimeException("Division by zero.")
    } catch (e: NumberFormatException) {
        throw RuntimeException("Invalid number format.")
    } catch (e: Exception) {
        throw RuntimeException("Invalid expression.")
    }
}

fun isValidExpression(expression: String, originalNumber: String): Boolean {
    val userDigits = expression.filter { it.isDigit() }
    return userDigits == originalNumber
}