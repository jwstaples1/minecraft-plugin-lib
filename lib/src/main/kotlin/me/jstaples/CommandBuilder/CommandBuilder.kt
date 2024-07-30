package me.jstaples.CommandBuilder

import me.jstaples.CommandBuilder.CommandTree.CommandTreeNode
import me.jstaples.CommandBuilder.CommandTree.CommandTree
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.plugin.java.JavaPlugin

class CommandBuilder(val plugin: JavaPlugin, val commandName: String) : CommandExecutor, TabCompleter {

    private val commandTree: CommandTree = CommandTree(commandName)

    init {
        plugin.getCommand(commandName)?.setExecutor(this)
    }

    fun addSubcommand(vararg subcommand: String): CommandTreeNode {
        return commandTree.addSubcommandNodes(*subcommand)
    }

    fun setBaseCommandFunction(function: (CommandSender) -> Unit) {
        commandTree.head.function = function
    }

    override fun onCommand(commandSender: CommandSender, command: Command, s: String, strings: Array<String>): Boolean {
        val executedNode = commandTree.findSubcommandNode(strings)
        if (executedNode?.function == null) {
            commandSender.sendMessage("${ChatColor.RED}Invalid usage")
            return true
        }

        executedNode.function?.invoke(commandSender)
        return true
    }

    override fun onTabComplete(commandSender: CommandSender, command: Command, s: String, strings: Array<String>): List<String> {
        return commandTree.getTabComplete(strings)
    }
}