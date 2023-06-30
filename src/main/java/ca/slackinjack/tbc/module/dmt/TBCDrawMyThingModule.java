package ca.slackinjack.tbc.module.dmt;

import ca.slackinjack.tbc.TBC;
import ca.slackinjack.tbc.server.Minigame;
import ca.slackinjack.tbc.server.MinigameModuleBase;
import ca.slackinjack.tbc.utils.packethandler.InboundPacketHandlerBase;
import net.minecraft.command.CommandBase;
import net.minecraftforge.fml.common.Mod;

@Mod(modid = TBCDrawMyThingModule.MODID, version = TBCDrawMyThingModule.VERSION, dependencies = TBCDrawMyThingModule.DEPENDENCIES, acceptedMinecraftVersions = TBCDrawMyThingModule.MCVERSION)
public class TBCDrawMyThingModule implements MinigameModuleBase {

    public static final String MODID = ">";
    public static final String VERSION = "1.0.1";
    public static final String DEPENDENCIES = "required-after:.@[3.0.2,)";
    public static final String MCVERSION = "1.8.9";

    private static TBCDrawMyThingModule MODULE_INSTANCE;
    private DrawMyThingInboundPacketHandler packetHandler;
    private DrawMyThingCommand command;
    private DrawMyThing DRAWMYTHING;

    public TBCDrawMyThingModule() {
        MODULE_INSTANCE = this;
    }

    @Override
    public MinigameModuleBase getModuleInstance() {
        return MODULE_INSTANCE;
    }

    @Override
    public Minigame getModuleMinigame(int dataIn) {
        if (DRAWMYTHING == null) {
            DRAWMYTHING = new DrawMyThing(TBC.getTBC(), MODULE_INSTANCE);
        }
        return DRAWMYTHING;
    }

    @Override
    public InboundPacketHandlerBase getInboundPacketHandler() {
        if (this.packetHandler == null) {
            this.packetHandler = new DrawMyThingInboundPacketHandler(TBC.getTBC(), DRAWMYTHING);
        }

        return this.packetHandler;
    }

    @Override
    public CommandBase getCommand() {
        if (this.command == null) {
            this.command = new DrawMyThingCommand(TBC.getTBC(), this, DRAWMYTHING);
        }

        return this.command;
    }
}
