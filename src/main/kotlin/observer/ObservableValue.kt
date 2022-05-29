package observer

sealed interface ObservableValue<T> {
    fun setValue(value: T)
}
