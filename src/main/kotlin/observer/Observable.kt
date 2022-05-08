package observer

import lifeCycle.LifecycleOwner
import lifeCycle.LifecycleState

class Observable<T> : IObservable<T> {
    private val observerSet = HashSet<ObserverData<T>>()

//    private val lifecycleOwners = mutableSetOf<LifecycleOwner>()

    fun setValue(value: T) {
        observerSet.filter { it.lifecycleOwner.state == LifecycleState.LIVE }
            .forEach { (_, observer) -> observer.onChange(value) }
    }

    override fun subscribe(observer: Observer<T>) =
        subscribe(lifecycleOwner = LifecycleOwner.getEmptyOwner(), observer)

    fun subscribe(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
        if (observerSet.add(ObserverData(lifecycleOwner, observer))) {
            lifecycleOwner.addListener {
                if (it == LifecycleState.DESTROYED) {
                    unsubscribe(observer)
                }
            }
        }
//        val lifecycleOwnerAddResult = lifecycleOwners.add(lifecycleOwner)
//        if (lifecycleOwnerAddResult)
//            bindLifeCycleOwner(lifecycleOwner)
    }

//    private fun bindLifeCycleOwner(lifecycleOwner: LifecycleOwner) {
//        lifecycleOwner.addListener {
//            whenLifeCycleStateChanged(lifecycleOwner, it)
//        }
//    }

//    private fun whenLifeCycleStateChanged(lifecycleOwner: LifecycleOwner, lifeCycleState: LifecycleState) {
//        when (lifeCycleState) {
//            LifecycleState.DESTROYED -> removeObserversByLifecycleOwner(lifecycleOwner)
//            else -> Unit
//        }
//    }

//    private fun removeObserversByLifecycleOwner(targetLifecycleOwner: LifecycleOwner) {
//        observerSet.filter { (lifecycleOwner, _) -> lifecycleOwner == targetLifecycleOwner }
//            .forEach { (_, observer) -> unsubscribe(observer) }
//        lifecycleOwners.remove(targetLifecycleOwner)
//    }

    override fun unsubscribe(observer: Observer<T>) {
        observerSet.removeIf { (_, inDataObserver) -> inDataObserver == observer }
    }

    private data class ObserverData<T>(val lifecycleOwner: LifecycleOwner, val observer: Observer<T>)
}
