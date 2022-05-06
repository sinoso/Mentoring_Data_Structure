package observer

sealed interface ObservableValue<T> {
    fun subscribe(observer: Observer<T>)

    fun unsubscribe(observer: Observer<T>)

}
