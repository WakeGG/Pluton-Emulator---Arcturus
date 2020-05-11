package com.eu.habbo.habbohotel.modtool;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.messenger.Message;
import com.eu.habbo.habbohotel.rooms.RoomChatMessage;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.outgoing.friends.FriendChatMessageComposer;
import com.eu.habbo.messages.outgoing.generic.alerts.BubbleAlertComposer;
import com.eu.habbo.plugin.events.users.UserTriggerWordFilterEvent;
import gnu.trove.iterator.hash.TObjectHashIterator;
import gnu.trove.map.hash.THashMap;
import gnu.trove.set.hash.THashSet;
import org.apache.commons.lang3.StringUtils;
import com.eu.habbo.util.FilterUtil;
import com.eu.habbo.habbohotel.modtool.FilterSQL;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Normalizer;
import java.util.Map;
import java.util.regex.Pattern;

public class WordFilter {
   /* public static Pattern DIACRITICS_AND_FRIENDS = Pattern.compile("[\\p{InCombiningDiacriticalMarks}\\p{IsLm}\\p{IsSk}]+");
    //Configuration. Loaded from database & updated accordingly.
    public static boolean ENABLED_FRIENDCHAT = true;
    public static String DEFAULT_REPLACEMENT = "bobba";
    protected THashSet<WordFilterWord> autoReportWords = new THashSet<>();
    protected THashSet<WordFilterWord> hideMessageWords = new THashSet<>();
    protected THashSet<WordFilterWord> words = new THashSet<>();*/

    private Map<String, String> wordfilter;

    public WordFilter() {
        this.loadFilter();
    }

    public void loadFilter() {
        if (this.wordfilter != null) {
            this.wordfilter.clear();
        }

        this.wordfilter = FilterSQL.loadWordfilter();

        Logger.getLogger(WordFilter.class.getName()).info("Carregadas " + wordfilter.size() + " palavras no filtro");
    }

    public String filter(String message) {
        String filteredMessage = message;

        message = FilterUtil.process(message.toLowerCase());


        for (Map.Entry<String, String> word : wordfilter.entrySet()) {
            if (message.toLowerCase().contains(word.getKey()))
                //return new FilterResult(true, word.getKey());
            filteredMessage = filteredMessage.replace("(?i)" + word.getKey(), word.getValue());
        }

        return filteredMessage;
    }

