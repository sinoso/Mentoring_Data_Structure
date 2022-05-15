package observer

class ObservableWithLifeCycle<T> : ObservableValue<T> {
    private val observerSet = HashSet<ObserverWithLifeCycle<T>>()

    override fun setValue(value: T) {
        observerSet.forEach { it.onChange(value) }
    }

    fun subscribe(observer: ObserverWithLifeCycle<T>) {
        observerSet.add(observer)
    }

    fun unsubscribe(observer: ObserverWithLifeCycle<T>) {
        observerSet.removeIf { it == observer }
    }
}
