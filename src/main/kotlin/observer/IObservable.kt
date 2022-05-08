package observer

interface IObservable<T> {
    fun subscribe(observer: Observer<T>)

    fun unsubscribe(observer: Observer<T>)
}
