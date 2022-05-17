package observer

class DataObserver<T>(private val onChangeListener: Observer.ValueChangeListener<T>) : Observer<T> {
    override fun onChange(value: T) = onChangeListener.onValueChange(value)
}
