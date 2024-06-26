/*
 * I tried to make this code as simple as I can, so it will be more easier
 * to understand if you are new in JavaScript ;)
 * 
 * Remember that you can ask me for help! (but don't ask me to teach you JS!)
 * 
 * Sheidy.
 */


// imports
const BaseAddon = require('base-addon')
const yaml      = require('yaml')
const path      = require('path')
const fs        = require('fs')
const listener  = require('./ChatListener')
const util      = require('./util')

// constants
const embed      = util.embed
const configFile = path.join(__data, 'config.yml')
const embedFile  = path.join(__data, 'message-template.json')
const addon      = new BaseAddon()

// variables
var bot
var jda
var config
var channelId
var channel

var webhookUrl

var messageType

var embedStr
var messageStr

// the chat listener
listener(__engine, function(event) {
    if (channel == null)
        return

    var player = event.player()
    var message = event.message()
    var server = event.server()

    if (messageType == 'webhook') {
        message = embedStr.replace("%player%", player).replace('%message%', message).replace('%server%', server)
        embed(message).toWebhook().sendTo(webhookUrl)
    }

    if (messageType == 'embed') {
        message = embedStr.replace("%player%", player).replace('%message%', message).replace('%server%', server)
        embed(message).sendToChannel(channel)
    }

    if (messageType == 'message') {
        message = messageStr.replace('%player%', player).replace('%message%', message).replace('%server%', server)
        channel.sendMessage(message).queue()
    }
})

// set the variables when the bot is ready
function ready(_bot) {
    bot         = _bot
    jda         = bot.getJda()
    config      = yaml.load(configFile)
    channelId   = config.getString('channel-id')
    messageType = config.getString('type')
    messageStr  = config.getString('message')
    webhookUrl  = config.getString('webhook-url')
    channel     = jda.getTextChannelById(channelId)
    embedStr    = fs.readFileSync(embedFile, 'utf8')
}

// ready handler
addon.on('ready', ready)

// command handler for 'setchannel'
addon.on('command: setchannel', function(cmd, args) {
    if (!util.hasPermission(cmd, 'MANAGE_CHANNEL')) {
        return cmd.reply('You do not have permission to perform this action.')
    }

    var mentioned_channels = cmd.getMessage().getMentionedChannels()

    if (mentioned_channels.size() == 1) {
        channel = mentioned_channels.get(0)
    } else if (args.length == 1) {
        channel = jda.getTextChannelById(args[0])
    } else {
        return cmd.reply('Usage: setchannel <ChannelMention/ChannelId>')
    }

    if (channel) {
        channelId = channel.getId()
        config.set('channel-id', channelId)
        config.save()
        cmd.reply('Channel set to ' + channel.getAsMention())
    } else {
        cmd.reply('Cannot find channel.')
    }
})

// command handler for 'csreload'
addon.on('command: csreload', function(cmd, args) {
    if (!util.hasPermission(cmd, 'MANAGE_CHANNEL')) {
        return cmd.reply('You do not have permission to perform this action.')
    }

    ready(bot) // this will re-set the variables and load the config again

    cmd.reply('Configuration reloaded.')
})

// exports the created addon to spicord, so it will be registered :)
// this line is obligatory, or else spicord cant get the addon instance
// and will throw an error
module.exports = addon
