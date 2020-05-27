const native = require('native')
const path   = require('path')

native.load(path.join(__dirname, 'lib', 'chatsync.jar'))

const ChatListener = native.get('chatsync.ChatListener')
const listener     = new ChatListener()

module.exports = function(engine, func) {
    listener.onChat(engine.toJava(func))
}