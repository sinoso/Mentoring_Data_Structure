package lifeCycle

class LifeCycleOwner(state: LifeCycleState) {
    private var lifeCycleListeners = mutableListOf<LifeCycleListener>()

    var state: LifeCycleState = state
        private set

    fun changeState(state: LifeCycleState) {
        this.state = state
        lifeCycleListeners.forEach { it.onStateChange(state) }
        if (state == LifeCycleState.DESTROYED)
            lifeCycleListeners.clear()
    }

    fun getLifeCycleListeners() = lifeCycleListeners.toList()

    fun addLifeCycleListener(onStateChanged:LifeCycleListener) {
        lifeCycleListeners.add(onStateChanged)
    }

    companion object {
        val NONE = LifeCycleOwner(LifeCycleState.LIVE)
        fun getEmptyOwner() = NONE
    }

   fun interface LifeCycleListener{
        fun onStateChange(stat:LifeCycleState)
    }
}
