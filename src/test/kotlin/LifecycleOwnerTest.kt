import lifeCycle.LifecycleState
import lifeCycle.LifecycleOwner
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class LifecycleOwnerTest {
    @Test
    fun `LifeCycle changeState 시 state값 변경`() {
        val lifecycleOwner = LifecycleOwner(LifecycleState.LIVE)
        fun changeAndCheck(changedLifeCycleState: LifecycleState) {
            lifecycleOwner.changeState(changedLifeCycleState)
            assertThat(lifecycleOwner.state).isEqualTo(changedLifeCycleState)
        }
        changeAndCheck(LifecycleState.STOP)
        changeAndCheck(LifecycleState.DESTROYED)
    }

    @Test
    fun `Empty LifeCycleOwner를 가져올 수 있다`() {
        val emptyLifecycleOwner = LifecycleOwner.getEmptyOwner()
        assertThat(emptyLifecycleOwner).isEqualTo(LifecycleOwner.NONE)
    }

    @Test
    fun `bindLifeCycle 후 상태 변경값을 전송 한다`() {
        val lifecycleOwner = LifecycleOwner(LifecycleState.LIVE)
        var changedByBinding: LifecycleState = LifecycleState.LIVE
        lifecycleOwner.addListener { changedByBinding = it }

        fun changeAndCheck(changedLifeCycleState: LifecycleState) {
            lifecycleOwner.changeState(changedLifeCycleState)
            assertThat(changedByBinding).isEqualTo(changedLifeCycleState)
        }

        changeAndCheck(LifecycleState.STOP)
        changeAndCheck(LifecycleState.DESTROYED)
    }

    @Test
    fun `LifeCycle이 DESTROYED로 변경 될경우 Listener를 제거한다`() {
        val lifecycleOwner = LifecycleOwner(LifecycleState.LIVE)
        val testListenerSize = 2
        val emptySize = 0
        val testListener = List<(LifecycleState) -> Unit>(testListenerSize) { {} }
        fun LifecycleOwner.numberOfListeners() = getListeners().size

        testListener.forEach(lifecycleOwner::addListener)

        assertThat(lifecycleOwner.numberOfListeners()).isEqualTo(testListenerSize)

        lifecycleOwner.changeState(LifecycleState.DESTROYED)
        assertThat(lifecycleOwner.numberOfListeners()).isEqualTo(emptySize)
    }
}
