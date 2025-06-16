package gloom.rules

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.RuleSet
import io.gitlab.arturbosch.detekt.api.RuleSetProvider

class GloomRuleSetProvider: RuleSetProvider {

    override val ruleSetId = "Gloom"

    override fun instance(config: Config): RuleSet {
        return RuleSet(
            ruleSetId,
            listOf(
                ModifierLastAtCallSite(config)
            )
        )
    }

}