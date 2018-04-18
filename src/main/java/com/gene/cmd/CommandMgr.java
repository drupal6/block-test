package com.gene.cmd;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.gene.util.ClassUtil;

public final class CommandMgr {

	/**
	 * 缓存命令对象
	 **/
	private static Map<Short, Command> cmdCache = new HashMap<Short, Command>();
	
	/**
	 * 初始化命令集合
	 */
	public static boolean init(Package pack) {
		try {
			
			Set<Class<?>> allClasses = ClassUtil.getClasses(pack);
			for (Class<?> clazz : allClasses) {
				try {
					Cmd cmd = clazz.getAnnotation(Cmd.class);
					if (cmd != null) {
						if (cmdCache.get(cmd.code()) != null) {
							cmdCache.get(cmd.code()).getClass().getName();
							return false;
						}
						cmdCache.put(cmd.code(), (Command) clazz.newInstance());
						continue;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 缓存中获取命令
	 */
	public static Command getCommand(short code) {
		return cmdCache.get(code);
	}
}
