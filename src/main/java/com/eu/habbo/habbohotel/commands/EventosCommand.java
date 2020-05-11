package com.eu.habbo.habbohotel.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
import com.eu.habbo.habbohotel.rooms.RoomState;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.generic.alerts.BubbleAlertComposer;
import com.eu.habbo.messages.outgoing.generic.alerts.HabboEpicPopupViewMessageComposer;
import com.eu.habbo.messages.outgoing.habboway.nux.NuxAlertComposer;
import com.eu.habbo.messages.outgoing.modtool.ModToolIssueHandledComposer;
import com.eu.habbo.messages.outgoing.rooms.RoomSettingsUpdatedComposer;
import gnu.trove.map.hash.THashMap;

public class EventosCommand extends Command {
    public EventosCommand() {
        super("cmd_alert", Emulator.getTexts().getValue("commands.keys.eventos").split(";"));
    }

    public boolean handle(GameClient gc, String[] params) throws Exception {

        if (params.length < 2) {
                gc.getHabbo().getClient().sendResponse(new NuxAlertComposer("habbopages/ev-explicacoes.php"));
            return true;
        }
        if (params.length > 0) {
            if (params[1].equalsIgnoreCase("sussurro")) {
                THashMap<String, String> codes = new THashMap<>();
                codes.put("display", "POP_UP");
                codes.put("image", "${image.library.url}notifications/evento_exp.png");
                codes.put("message", "Quando o MODerador der o sinal, vocês devem sussurrar ao MODerador o nick de um usuário do quarto. O primeiro a ser falado será expulso, até sobrar apenas um jogador.\n");
                codes.put("title", "Sussurro");
                ServerMessage msg = new BubbleAlertComposer("haibbo.explica", codes).compose();
                Room room = gc.getHabbo().getHabboInfo().getCurrentRoom();
                gc.getHabbo().getHabboInfo().getCurrentRoom().setState(RoomState.LOCKED);
                gc.getHabbo().getHabboInfo().getCurrentRoom().sendComposer((new RoomSettingsUpdatedComposer(gc.getHabbo().getHabboInfo().getCurrentRoom())).compose());
                THashMap<String, String> keys = new THashMap();
                keys.put("display", "BUBBLE");
                keys.put("image", "${image.library.url}notifications/quarto_fechado.png");
                keys.put("message", "Este quarto foi fechado, pois o evento foi iniciado");
                if (room != null) {
                    room.sendComposer(msg);
                    room.sendComposer((new BubbleAlertComposer("", keys)).compose());
                    return true;
                }}}


        if (params.length > 0) {
            if (params[1].equalsIgnoreCase("acheopato")) {
                THashMap<String, String> codes = new THashMap<>();
                codes.put("display", "POP_UP");
                codes.put("image", "${image.library.url}notifications/evento_exp.png");
                codes.put("message", "Ao chegar no quarto o moderador irá perguntar onde estará o pato escondido. Voce tera que escolher entre as opções 1, 2 e 3, e se o pato estiver na sua opção escolhida voce ganhará! Caso perca voce é kikado.\n");
                codes.put("title", "Ache o pato");
                ServerMessage msg = new BubbleAlertComposer("haibbo.explica", codes).compose();
                Room room = gc.getHabbo().getHabboInfo().getCurrentRoom();
                gc.getHabbo().getHabboInfo().getCurrentRoom().setState(RoomState.LOCKED);
                gc.getHabbo().getHabboInfo().getCurrentRoom().sendComposer((new RoomSettingsUpdatedComposer(gc.getHabbo().getHabboInfo().getCurrentRoom())).compose());
                THashMap<String, String> keys = new THashMap();
                keys.put("display", "BUBBLE");
                keys.put("image", "${image.library.url}notifications/quarto_fechado.png");
                keys.put("message", "Este quarto foi fechado, pois o evento foi iniciado");
                if (room != null) {
                    room.sendComposer(msg);
                    room.sendComposer((new BubbleAlertComposer("", keys)).compose());
                    return true;
                }}}

        if (params.length > 0) {
            if (params[1].equalsIgnoreCase("bingo")) {
                THashMap<String, String> codes = new THashMap<>();
                codes.put("display", "POP_UP");
                codes.put("image", "${image.library.url}notifications/evento_exp.png");
                codes.put("message", "Todos sentados irão esperar o moderador girar os seus 3 holodices e quando ele der o sinal de 'JÁ' voces terão que girar os seus holodices até ficarem todos na mesma ordem de numeros igual aos holos do moderador. O primeiro que conseguir os tres numeros CORRETAMENTE fala 'BINGO' e tem direito de kikar 1/2 users.\n");
                codes.put("title", "Bingo");
                ServerMessage msg = new BubbleAlertComposer("haibbo.explica", codes).compose();
                Room room = gc.getHabbo().getHabboInfo().getCurrentRoom();
                gc.getHabbo().getHabboInfo().getCurrentRoom().setState(RoomState.LOCKED);
                gc.getHabbo().getHabboInfo().getCurrentRoom().sendComposer((new RoomSettingsUpdatedComposer(gc.getHabbo().getHabboInfo().getCurrentRoom())).compose());
                THashMap<String, String> keys = new THashMap();
                keys.put("display", "BUBBLE");
                keys.put("image", "${image.library.url}notifications/quarto_fechado.png");
                keys.put("message", "Este quarto foi fechado, pois o evento foi iniciado");
                if (room != null) {
                    room.sendComposer(msg);
                    room.sendComposer((new BubbleAlertComposer("", keys)).compose());
                    return true;
                }}}

        if (params.length > 0) {
            if (params[1].equalsIgnoreCase("caracolbanzai")) {
                THashMap<String, String> codes = new THashMap<>();
                codes.put("display", "POP_UP");
                codes.put("image", "${image.library.url}notifications/evento_exp.png");
                codes.put("message", "Após o moderador dar o sinal, voce deverá correr até ao centro do quarto, porém vários teles banzai estarão espalhados por todo o quarto. Ou seja, ganha quem for o mais rápido e mais sortudo a chegar primeiro ao final! Boa sorte.\n");
                codes.put("title", "Caracol Banzai");
                ServerMessage msg = new BubbleAlertComposer("haibbo.explica", codes).compose();
                Room room = gc.getHabbo().getHabboInfo().getCurrentRoom();
                gc.getHabbo().getHabboInfo().getCurrentRoom().setState(RoomState.LOCKED);
                gc.getHabbo().getHabboInfo().getCurrentRoom().sendComposer((new RoomSettingsUpdatedComposer(gc.getHabbo().getHabboInfo().getCurrentRoom())).compose());
                THashMap<String, String> keys = new THashMap();
                keys.put("display", "BUBBLE");
                keys.put("image", "${image.library.url}notifications/quarto_fechado.png");
                keys.put("message", "Este quarto foi fechado, pois o evento foi iniciado");
                if (room != null) {
                    room.sendComposer(msg);
                    room.sendComposer((new BubbleAlertComposer("", keys)).compose());
                    return true;
                }}}

        if (params.length > 0) {
            if (params[1].equalsIgnoreCase("caracol")) {
                THashMap<String, String> codes = new THashMap<>();
                codes.put("display", "POP_UP");
                codes.put("image", "${image.library.url}notifications/evento_exp.png");
                codes.put("message", "O moderador irá dar o sinal de inicio, e o seu objetivo é correr até ao centro do quarto. O primeiro a chegar ganha.\n");
                codes.put("title", "Caracol");
                ServerMessage msg = new BubbleAlertComposer("haibbo.explica", codes).compose();
                Room room = gc.getHabbo().getHabboInfo().getCurrentRoom();
                gc.getHabbo().getHabboInfo().getCurrentRoom().setState(RoomState.LOCKED);
                gc.getHabbo().getHabboInfo().getCurrentRoom().sendComposer((new RoomSettingsUpdatedComposer(gc.getHabbo().getHabboInfo().getCurrentRoom())).compose());
                THashMap<String, String> keys = new THashMap();
                keys.put("display", "BUBBLE");
                keys.put("image", "${image.library.url}notifications/quarto_fechado.png");
                keys.put("message", "Este quarto foi fechado, pois o evento foi iniciado");
                if (room != null) {
                    room.sendComposer(msg);
                    room.sendComposer((new BubbleAlertComposer("", keys)).compose());
                    return true;
                }}}

        if (params.length > 0) {
            if (params[1].equalsIgnoreCase("ceuvsinferno")) {
                THashMap<String, String> codes = new THashMap<>();
                codes.put("display", "POP_UP");
                codes.put("image", "${image.library.url}notifications/evento_exp.png");
                codes.put("message", "Quando o moderador der o sinal todos devem entrar, e automaticamente cairão no inferno ou no céu, e quem cair no céu seguirá pra a próxima rodada enquanto os que cairam no inferno serão kikados. Boa sorte!\n");
                codes.put("title", "Céu vs Inferno");
                ServerMessage msg = new BubbleAlertComposer("haibbo.explica", codes).compose();
                Room room = gc.getHabbo().getHabboInfo().getCurrentRoom();
                gc.getHabbo().getHabboInfo().getCurrentRoom().setState(RoomState.LOCKED);
                gc.getHabbo().getHabboInfo().getCurrentRoom().sendComposer((new RoomSettingsUpdatedComposer(gc.getHabbo().getHabboInfo().getCurrentRoom())).compose());
                THashMap<String, String> keys = new THashMap();
                keys.put("display", "BUBBLE");
                keys.put("image", "${image.library.url}notifications/quarto_fechado.png");
                keys.put("message", "Este quarto foi fechado, pois o evento foi iniciado");
                if (room != null) {
                    room.sendComposer(msg);
                    room.sendComposer((new BubbleAlertComposer("", keys)).compose());
                    return true;
                }}}

        if (params.length > 0) {
            if (params[1].equalsIgnoreCase("corcerta")) {
                THashMap<String, String> codes = new THashMap<>();
                codes.put("display", "POP_UP");
                codes.put("image", "${image.library.url}notifications/evento_exp.png");
                codes.put("message", "Ao entrar no quarto escolha uma das cores e vá até a cor escolhida.\n" +
                        "Após isso o moderador irá girar a roleta e a cor que cair for a cor em que voce estiver, voce perderá e será kikado.\n" +
                        "Assim o evento ocorrerá até que apenas reste uma cor e os users que estiverem nela ganharão.\n");
                codes.put("title", "Cor Certa");
                ServerMessage msg = new BubbleAlertComposer("haibbo.explica", codes).compose();
                Room room = gc.getHabbo().getHabboInfo().getCurrentRoom();
                gc.getHabbo().getHabboInfo().getCurrentRoom().setState(RoomState.LOCKED);
                gc.getHabbo().getHabboInfo().getCurrentRoom().sendComposer((new RoomSettingsUpdatedComposer(gc.getHabbo().getHabboInfo().getCurrentRoom())).compose());
                THashMap<String, String> keys = new THashMap();
                keys.put("display", "BUBBLE");
                keys.put("image", "${image.library.url}notifications/quarto_fechado.png");
                keys.put("message", "Este quarto foi fechado, pois o evento foi iniciado");
                if (room != null) {
                    room.sendComposer(msg);
                    room.sendComposer((new BubbleAlertComposer("", keys)).compose());
                    return true;
                }}}

        if (params.length > 0) {
            if (params[1].equalsIgnoreCase("corridao")) {
                THashMap<String, String> codes = new THashMap<>();
                codes.put("display", "POP_UP");
                codes.put("image", "${image.library.url}notifications/evento_exp.png");
                codes.put("message", "Após o moderador dar o sinal o seu objetivo é correr pelo percurso até chegar ao final.\n");
                codes.put("title", "Corridão");
                ServerMessage msg = new BubbleAlertComposer("haibbo.explica", codes).compose();
                Room room = gc.getHabbo().getHabboInfo().getCurrentRoom();
                gc.getHabbo().getHabboInfo().getCurrentRoom().setState(RoomState.LOCKED);
                gc.getHabbo().getHabboInfo().getCurrentRoom().sendComposer((new RoomSettingsUpdatedComposer(gc.getHabbo().getHabboInfo().getCurrentRoom())).compose());
                THashMap<String, String> keys = new THashMap();
                keys.put("display", "BUBBLE");
                keys.put("image", "${image.library.url}notifications/quarto_fechado.png");
                keys.put("message", "Este quarto foi fechado, pois o evento foi iniciado");
                if (room != null) {
                    room.sendComposer(msg);
                    room.sendComposer((new BubbleAlertComposer("", keys)).compose());
                    return true;
                }}}

        if (params.length > 0) {
            if (params[1].equalsIgnoreCase("dangermaluco")) {
                THashMap<String, String> codes = new THashMap<>();
                codes.put("display", "POP_UP");
                codes.put("image", "${image.library.url}notifications/evento_exp.png");
                codes.put("message", "Apos o moderador dar o sinal, corra até a bonnie do meio e faça o percurso até chegar as bonnies vermelha e branca. Bonnie vermelha em direito de kikar um usuário, já a bonnie branca deixa o usuário imune na rodada.\n");
                codes.put("title", "Danger Maluco");
                ServerMessage msg = new BubbleAlertComposer("haibbo.explica", codes).compose();
                Room room = gc.getHabbo().getHabboInfo().getCurrentRoom();
                gc.getHabbo().getHabboInfo().getCurrentRoom().setState(RoomState.LOCKED);
                gc.getHabbo().getHabboInfo().getCurrentRoom().sendComposer((new RoomSettingsUpdatedComposer(gc.getHabbo().getHabboInfo().getCurrentRoom())).compose());
                THashMap<String, String> keys = new THashMap();
                keys.put("display", "BUBBLE");
                keys.put("image", "${image.library.url}notifications/quarto_fechado.png");
                keys.put("message", "Este quarto foi fechado, pois o evento foi iniciado");
                if (room != null) {
                    room.sendComposer(msg);
                    room.sendComposer((new BubbleAlertComposer("", keys)).compose());
                    return true;
                }}}

        if (params.length > 0) {
            if (params[1].equalsIgnoreCase("defendasuabonnie")) {
                THashMap<String, String> codes = new THashMap<>();
                codes.put("display", "POP_UP");
                codes.put("image", "${image.library.url}notifications/evento_exp.png");
                codes.put("message", "O moderador irá mover a sua bonnie e você deverá defende-la para que ninguém sente nela, caso alguém senta você perderá,(OBS: Para roubar a bonnie do seu adversário você tem que sentar na bonnie do seu 'ALVO' e voltar para a sua).\n");
                codes.put("title", "Defenda sua Bonnie");
                ServerMessage msg = new BubbleAlertComposer("haibbo.explica", codes).compose();
                Room room = gc.getHabbo().getHabboInfo().getCurrentRoom();
                gc.getHabbo().getHabboInfo().getCurrentRoom().setState(RoomState.LOCKED);
                gc.getHabbo().getHabboInfo().getCurrentRoom().sendComposer((new RoomSettingsUpdatedComposer(gc.getHabbo().getHabboInfo().getCurrentRoom())).compose());
                THashMap<String, String> keys = new THashMap();
                keys.put("display", "BUBBLE");
                keys.put("image", "${image.library.url}notifications/quarto_fechado.png");
                keys.put("message", "Este quarto foi fechado, pois o evento foi iniciado");
                if (room != null) {
                    room.sendComposer(msg);
                    room.sendComposer((new BubbleAlertComposer("", keys)).compose());
                    return true;
                }}}

        if (params.length > 0) {
            if (params[1].equalsIgnoreCase("singular")) {
                THashMap<String, String> codes = new THashMap<>();
                codes.put("display", "POP_UP");
                codes.put("image", "${image.library.url}notifications/evento_exp.png");
                codes.put("message", "O MODerador irá dizer uma palavra no plural, exemplo: Casas, e vocês deverão passar a palavra para o singular, ou seja: casa o primeiro a fazer isso, tem direito a expulsar um ou mais usuários do evento.\n");
                codes.put("title", "Singular");
                ServerMessage msg = new BubbleAlertComposer("haibbo.explica", codes).compose();
                Room room = gc.getHabbo().getHabboInfo().getCurrentRoom();
                gc.getHabbo().getHabboInfo().getCurrentRoom().setState(RoomState.LOCKED);
                gc.getHabbo().getHabboInfo().getCurrentRoom().sendComposer((new RoomSettingsUpdatedComposer(gc.getHabbo().getHabboInfo().getCurrentRoom())).compose());
                THashMap<String, String> keys = new THashMap();
                keys.put("display", "BUBBLE");
                keys.put("image", "${image.library.url}notifications/quarto_fechado.png");
                keys.put("message", "Este quarto foi fechado, pois o evento foi iniciado");
                if (room != null) {
                    room.sendComposer(msg);
                    room.sendComposer((new BubbleAlertComposer("", keys)).compose());
                    return true;
                }}}

        if (params.length > 0) {
            if (params[1].equalsIgnoreCase("multiplicando")) {
                THashMap<String, String> codes = new THashMap<>();
                codes.put("display", "POP_UP");
                codes.put("image", "${image.library.url}notifications/evento_exp.png");
                codes.put("message", "O MODerador irá girar 3 dados, eles mostrarão 3 números, e você terá que multiplica-los.\n" +
                        "O primeiro a falar a resposta em direito a expulsar um usuário do evento.\n");
                codes.put("title", "multiplicando");
                ServerMessage msg = new BubbleAlertComposer("haibbo.explica", codes).compose();
                Room room = gc.getHabbo().getHabboInfo().getCurrentRoom();
                gc.getHabbo().getHabboInfo().getCurrentRoom().setState(RoomState.LOCKED);
                gc.getHabbo().getHabboInfo().getCurrentRoom().sendComposer((new RoomSettingsUpdatedComposer(gc.getHabbo().getHabboInfo().getCurrentRoom())).compose());
                THashMap<String, String> keys = new THashMap();
                keys.put("display", "BUBBLE");
                keys.put("image", "${image.library.url}notifications/quarto_fechado.png");
                keys.put("message", "Este quarto foi fechado, pois o evento foi iniciado");
                if (room != null) {
                    room.sendComposer(msg);
                    room.sendComposer((new BubbleAlertComposer("", keys)).compose());
                    return true;
                }}}

        if (params.length > 0) {
            if (params[1].equalsIgnoreCase("duplicandoletras")) {
                THashMap<String, String> codes = new THashMap<>();
                codes.put("display", "POP_UP");
                codes.put("image", "${image.library.url}notifications/evento_exp.png");
                codes.put("message", "O MODerador irá falar uma palavra (ex: Casa) e o usuário deverá duplicar cada letra da palavra, ou seja, ficaria assim: Ccaassaa. Boa sorte!\n");
                codes.put("title", "Duplicando letras");
                ServerMessage msg = new BubbleAlertComposer("haibbo.explica", codes).compose();
                Room room = gc.getHabbo().getHabboInfo().getCurrentRoom();
                gc.getHabbo().getHabboInfo().getCurrentRoom().setState(RoomState.LOCKED);
                gc.getHabbo().getHabboInfo().getCurrentRoom().sendComposer((new RoomSettingsUpdatedComposer(gc.getHabbo().getHabboInfo().getCurrentRoom())).compose());
                THashMap<String, String> keys = new THashMap();
                keys.put("display", "BUBBLE");
                keys.put("image", "${image.library.url}notifications/quarto_fechado.png");
                keys.put("message", "Este quarto foi fechado, pois o evento foi iniciado");
                if (room != null) {
                    room.sendComposer(msg);
                    room.sendComposer((new BubbleAlertComposer("", keys)).compose());
                    return true;
                }}}

        if (params.length > 0) {
            if (params[1].equalsIgnoreCase("línguadoi")) {
                THashMap<String, String> codes = new THashMap<>();
                codes.put("display", "POP_UP");
                codes.put("image", "${image.library.url}notifications/evento_exp.png");
                codes.put("message", "O MODerador irá falar algumas palavras (ex: Amor), vocês deverão trocar as vogais da palavra pela letra I, ou seja: Imir. Quem fizer isso corretamente, terá o direito de expulsar um usuário do evento.\n");
                codes.put("title", "Língua do i");
                ServerMessage msg = new BubbleAlertComposer("haibbo.explica", codes).compose();
                Room room = gc.getHabbo().getHabboInfo().getCurrentRoom();
                gc.getHabbo().getHabboInfo().getCurrentRoom().setState(RoomState.LOCKED);
                gc.getHabbo().getHabboInfo().getCurrentRoom().sendComposer((new RoomSettingsUpdatedComposer(gc.getHabbo().getHabboInfo().getCurrentRoom())).compose());
                THashMap<String, String> keys = new THashMap();
                keys.put("display", "BUBBLE");
                keys.put("image", "${image.library.url}notifications/quarto_fechado.png");
                keys.put("message", "Este quarto foi fechado, pois o evento foi iniciado");
                if (room != null) {
                    room.sendComposer(msg);
                    room.sendComposer((new BubbleAlertComposer("", keys)).compose());
                    return true;
                }}}

        if (params.length > 0) {
            if (params[1].equalsIgnoreCase("traduzindo")) {
                THashMap<String, String> codes = new THashMap<>();
                codes.put("display", "POP_UP");
                codes.put("image", "${image.library.url}notifications/evento_exp.png");
                codes.put("message", "O MODerador irá falar uma palavra em inglês e você deverá traduzi-la. O primeiro a traduzir corretamente tem direito a expulsar um usuário do evento.\n" +
                        "Ex: Ball = Bola\n");
                codes.put("title", "Traduzindo");
                ServerMessage msg = new BubbleAlertComposer("haibbo.explica", codes).compose();
                Room room = gc.getHabbo().getHabboInfo().getCurrentRoom();
                gc.getHabbo().getHabboInfo().getCurrentRoom().setState(RoomState.LOCKED);
                gc.getHabbo().getHabboInfo().getCurrentRoom().sendComposer((new RoomSettingsUpdatedComposer(gc.getHabbo().getHabboInfo().getCurrentRoom())).compose());
                THashMap<String, String> keys = new THashMap();
                keys.put("display", "BUBBLE");
                keys.put("image", "${image.library.url}notifications/quarto_fechado.png");
                keys.put("message", "Este quarto foi fechado, pois o evento foi iniciado");
                if (room != null) {
                    room.sendComposer(msg);
                    room.sendComposer((new BubbleAlertComposer("", keys)).compose());
                    return true;
                }}}

        if (params.length > 0) {
            if (params[1].equalsIgnoreCase("jogodocopy")) {
                THashMap<String, String> codes = new THashMap<>();
                codes.put("display", "POP_UP");
                codes.put("image", "${image.library.url}notifications/evento_exp.png");
                codes.put("message", "O MODerador irá copiar o visual de um usuário da quarto, e seu objetivo é falar o nome desse usuário corretamente.\n" +
                        "Quem fizer isso primeiro expulsa um usuário do evento.\n");
                codes.put("title", "Jogo do Copy");
                ServerMessage msg = new BubbleAlertComposer("haibbo.explica", codes).compose();
                Room room = gc.getHabbo().getHabboInfo().getCurrentRoom();
                gc.getHabbo().getHabboInfo().getCurrentRoom().setState(RoomState.LOCKED);
                gc.getHabbo().getHabboInfo().getCurrentRoom().sendComposer((new RoomSettingsUpdatedComposer(gc.getHabbo().getHabboInfo().getCurrentRoom())).compose());
                THashMap<String, String> keys = new THashMap();
                keys.put("display", "BUBBLE");
                keys.put("image", "${image.library.url}notifications/quarto_fechado.png");
                keys.put("message", "Este quarto foi fechado, pois o evento foi iniciado");
                if (room != null) {
                    room.sendComposer(msg);
                    room.sendComposer((new BubbleAlertComposer("", keys)).compose());
                    return true;
                }}}

        if (params.length > 0) {
            if (params[1].equalsIgnoreCase("consoantes")) {
                THashMap<String, String> codes = new THashMap<>();
                codes.put("display", "POP_UP");
                codes.put("image", "${image.library.url}notifications/evento_exp.png");
                codes.put("message", "O MODerador irá dizer uma palavra, e vocês deverão falar somente as consoantes da palavra, (ex:Amor -> mr). O primeiro que fizer corretamente, tem direito a expulsar um usuário do evento.\n");
                codes.put("title", "Consoantes");
                ServerMessage msg = new BubbleAlertComposer("haibbo.explica", codes).compose();
                Room room = gc.getHabbo().getHabboInfo().getCurrentRoom();
                gc.getHabbo().getHabboInfo().getCurrentRoom().setState(RoomState.LOCKED);
                gc.getHabbo().getHabboInfo().getCurrentRoom().sendComposer((new RoomSettingsUpdatedComposer(gc.getHabbo().getHabboInfo().getCurrentRoom())).compose());
                THashMap<String, String> keys = new THashMap();
                keys.put("display", "BUBBLE");
                keys.put("image", "${image.library.url}notifications/quarto_fechado.png");
                keys.put("message", "Este quarto foi fechado, pois o evento foi iniciado");
                if (room != null) {
                    room.sendComposer(msg);
                    room.sendComposer((new BubbleAlertComposer("", keys)).compose());
                    return true;
                }}}

        if (params.length > 0) {
            if (params[1].equalsIgnoreCase("somatoria")) {
                THashMap<String, String> codes = new THashMap<>();
                codes.put("display", "POP_UP");
                codes.put("image", "${image.library.url}notifications/evento_exp.png");
                codes.put("message", "O MODerador irá girar os dados, e voces tem que somar os números que cairem nos dados.\n" +
                        "Quem fizer isso primeiro tem direito a expulsar um usuário do evento.\n");
                codes.put("title", "Somatória");
                ServerMessage msg = new BubbleAlertComposer("haibbo.explica", codes).compose();
                Room room = gc.getHabbo().getHabboInfo().getCurrentRoom();
                gc.getHabbo().getHabboInfo().getCurrentRoom().setState(RoomState.LOCKED);
                gc.getHabbo().getHabboInfo().getCurrentRoom().sendComposer((new RoomSettingsUpdatedComposer(gc.getHabbo().getHabboInfo().getCurrentRoom())).compose());
                THashMap<String, String> keys = new THashMap();
                keys.put("display", "BUBBLE");
                keys.put("image", "${image.library.url}notifications/quarto_fechado.png");
                keys.put("message", "Este quarto foi fechado, pois o evento foi iniciado");
                if (room != null) {
                    room.sendComposer(msg);
                    room.sendComposer((new BubbleAlertComposer("", keys)).compose());
                    return true;
                }}}

        if (params.length > 0) {
            if (params[1].equalsIgnoreCase("silabas")) {
                THashMap<String, String> codes = new THashMap<>();
                codes.put("display", "POP_UP");
                codes.put("image", "${image.library.url}notifications/evento_exp.png");
                codes.put("message", "O MODerador irá falar uma palavra, na qual você terá que separar ela em sílabas, (ex:Egito - E-gi-to).\n" +
                        "Quem fizer isso primeiro corretamente tem direito a expulsar um usuário do evento.\n");
                codes.put("title", "Sílabas");
                ServerMessage msg = new BubbleAlertComposer("haibbo.explica", codes).compose();
                Room room = gc.getHabbo().getHabboInfo().getCurrentRoom();
                gc.getHabbo().getHabboInfo().getCurrentRoom().setState(RoomState.LOCKED);
                gc.getHabbo().getHabboInfo().getCurrentRoom().sendComposer((new RoomSettingsUpdatedComposer(gc.getHabbo().getHabboInfo().getCurrentRoom())).compose());
                THashMap<String, String> keys = new THashMap();
                keys.put("display", "BUBBLE");
                keys.put("image", "${image.library.url}notifications/quarto_fechado.png");
                keys.put("message", "Este quarto foi fechado, pois o evento foi iniciado");
                if (room != null) {
                    room.sendComposer(msg);
                    room.sendComposer((new BubbleAlertComposer("", keys)).compose());
                    return true;
                }}}

        if (params.length > 0) {
            if (params[1].equalsIgnoreCase("repitadepoisdemim")) {
                THashMap<String, String> codes = new THashMap<>();
                codes.put("display", "POP_UP");
                codes.put("image", "${image.library.url}notifications/evento_exp.png");
                codes.put("message", "O MODerador irá dizer uma palavra/frase, e com isso voce terá que repetir.\n" +
                        "Caso voce seja o primeiro a fazer isso tem direito a expulsar um usuário do evento.\n");
                codes.put("title", "Repita depois de mim");
                ServerMessage msg = new BubbleAlertComposer("haibbo.explica", codes).compose();
                Room room = gc.getHabbo().getHabboInfo().getCurrentRoom();
                gc.getHabbo().getHabboInfo().getCurrentRoom().setState(RoomState.LOCKED);
                gc.getHabbo().getHabboInfo().getCurrentRoom().sendComposer((new RoomSettingsUpdatedComposer(gc.getHabbo().getHabboInfo().getCurrentRoom())).compose());
                THashMap<String, String> keys = new THashMap();
                keys.put("display", "BUBBLE");
                keys.put("image", "${image.library.url}notifications/quarto_fechado.png");
                keys.put("message", "Este quarto foi fechado, pois o evento foi iniciado");
                if (room != null) {
                    room.sendComposer(msg);
                    room.sendComposer((new BubbleAlertComposer("", keys)).compose());
                    return true;
                }}}

        if (params.length > 0) {
            if (params[1].equalsIgnoreCase("plural")) {
                THashMap<String, String> codes = new THashMap<>();
                codes.put("display", "POP_UP");
                codes.put("image", "${image.library.url}notifications/evento_exp.png");
                codes.put("message", "O MODerador irá dizer uma palavra, (ex: casa)\n" +
                        "Você deverá passar essa palavra para o plural, ou seja: (casas). Caso voce seja o primeiro a fazer isso tem direito a expulsar um usuário do evento.\n");
                codes.put("title", "Plural");
                ServerMessage msg = new BubbleAlertComposer("haibbo.explica", codes).compose();
                Room room = gc.getHabbo().getHabboInfo().getCurrentRoom();
                gc.getHabbo().getHabboInfo().getCurrentRoom().setState(RoomState.LOCKED);
                gc.getHabbo().getHabboInfo().getCurrentRoom().sendComposer((new RoomSettingsUpdatedComposer(gc.getHabbo().getHabboInfo().getCurrentRoom())).compose());
                THashMap<String, String> keys = new THashMap();
                keys.put("display", "BUBBLE");
                keys.put("image", "${image.library.url}notifications/quarto_fechado.png");
                keys.put("message", "Este quarto foi fechado, pois o evento foi iniciado");
                if (room != null) {
                    room.sendComposer(msg);
                    room.sendComposer((new BubbleAlertComposer("", keys)).compose());
                    return true;
                }}}

        if (params.length > 0) {
            if (params[1].equalsIgnoreCase("capitais")) {
                THashMap<String, String> codes = new THashMap<>();
                codes.put("display", "POP_UP");
                codes.put("image", "${image.library.url}notifications/evento_exp.png");
                codes.put("message", "O MODerador irá dizer um país e você terá que falar a sua capital.\n" +
                        "(ex:Brasil = Brasilia), caso voce seja o primeiro a fazer isso tem direito a expulsar um usuário do evento.\n");
                codes.put("title", "Capitais");
                ServerMessage msg = new BubbleAlertComposer("haibbo.explica", codes).compose();
                Room room = gc.getHabbo().getHabboInfo().getCurrentRoom();
                gc.getHabbo().getHabboInfo().getCurrentRoom().setState(RoomState.LOCKED);
                gc.getHabbo().getHabboInfo().getCurrentRoom().sendComposer((new RoomSettingsUpdatedComposer(gc.getHabbo().getHabboInfo().getCurrentRoom())).compose());
                THashMap<String, String> keys = new THashMap();
                keys.put("display", "BUBBLE");
                keys.put("image", "${image.library.url}notifications/quarto_fechado.png");
                keys.put("message", "Este quarto foi fechado, pois o evento foi iniciado");
                if (room != null) {
                    room.sendComposer(msg);
                    room.sendComposer((new BubbleAlertComposer("", keys)).compose());
                    return true;
                }}}

        if (params.length > 0) {
            if (params[1].equalsIgnoreCase("kickanaja")) {
                THashMap<String, String> codes = new THashMap<>();
                codes.put("display", "POP_UP");
                codes.put("image", "${image.library.url}notifications/evento_exp.png");
                codes.put("message", "O MODerador irá dar o sinal, e vocês vão falar um nome de um usuário do quarto que quer kickar, caso você seja o primeiro a falar, a pessoa que você citou, será kickada.\n");
                codes.put("title", "Kick a naja");
                ServerMessage msg = new BubbleAlertComposer("haibbo.explica", codes).compose();
                Room room = gc.getHabbo().getHabboInfo().getCurrentRoom();
                gc.getHabbo().getHabboInfo().getCurrentRoom().setState(RoomState.LOCKED);
                gc.getHabbo().getHabboInfo().getCurrentRoom().sendComposer((new RoomSettingsUpdatedComposer(gc.getHabbo().getHabboInfo().getCurrentRoom())).compose());
                THashMap<String, String> keys = new THashMap();
                keys.put("display", "BUBBLE");
                keys.put("image", "${image.library.url}notifications/quarto_fechado.png");
                keys.put("message", "Este quarto foi fechado, pois o evento foi iniciado");
                if (room != null) {
                    room.sendComposer(msg);
                    room.sendComposer((new BubbleAlertComposer("", keys)).compose());
                    return true;
                }}}

        if (params.length > 0) {
            if (params[1].equalsIgnoreCase("vogais")) {
                THashMap<String, String> codes = new THashMap<>();
                codes.put("display", "POP_UP");
                codes.put("image", "${image.library.url}notifications/evento_exp.png");
                codes.put("message", "O MODerador irá falar uma palavra, e vocês deverão falar somente as vogais da palavra, (ex: amor - ao), caso voce seja o primeiro a fazer isso tem direito a expulsar um usuário do evento.\n");
                codes.put("title", "Vogais");
                ServerMessage msg = new BubbleAlertComposer("haibbo.explica", codes).compose();
                Room room = gc.getHabbo().getHabboInfo().getCurrentRoom();
                gc.getHabbo().getHabboInfo().getCurrentRoom().setState(RoomState.LOCKED);
                gc.getHabbo().getHabboInfo().getCurrentRoom().sendComposer((new RoomSettingsUpdatedComposer(gc.getHabbo().getHabboInfo().getCurrentRoom())).compose());
                THashMap<String, String> keys = new THashMap();
                keys.put("display", "BUBBLE");
                keys.put("image", "${image.library.url}notifications/quarto_fechado.png");
                keys.put("message", "Este quarto foi fechado, pois o evento foi iniciado");
                if (room != null) {
                    room.sendComposer(msg);
                    room.sendComposer((new BubbleAlertComposer("", keys)).compose());
                    return true;
                }}}

        if (params.length > 0) {
            if (params[1].equalsIgnoreCase("decompondo")) {
                THashMap<String, String> codes = new THashMap<>();
                codes.put("display", "POP_UP");
                codes.put("image", "${image.library.url}notifications/evento_exp.png");
                codes.put("message", "O MODerador irá dizer um número, (ex: 456), você deverá fazer assim (ex:400+50+6), caso voce seja o primeiro a fazer isso tem direito a expulsar um usuário do evento.\n");
                codes.put("title", "Decompondo");
                ServerMessage msg = new BubbleAlertComposer("haibbo.explica", codes).compose();
                Room room = gc.getHabbo().getHabboInfo().getCurrentRoom();
                gc.getHabbo().getHabboInfo().getCurrentRoom().setState(RoomState.LOCKED);
                gc.getHabbo().getHabboInfo().getCurrentRoom().sendComposer((new RoomSettingsUpdatedComposer(gc.getHabbo().getHabboInfo().getCurrentRoom())).compose());
                THashMap<String, String> keys = new THashMap();
                keys.put("display", "BUBBLE");
                keys.put("image", "${image.library.url}notifications/quarto_fechado.png");
                keys.put("message", "Este quarto foi fechado, pois o evento foi iniciado");
                if (room != null) {
                    room.sendComposer(msg);
                    room.sendComposer((new BubbleAlertComposer("", keys)).compose());
                    return true;
                }}}

        if (params.length > 0) {
            if (params[1].equalsIgnoreCase("acentuacao")) {
                THashMap<String, String> codes = new THashMap<>();
                codes.put("display", "POP_UP");
                codes.put("image", "${image.library.url}notifications/evento_exp.png");
                codes.put("message", "O MODerador irá dizer uma palavra e os usuários deverão acentua-la, (ex: Aniversario = Aniversário), caso voce seja o primeiro a fazer isso tem direito a expulsar um usuário do evento.\n");
                codes.put("title", "Acentuação");
                ServerMessage msg = new BubbleAlertComposer("haibbo.explica", codes).compose();
                Room room = gc.getHabbo().getHabboInfo().getCurrentRoom();
                gc.getHabbo().getHabboInfo().getCurrentRoom().setState(RoomState.LOCKED);
                gc.getHabbo().getHabboInfo().getCurrentRoom().sendComposer((new RoomSettingsUpdatedComposer(gc.getHabbo().getHabboInfo().getCurrentRoom())).compose());
                THashMap<String, String> keys = new THashMap();
                keys.put("display", "BUBBLE");
                keys.put("image", "${image.library.url}notifications/quarto_fechado.png");
                keys.put("message", "Este quarto foi fechado, pois o evento foi iniciado");
                if (room != null) {
                    room.sendComposer(msg);
                    room.sendComposer((new BubbleAlertComposer("", keys)).compose());
                    return true;
                }}}

        if (params.length > 0) {
            if (params[1].equalsIgnoreCase("gerundio")) {
                THashMap<String, String> codes = new THashMap<>();
                codes.put("display", "POP_UP");
                codes.put("image", "${image.library.url}notifications/evento_exp.png");
                codes.put("message", "O MODerador irá falar um verbo e voce transformará o verbo em gerundio.\n" +
                        "(ex: Amar = amando), caso voce seja o primeiro a fazer isso tem direito a expulsar um usuário do evento.\n");
                codes.put("title", "Gerundio");
                ServerMessage msg = new BubbleAlertComposer("haibbo.explica", codes).compose();
                Room room = gc.getHabbo().getHabboInfo().getCurrentRoom();
                gc.getHabbo().getHabboInfo().getCurrentRoom().setState(RoomState.LOCKED);
                gc.getHabbo().getHabboInfo().getCurrentRoom().sendComposer((new RoomSettingsUpdatedComposer(gc.getHabbo().getHabboInfo().getCurrentRoom())).compose());
                THashMap<String, String> keys = new THashMap();
                keys.put("display", "BUBBLE");
                keys.put("image", "${image.library.url}notifications/quarto_fechado.png");
                keys.put("message", "Este quarto foi fechado, pois o evento foi iniciado");
                if (room != null) {
                    room.sendComposer(msg);
                    room.sendComposer((new BubbleAlertComposer("", keys)).compose());
                    return true;
                }}}

        if (params.length > 0) {
            if (params[1].equalsIgnoreCase("escadaria")) {
                THashMap<String, String> codes = new THashMap<>();
                codes.put("display", "POP_UP");
                codes.put("image", "${image.library.url}notifications/evento_exp.png");
                codes.put("message", "O MODerador irá abri a porta e o seu objetivo é correr até as bonnies, os primeiros que chegarem nelas ganham\n");
                codes.put("title", "Escadaria");
                ServerMessage msg = new BubbleAlertComposer("haibbo.explica", codes).compose();
                Room room = gc.getHabbo().getHabboInfo().getCurrentRoom();
                gc.getHabbo().getHabboInfo().getCurrentRoom().setState(RoomState.LOCKED);
                gc.getHabbo().getHabboInfo().getCurrentRoom().sendComposer((new RoomSettingsUpdatedComposer(gc.getHabbo().getHabboInfo().getCurrentRoom())).compose());
                THashMap<String, String> keys = new THashMap();
                keys.put("display", "BUBBLE");
                keys.put("image", "${image.library.url}notifications/quarto_fechado.png");
                keys.put("message", "Este quarto foi fechado, pois o evento foi iniciado");
                if (room != null) {
                    room.sendComposer(msg);
                    room.sendComposer((new BubbleAlertComposer("", keys)).compose());
                    return true;
                }}}

        if (params.length > 0) {
            if (params[1].equalsIgnoreCase("killerbanzai")) {
                THashMap<String, String> codes = new THashMap<>();
                codes.put("display", "POP_UP");
                codes.put("image", "${image.library.url}notifications/evento_exp.png");
                codes.put("message", "O MODerador irá se dirigir ao tele banzai e se ele cair na sua frente voce perde.\n");
                codes.put("title", "Killer Banzai");
                ServerMessage msg = new BubbleAlertComposer("haibbo.explica", codes).compose();
                Room room = gc.getHabbo().getHabboInfo().getCurrentRoom();
                gc.getHabbo().getHabboInfo().getCurrentRoom().setState(RoomState.LOCKED);
                gc.getHabbo().getHabboInfo().getCurrentRoom().sendComposer((new RoomSettingsUpdatedComposer(gc.getHabbo().getHabboInfo().getCurrentRoom())).compose());
                THashMap<String, String> keys = new THashMap();
                keys.put("display", "BUBBLE");
                keys.put("image", "${image.library.url}notifications/quarto_fechado.png");
                keys.put("message", "Este quarto foi fechado, pois o evento foi iniciado");
                if (room != null) {
                    room.sendComposer(msg);
                    room.sendComposer((new BubbleAlertComposer("", keys)).compose());
                    return true;
                }}}

        if (params.length > 0) {
            if (params[1].equalsIgnoreCase("naotire3")) {
                THashMap<String, String> codes = new THashMap<>();
                codes.put("display", "POP_UP");
                codes.put("image", "${image.library.url}notifications/evento_exp.png");
                codes.put("message", "O MODerador irá abrir a porta e você deve pisar no teleporte banzai, caso caia no numero 3 voce perde.\n");
                codes.put("title", "Não tire 3");
                ServerMessage msg = new BubbleAlertComposer("haibbo.explica", codes).compose();
                Room room = gc.getHabbo().getHabboInfo().getCurrentRoom();
                gc.getHabbo().getHabboInfo().getCurrentRoom().setState(RoomState.LOCKED);
                gc.getHabbo().getHabboInfo().getCurrentRoom().sendComposer((new RoomSettingsUpdatedComposer(gc.getHabbo().getHabboInfo().getCurrentRoom())).compose());
                THashMap<String, String> keys = new THashMap();
                keys.put("display", "BUBBLE");
                keys.put("image", "${image.library.url}notifications/quarto_fechado.png");
                keys.put("message", "Este quarto foi fechado, pois o evento foi iniciado");
                if (room != null) {
                    room.sendComposer(msg);
                    room.sendComposer((new BubbleAlertComposer("", keys)).compose());
                    return true;
                }}}

        if (params.length > 0) {
            if (params[1].equalsIgnoreCase("naotire6")) {
                THashMap<String, String> codes = new THashMap<>();
                codes.put("display", "POP_UP");
                codes.put("image", "${image.library.url}notifications/evento_exp.png");
                codes.put("message", "O MODerador irá abrir a porta e você deve pisar no teleporte banzai, caso caia no numero 6 voce perde.\n");
                codes.put("title", "Não tire 6");
                ServerMessage msg = new BubbleAlertComposer("haibbo.explica", codes).compose();
                Room room = gc.getHabbo().getHabboInfo().getCurrentRoom();
                gc.getHabbo().getHabboInfo().getCurrentRoom().setState(RoomState.LOCKED);
                gc.getHabbo().getHabboInfo().getCurrentRoom().sendComposer((new RoomSettingsUpdatedComposer(gc.getHabbo().getHabboInfo().getCurrentRoom())).compose());
                THashMap<String, String> keys = new THashMap();
                keys.put("display", "BUBBLE");
                keys.put("image", "${image.library.url}notifications/quarto_fechado.png");
                keys.put("message", "Este quarto foi fechado, pois o evento foi iniciado");
                if (room != null) {
                    room.sendComposer(msg);
                    room.sendComposer((new BubbleAlertComposer("", keys)).compose());
                    return true;
                }}}

        if (params.length > 0) {
            if (params[1].equalsIgnoreCase("naotire2")) {
                THashMap<String, String> codes = new THashMap<>();
                codes.put("display", "POP_UP");
                codes.put("image", "${image.library.url}notifications/evento_exp.png");
                codes.put("message", "O MODerador irá abrir a porta e você deve pisar no teleporte banzai, caso caia no numero 2 voce perde.\n");
                codes.put("title", "Não tire 2");
                ServerMessage msg = new BubbleAlertComposer("haibbo.explica", codes).compose();
                Room room = gc.getHabbo().getHabboInfo().getCurrentRoom();
                gc.getHabbo().getHabboInfo().getCurrentRoom().setState(RoomState.LOCKED);
                gc.getHabbo().getHabboInfo().getCurrentRoom().sendComposer((new RoomSettingsUpdatedComposer(gc.getHabbo().getHabboInfo().getCurrentRoom())).compose());
                THashMap<String, String> keys = new THashMap();
                keys.put("display", "BUBBLE");
                keys.put("image", "${image.library.url}notifications/quarto_fechado.png");
                keys.put("message", "Este quarto foi fechado, pois o evento foi iniciado");
                if (room != null) {
                    room.sendComposer(msg);
                    room.sendComposer((new BubbleAlertComposer("", keys)).compose());
                    return true;
                }}}

        if (params.length > 0) {
            if (params[1].equalsIgnoreCase("naotire1")) {
                THashMap<String, String> codes = new THashMap<>();
                codes.put("display", "POP_UP");
                codes.put("image", "${image.library.url}notifications/evento_exp.png");
                codes.put("message", "O MODerador irá abrir a porta e você deve pisar no teleporte banzai, caso caia no numero 1 voce perde.\n");
                codes.put("title", "Não tire 1");
                ServerMessage msg = new BubbleAlertComposer("haibbo.explica", codes).compose();
                Room room = gc.getHabbo().getHabboInfo().getCurrentRoom();
                gc.getHabbo().getHabboInfo().getCurrentRoom().setState(RoomState.LOCKED);
                gc.getHabbo().getHabboInfo().getCurrentRoom().sendComposer((new RoomSettingsUpdatedComposer(gc.getHabbo().getHabboInfo().getCurrentRoom())).compose());
                THashMap<String, String> keys = new THashMap();
                keys.put("display", "BUBBLE");
                keys.put("image", "${image.library.url}notifications/quarto_fechado.png");
                keys.put("message", "Este quarto foi fechado, pois o evento foi iniciado");
                if (room != null) {
                    room.sendComposer(msg);
                    room.sendComposer((new BubbleAlertComposer("", keys)).compose());
                    return true;
                }}}

        if (params.length > 0) {
            if (params[1].equalsIgnoreCase("naotire5")) {
                THashMap<String, String> codes = new THashMap<>();
                codes.put("display", "POP_UP");
                codes.put("image", "${image.library.url}notifications/evento_exp.png");
                codes.put("message", "O MODerador irá abrir a porta e você deve pisar no teleporte banzai, caso caia no numero 5 voce perde.\n");
                codes.put("title", "Não tire 5");
                ServerMessage msg = new BubbleAlertComposer("haibbo.explica", codes).compose();
                Room room = gc.getHabbo().getHabboInfo().getCurrentRoom();
                gc.getHabbo().getHabboInfo().getCurrentRoom().setState(RoomState.LOCKED);
                gc.getHabbo().getHabboInfo().getCurrentRoom().sendComposer((new RoomSettingsUpdatedComposer(gc.getHabbo().getHabboInfo().getCurrentRoom())).compose());
                THashMap<String, String> keys = new THashMap();
                keys.put("display", "BUBBLE");
                keys.put("image", "${image.library.url}notifications/quarto_fechado.png");
                keys.put("message", "Este quarto foi fechado, pois o evento foi iniciado");
                if (room != null) {
                    room.sendComposer(msg);
                    room.sendComposer((new BubbleAlertComposer("", keys)).compose());
                    return true;
                }}}

        if (params.length > 0) {
            if (params[1].equalsIgnoreCase("naotire4")) {
                THashMap<String, String> codes = new THashMap<>();
                codes.put("display", "POP_UP");
                codes.put("image", "${image.library.url}notifications/evento_exp.png");
                codes.put("message", "O MODerador irá abrir a porta e você deve pisar no teleporte banzai, caso caia no numero 4 voce perde.\n");
                codes.put("title", "Não tire 4");
                ServerMessage msg = new BubbleAlertComposer("haibbo.explica", codes).compose();
                Room room = gc.getHabbo().getHabboInfo().getCurrentRoom();
                gc.getHabbo().getHabboInfo().getCurrentRoom().setState(RoomState.LOCKED);
                gc.getHabbo().getHabboInfo().getCurrentRoom().sendComposer((new RoomSettingsUpdatedComposer(gc.getHabbo().getHabboInfo().getCurrentRoom())).compose());
                THashMap<String, String> keys = new THashMap();
                keys.put("display", "BUBBLE");
                keys.put("image", "${image.library.url}notifications/quarto_fechado.png");
                keys.put("message", "Este quarto foi fechado, pois o evento foi iniciado");
                if (room != null) {
                    room.sendComposer(msg);
                    room.sendComposer((new BubbleAlertComposer("", keys)).compose());
                    return true;
                }}}

        if (params.length > 0) {
            if (params[1].equalsIgnoreCase("pegueacenoura")) {
                THashMap<String, String> codes = new THashMap<>();
                codes.put("display", "POP_UP");
                codes.put("image", "${image.library.url}notifications/evento_exp.png");
                codes.put("message", "O MODerador irá abrir a porta e terá 3 chances para pegar a cenoura. Se nas 3 tentativas voce não conseguir voce perderá. Caso pegue a cenoura você ganha!\n");
                codes.put("title", "Pegue a cenoura");
                ServerMessage msg = new BubbleAlertComposer("haibbo.explica", codes).compose();
                Room room = gc.getHabbo().getHabboInfo().getCurrentRoom();
                gc.getHabbo().getHabboInfo().getCurrentRoom().setState(RoomState.LOCKED);
                gc.getHabbo().getHabboInfo().getCurrentRoom().sendComposer((new RoomSettingsUpdatedComposer(gc.getHabbo().getHabboInfo().getCurrentRoom())).compose());
                THashMap<String, String> keys = new THashMap();
                keys.put("display", "BUBBLE");
                keys.put("image", "${image.library.url}notifications/quarto_fechado.png");
                keys.put("message", "Este quarto foi fechado, pois o evento foi iniciado");
                if (room != null) {
                    room.sendComposer(msg);
                    room.sendComposer((new BubbleAlertComposer("", keys)).compose());
                    return true;
                }}}

        if (params.length > 0) {
            if (params[1].equalsIgnoreCase("pegueosorvete")) {
                THashMap<String, String> codes = new THashMap<>();
                codes.put("display", "POP_UP");
                codes.put("image", "${image.library.url}notifications/evento_exp.png");
                codes.put("message", "O MODerador irá dar o sinal voces terão que fazer o percurso até chegar as sorveterias, pegar o sorvete e voltar pra o ponto inicial. O primeiro a fazer esse percurso completo ganha.\n");
                codes.put("title", "Pegue o sorvete");
                ServerMessage msg = new BubbleAlertComposer("haibbo.explica", codes).compose();
                Room room = gc.getHabbo().getHabboInfo().getCurrentRoom();
                gc.getHabbo().getHabboInfo().getCurrentRoom().setState(RoomState.LOCKED);
                gc.getHabbo().getHabboInfo().getCurrentRoom().sendComposer((new RoomSettingsUpdatedComposer(gc.getHabbo().getHabboInfo().getCurrentRoom())).compose());
                THashMap<String, String> keys = new THashMap();
                keys.put("display", "BUBBLE");
                keys.put("image", "${image.library.url}notifications/quarto_fechado.png");
                keys.put("message", "Este quarto foi fechado, pois o evento foi iniciado");
                if (room != null) {
                    room.sendComposer(msg);
                    room.sendComposer((new BubbleAlertComposer("", keys)).compose());
                    return true;
                }}}

        if (params.length > 0) {
            if (params[1].equalsIgnoreCase("esferasdasorte")) {
                THashMap<String, String> codes = new THashMap<>();
                codes.put("display", "POP_UP");
                codes.put("image", "${image.library.url}notifications/evento_exp.png");
                codes.put("message", "Quando pisar no tele banzai irá cair em uma das esferas e as seguintes cores irão decidir se você ganha ou perde no evento:\n" +
                        "Esferas: verde e branca = win\n" +
                        "Esferas: amarela e vermelha = kick\n");
                codes.put("title", "Esferas da Sorte");
                ServerMessage msg = new BubbleAlertComposer("haibbo.explica", codes).compose();
                Room room = gc.getHabbo().getHabboInfo().getCurrentRoom();
                gc.getHabbo().getHabboInfo().getCurrentRoom().setState(RoomState.LOCKED);
                gc.getHabbo().getHabboInfo().getCurrentRoom().sendComposer((new RoomSettingsUpdatedComposer(gc.getHabbo().getHabboInfo().getCurrentRoom())).compose());
                THashMap<String, String> keys = new THashMap();
                keys.put("display", "BUBBLE");
                keys.put("image", "${image.library.url}notifications/quarto_fechado.png");
                keys.put("message", "Este quarto foi fechado, pois o evento foi iniciado");
                if (room != null) {
                    room.sendComposer(msg);
                    room.sendComposer((new BubbleAlertComposer("", keys)).compose());
                    return true;
                }}}

                return true;
    }
}
