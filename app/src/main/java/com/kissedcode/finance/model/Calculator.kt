//package com.kissedcode.finance.model
//
//class Calculator {
//
//    // money operations summary
//
//    fun sum(operations: Collection<MoneyOperation>): Double {
//        var sum = 0.0
//
//        for(oper in operations) {
//
//            when (oper.type) {
//                MoneyOperation.Type.PLUS -> sum += oper.value * oper.currency.cost
//                MoneyOperation.Type.MINUS -> sum -= oper.value * oper.currency.cost
//            }
//        }
//
//        return sum
//    }
//
//    fun sum(vararg operations: MoneyOperation) = sum(operations.asList())
//}