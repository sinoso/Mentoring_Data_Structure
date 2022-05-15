package observer

sealed interface Observer<T> {
    fun onChange(value: T)

    fun interface DoOnChangeListener<T> {
        fun onChange(value: T)
    }
}
