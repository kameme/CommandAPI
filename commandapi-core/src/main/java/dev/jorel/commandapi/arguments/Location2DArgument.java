/*******************************************************************************
 * Copyright 2018, 2020 Jorel Ali (Skepter) - MIT License
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

import org.bukkit.Location;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import dev.jorel.commandapi.CommandAPIHandler;
import dev.jorel.commandapi.nms.NMS;
import dev.jorel.commandapi.wrappers.Location2D;

/**
 * An argument that represents the Bukkit {@link Location} object in x and z directions
 * @apiNote Returns a {@link Location2D} object
 */
public class Location2DArgument extends SafeOverrideableArgument<Location2D, Location2D> {

	/**
	 * A Location argument. Represents Minecraft locations in 2D space. Defaults to {@link LocationType#PRECISE_POSITION}
	 * @param nodeName the name of the node for this argument
	 */
	public Location2DArgument(String nodeName) {
		this(nodeName, LocationType.PRECISE_POSITION);
	}

	/**
	 * A Location argument. Represents Minecraft locations in 2D space
	 * @param nodeName the name of the node for this argument
	 * @param type the location type of this location, either {@link LocationType#BLOCK_POSITION} or {@link LocationType#PRECISE_POSITION}
	 */
	public Location2DArgument(String nodeName, LocationType type) {
		super(nodeName, type == LocationType.BLOCK_POSITION ? CommandAPIHandler.getInstance().getNMS()._ArgumentPosition2D()
				: CommandAPIHandler.getInstance().getNMS()._ArgumentVec2(),
				type == LocationType.BLOCK_POSITION ? (Location2D l) -> l.getBlockX() + " " + l.getBlockZ()
						: (Location2D l) -> l.getX() + " " + l.getZ());
		isPrecise = type == LocationType.PRECISE_POSITION;
	}

	private final boolean isPrecise;

	/**
	 * Returns whether this argument is {@link LocationType#BLOCK_POSITION} or {@link LocationType#PRECISE_POSITION}
	 * @return the location type of this argument
	 */
	public LocationType getLocationType() {
		return isPrecise ? LocationType.PRECISE_POSITION : LocationType.BLOCK_POSITION;
	}
	
	@Override
	public Class<Location2D> getPrimitiveType() {
		return Location2D.class;
	}

	@Override
	public CommandAPIArgumentType getArgumentType() {
		return CommandAPIArgumentType.LOCATION_2D;
	}
	
	@Override
	public <CommandListenerWrapper> Location2D parseArgument(NMS<CommandListenerWrapper> nms,
			CommandContext<CommandListenerWrapper> cmdCtx, String key, Object[] previousArgs) throws CommandSyntaxException {
		return isPrecise ? nms.getLocation2DPrecise(cmdCtx, key) : nms.getLocation2DBlock(cmdCtx, key);
	}
}
