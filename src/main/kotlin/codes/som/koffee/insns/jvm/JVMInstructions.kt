package codes.som.koffee.insns.jvm

import codes.som.koffee.insns.InstructionAssembly
import org.objectweb.asm.Label
import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.tree.FrameNode
import org.objectweb.asm.tree.InsnNode
import org.objectweb.asm.tree.LabelNode

internal typealias U = Unit

/**
 * Do nothing.
 *
 * (no stack consumed) -> (no stack produced)
 */
public val InstructionAssembly.nop: U get() {
    instructions.add(InsnNode(NOP))
}

public fun InstructionAssembly.f_new(nLocal: Int, local: Array<Any>, nStack: Int, stack: Array<Any>) {
    instructions.add(FrameNode(F_NEW, nLocal, local, nStack, stack))
}

public fun InstructionAssembly.f_full(nLocal: Int, local: Array<Any>, nStack: Int, stack: Array<Any>) {
    instructions.add(FrameNode(F_FULL, nLocal, local, nStack, stack))
}

public fun InstructionAssembly.f_append(nLocal: Int, local: Array<Any>) {
    instructions.add(FrameNode(F_APPEND, nLocal, local, 0, null))
}

public fun InstructionAssembly.f_chop(nLocal: Int) {
    instructions.add(FrameNode(F_CHOP, nLocal, null, 0, null))
}

public val InstructionAssembly.f_same: U get() {
    instructions.add(FrameNode(F_SAME, 0, null, 0, null))
}

public fun InstructionAssembly.f_same1(stack: Array<Any>) {
    instructions.add(FrameNode(F_SAME1, 0, null, 0, stack))
}

public fun InstructionAssembly.label(label: Label) {
    instructions.add(LabelNode(label))
}
