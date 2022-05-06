package observer

class Observable<T> : ObservableValue<T> {
    private val observerSet = HashSet<Observer<T>>()

    fun setValue(value: T) = observerSet.forEach { it.onChange(value) }

    override fun subscribe(observer: Observer<T>) {
        observerSet.add(observer)
    }

    override fun unsubscribe(observer: Observer<T>) {
        observerSet.remove(observer)
    }

}
