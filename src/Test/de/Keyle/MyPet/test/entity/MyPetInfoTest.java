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

package de.Keyle.MyPet.test.entity;

import de.Keyle.MyPet.entity.MyPetInfo;
import de.Keyle.MyPet.entity.types.MyPet;
import de.Keyle.MyPet.entity.types.MyPetType;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class MyPetInfoTest
{
    @Test
    public void testMyPetInfo()
    {
        for (MyPetType petType : MyPetType.values())
        {
            Class<? extends MyPet> entityClass = petType.getMyPetClass();
            MyPetInfo pi = entityClass.getAnnotation(MyPetInfo.class);
            assertNotNull(pi);
            assertTrue(pi.walkSpeed() > 0);
            assertTrue(pi.hp() > 0);
            assertTrue(pi.damage() >= 0);
        }
    }
}
