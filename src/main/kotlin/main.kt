import lifeCycle.LifeCycleState
import lifeCycle.LifeCycleOwner
import observer.Observable
import observer.Listener

fun main() {
    val firstListener = Listener<String> {
        println("This is First $it")
    }

    val secondListener = Listener<String> {
        println("This is second $it")
    }

    val observable = Observable<String>()

    val lifecycleOwner = LifeCycleOwner(LifeCycleState.LIVE)

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
    lifecycleOwner.changeState(LifeCycleState.STOP)
    observable.setValue("call from observable")

    println()
    println("LifeCycle STOP -> LIVE 변경")
    lifecycleOwner.changeState(LifeCycleState.LIVE)
    observable.setValue("call from observable")

    println()
    println("LifeCycle LIVE -> DESTROYED 변경")
    lifecycleOwner.changeState(LifeCycleState.DESTROYED)
    observable.setValue("call from observable")

}
