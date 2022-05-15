package observer

import lifeCycle.LifeCycleState
import lifeCycle.LifeCycleOwner

class Observable<T> : ObservableValue<T> {
    private val observerSet = HashSet<ObserverData<T>>()

    private val lifeCycleOwners = mutableSetOf<LifeCycleOwner>()

    fun setValue(value: T) {
        observerSet.filter { it.lifecycleOwner.state == LifeCycleState.LIVE }
            .forEach { (_, observer) -> observer.onChange(value) }
    }

    override fun subscribe(observer: Observer<T>) =
        subscribe(lifecycleOwner = LifeCycleOwner.getEmptyOwner(), observer)


    fun subscribe(lifecycleOwner: LifeCycleOwner, observer: Observer<T>) {
        observerSet.add(ObserverData(lifecycleOwner, observer))
        val lifecycleOwnerAddResult = lifeCycleOwners.add(lifecycleOwner)
        if (lifecycleOwnerAddResult)
            bindLifeCycleOwner(lifecycleOwner)
    }

    private fun bindLifeCycleOwner(lifecycleOwner: LifeCycleOwner) {
        lifecycleOwner.bindLifeCycle {
            whenLifeCycleStateChanged(lifecycleOwner, it)
        }
    }

    private fun whenLifeCycleStateChanged(lifecycleOwner: LifeCycleOwner, lifeCycleState: LifeCycleState) {
        when (lifeCycleState) {
            LifeCycleState.DESTROYED -> removeObserversByLifecycleOwner(lifecycleOwner)
            else -> Unit
        }
    }

    private fun removeObserversByLifecycleOwner(targetLifeCycleOwner: LifeCycleOwner) {
        observerSet.filter { (lifecycleOwner, _) -> lifecycleOwner == targetLifeCycleOwner }
            .forEach { (_, observer) -> unsubscribe(observer) }
        lifeCycleOwners.remove(targetLifeCycleOwner)
    }

    override fun unsubscribe(observer: Observer<T>) {
        observerSet.removeIf { (_, inDataObserver) -> inDataObserver == observer }
    }

   private data class ObserverData<T>(val lifecycleOwner: LifeCycleOwner, val observer: Observer<T>)

}
