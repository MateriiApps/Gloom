package gloom.rules.util

import org.jetbrains.kotlin.psi.KtDotQualifiedExpression
import org.jetbrains.kotlin.psi.KtReferenceExpression
import org.jetbrains.kotlin.psi.KtValueArgument

fun KtValueArgument.hasValue(value: String): Boolean {
    val expr = getArgumentExpression() ?: return false
    val exprValue = when (expr) {
        is KtReferenceExpression -> expr.text
        is KtDotQualifiedExpression -> expr.receiverExpression.text
        else -> null
    } ?: return false

    return exprValue == value
}

infix fun KtValueArgument.isNamed(argName: String): Boolean {
    return getArgumentName()?.asName?.asString() == argName
}