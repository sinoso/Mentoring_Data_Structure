package observer

class ObserverImpl<T>(private val _onChange: Observer<T>) : Observer<T> by _onChange {
    override fun onChange(value: T) {
        _onChange.onChange(value)
    }

    override fun equals(other: Any?): Boolean {
        return _onChange.hashCode() == other.hashCode()
    }

    override fun hashCode(): Int {
        return _onChange.hashCode()
    }
}
