package observer

sealed interface ObserverWithLifeCycle<T> : Observer<T> {
    fun isChangeAbleState(): Boolean
}
