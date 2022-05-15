package observer

class DataObserver<T>(private val onChangeListener: Observer.DoOnChangeListener<T>) : Observer<T> {
    override fun onChange(value: T) = onChangeListener.onChange(value)
}
