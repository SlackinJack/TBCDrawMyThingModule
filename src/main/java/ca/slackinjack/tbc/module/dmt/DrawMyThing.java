package ca.slackinjack.tbc.module.dmt;

import ca.slackinjack.tbc.TBC;
import ca.slackinjack.tbc.server.Minigame;
import java.util.ArrayList;
import java.util.List;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class DrawMyThing extends Minigame {

    private final TBCDrawMyThingModule MODULE_INSTANCE;
    private final List<String> currentSuggestions = new ArrayList();
    private String lastHintText = "";

    private boolean shouldAutomaticallyGuess = true;
    private boolean hintUpdatedForAutomaticGuessing = false;
    private boolean canAutomaticallyGuess = false;
    private int timeSinceLastAutomaticGuess = 0;
    private final int delayBeforeStartingGuesses = 120;
    private int startDelay = 0;

    private boolean shouldAutomaticallyPostNewHints = false;

    public DrawMyThing(TBC tbcIn, TBCDrawMyThingModule modIn) {
        super(tbcIn);
        MODULE_INSTANCE = modIn;
    }

    public void processHint(String hintTextIn) {
        hintTextIn = hintTextIn.toLowerCase();

        if (hintTextIn.compareTo(this.lastHintText) != 0) {
            this.lastHintText = hintTextIn;
            this.hintUpdatedForAutomaticGuessing = false;

            boolean multiWord = hintTextIn.contains(" ");

            List<String> wordListToUse = this.getWordListWithLength(hintTextIn.length());
            List<String> hintedWordCandidates = new ArrayList();
            for (String s : wordListToUse) {
                if ((!multiWord && s.contains(" ")) || (multiWord && !s.contains(" "))) {
                    continue;
                }

                boolean verifiedCandidate = true;

                for (int i = 0; i < s.length(); i++) {
                    if (hintTextIn.charAt(i) != (("_".charAt(0)))) {
                        if (s.charAt(i) != hintTextIn.charAt(i)) {
                            verifiedCandidate = false;
                            break;
                        }
                    }
                }

                if (verifiedCandidate) {
                    hintedWordCandidates.add(s);
                }
            }

            this.currentSuggestions.clear();

            for (String s : hintedWordCandidates) {
                this.currentSuggestions.add(s);
            }

            if (this.shouldAutomaticallyGuess) {
                this.hintUpdatedForAutomaticGuessing = true;
            }

            if (this.shouldAutomaticallyPostNewHints) {
                this.outputHints();
            }
        }
    }

    private String[] getWordList() {
        // firework
        return new String[]{"olympics", "crafting table", "ferris wheel", "parachute", "mars", "canoe", "palm tree", "acorn", "airplane", "alarm clock", "alcohol", "alien", "alligator", "america", "anchor", "angel", "ankle", "ant", "ants", "anvil", "apple", "arcade machine", "archer", "arm", "armor", "astronaut", "australia", "autumn", "axe", "baby", "bacon", "bag", "bagel", "baggage", "ball", "balloon", "balloons", "bamboo", "banana", "bank", "barbie", "barn", "base", "baseball", "basketball", "basketball court", "bat", "bathroom", "batman", "battery", "battleship", "beach", "beans", "bear", "beard", "beaver", "bed", "beehive", "beer", "bell", "bells", "belly button", "bench", "berry", "bicycle", "bike", "bikini", "binoculars", "bird", "birthday cake", "blanket", "blocks", "boar", "boat", "bomb", "bone", "booger", "book", "bookcase", "bookstore", "boots", "booty", "bottle", "bow", "bowl", "bowtie", "box", "boy", "bracelet", "brain", "branch", "bread", "bridge", "broomstick", "brush", "bubble", "bucket", "bug", "building", "bumblebee", "bunny", "burger", "burn", "bus", "butterfly", "button", "cabin", "cactus", "cage", "cake", "calculator", "calendar", "camel", "camera", "camp fire", "campfire", "canada", "candle", "candy", "candy cane", "cannibal", "cap", "cape", "capture", "car", "card", "carrot", "castle", "cat", "cauldron", "cave", "cd", "cereal", "chair", "chalk", "chard", "charmander", "cheek", "cheese", "cheeseburger", "chef", "cherry", "chess", "chest", "chestnut", "chestplate", "chicken", "children", "chimney", "chin", "chocolate", "christmas", "christmas tree", "cigar", "cinema", "circle", "circus", "clock", "cloud", "clown", "coal", "coat", "coconut", "coffee", "cold", "comet", "compass", "computer", "cone", "confused", "construction", "cookie", "cookies", "cork", "corn", "cort", "couch", "cow", "crab", "crack", "crayon", "creeper", "crib", "crowd", "crown", "crying", "cup", "cupcake", "cyclop", "cyclops", "dance", "darts", "decorations", "desert", "desk", "diamond", "diamonds", "diary", "dice", "dinosaur", "disco", "dog", "doll", "dolphin", "domino", "dominoes", "donut", "door", "door knob", "doormat", "dragon", "draw my thing", "dream", "dress", "drill", "drink", "drool", "drum", "drums", "duck", "eagle", "ear", "ears", "earth", "egg", "eggnog", "eggs", "elbow", "electricity", "elephant", "elevator", "elf", "emerald", "emeralds", "ender dragon", "eraser", "erupt", "explosion", "eye", "eyepatch", "eyes", "face", "facebook", "fall", "family", "farm", "fat", "father", "fighting", "filing", "finger", "fire", "fireplace", "fishing", "fishing pole", "fishing rod", "fist", "flag", "flamingo", "flashlight", "flint", "flower", "flowers", "flute", "fly", "food", "football", "forest", "fountain", "frenchfries", "fridge", "fries", "frisbee", "frog", "frozen", "galaxy", "gapples", "garbage", "garden", "gate", "ghast", "ghost", "giant", "gift", "gingerbread", "gingerbread man", "giraffe", "girl", "glacier", "glasses", "godzilla", "gold", "goldfish", "golem", "golf club", "grapes", "grass", "grave", "gravestone", "graveyard", "grim reaper", "grinch", "grocer", "guard", "guitar", "gun", "hair", "hair dryer", "halloween", "hammer", "hamster", "hand", "handcuffs", "hang", "harry potter", "hat", "head", "headphones", "heart", "hedgehog", "helicopter", "helmet", "hill", "hippo", "hobo", "hockey", "holding hands", "hook", "hopscotch", "horn", "horse", "horse riding", "hospital", "hot air balloon", "hot chocolate", "hotdog", "house", "hula hoop", "ice", "ice cream", "ice hockey", "ice skating", "iceberg", "icecream", "icicle", "igloo", "ipod", "iron ore", "jacket", "jam", "james bond", "jar", "jellyfish", "jingle bells", "joker", "juggle", "jump", "jungle", "jungle kitten", "kangaroo", "key", "keyboard", "keys", "king", "kirby", "kiss", "kitchen", "kite", "kitten", "knot", "ladybug", "lake", "lamb", "lamp", "lance", "laptop", "lasagna", "lava", "lawnmower", "leaf", "leash", "letter", "letterbox", "light", "lightbulb", "lighthouse", "lightning", "lightsaber", "lion", "lips", "lipstick", "lizard", "llama", "lobster", "lollipop", "love", "luigi", "magic", "magnet", "mail", "mailman", "mansion", "mario", "math", "mattress", "melon", "mickey", "microsoft", "milk", "minecart", "minecraft", "mineplex", "miner", "mistletoe", "mittens", "money", "monitor", "monkey", "moon", "moose", "mosquito", "mother", "motorbike", "motorcycle", "mountain", "mountain bike", "mouse", "mouth", "movie", "mudkip", "muffin", "muscles", "mushroom", "music", "nail", "neck", "necklace", "nether", "nether portal", "newspaper", "night", "night time", "nightmare", "ninja", "nintendo", "noodles", "north pole", "nose", "notebook", "nutcracker", "obsidian", "ocean", "octagon", "orange", "ornament", "owl", "paint", "painting", "pajamas", "palace", "pancake", "panda", "pants", "paper", "paper clip", "park", "party", "peach", "peanut", "peasant", "pen", "pencil", "penguin", "pepsi", "person", "pewdiepie", "phone", "photo", "photograph", "piano", "pickle", "picnic", "pie", "pig", "pikachu", "pillow", "pineapple", "pinecone", "ping pong", "pinwheel", "pirate", "pistol", "piston", "pizza", "plane", "planet", "plant", "plate", "playstation", "plumber", "pokeball", "pokemon", "police", "pool", "pool party", "poop", "popcorn", "popsicle", "portal", "pot of gold", "potato", "pregnant", "present", "presenter", "pretzel", "prince", "princess", "prison", "prize", "pull", "pumpkin", "pumpkin pie", "punch", "puncture", "puppet", "puppy", "purse", "push", "pyramid", "queen", "quick", "rabbit", "racecar", "radar", "radio", "rain", "rainbow", "raspberry", "redstone", "refrigerator", "reindeer", "remote", "restaurant", "rhinoceros", "rice", "rifle", "ring", "river", "road", "robot", "rocket", "rocking chair", "roll", "roof", "rope", "rose", "round", "royal", "rubbish", "rug", "ruins", "ruler", "saddle", "sailboat", "salad", "salsa", "salt and pepper", "sandwich", "santa", "santa hat", "saturn", "scale", "scarf", "school", "scissors", "scooby doo", "screw", "scrooge", "sea", "seahorse", "seashell", "seesaw", "shark", "sheep", "shell", "shield", "shirt", "shoe", "shoe lace", "shopping cart", "shorts", "shotgun", "shout", "shovel", "sideburns", "sidewalk", "skate", "skateboard", "skeleton", "ski", "skiing", "skinny", "skirt", "skull", "skunk", "skype", "sled", "sleeping", "sleigh", "slide", "slime", "slippers", "sloth", "smile", "snail", "snake", "snorlax", "snow fight", "snow fort", "snowball", "snowball fight", "snowboard", "snowflake", "snowman", "soap", "sock", "socks", "soda", "song", "soup", "spaceship", "spaghetti", "speaker", "speedboat", "spider", "spider man", "spider web", "spikes", "sponge", "spongebob", "spoon", "spray", "spring", "sprout", "squid", "squirrel", "stable", "stage", "stain", "staircase", "stairs", "stamp", "star", "stars", "state", "statue", "stingray", "stocking", "stomach", "stool", "stop sign", "stoplight", "storm", "strawberry", "stump", "sugar", "suitcase", "summer", "sun", "sunflower", "sunglasses", "sunrise", "sunset", "superman", "sushi", "sweater", "swimming", "swimming pool", "swing", "swordfish", "syrup", "t-series", "table", "taco", "tail", "tank", "tape", "taxi", "teapot", "tears", "technology", "teddy", "telephone", "telescope", "television", "temple", "tennis", "tennis racket", "tent", "tetris", "thief", "thorn", "throne", "thumb", "tiger", "time", "tiny", "toast", "toaster", "toilet", "tomato", "tooth", "toothbrush", "top hat", "torch", "tornado", "towel", "tower", "toys", "traffic lights", "trash", "treasure", "tree", "treehouse", "trick", "trick or treat", "troll face", "truck", "trumpet", "turtle", "tv", "twitter", "umbrella", "unicorn", "united states", "uppercut", "usb", "vest", "video game", "villager", "violin", "volcano", "vomit", "wagon", "waist", "wallet", "washing machine", "watch", "water", "water gun", "waterfall", "watering can", "watermelon", "whale", "wheat", "whisk", "whistle", "wind", "windmill", "window", "winter", "witch", "wither", "wizard", "wolf", "worm", "wrench", "xbox", "yawn", "yelling", "yo-yo", "yoshi", "youtube", "zebra", "zipper", "zombie", "zombie pigman", "zoo"};
    }

    private List<String> getWordListWithLength(int numberOfLettersIn) {
        List<String> wordCandidates = new ArrayList();

        for (String s : this.getWordList()) {
            if (s.length() == numberOfLettersIn) {
                wordCandidates.add(s);
            }
        }

        return wordCandidates;
    }

    @Override
    public void onClientTickEvent(TickEvent.ClientTickEvent e) {
        if (this.canAutomaticallyGuess && this.hintUpdatedForAutomaticGuessing) {
            if (this.startDelay >= this.delayBeforeStartingGuesses) {
                this.timeSinceLastAutomaticGuess++;
                if (this.timeSinceLastAutomaticGuess % 60 == 0) {
                    this.timeSinceLastAutomaticGuess = 0;
                    if (!MINECRAFT.thePlayer.capabilities.allowFlying) {
                        if (this.getGuessingWordList().size() > 0) {
                            String wordToTry = this.getGuessingWordList().get(0);
                            MINECRAFT.thePlayer.sendChatMessage("@" + wordToTry);
                            INSTANCE.getUtilsPublic().addUnformattedChatMessage("Sending word: " + wordToTry, 2);
                            this.currentSuggestions.remove(wordToTry);
                        }
                    }
                }
            } else {
                this.startDelay++;
            }
        }
    }

    public List<String> getGuessingWordList() {
        return this.currentSuggestions;
    }

    public void outputHints() {
        if (this.getGuessingWordList().size() > 0) {
            if (this.getGuessingWordList().size() < 30) {
                String outputText = "";
                for (String s : this.getGuessingWordList()) {
                    outputText += (s + ", ");
                }
                INSTANCE.getUtilsPublic().addUnformattedChatMessage("Possible words: " + outputText, 2);
            } else {
                INSTANCE.getUtilsPublic().addUnformattedChatMessage("Too many possible words.", 1);
            }
        } else {
            INSTANCE.getUtilsPublic().addUnformattedChatMessage("No hints are available.", 1);
        }
    }

    public void setCanAutomaticallyGuess(boolean enabled) {
        this.canAutomaticallyGuess = enabled;

        if (!enabled) {
            this.startDelay = 0;
        }
    }
}
