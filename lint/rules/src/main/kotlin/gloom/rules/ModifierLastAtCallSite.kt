package gloom.rules

import gloom.rules.util.isModifier
import gloom.rules.util.isNamed
import gloom.rules.util.report
import io.gitlab.arturbosch.detekt.api.*
import org.jetbrains.kotlin.psi.KtCallExpression
import org.jetbrains.kotlin.psi.KtLambdaExpression
import org.jetbrains.kotlin.psi.psiUtil.argumentIndex

/**
 * The modifier argument used in a composable call should be the final non-lambda argument
 *
 * Non-compliant:
 *
 * ```kt
 * MyComposable(
 *     modifier = Modifier
 *         .clickable { /* ... */  }
 *         .padding(16.dp),
 *     someArg = "Hello"
 * )
 * ```
 *
 * Compliant:
 * ```kt
 * MyComposable(
 *     someArg = "Hello",
 *     modifier = Modifier
 *         .clickable { /* ... */  }
 *         .padding(16.dp)
 * )
 * ```
 */
class ModifierLastAtCallSite(
    config: Config,
    private val composableAnnotationPackage: String = "androidx.compose.runtime"
): Rule(config) {

    override val issue = Issue(
        id = javaClass.simpleName,
        severity = Severity.CodeSmell,
        description = MESSAGE,
        debt = Debt.FIVE_MINS
    )

    override fun visitCallExpression(expression: KtCallExpression) {
        // For some reason analysis doesn't work in common code, for now just treat every function receiving
        // a modifier argument as Composable. (Wait for detekt/detekt#8021 maybe?)
        // if (!expression.isComposableCall(composableAnnotationPackage, bindingContext)) return

        val modifierArg = expression.valueArguments.find { it.isModifier() } ?: return
        val lastArg = expression.valueArguments
            .last {
                it.getArgumentExpression() !is KtLambdaExpression
                        && !it.isNamed("content")
                        && !it.isSpread
            }

        if (modifierArg.argumentIndex != lastArg.argumentIndex) {
            report(MESSAGE, modifierArg)
        }
    }

    companion object {

        val MESSAGE = """
            The modifier argument used in a composable call should be placed right before the content lambda or as the final argument.
        """.trimIndent()

    }

}