/**
 *
 */
package com.computerdatabase.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.computerdatabase.model.entity.Computer;
import com.computerdatabase.model.entity.Entity;
import com.computerdatabase.model.entity.IEntity;
import com.computerdatabase.model.idao.IComputerDao;

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
			statement = this.connection.prepareStatement(
					"INSERT INTO ? SET name = ?, introduced = ?, discontinued = ?, company_id = ? ",
					Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, this.tableName);
			statement.setString(2, centity.getName());
			if (centity.getIntroduced() == null)
				statement.setNull(3, java.sql.Types.TIMESTAMP);
			else
				statement.setTimestamp(3, Timestamp.valueOf(centity.getIntroduced()));

			if (centity.getDiscontinued() == null)
				statement.setNull(4, java.sql.Types.TIMESTAMP);
			else
				statement.setTimestamp(4, Timestamp.valueOf(centity.getDiscontinued()));

			if (centity.getCompanyId() == 0)
				statement.setNull(5, java.sql.Types.INTEGER);
			else
				statement.setLong(5, centity.getCompanyId());
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
			DatabaseConnection.INSTANCE.closeConnection(this.connection);
		}
		return _entity;
	}

	@Override
	public ArrayList<Computer> find() {
		ArrayList<Computer> entities = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = this.connection.prepareStatement("SELECT * FROM ?", ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			statement.setString(1, this.tableName);
			statement.executeUpdate();
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
			DatabaseConnection.INSTANCE.closeConnection(this.connection);
		}
		return entities;
	}

	@Override
	public Computer find(final long id) {
		Computer entity = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = this.connection.prepareStatement("SELECT * FROM ? WHERE id = ?",
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			statement.setString(1, this.tableName);
			statement.setLong(2, id);
			statement.executeUpdate();
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
			DatabaseConnection.INSTANCE.closeConnection(this.connection);
		}
		return entity;
	}

	@Override
	public ArrayList<Computer> findRange(final int first, final int nbRecord) {
		ArrayList<Computer> entities = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = this.connection.prepareStatement("SELECT * FROM ? LIMIT ?,?", ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			statement.setString(1, this.tableName);
			statement.setInt(2, first);
			statement.setInt(3, nbRecord);
			statement.executeUpdate();
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
			DatabaseConnection.INSTANCE.closeConnection(this.connection);
		}
		return entities;
	}

	@Override
	public Entity update(final IEntity entity) {
		final Computer centity = (Computer) entity;
		Computer _entity = null;
		PreparedStatement statement = null;
		try {
			statement = this.connection.prepareStatement(
					"UPDATE ? SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?",
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			statement.setString(1, this.tableName);
			statement.setString(2, centity.getName());
			if (centity.getIntroduced() == null)
				statement.setNull(3, java.sql.Types.TIMESTAMP);
			else
				statement.setTimestamp(3, Timestamp.valueOf(centity.getIntroduced()));

			if (centity.getDiscontinued() == null)
				statement.setNull(4, java.sql.Types.TIMESTAMP);
			else
				statement.setTimestamp(4, Timestamp.valueOf(centity.getDiscontinued()));

			if (centity.getCompanyId() == 0)
				statement.setNull(5, java.sql.Types.INTEGER);
			else
				statement.setLong(5, centity.getCompanyId());

			statement.setLong(6, entity.getId());
			statement.executeUpdate();
			_entity = centity;
		} catch (final Exception ex) {
			Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE, null, ex);
		} finally {
			DatabaseConnection.INSTANCE.closeStatement(statement);
			DatabaseConnection.INSTANCE.closeConnection(this.connection);
		}
		return _entity;
	}
}
