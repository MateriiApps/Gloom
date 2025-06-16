package gloom.rules.util

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Location
import io.gitlab.arturbosch.detekt.api.Rule
import org.jetbrains.kotlin.com.intellij.psi.PsiElement

fun Rule.report(message: String, element: PsiElement) = report(
    CodeSmell(
        issue = issue,
        entity = Entity.from(element, Location.from(element)),
        message = message
    )
)