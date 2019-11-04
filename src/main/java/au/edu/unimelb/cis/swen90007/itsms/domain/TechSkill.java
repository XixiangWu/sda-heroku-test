package au.edu.unimelb.cis.swen90007.itsms.domain;

import au.edu.unimelb.cis.swen90007.itsms.database.TechGateway;
import au.edu.unimelb.cis.swen90007.itsms.database.TechSkillFinder;
import au.edu.unimelb.cis.swen90007.itsms.database.TechSkillGateway;

import java.util.ArrayList;
import java.util.List;

/**
 * The TechSkill class defines the Domain layer interface
 * for TechSkill data,
 */
public class TechSkill {

    private int techId;

    private String skillName;

    private int skillLevel;

    public TechSkill(int techId, String skillName, int skillLevel) {
        this.techId = techId;
        this.skillName = skillName;
        this.skillLevel = skillLevel;
    }

    public TechSkill(TechSkillGateway techSkillGateway) {
        this.techId = techSkillGateway.getTechId();
        this.skillName = techSkillGateway.getSkillName();
        this.skillLevel = techSkillGateway.getSkillLevel();
    }

    public int getTechId() {
        return techId;
    }

    public void setTechId(int techId) {
        this.techId = techId;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(int skillLevel) {
        this.skillLevel = skillLevel;
    }

    public static List<TechSkill> getTechSkillsForTech(int techId) {
        TechSkillFinder finder = new TechSkillFinder();
        List<TechSkill> result = new ArrayList<>();
        List<TechSkillGateway> techSkillGateways = finder.findForTech(techId);

        for (TechSkillGateway techSkillGateway : techSkillGateways) {
            TechSkill techSkill = new TechSkill(techSkillGateway);
            result.add(techSkill);
        }
        return result;
    }
}
