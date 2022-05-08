import lifeCycle.LifecycleOwner
import lifeCycle.LifecycleState
import observer.Observable
import observer.Observer

fun main() {
    val firstListener = Observer<String> {
        println("This is First $it")
    }

    val secondListener = Observer<String> {
        println("This is second $it")
    }

    val observable = Observable<String>()

    val lifecycleOwner = LifecycleOwner(LifecycleState.LIVE)

    observable.apply {
        subscribe(lifecycleOwner, firstListener)
        subscribe(lifecycleOwner, secondListener)
    }
    println("Listener 추가 최초 호출")
    observable.setValue("call from observable")

    println()
    println("secondListener secondListener 제거")
    observable.unsubscribe(secondListener)
    observable.setValue("call from observable")

    println()
    println("LifeCycle LIVE -> STOP 변경")
    lifecycleOwner.changeState(LifecycleState.STOP)
    observable.setValue("call from observable")

    println()
    println("LifeCycle STOP -> LIVE 변경")
    lifecycleOwner.changeState(LifecycleState.LIVE)
    observable.setValue("call from observable")

    println()
    println("LifeCycle LIVE -> DESTROYED 변경")
    lifecycleOwner.changeState(LifecycleState.DESTROYED)
    observable.setValue("call from observable")

}
