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

package de.Keyle.MyPet.skill.skilltreeloader;


import de.Keyle.MyPet.entity.types.MyPetType;
import de.Keyle.MyPet.skill.MyPetSkillTree;
import de.Keyle.MyPet.skill.MyPetSkillTreeMobType;
import de.Keyle.MyPet.skill.MyPetSkillTreeSkill;
import de.Keyle.MyPet.skill.MyPetSkills;
import de.Keyle.MyPet.util.MyPetUtil;
import de.Keyle.MyPet.util.configuration.YAML_Configuration;
import org.bukkit.configuration.ConfigurationSection;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MyPetSkillTreeLoaderYAML extends MyPetSkillTreeLoader
{
    public static MyPetSkillTreeLoader getSkilltreeLoader()
    {
        return new MyPetSkillTreeLoaderYAML();
    }

    private MyPetSkillTreeLoaderYAML()
    {
    }

    public void loadSkillTrees(String configPath)
    {
        YAML_Configuration skilltreeConfig;
        if (MyPetUtil.getDebugLogger() != null)
        {
            MyPetUtil.getDebugLogger().info("Loading yaml skill configs in: " + configPath);
        }
        File skillFile;

        skillFile = new File(configPath + File.separator + "default.yml");
        MyPetSkillTreeMobType skillTreeMobType = MyPetSkillTreeMobType.getMobTypeByName("default");
        if (skillFile.exists())
        {
            skilltreeConfig = new YAML_Configuration(skillFile);
            loadSkillTree(skilltreeConfig, skillTreeMobType);
            skillFile.renameTo(new File(configPath + File.separator + "old_default.yml"));
            if (MyPetUtil.getDebugLogger() != null)
            {
                MyPetUtil.getDebugLogger().info("  default.yml");
            }
        }

        for (MyPetType mobType : MyPetType.values())
        {
            skillFile = new File(configPath + File.separator + mobType.getTypeName().toLowerCase() + ".yml");

            skillTreeMobType = MyPetSkillTreeMobType.getMobTypeByName(mobType.getTypeName());

            if (!skillFile.exists())
            {
                continue;
            }

            skilltreeConfig = new YAML_Configuration(skillFile);
            loadSkillTree(skilltreeConfig, skillTreeMobType);
            skillFile.renameTo(new File(configPath + File.separator + "old_" + mobType.getTypeName().toLowerCase() + ".yml"));
            if (MyPetUtil.getDebugLogger() != null)
            {
                MyPetUtil.getDebugLogger().info("  " + mobType.getTypeName().toLowerCase() + ".yml");
            }
        }

    }

    @Override
    public List<String> saveSkillTrees(String configPath)
    {
        return new ArrayList<String>();
    }

    private void loadSkillTree(YAML_Configuration MWConfig, MyPetSkillTreeMobType skillTreeMobType)
    {
        ConfigurationSection sec = MWConfig.getConfig().getConfigurationSection("skilltrees");
        if (sec == null)
        {
            return;
        }
        Set<String> SkillTreeNames = sec.getKeys(false);
        if (SkillTreeNames.size() > 0)
        {
            for (String skillTreeName : SkillTreeNames)
            {
                String inherit = MWConfig.getConfig().getString("skilltrees." + skillTreeName + ".inherit", "%#_DeFaUlT_#%");
                MyPetSkillTree skillTree;
                if (!inherit.equals("%#_DeFaUlT_#%"))
                {
                    skillTree = new MyPetSkillTree(skillTreeName, inherit);
                }
                else
                {
                    skillTree = new MyPetSkillTree(skillTreeName);
                }

                Set<String> level = MWConfig.getConfig().getConfigurationSection("skilltrees." + skillTreeName).getKeys(false);
                if (level.size() > 0)
                {
                    for (String thisLevel : level)
                    {
                        if (MyPetUtil.isInt(thisLevel))
                        {
                            List<String> skillsOfThisLevel = MWConfig.getConfig().getStringList("skilltrees." + skillTreeName + "." + thisLevel);
                            if (skillsOfThisLevel.size() > 0)
                            {
                                for (String thisSkill : skillsOfThisLevel)
                                {
                                    if (MyPetSkills.isValidSkill(thisSkill))
                                    {
                                        MyPetSkillTreeSkill skillTreeSkill = MyPetSkills.getNewSkillInstance(thisSkill);
                                        skillTreeSkill.setDefaultProperties();
                                        skillTree.addSkillToLevel(Short.parseShort(thisLevel), skillTreeSkill);
                                    }
                                }
                            }
                        }
                    }
                    skillTreeMobType.addSkillTree(skillTree);
                }
            }
        }
    }
}