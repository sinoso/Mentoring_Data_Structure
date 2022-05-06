package observer

sealed interface Observer<T> {
    fun onChange(value: T)
}
