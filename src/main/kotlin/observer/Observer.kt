package observer

import lifeCycle.LifeCycle
import lifeCycle.LifeCycleState

fun <T> Observable<T>.subscribe(lifeCycle: LifeCycle, observer: Observer<T>) {
    subscribe(observer)

    lifeCycle.addListener {
        if (it == LifeCycleState.DESTROYED) {
            unsubscribe(observer)
        }
    }
}

fun interface Observer<T> {
    fun onChange(value: T)
}
