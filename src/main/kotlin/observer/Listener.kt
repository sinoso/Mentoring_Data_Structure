package observer

class Listener<T>(private val onChangeBlock: (T) -> Unit) : Observer<T> {
    override fun onChange(value: T) = onChangeBlock(value)
}
