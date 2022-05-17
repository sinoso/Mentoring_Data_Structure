package observer

sealed interface Observer<T> {
    fun onChange(value: T)

    fun interface ValueChangeListener<T> {
        fun onValueChange(value: T)
    }
}
