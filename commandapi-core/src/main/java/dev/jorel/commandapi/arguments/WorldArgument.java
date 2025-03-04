/*******************************************************************************
 * Copyright 2022 Jorel Ali (Skepter) - MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *******************************************************************************/
package dev.jorel.commandapi.arguments;

import org.bukkit.World;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import dev.jorel.commandapi.CommandAPIHandler;
import dev.jorel.commandapi.nms.NMS;

/**
 * An argument that represents the Bukkit World object
 */
public class WorldArgument extends SafeOverrideableArgument<World, World> {

	/**
	 * A World argument. Represents Bukkit's World object
	 * 
	 * @param nodeName the name of the node for this argument
	 */
	public WorldArgument(String nodeName) {
		super(nodeName, CommandAPIHandler.getInstance().getNMS()._ArgumentDimension(), world -> world.getName().toLowerCase());
	}

	@Override
	public Class<World> getPrimitiveType() {
		return World.class;
	}

	@Override
	public CommandAPIArgumentType getArgumentType() {
		// We're keeping this underlying internal name as Dimension. The only thing
		// that differs is the name of this class which is "WorldArgument", because
		// the CommandAPI argument class names represent Bukkit objects wherever
		// possible
		return CommandAPIArgumentType.DIMENSION;
	}

	@Override
	public <CommandListenerWrapper> World parseArgument(NMS<CommandListenerWrapper> nms,
		CommandContext<CommandListenerWrapper> cmdCtx, String key, Object[] previousArgs) throws CommandSyntaxException {
		return nms.getDimension(cmdCtx, key);
	}
}
