package au.edu.unimelb.cis.swen90007.itsms.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * The TechSkillFinder class provides methods for retrieving TechSkill
 * data from the database.
 */
public class TechSkillFinder {
    private static final String findTechSkillsString =
            "SELECT tech_id, APP.skills.id, skill_name, skill_level" +
                    " FROM APP.tech_skills INNER JOIN APP.skills" +
                    " ON APP.tech_skills.skill_id = APP.skills.id" +
                    " WHERE tech_id = ?";

    /**
     * Retrieves the list of TechSkills associated with the Tech with the
     * provided Tech ID..
     * @param techId the Tech's ID.
     * @return a List of TechSkillGateway objects with the data for
     *             each found TechSkill.
     */
    public List<TechSkillGateway> findForTech(int techId) {
        List<TechSkillGateway> result = new LinkedList<>();
        try {
            PreparedStatement findTechSkillsStatement =
                    DBConnection.prepare(findTechSkillsString);
            ResultSet rs = findTechSkillsStatement.executeQuery();
            while (rs.next()) {
                result.add(TechSkillGateway.load(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
