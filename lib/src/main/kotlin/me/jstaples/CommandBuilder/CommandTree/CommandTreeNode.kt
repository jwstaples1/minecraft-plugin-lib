package me.jstaples.CommandBuilder.CommandTree

import org.bukkit.command.CommandSender

class CommandTreeNode(val name: String) {
    val children: MutableMap<String, CommandTreeNode> = mutableMapOf()
    var function: ((CommandSender) -> Unit)? = null
        set(value) {
            field = value
        }

    fun addChildrenNodes(vararg nodes: CommandTreeNode) {
        for (node in nodes) {
            children[node.name] = node
        }
    }
}