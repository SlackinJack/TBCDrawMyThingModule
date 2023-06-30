package ca.slackinjack.tbc.module.dmt;

import ca.slackinjack.tbc.TBC;
import ca.slackinjack.tbc.utils.TBCExternalModulesEnum;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class DrawMyThingCommand extends CommandBase {

    private final TBC INSTANCE;
    private final TBCDrawMyThingModule MODULE_INSTANCE;
    private final DrawMyThing DRAWMYTHING;
    private final Minecraft MINECRAFT = Minecraft.getMinecraft();
    private final String usageText = "Usage: /dmt hint";

    public DrawMyThingCommand(TBC tbcIn, TBCDrawMyThingModule modIn, DrawMyThing dmtIn) {
        INSTANCE = tbcIn;
        MODULE_INSTANCE = modIn;
        DRAWMYTHING = dmtIn;
    }

    @Override
    public String getCommandName() {
        return TBCExternalModulesEnum.getCommandNameFor(TBCExternalModulesEnum.DRAWMYTHING);
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        switch (args.length) {
            case 1:
                if (args[0].equals("hint")) {
                    DRAWMYTHING.outputHints();
                }
                break;
            default:
                INSTANCE.getUtilsPublic().addUnformattedChatMessage(this.usageText, 1);
                break;
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }
}
