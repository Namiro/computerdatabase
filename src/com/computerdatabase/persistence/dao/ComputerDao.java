/**
 *
 */
package com.computerdatabase.persistence.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.computerdatabase.persistence.idao.IComputerDao;
import com.computerdatabase.persistence.model.Computer;
import com.computerdatabase.persistence.model.Entity;
import com.computerdatabase.persistence.model.IEntity;

/**
 * @author Junior Burleon
 *
 */
public class ComputerDao extends Dao<Computer> implements IComputerDao {

	public ComputerDao() {
		this.tableName = Computer.class.getSimpleName().toLowerCase();
	}

	@Override
	public Computer create(final IEntity entity) {

		final Computer centity = (Computer) entity;
		Computer _entity = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = DatabaseConnection.INSTANCE.getConnection().prepareStatement(
					"INSERT INTO " + this.tableName
							+ " SET name = ?, introduced = ?, discontinued = ?, company_id = ? ",
					Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, centity.getName());
			if (centity.getIntroduced() == null)
				statement.setNull(2, java.sql.Types.TIMESTAMP);
			else
				statement.setTimestamp(2, Timestamp.valueOf(centity.getIntroduced()));

			if (centity.getDiscontinued() == null)
				statement.setNull(3, java.sql.Types.TIMESTAMP);
			else
				statement.setTimestamp(3, Timestamp.valueOf(centity.getDiscontinued()));

			if (centity.getCompanyId() == 0)
				statement.setNull(4, java.sql.Types.INTEGER);
			else
				statement.setLong(4, centity.getCompanyId());
			statement.executeUpdate();
			resultSet = statement.getGeneratedKeys();
			resultSet.next();
			entity.setId(resultSet.getInt(1));
			_entity = centity;
		} catch (final SQLException ex) {
			Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE, null, ex);
		} finally {
			DatabaseConnection.INSTANCE.closeResultSet(resultSet);
			DatabaseConnection.INSTANCE.closeStatement(statement);
			DatabaseConnection.INSTANCE.closeConnection(DatabaseConnection.INSTANCE.getConnection());
		}
		return _entity;
	}

	@Override
	public ArrayList<Computer> find() {
		ArrayList<Computer> entities = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = DatabaseConnection.INSTANCE.getConnection().prepareStatement("SELECT * FROM " + this.tableName,
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			statement.execute();
			resultSet = statement.getResultSet();
			entities = new ArrayList<>();
			while (resultSet.next())
				entities.add(
						new Computer.ComputerBuilder().name(resultSet.getString("name")).id(resultSet.getLong("id"))
								.introduced((resultSet.getTimestamp("introduced") != null)
										? resultSet.getTimestamp("introduced").toLocalDateTime() : null)
								.discontinued((resultSet.getTimestamp("discontinued") != null)
										? resultSet.getTimestamp("discontinued").toLocalDateTime() : null)
								.companyId(resultSet.getLong("company_id")).build());

		} catch (final SQLException ex) {
			Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE, null, ex);
		} finally {
			DatabaseConnection.INSTANCE.closeResultSet(resultSet);
			DatabaseConnection.INSTANCE.closeStatement(statement);
			DatabaseConnection.INSTANCE.closeConnection(DatabaseConnection.INSTANCE.getConnection());
		}
		return entities;
	}

	@Override
	public Computer find(final long id) {
		Computer entity = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = DatabaseConnection.INSTANCE.getConnection().prepareStatement(
					"SELECT * FROM " + this.tableName + " WHERE id = ?", ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			statement.setLong(1, id);
			statement.execute();
			resultSet = statement.getResultSet();
			if (resultSet.first())
				entity = new Computer(resultSet.getLong("id"), resultSet.getString("name"),
						(resultSet.getTimestamp("introduced") != null)
								? resultSet.getTimestamp("introduced").toLocalDateTime() : null,
						(resultSet.getTimestamp("discontinued") != null)
								? resultSet.getTimestamp("discontinued").toLocalDateTime() : null,
						resultSet.getLong("company_id"));

		} catch (final SQLException ex) {
			Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE, null, ex);
		} finally {
			DatabaseConnection.INSTANCE.closeResultSet(resultSet);
			DatabaseConnection.INSTANCE.closeStatement(statement);
			DatabaseConnection.INSTANCE.closeConnection(DatabaseConnection.INSTANCE.getConnection());
		}
		return entity;
	}

	@Override
	public ArrayList<Computer> findRange(final int first, final int nbRecord) {
		ArrayList<Computer> entities = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = DatabaseConnection.INSTANCE.getConnection().prepareStatement(
					"SELECT * FROM " + this.tableName + " LIMIT ?,?", ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			statement.setInt(1, first);
			statement.setInt(2, nbRecord);
			statement.execute();
			resultSet = statement.getResultSet();
			entities = new ArrayList<>();
			while (resultSet.next())
				entities.add(new Computer(resultSet.getLong("id"), resultSet.getString("name"),
						(resultSet.getTimestamp("introduced") != null)
								? resultSet.getTimestamp("introduced").toLocalDateTime() : null,
						(resultSet.getTimestamp("discontinued") != null)
								? resultSet.getTimestamp("discontinued").toLocalDateTime() : null,
						resultSet.getLong("company_id")));

		} catch (final SQLException ex) {
			Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE, null, ex);
		} finally {
			DatabaseConnection.INSTANCE.closeResultSet(resultSet);
			DatabaseConnection.INSTANCE.closeStatement(statement);
			DatabaseConnection.INSTANCE.closeConnection(DatabaseConnection.INSTANCE.getConnection());
		}
		return entities;
	}

	@Override
	public Entity update(final IEntity entity) {
		final Computer centity = (Computer) entity;
		Computer _entity = null;
		PreparedStatement statement = null;
		try {
			statement = DatabaseConnection.INSTANCE.getConnection().prepareStatement(
					"UPDATE " + this.tableName
							+ " SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?",
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			statement.setString(1, centity.getName());
			if (centity.getIntroduced() == null)
				statement.setNull(2, java.sql.Types.TIMESTAMP);
			else
				statement.setTimestamp(2, Timestamp.valueOf(centity.getIntroduced()));

			if (centity.getDiscontinued() == null)
				statement.setNull(3, java.sql.Types.TIMESTAMP);
			else
				statement.setTimestamp(3, Timestamp.valueOf(centity.getDiscontinued()));

			if (centity.getCompanyId() == 0)
				statement.setNull(4, java.sql.Types.INTEGER);
			else
				statement.setLong(4, centity.getCompanyId());

			statement.setLong(5, entity.getId());
			statement.executeUpdate();
			_entity = centity;
		} catch (final Exception ex) {
			Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE, null, ex);
		} finally {
			DatabaseConnection.INSTANCE.closeStatement(statement);
			DatabaseConnection.INSTANCE.closeConnection(DatabaseConnection.INSTANCE.getConnection());
		}
		return _entity;
	}
}
