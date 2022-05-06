package lifeCycle

class LifecycleOwner(state: LifeCycleState) {
    private var lifeCycleListeners = mutableListOf<(LifeCycleState) -> Unit>()

    var state: LifeCycleState = state
        private set

    fun changeState(state: LifeCycleState) {
        this.state = state
        lifeCycleListeners.forEach { it(state) }
        if (state == LifeCycleState.DESTROYED)
            lifeCycleListeners.clear()
    }

    fun getLifeCycleListeners() = lifeCycleListeners.toList()

    fun bindLifeCycle(onStateChanged: (LifeCycleState) -> Unit) {
        lifeCycleListeners.add(onStateChanged)
    }

    companion object {
        val NONE = LifecycleOwner(LifeCycleState.LIVE)
        fun getEmptyOwner() = NONE
    }
}
