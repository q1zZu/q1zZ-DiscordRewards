package me.q1zz.discordrewards.helper;

import lombok.experimental.UtilityClass;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import java.util.List;
import java.util.logging.Logger;

@UtilityClass
public class RoleHelper {

    public void addRoles(Guild guild, Member member, List<String> roles, Logger logger) {
        roles.forEach(roleID -> {
            Role role = guild.getJDA().getRoleById(roleID);
            if (role == null) {
                logger.severe("Cannot give role with id: " + roleID + " to user " + member.getUser().getAsTag() + " (" + member.getId() + ") because role is null!");
            } else if (!hasRole(member, role)) {
                if (!guild.getSelfMember().canInteract(role)) {
                    logger.severe("Cannot give role with id: " + roleID + " to user " + member.getUser().getAsTag() + " (" + member.getId() + ") because role is higher than the bot role.");
                } else {
                    guild.addRoleToMember(member.getUser(), role).queue();
                }
            }
        });
    }

    public boolean hasRole(Member member, Role role) {
        return member.getRoles().contains(role);
    }

}
