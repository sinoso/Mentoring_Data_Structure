package lifeCycle

class LifeCycle(state: LifeCycleState) {
    private var lifeCycleListeners = mutableListOf<LifeCycleListener>()

    var state: LifeCycleState = state
        private set

    fun changeState(state: LifeCycleState) {
        this.state = state
        lifeCycleListeners.forEach { it.onStateChange(state) }
        if (state == LifeCycleState.DESTROYED)
            lifeCycleListeners.clear()
    }

    fun isChangeAble(): Boolean {
        return when (state) {
            LifeCycleState.LIVE -> true
            else -> false
        }
    }

    fun getLifeCycleListeners() = lifeCycleListeners.toList()

    fun addLifeCycleListener(onStateChanged: LifeCycleListener) {
        lifeCycleListeners.add(onStateChanged)
    }

    fun interface LifeCycleListener {
        fun onStateChange(stat: LifeCycleState)
    }
}
