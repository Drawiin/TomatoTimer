package core.arch

import cafe.adriel.voyager.core.model.ScreenModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * This is an abstract class that represents an interaction model.
 * It is a part of the architecture core and extends the ScreenModel class.
 *
 * @param S The type of the state.
 * @param A The type of the action.
 * @param E The type of the effects.
 * @param initial The initial state.
 */
abstract class InteractionModel<S, A, E>(
    initial: S
): ScreenModel {
    // A MutableStateFlow that represents the mutable state of the interaction model.
    // It is initialized with the initial state passed to the constructor.
    protected val mutableState =  MutableStateFlow(initial)

    // A StateFlow that represents the state of the interaction model.
    // It is derived from the mutableState.
    val state: StateFlow<S> = mutableState.asStateFlow()

    // A MutableSharedFlow that represents the mutable effects of the interaction model.
    protected val mutableEffects = MutableSharedFlow<E>()

    // A Flow that represents the effects of the interaction model.
    // It is derived from the mutableEffects.
    val effects: Flow<E> = mutableEffects

    /**
     * This is an abstract function that should be implemented by subclasses.
     * It is called when an action occurs.
     *
     * @param action The action that occurred.
     */
    abstract fun onAction(action: A)
}