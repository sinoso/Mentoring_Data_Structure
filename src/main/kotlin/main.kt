import lifeCycle.LifeCycle
import lifeCycle.LifeCycleOwner
import lifeCycle.LifeCycleState
import observer.Observable
import observer.Observer
import observer.subscribe

fun main() {
    check()
    checkWithLifeCycle()
}

fun check() {
    println("observable 테스트")
    val dataObserver = Observer<String> {
        println("This is FirstListener $it")
    }
    val observable = Observable<String>()
    observable.subscribe(dataObserver)

    println("Listener 최초 호출")
    observable.setValue("call from observable")
    println()
    println("Listener 제거 후 호출")
    observable.unsubscribe(dataObserver)
    observable.setValue("call from observable")
    println()
}

fun checkWithLifeCycle() {
    println("ObservableWithLifeCycle 테스트")
    val lifeCycleOwner = LifeCycleOwner(LifeCycle(LifeCycleState.LIVE))

    val firstListener = Observer<String> {
        println("This is FirstListener $it")
    }

    val secondListener = Observer<String> {
        println("This is secondListener $it")
    }
    val observableWithLifeCycle = Observable<String>()

    observableWithLifeCycle.apply {
        subscribe(lifeCycleOwner.lifeCycle, firstListener)
        subscribe(lifeCycleOwner.lifeCycle, secondListener)
    }
    println("Listener 추가 최초 호출")
    observableWithLifeCycle.setValue("call from observable")

    println()
    println("secondListener secondListener 제거")
    observableWithLifeCycle.unsubscribe(secondListener)
    observableWithLifeCycle.setValue("call from observable")

    println()
    println("LifeCycle LIVE -> STOP 변경")
    lifeCycleOwner.lifeCycle.changeState(LifeCycleState.STOP)
    observableWithLifeCycle.setValue("call from observable")

    println()
    println("LifeCycle STOP -> LIVE 변경")
    lifeCycleOwner.lifeCycle.changeState(LifeCycleState.LIVE)
    observableWithLifeCycle.setValue("call from observable")

    println()
    println("LifeCycle LIVE -> DESTROYED 변경")
    lifeCycleOwner.lifeCycle.changeState(LifeCycleState.DESTROYED)
    observableWithLifeCycle.setValue("call from observable")
}
