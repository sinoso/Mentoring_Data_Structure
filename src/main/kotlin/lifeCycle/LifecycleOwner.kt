package lifeCycle


class LifecycleOwner(state: LifecycleState) {
    fun interface Listener {
        fun onChanged(state: LifecycleState)
    }

    private var listeners = mutableListOf<Listener>()

    var state: LifecycleState = state
        private set

    fun changeState(state: LifecycleState) {
        this.state = state
        listeners.forEach { it.onChanged(state) }
        if (state == LifecycleState.DESTROYED)
            listeners.clear()
    }

    fun getListeners() = listeners.toList()

    fun addListener(listener: Listener) {
        listeners.add(listener)
    }

    companion object {
        val NONE = LifecycleOwner(LifecycleState.LIVE)
        fun getEmptyOwner() = NONE
    }
}
