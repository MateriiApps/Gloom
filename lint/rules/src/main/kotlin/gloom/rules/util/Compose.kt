package gloom.rules.util

import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.psi.KtCallExpression
import org.jetbrains.kotlin.psi.KtValueArgument
import org.jetbrains.kotlin.resolve.BindingContext
import org.jetbrains.kotlin.resolve.calls.util.getResolvedCall

fun KtValueArgument.isModifier() = this isNamed "modifier" || hasValue("Modifier")

fun KtCallExpression.isComposableCall(
    composableAnnotationClassPackage: String,
    bindingContext: BindingContext,
): Boolean {
    val resolvedCall = this.getResolvedCall(bindingContext) ?: return false
    val annotationFqName = FqName("$composableAnnotationClassPackage.Composable")
    return resolvedCall.resultingDescriptor.annotations.hasAnnotation(annotationFqName) ||
            resolvedCall.dispatchReceiver?.type?.annotations?.hasAnnotation(annotationFqName) == true
}