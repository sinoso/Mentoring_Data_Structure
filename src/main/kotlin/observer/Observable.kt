package observer

import lifeCycle.LifeCycleState
import lifeCycle.LifecycleOwner

class Observable<T> : ObservableValue<T> {
    private val observerSet = HashSet<ObserverData<T>>()

    private val lifecycleOwners = mutableSetOf<LifecycleOwner>()

    fun setValue(value: T) {
        observerSet.filter { it.lifecycleOwner.state == LifeCycleState.LIVE }
            .forEach { (_, observer) -> observer.onChange(value) }
    }

    override fun subscribe(observer: Observer<T>) =
        subscribe(lifecycleOwner = LifecycleOwner.getEmptyOwner(), observer)


    fun subscribe(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
        observerSet.add(ObserverData(lifecycleOwner, observer))
        val lifecycleOwnerAddResult = lifecycleOwners.add(lifecycleOwner)
        if (lifecycleOwnerAddResult)
            bindLifeCycleOwner(lifecycleOwner)
    }

    private fun bindLifeCycleOwner(lifecycleOwner: LifecycleOwner) {
        lifecycleOwner.bindLifeCycle {
            whenLifeCycleStateChanged(lifecycleOwner, it)
        }
    }

    private fun whenLifeCycleStateChanged(lifecycleOwner: LifecycleOwner, lifeCycleState: LifeCycleState) {
        when (lifeCycleState) {
            LifeCycleState.DESTROYED -> removeObserversByLifecycleOwner(lifecycleOwner)
            else -> Unit
        }
    }

    private fun removeObserversByLifecycleOwner(targetLifecycleOwner: LifecycleOwner) {
        observerSet.filter { (lifecycleOwner, _) -> lifecycleOwner == targetLifecycleOwner }
            .forEach { (_, observer) -> unsubscribe(observer) }
        lifecycleOwners.remove(targetLifecycleOwner)
    }

    override fun unsubscribe(observer: Observer<T>) {
        observerSet.removeIf { (_, inDataObserver) -> inDataObserver == observer }
    }

   private data class ObserverData<T>(val lifecycleOwner: LifecycleOwner, val observer: Observer<T>)

}
