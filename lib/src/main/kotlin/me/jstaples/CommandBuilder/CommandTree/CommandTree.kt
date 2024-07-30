package me.jstaples.CommandBuilder.CommandTree

class CommandTree(commandName: String) {
    val head: CommandTreeNode = CommandTreeNode(commandName)

    fun addSubcommandNodes(vararg arguments: String): CommandTreeNode {
        var currentNode = head
        var foundNode = false
        for (arg in arguments) {
            val existingNode = currentNode.children[arg]
            if (existingNode != null) {
                currentNode = existingNode
                foundNode = true
            }

            if (foundNode) foundNode = false
            else {
                val newNode = CommandTreeNode(arg)
                currentNode.addChildrenNodes(newNode)
                currentNode = newNode
            }
        }
        return currentNode
    }

    fun findSubcommandNode(arguments: Array<String>): CommandTreeNode? {
        var currentNode = head
        var foundNode = false
        for (arg in arguments) {
            val existingNode = currentNode.children[arg]
            if (existingNode != null) {
                currentNode = existingNode
                foundNode = true
            }

            if (foundNode) foundNode = false
            else return null
        }
        return currentNode
    }

    fun getTabComplete(arguments: Array<String>): List<String> {
        return if (arguments.size == 1) {
            head.children.keys.toList()
        } else {
            val slicedArgs = arguments.copyOfRange(0, arguments.size - 1)
            val commandNode = findSubcommandNode(slicedArgs)
            commandNode?.children?.keys?.toList() ?: emptyList()
        }
    }
}