    public Map<String, String> loadWordfilter() {
        this.wordfilter.clear();



        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet set = statement.executeQuery("SELECT * FROM guilds_elements")) {
            while (set.next()) {
                this.wordfilter.get(WordFilter.valueOf(set.getString("type").toUpperCase())).put(set.getInt("id"), new WordFilter(set));
            }
        } catch (SQLException e) {
            Emulator.getLogging().logSQLException(e);
        }
    }

   // public static String stripDiacritics(String str) {
    //    String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
     //   Pattern DIACRITICS_AND_FRIENDS = Pattern.compile("[\\p{InCombiningDiacriticalMarks}\\p{IsLm}\\p{IsSk}]+");
    //    return DIACRITICS_AND_FRIENDS.matcher(nfdNormalizedString).replaceAll("");
   // }

   /* private static String stripDiacritics(String str) {
        str = Normalizer.normalize(str, Normalizer.Form.NFD);
        str = DIACRITICS_AND_FRIENDS.matcher(str).replaceAll("");
        return str;
    }

    public synchronized void reload() {
        if (!Emulator.getConfig().getBoolean("hotel.wordfilter.enabled"))
            return;

        this.autoReportWords.clear();
        this.hideMessageWords.clear();
        this.words.clear();

        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection(); Statement statement = connection.createStatement()) {
            try (ResultSet set = statement.executeQuery("SELECT * FROM wordfilter")) {
                while (set.next()) {
                    WordFilterWord word;

                    try {
                        word = new WordFilterWord(set);
                    } catch (SQLException e) {
                        Emulator.getLogging().logSQLException(e);
                        continue;
                    }

                    if (word.autoReport)
                        this.autoReportWords.add(word);
                    else if (word.hideMessage)
                        this.hideMessageWords.add(word);

                    this.words.add(word);
                }
            }
        } catch (SQLException e) {
            Emulator.getLogging().logSQLException(e);
        }
    }

    public String normalise(String message) {
        return DIACRITICS_AND_FRIENDS.matcher(Normalizer.normalize(StringUtils.stripAccents(message), Normalizer.Form.NFKD)
                .replaceAll("[-?!çóéíÊ,_.; ´^~:'\"]", " ").replace("I", "l")
                .replaceAll("\\p{M}", " ")
                .replaceAll("^\\p{M}*$]", "").replaceAll("[1|]", "i")
                .replaceAll("[^a-zA-Z0-9 ]", "bobba")
                .replaceAll("ºª", "bobba")
                .replaceAll(": /[\\xE8-\\xEB&(?!amp;)]/g", "bobba")
                .replaceAll("Ð:D,µ:u,Ð:D,µ:u,ø:o,ð:o,Ø:O,ß:B,0:O,4:A,3:E,1:I,Ø:O,$:S,©:c,®:r,?:d,8:B,@:a,?:H,€:e", "bobba")
                .replace("2", "z").replace("3", "e")
                .replace("4", "a").replace("5", "s")
                .replace("8", "b").replace("0", "o")
                .replace(" ", "bobba").replace("$", "s")
                .replace("ß", "b").trim()).replaceAll("");

    }

    public boolean autoReportCheck(RoomChatMessage roomChatMessage) {
        String message = this.normalise(roomChatMessage.getMessage()).toLowerCase();

        TObjectHashIterator iterator = this.autoReportWords.iterator();

        while (iterator.hasNext()) {
            WordFilterWord word = (WordFilterWord) iterator.next();

            if (message.contains(word.key)) {
                Emulator.getGameEnvironment().getModToolManager().quickTicket(roomChatMessage.getHabbo(), "Filtro automático:", roomChatMessage.getMessage());

                Habbo habbo;
                for (Map.Entry<Integer, Habbo> map : Emulator.getGameEnvironment().getHabboManager().getOnlineHabbos().entrySet()) {
                    habbo = map.getValue();
                    if (habbo.getHabboInfo().getRank().getId() >= 6) {
                        THashMap<String, String> keys = new THashMap();
                        keys.put("display", "BUBBLE");
                        keys.put("image", "${image.library.url}notifications/erros.png");
                        keys.put("message", roomChatMessage.getHabbo() + " foi pego no filtro falando\r (" + roomChatMessage.getMessage() + ")");
                        Emulator.getGameServer().getGameClientManager().sendBroadcastResponse(new BubbleAlertComposer("", keys));
                    }
                }

                if (Emulator.getConfig().getBoolean("notify.staff.chat.auto.report")) {
                    Emulator.getGameEnvironment().getHabboManager().sendPacketToHabbosWithPermission(new FriendChatMessageComposer(new Message(roomChatMessage.getHabbo().getHabboInfo().getId(), 0, Emulator.getTexts().getValue("warning.auto.report").replace("%user%", roomChatMessage.getHabbo().getHabboInfo().getUsername()).replace("%word%", word.key))).compose(), "acc_staff_chat");
                }
                return true;
            }
        }

        return false;
    }

    public boolean hideMessageCheck(String message) {
        message = this.normalise(message).toLowerCase();

        TObjectHashIterator iterator = this.hideMessageWords.iterator();

        while (iterator.hasNext()) {
            WordFilterWord word = (WordFilterWord) iterator.next();

            if (message.contains(word.key)) {
                return true;
            }
        }

        return false;
    }

    public String[] filter(String[] messages) {
        for (int i = 0; i < messages.length; i++) {
            messages[i] = this.filter(messages[i], null);
        }

        return messages;
    }

    public String filter(String message, Habbo habbo) {
        String filteredMessage = message;
        if (Emulator.getConfig().getBoolean("hotel.wordfilter.normalise")) {
            filteredMessage = this.normalise(filteredMessage);
        }

        TObjectHashIterator iterator = this.words.iterator();

        boolean foundShit = false;

        while (iterator.hasNext()) {
            WordFilterWord word = (WordFilterWord) iterator.next();

            if (StringUtils.containsIgnoreCase(filteredMessage, word.key)) {
                if (habbo != null) {
                    if (Emulator.getPluginManager().fireEvent(new UserTriggerWordFilterEvent(habbo, word)).isCancelled())
                        continue;
                }
                filteredMessage = filteredMessage.replace("(?i)" + word.key, word.replacement);
                foundShit = true;

                if (habbo != null && word.muteTime > 0) {
                    habbo.mute(word.muteTime, false);
                }
            }
        }

        if (!foundShit) {
            return message;
        }

        return filteredMessage;
    }*/


    /*public WordFilter() {
        long start = System.currentTimeMillis();
        this.reload();
        Emulator.getLogging().logStart("Filtro carregado (" + (System.currentTimeMillis() - start) + " MS)");
    }


    public void filter(RoomChatMessage roomChatMessage, Habbo habbo) {
        String message = roomChatMessage.getMessage().toLowerCase();

        /*if (Emulator.getConfig().getBoolean("hotel.wordfilter.normalise")) {
            message = this.normalise(message);
        }

        TObjectHashIterator iterator = this.words.iterator();

        while (iterator.hasNext()) {
            WordFilterWord word = (WordFilterWord) iterator.next();

            if (StringUtils.containsIgnoreCase(message, word.key)) {
                if (habbo != null) {
                    if (Emulator.getPluginManager().fireEvent(new UserTriggerWordFilterEvent(habbo, word)).isCancelled())
                        continue;
                }

                message = message.replace(word.key, word.replacement);
                roomChatMessage.filtered = true;
            }
        }

        if (roomChatMessage.filtered) {
            roomChatMessage.setMessage(message);
        }
    }

    public void addWord(WordFilterWord word) {
        this.words.add(word);
    }*/
}
