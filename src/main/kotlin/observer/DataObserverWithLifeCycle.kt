package observer

import lifeCycle.LifeCycleOwner

class DataObserverWithLifeCycle<T>(
    private val lifeCycleOwner: LifeCycleOwner,
    private val onChangeListener: Observer.ValueChangeListener<T>
) : ObserverWithLifeCycle<T> {
    override fun onChange(value: T) {
        if (isChangeAbleState())
            onChangeListener.onValueChange(value)
    }

    override fun isChangeAbleState() = lifeCycleOwner.lifeCycle.isChangeAble()
}
