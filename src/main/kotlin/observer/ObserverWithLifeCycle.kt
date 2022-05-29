package observer

import observer.lifeCycle.LifeCycle
import observer.lifeCycle.LifeCycleState

fun <T> Observable<T>.subscribe(lifeCycle: LifeCycle, observer: Observer<T>) {
    val wrapper = ObserverWithLifeCycle(lifeCycle, observer)
    subscribe(wrapper)

    lifeCycle.addListener {
        if (it == LifeCycleState.DESTROYED) {
            unsubscribe(wrapper)
        }
    }
}

class ObserverWithLifeCycle<T>(
    private val lifeCycle: LifeCycle,
    private val observer: Observer<T>
) : Observer<T> {

    override fun onChange(value: T) {
        if (lifeCycle.isChangeAble()) {
            observer.onChange(value)
        }
    }

    override fun equals(other: Any?): Boolean {
        return observer == other
    }

    override fun hashCode(): Int {
        return observer.hashCode()
    }
}
