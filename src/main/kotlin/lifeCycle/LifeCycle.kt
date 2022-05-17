package lifeCycle

class LifeCycle(state: LifeCycleState) {
    private val lifeCycleChangeListeners = mutableListOf<OnChangeListener>()

    var state: LifeCycleState = state
        private set

    fun changeState(state: LifeCycleState) {
        this.state = state
        lifeCycleChangeListeners.forEach { it.onStateChange(state) }
        if (state == LifeCycleState.DESTROYED)
            lifeCycleChangeListeners.clear()
    }

    fun isChangeAble(): Boolean {
        return when (state) {
            LifeCycleState.LIVE -> true
            else -> false
        }
    }

    fun getListeners() = lifeCycleChangeListeners.toList()

    fun addListener(onStateChanged: OnChangeListener) {
        lifeCycleChangeListeners.add(onStateChanged)
    }

    fun interface OnChangeListener {
        fun onStateChange(stat: LifeCycleState)
    }
}
