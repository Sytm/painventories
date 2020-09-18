package de.md5lukas.painventories.slots

inline fun normalSlot(init: NormalSlot.() -> Unit): NormalSlot {
    return NormalSlot().apply(init)
}

inline fun editableSlot(init: EditableSlot.() -> Unit): EditableSlot {
    return EditableSlot().apply(init)
}