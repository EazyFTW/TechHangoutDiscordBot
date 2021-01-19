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

public class StopCommand extends CommandModule {

    private final DefinedQuery<Role> STAFF_ROLE = new DefinedQuery<Role>() {
        @Override
        protected Query<Role> newQuery() {
            return bot.getRoles("Staff");
        }
    };

    public StopCommand(TechHangoutBot bot) { super(bot); }

    @Override
    public String getCommand() { return "!stop"; }

    @Override
    public String[] getAliases() { return null; }

    @Override
    public DefinedQuery<Role> getRestrictedRoles() { return STAFF_ROLE; }

    @Override
    public DefinedQuery<TextChannel> getRestrictedChannels() { return null; }

    @Override
    public CommandCategory getCategory() { return CommandCategory.ADMIN; }

    @Override
    public int getCooldown() {
        return 0;
    }

    @Override
    public void onCommand(TextChannel channel, Message message, Member member, String[] args) {
        if(args.length == 2 && args[0].equals("acTualLy") && args[1].equals("sToP")) {
            new TechEmbedBuilder("Stop")
                .setText("The bot will now stop!")
                .send(channel);

            TechHangoutBot.getJDA().shutdownNow();
            System.exit(0);
        } else {
            new TechEmbedBuilder("Stop")
                    .setText("Hello, " + member.getAsMention() + "! I've detected that you're trying to stop me!" +
                            "\n\nI do not like that, especially that if I do stop, I will not be restarted!\nIf you **REALLY** wish to stop me, type the following command:\n`!stop acTualLy sToP`")
                    .send(channel);
        }
    }
}
