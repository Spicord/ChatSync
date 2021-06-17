const Permission = require('class:net.dv8tion.jda.api.Permission')
const Embed      = require('class:org.spicord.embed.Embed')

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
