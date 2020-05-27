const Permission = require('class:net.dv8tion.jda.core.Permission')
const Embed      = require('class:eu.mcdb.spicord.embed.Embed')

module.exports = {
    hasPermission: function(cmd, perm) {
        perm = perm.toUpperCase()
        perm = Permission.valueOf(perm)
        if (perm) {
            return cmd.getMember().hasPermission(perm)
        }
        return false;
    },
    embed: function(obj) {
        return Embed.fromJson(obj)
    }
}
