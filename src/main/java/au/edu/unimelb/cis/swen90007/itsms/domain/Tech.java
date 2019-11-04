package au.edu.unimelb.cis.swen90007.itsms.domain;

import au.edu.unimelb.cis.swen90007.itsms.database.TechGateway;

import java.util.List;

/**
 * The Tech class defines the Domain layer interface
 * for Tech data,
 */
public class Tech extends User {

    private List<TechSkill> techSkills = null;

    public Tech(int id, String username, String password, String firstName,
                String lastName) {
        super(id, username, password, firstName, lastName);
    }

    public Tech(TechGateway techGateway) {
        super(techGateway);
    }

    public List<TechSkill> getTechSkills() {
        if (techSkills == null) {
            techSkills = TechSkill.getTechSkillsForTech(getId());
        }
        return techSkills;
    }

    public void setTechSkills(List<TechSkill> techSkills) {
        this.techSkills = techSkills;
    }
}
