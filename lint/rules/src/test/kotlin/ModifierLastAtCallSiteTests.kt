import gloom.rules.ModifierLastAtCallSite
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.rules.KotlinCoreEnvironmentTest
import io.gitlab.arturbosch.detekt.test.compileAndLintWithContext
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
import org.junit.jupiter.api.Test
import util.asComposeSnippet

@KotlinCoreEnvironmentTest
class ModifierLastAtCallSiteTests(
    private val env: KotlinCoreEnvironment
) {

    val rule = ModifierLastAtCallSite(Config.empty, "gloom.rules")

    @Test
    fun `Not final argument (unnamed) - Should lint`() {
        val code = """
            Text(
                Modifier,
                text = ""
            )
        """.trimIndent()

        val findings = rule.compileAndLintWithContext(env, code.asComposeSnippet())

        assert(findings.size == 1) {
            "Expected 1 finding, got ${findings.size}"
        }

        assert(findings.firstOrNull()?.message == ModifierLastAtCallSite.MESSAGE) {
            "ModifierLastAtCallSite not linted"
        }
    }

    @Test
    fun `Not final argument (named) - Should lint`() {
        val code = """
            Text(
                modifier = Modifier,
                text = ""
            )
        """.trimIndent()

        val findings = rule.compileAndLintWithContext(env, code.asComposeSnippet())

        assert(findings.size == 1) {
            "Expected 1 finding, got ${findings.size}"
        }

        assert(findings.firstOrNull()?.message == ModifierLastAtCallSite.MESSAGE) {
            "ModifierLastAtCallSite not linted"
        }
    }

    @Test
    fun `Final argument (unnamed) - Shouldn't lint`() {
        val code = """
            Text(
                text = "",
                Modifier
            )
        """.trimIndent()

        val findings = rule.compileAndLintWithContext(env, code.asComposeSnippet())
        assert(findings.isEmpty())
    }

    @Test
    fun `Final argument (named) - Shouldn't lint`() {
        val code = """
            Text(
                text = "",
                modifier = Modifier
            )
        """.trimIndent()

        val findings = rule.compileAndLintWithContext(env, code.asComposeSnippet())
        assert(findings.isEmpty())
    }

    @Test
    fun `Not final argument (trailing lambda) - Should lint`() {
        val code = """
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                /* ... */
            }
        """.trimIndent()

        val findings = rule.compileAndLintWithContext(env, code.asComposeSnippet())

        assert(findings.size == 1) {
            "Expected 1 finding, got ${findings.size}"
        }

        assert(findings.firstOrNull()?.message == ModifierLastAtCallSite.MESSAGE) {
            "ModifierLastAtCallSite not linted"
        }
    }

    @Test
    fun `Final argument (trailing lambda) - Shouldn't lint`() {
        val code = """
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
            ) {
                /* ... */
            }
        """.trimIndent()

        val findings = rule.compileAndLintWithContext(env, code.asComposeSnippet())
        assert(findings.isEmpty())
    }

    @Test
    fun `Final argument (content arg) - Shouldn't lint`() {
        val code = """
            @Composable
            fun Component(
                someArg: String,
                modifier = Modifier,
                content: @Composable () -> Unit
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier,
                    content = content
                )
            }
        """.trimIndent()

        val findings = rule.compileAndLintWithContext(env, code.asComposeSnippet())
        assert(findings.isEmpty())
    }

}