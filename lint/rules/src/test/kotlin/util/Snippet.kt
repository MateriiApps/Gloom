package util

fun String.asComposeSnippet() = """
      package gloom.rules

      @Target(
          AnnotationTarget.FUNCTION,
          AnnotationTarget.TYPE,
          AnnotationTarget.TYPE_PARAMETER,
          AnnotationTarget.PROPERTY_GETTER
      )
      annotation class Composable
      
      data class Dp(val v: Int)
      
      val Int.dp get() = Dp(this)
      
      interface Modifier {
        fun weight(v: Float): Modifier { TODO() }
        fun fillMaxSize(): Modifier { TODO() }
        fun padding(horizontal: Dp, vertical: Dp): Modifier { TODO() }
        fun padding(all: Dp): Modifier { TODO() }
      }
      
      val Modifier = object : Modifier {
      }
      
      enum class Alignment { CenterVertically }
    
      @Composable fun Row(modifier: Modifier = Modifier, verticalAlignment: Alignment = Alignment.CenterVertically, content: @Composable () -> Unit) {}
      @Composable fun Column(modifier: Modifier = Modifier, verticalAlignment: Alignment = Alignment.CenterVertically, content: @Composable () -> Unit) {}
      @Composable fun Box(modifier: Modifier = Modifier, content: @Composable () -> Unit) {}
      @Composable fun Text(text: String, modifier: Modifier = Modifier) {}
      @Composable fun Button(onClick: (String) -> Unit, content: @Composable () -> Unit) {}
      
      fun main() {
          $this
      }
""".trimIndent()