object EnvironmentVariables {
    val MARVEL_API_KEY: String? get() = System.getenv("MARVEL_API_KEY")
    val MARVEL_PRIVATE_API_KEY: String? get() = System.getenv("MARVEL_PRIVATE_API_KEY")
}