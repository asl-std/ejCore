package ru.aslcraft.bots.api;

import net.dv8tion.jda.api.JDA;
import ru.aslcraft.bots.core.Classes;
import ru.aslcraft.bots.core.discord.DSBot;

/**
 * <p>ReceiverRegistrator class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class ReceiverRegistrator {

	/**
	 * <p>init.</p>
	 * Тут происходит регистрация для слушателя бота дискорд.<br><br>
	 * {@code ReceiverRegistrator.init(new BotListener());}
	 * @param clazz a {@link java.lang.Object} object
	 */
	public static void init(Object clazz) {
		Classes.classes.add(clazz);
	}

	/**
	 * Получение экземпляра {@link JDA} для работы с ботом дискорд вне ejCore
	 * @return {@link JDA}
	 */
	public static JDA getJDA(){
		return DSBot.getJda();
	}
}
