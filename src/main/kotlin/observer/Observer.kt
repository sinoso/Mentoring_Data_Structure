package observer

fun interface Observer<T> {
    fun onChange(value: T)
}