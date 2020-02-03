const classLoader = require('class:eu.mcdb.spicord.util.SpicordClassLoader')
const path        = require('path')

classLoader.loadJar(path.join(__dirname, 'lib', 'chatsync.jar'))

const ChatListener = require('class:chatsync.ChatListener')
const listener     = new ChatListener()

module.exports = function(func) {
    // the 'J' method will wrap the JS function into a Java
    // instance before it get passed the Java environment.
    listener.onChat(J(func))
}