package com.eazyftw.techhangout.module.cmds;

import com.eazyftw.techhangout.TechHangoutBot;
import com.eazyftw.techhangout.module.CommandCategory;
import com.eazyftw.techhangout.module.CommandModule;
import com.eazyftw.techhangout.objects.DefinedQuery;
import com.eazyftw.techhangout.objects.Query;
import com.eazyftw.techhangout.util.TechEmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;

public class PurgeCommand extends CommandModule {

    private final DefinedQuery<Role> STAFF_ROLE = new DefinedQuery<Role>() {
        @Override
        protected Query<Role> newQuery() {
            return bot.getRoles("Staff");
        }
    };

    public PurgeCommand(TechHangoutBot bot) { super(bot); }

    @Override
    public String getCommand() { return "!purge"; }

    @Override
    public String[] getAliases() { return new String[]{"!p"}; }

    @Override
    public DefinedQuery<Role> getRestrictedRoles() { return STAFF_ROLE; }

    @Override
    public DefinedQuery<TextChannel> getRestrictedChannels() { return null; }

    @Override public CommandCategory getCategory() { return null; }

    @Override
    public int getCooldown() {
        return 0;
    }

    @Override
    public void onCommand(TextChannel channel, Message message, Member member, String[] args) {
        if(args.length == 0) {
            new TechEmbedBuilder("Purge Cmd - Error")
                    .setText("Please specify an amount to purge (2-100)")
                    .error()
                    .sendTemporary(channel, 5);
        } else {
            int amount;

            try {
                amount = Integer.parseInt(args[0]);
            } catch (NumberFormatException ex) {
                new TechEmbedBuilder("Purge Cmd - Error")
                        .setText("Could not get a integer from the value '" + args[0] + "'")
                        .error()
                        .sendTemporary(channel, 5);
                return;
            }

            if(amount < 2 || amount > 100) {
                new TechEmbedBuilder("Purge Cmd - Error")
                        .setText("The int has to be between (2-100), " + amount + " is not in that range!")
                        .error()
                        .sendTemporary(channel, 5);
                return;
            }

            channel.getHistory().retrievePast(amount).queue(success -> success.forEach(msg -> msg.delete().queue()));

            new TechEmbedBuilder("Pure Cmd - Success")
                    .setText("Successfully purged " + amount + " messages!")
                    .success()
                    .sendTemporary(channel, 5);
        }
    }
}