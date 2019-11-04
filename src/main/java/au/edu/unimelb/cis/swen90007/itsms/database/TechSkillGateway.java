package au.edu.unimelb.cis.swen90007.itsms.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The TechSkillGateway class provides a data-layer interface between
 * local TechSkill objects and database TechSkill objects, including
 * methods fot creating, updating, and deleting TechSkill object data
 * in the connected database.
 */
public class TechSkillGateway implements IGateway {

    private int techId;

    private int skillId;

    private String skillName;

    private int skillLevel;

    private static final String insertTechSkillStatement =
            "INSERT INTO APP.tech_skills(tech_id, skill_id, skill_level)" +
                    " VALUES (?, ?, ?)";

    private static final String updateTechSkillStatement =
            "UPDATE APP.tech_skills SET skill_level = ?" +
                    " WHERE tech_id = ? AND skill_id = ?";

    private  static final String deleteTechSkillStatement =
            "DELETE FROM APP.tech_skills WHERE tech_id = ? AND skill_id = ?";

    public TechSkillGateway(int techId, int skillId, String skillName, int skillLevel) {
        this.techId = techId;
        this.skillId = skillId;
        this.skillName = skillName;
        this.skillLevel = skillLevel;
    }

    public int getTechId() {
        return techId;
    }

    public void setTechId(int techId) {
        this.techId = techId;
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
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

    /**
     * Creates a new TechSkillGateway object based on data retrieved
     * from the connected database.
     * @param rs the results from the database retrieval query.
     * @return a TechSkillGateway with data from the provided database
     *           results.
     * @throws SQLException
     */
    public static TechSkillGateway load(ResultSet rs) throws SQLException {
        int techIdArg = rs.getInt(1);
        int skillIdArg = rs.getInt(2);
        String skillNameArg = rs.getString(3);
        int skillLevelArg = rs.getInt(4);

        TechSkillGateway result = new TechSkillGateway(techIdArg, skillIdArg,
                skillNameArg, skillLevelArg);
        Registry.addTechSkill(result);
        return result;
    }

    /**
     * Attempts to insert the data of the TechSkillGateway into the
     * connected database.
     * @return the new id returned by the database.
     */
    public int insert() {
        PreparedStatement insertStatement = null;
        try {
            insertStatement = DBConnection.prepare(insertTechSkillStatement);
            insertStatement.setInt(1, getTechId());
            insertStatement.setInt(2, getSkillId());
            insertStatement.setInt(3, getSkillLevel());
            insertStatement.executeUpdate();

            Registry.addTechSkill(this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Attempts to update the data of the corresponding TechSkill entry in the
     * connected database with the data from the TechSkillGateway object.
     */
    public void update() {
        PreparedStatement updateStatement = null;
        try {
            updateStatement = DBConnection.prepare(updateTechSkillStatement);
            updateStatement.setInt(1, getSkillLevel());
            updateStatement.setInt(2, getTechId());
            updateStatement.setInt(3, getSkillId());
            updateStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Attempts to delete the corresponding TechSkill entry in the
     * connected database.
     */
    public void delete() {
        PreparedStatement deleteStatement = null;
        try {
            deleteStatement = DBConnection.prepare(deleteTechSkillStatement);
            deleteStatement.setInt(1, getTechId());
            deleteStatement.setInt(2, getSkillId());
            deleteStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
