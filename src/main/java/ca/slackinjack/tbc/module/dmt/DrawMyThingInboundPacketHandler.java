package ca.slackinjack.tbc.module.dmt;

import ca.slackinjack.tbc.TBC;
import ca.slackinjack.tbc.server.mineplex.Mineplex;
import ca.slackinjack.tbc.server.mineplex.MineplexInboundPacketHandler;
import ca.slackinjack.tbc.utils.packethandler.PacketEnum;
import static ca.slackinjack.tbc.utils.packethandler.PacketEnum.S02;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S02PacketChat;

public class DrawMyThingInboundPacketHandler extends MineplexInboundPacketHandler {

    private final TBC INSTANCE;
    private final DrawMyThing DRAWMYTHING;
    private final Minecraft MINECRAFT = Minecraft.getMinecraft();

    public DrawMyThingInboundPacketHandler(TBC tbcIn, DrawMyThing msIn) {
        super(tbcIn, (Mineplex) tbcIn.getUtilsPublic().getCurrentServer());
        INSTANCE = tbcIn;
        DRAWMYTHING = msIn;
    }

    @Override
    public boolean processPacket(Packet thePacket) {
        PacketEnum packetType = null;

        for (PacketEnum p : PacketEnum.values()) {
            if (p.getPacketClass() == thePacket.getClass()) {
                packetType = p;
                break;
            }
        }

        if (packetType != null) {
            switch (packetType) {
                case S02:
                    if (((S02PacketChat) thePacket).getType() == 2) {
                        //_ _ _ m _ _§e§l   HINT   §r12.7 Seconds
                        //_ k _ p _§e§l   HINT   §rPermanent
                        //_ _ _ _   _ _ _ _§e§l   HINT   §r60.0 Seconds

                        String theFormattedMessage = ((S02PacketChat) thePacket).getChatComponent().getUnformattedText();
                        if (theFormattedMessage.contains("HINT") && theFormattedMessage.contains("_ ") && theFormattedMessage.contains("\u00a7e\u00a7l") && !theFormattedMessage.contains("Permanent")) {
                            String hintText = theFormattedMessage.split("\u00a7e\u00a7l")[0];
                            String outputHintString = "";

                            for (int i = 0; i < hintText.length(); i++) {
                                if (i % 2 == 0) {
                                    outputHintString = outputHintString + Character.toString(hintText.charAt(i));
                                }
                            }

                            DRAWMYTHING.processHint(outputHintString);
                        }
                    } else {
                        String theUnformattedMessage = INSTANCE.getUtilsPublic().getUnstylizedText(((S02PacketChat) thePacket).getChatComponent().getUnformattedText());
                        //Round 1: TigerofRussia is drawing!
                        //+3 olayolayolayolay has guessed the word!
                        if (theUnformattedMessage.startsWith("Round ") && theUnformattedMessage.contains(": ") && theUnformattedMessage.contains(" is drawing!")) {
                            // check player
                            String playerName = theUnformattedMessage.split("  is drawing!")[0];
                            playerName = playerName.split(": ")[1];
                            if (playerName.toLowerCase().compareTo(MINECRAFT.thePlayer.getGameProfile().getName().toLowerCase()) != 0) {
                                // start
                                DRAWMYTHING.setCanAutomaticallyGuess(true);
                            } else {
                                // we are drawing
                                DRAWMYTHING.setCanAutomaticallyGuess(false);
                            }
                        } else if (theUnformattedMessage.startsWith("+") && theUnformattedMessage.contains(" has guessed the word!")) {
                            // check player
                            String playerName = theUnformattedMessage.split(" has guessed the word!")[0];
                            //+3 olayolayolayolay
                            playerName = playerName.split(" ")[1];
                            if (playerName.toLowerCase().compareTo(MINECRAFT.thePlayer.getGameProfile().getName().toLowerCase()) == 0) {
                                // stop
                                DRAWMYTHING.setCanAutomaticallyGuess(false);
                            }
                        }
                    }

                    break;
            }
        }

        return super.processPacket(thePacket);
    }
}
