/*
 * Copyright (C) 2011-2013 Keyle
 *
 * This file is part of MyPet
 *
 * MyPet is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MyPet is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MyPet. If not, see <http://www.gnu.org/licenses/>.
 */

package de.Keyle.MyPet.util;

import org.bukkit.entity.Player;

public class MyPetPermissions
{
    public static boolean USE_EXTENDET_PERMISSIONS = false;
    public static boolean ENABLED = true;

    public static boolean has(Player player, String node)
    {
        if(player != null)
        {
            if(ENABLED)
            {
                return player.hasPermission(node);
            }
            return true;
        }
        return false;
    }

    public static boolean has(Player player, String node, boolean defaultValue)
    {
        if(player != null)
        {
            if(ENABLED)
            {
                return player.isOp() || player.hasPermission(node);
            }
            return defaultValue || player.isOp();
        }
        return false;
    }

    public static boolean hasExtended(Player player, String node)
    {
        if (USE_EXTENDET_PERMISSIONS)
        {
            return has(player, node);
        }
        return true;
    }

    public static boolean hasExtended(Player player, String node, boolean defaultValue)
    {
        if (USE_EXTENDET_PERMISSIONS)
        {
            return has(player, node, defaultValue);
        }
        return defaultValue;
    }
}