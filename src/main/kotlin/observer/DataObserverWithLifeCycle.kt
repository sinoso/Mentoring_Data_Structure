package observer

import lifeCycle.LifeCycleOwner

class DataObserverWithLifeCycle<T>(
    private val lifeCycleOwner: LifeCycleOwner,
    private val onChangeListener: Observer.DoOnChangeListener<T>
) : ObserverWithLifeCycle<T> {
    override fun onChange(value: T) {
        if (isChangeAbleState())
            onChangeListener.onChange(value)
    }

    override fun isChangeAbleState() = lifeCycleOwner.lifeCycle.isChangeAble()
}
