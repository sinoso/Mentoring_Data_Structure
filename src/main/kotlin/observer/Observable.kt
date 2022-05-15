package observer

import lifeCycle.LifeCycleState
import lifeCycle.LifeCycleOwner

class Observable<T> : ObservableValue<T> {
    private val observerSet = HashSet<ObserverData<T>>()

    fun setValue(value: T) {
        observerSet.filter { it.lifecycleOwner.state == LifeCycleState.LIVE }
            .forEach { (_, observer) -> observer.onChange(value) }
    }

    override fun subscribe(observer: Observer<T>) =
        subscribe(lifecycleOwner = LifeCycleOwner.getEmptyOwner(), observer)


    fun subscribe(lifecycleOwner: LifeCycleOwner, observer: Observer<T>) {
        if (observerSet.add(ObserverData(lifecycleOwner, observer))) {
            lifecycleOwner.addLifeCycleListener(getLifeCycleListenerForDESTROYED(observer))
        }
    }

    private fun getLifeCycleListenerForDESTROYED(observer: Observer<T>) = LifeCycleOwner.LifeCycleListener {
        if (it == LifeCycleState.DESTROYED) {
            unsubscribe(observer)
        }
    }

    override fun unsubscribe(observer: Observer<T>) {
        observerSet.removeIf { (_, inDataObserver) -> inDataObserver == observer }
    }

    private data class ObserverData<T>(val lifecycleOwner: LifeCycleOwner, val observer: Observer<T>)

}
