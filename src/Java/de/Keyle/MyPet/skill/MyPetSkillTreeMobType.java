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

package de.Keyle.MyPet.skill;

import de.Keyle.MyPet.entity.types.MyPetType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyPetSkillTreeMobType
{
    private Map<String, MyPetSkillTree> skillTrees = new HashMap<String, MyPetSkillTree>();
    private List<String> skillTreeList = new ArrayList<String>();
    private String mobTypeName;

    private static Map<String, MyPetSkillTreeMobType> mobTypes = new HashMap<String, MyPetSkillTreeMobType>();

    private MyPetSkillTreeMobType(String mobTypeName)
    {
        this.mobTypeName = mobTypeName.toLowerCase();
        mobTypes.put(this.mobTypeName, this);
    }

    public String getMobTypeName()
    {
        return mobTypeName;
    }

    public void addSkillTree(MyPetSkillTree skillTree)
    {
        addSkillTree(skillTree, getNextPlace());
    }

    public void addSkillTree(MyPetSkillTree skillTree, int place)
    {
        if (!skillTrees.containsKey(skillTree.getName()))
        {
            skillTrees.put(skillTree.getName(), skillTree);
            if (skillTreeList.size() - 1 < place)
            {
                for (int x = skillTreeList.size() ; x <= place ; x++)
                {
                    skillTreeList.add(x, null);
                }
            }
            if (skillTreeList.get(place) != null)
            {
                place = getNextPlace();
            }
            skillTreeList.set(place, skillTree.getName());
        }
    }

    public void removeSkillTree(String skillTreeName)
    {
        if (skillTrees.containsKey(skillTreeName))
        {
            skillTrees.remove(skillTreeName);
            skillTreeList.remove(skillTreeName);
        }
    }

    public void moveSkillTreeUp(MyPetSkillTree skillTree)
    {
        moveSkillTreeUp(skillTree.getName());
    }

    public void moveSkillTreeUp(String skillTreeName)
    {
        int index = skillTreeList.indexOf(skillTreeName);
        if (index != -1 && index > 0 && index < skillTreeList.size())
        {
            String skillTree = skillTreeList.get(index - 1);
            skillTreeList.set(index - 1, skillTreeName);
            skillTreeList.set(index, skillTree);
        }
    }

    public void moveSkillTreeDown(MyPetSkillTree skillTree)
    {
        moveSkillTreeDown(skillTree.getName());
    }

    public void moveSkillTreeDown(String skillTreeName)
    {
        int index = skillTreeList.indexOf(skillTreeName);
        if (index != -1 && index >= 0 && index < skillTreeList.size())
        {
            String skillTree = skillTreeList.get(index + 1);
            skillTreeList.set(index + 1, skillTreeName);
            skillTreeList.set(index, skillTree);
        }
    }

    public int getSkillTreePlace(String skillTreeName)
    {
        if (skillTrees.containsKey(skillTreeName))
        {
            return skillTreeList.indexOf(skillTreeName);
        }
        return -1;
    }

    public int getSkillTreePlace(MyPetSkillTree skillTree)
    {
        return getSkillTreePlace(skillTree.getName());
    }

    public MyPetSkillTree getSkillTree(String skillTreeName)
    {
        if (skillTrees.containsKey(skillTreeName))
        {
            return skillTrees.get(skillTreeName);
        }
        return null;
    }

    public boolean hasSkillTree(String skillTreeName)
    {
        return skillTrees.containsKey(skillTreeName);
    }

    public List<String> getSkillTreeNames()
    {
        List<String> skilltreeNames = new ArrayList<String>();
        for (String name : skillTreeList)
        {
            if (name != null)
            {
                skilltreeNames.add(name);
            }
        }
        return skilltreeNames;
    }

    public List<MyPetSkillTree> getSkillTrees()
    {
        cleanupPlaces();
        List<MyPetSkillTree> skilltreeNames = new ArrayList<MyPetSkillTree>();
        for (String name : skillTreeList)
        {
            skilltreeNames.add(getSkillTree(name));
        }
        return skilltreeNames;
    }

    public short getNextPlace()
    {
        for (int i = 0 ; i < skillTreeList.size() ; i++)
        {
            if (skillTreeList.get(i) == null)
            {
                return (short) i;
            }
        }
        short place = (short) skillTreeList.size();
        skillTreeList.add(null);
        return place;
    }

    public void cleanupPlaces()
    {
        while (skillTreeList.indexOf(null) != -1)
        {
            skillTreeList.remove(null);
        }
    }

    public static MyPetSkillTreeMobType getMobTypeByName(String mobTypeName)
    {
        if (!mobTypes.containsKey(mobTypeName.toLowerCase()))
        {
            new MyPetSkillTreeMobType(mobTypeName);
        }
        return mobTypes.get(mobTypeName.toLowerCase());
    }

    public static MyPetSkillTreeMobType getMobTypeByPetType(MyPetType myPetType)
    {
        if (!mobTypes.containsKey(myPetType.getTypeName().toLowerCase()))
        {
            new MyPetSkillTreeMobType(myPetType.getTypeName().toLowerCase());
        }
        return mobTypes.get(myPetType.getTypeName().toLowerCase());
    }

    public static boolean hasMobType(String mobTypeName)
    {
        return mobTypes.containsKey(mobTypeName.toLowerCase());
    }

    public static List<String> getSkillTreeNames(MyPetType myPetType)
    {
        return getSkillTreeNames(myPetType.getTypeName().toLowerCase());
    }

    public static List<String> getSkillTreeNames(String myPetTypeName)
    {
        List<String> skillTreeNames;
        if (mobTypes.containsKey(myPetTypeName.toLowerCase()))
        {
            skillTreeNames = getMobTypeByName(myPetTypeName.toLowerCase()).getSkillTreeNames();
        }
        else
        {
            skillTreeNames = new ArrayList<String>();
        }
        return skillTreeNames;
    }

    public static List<MyPetSkillTree> getSkillTrees(MyPetType myPetType)
    {
        return getSkillTrees(myPetType.getTypeName().toLowerCase());
    }

    public static List<MyPetSkillTree> getSkillTrees(String myPetTypeName)
    {
        if (mobTypes.containsKey(myPetTypeName.toLowerCase()))
        {
            return getMobTypeByName(myPetTypeName.toLowerCase()).getSkillTrees();
        }
        else
        {
            return new ArrayList<MyPetSkillTree>();
        }
    }

    public static void clearMobTypes()
    {
        mobTypes.clear();
    }

    public static boolean containsSkillTree(String myPetTypeName, String name)
    {
        return mobTypes.containsKey(myPetTypeName.toLowerCase()) && getMobTypeByName(myPetTypeName.toLowerCase()).getSkillTreeNames().indexOf(name) != -1;
    }
}
