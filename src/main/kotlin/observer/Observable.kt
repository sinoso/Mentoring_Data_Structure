package observer

class Observable<T> : ObservableValue<T> {
    private val observerSet = HashSet<Observer<T>>()

    override fun setValue(value: T) {
        observerSet.forEach { it.onChange(value) }
    }

    fun subscribe(observer: Observer<T>) {
        observerSet.add(observer)
    }

    fun unsubscribe(observer: Observer<T>) {
        observerSet.removeIf { it == observer }
//        observerSet.remove(observer)
    }
}
