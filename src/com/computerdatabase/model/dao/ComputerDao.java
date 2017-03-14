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
		try {
			final PreparedStatement prepare = this.connection.prepareStatement(
					"INSERT INTO " + this.tableName
							+ " SET name = ?, introduced = ?, discontinued = ?, company_id = ? ",
					Statement.RETURN_GENERATED_KEYS);
			prepare.setString(1, centity.getName());
			if (centity.getIntroduced() == null)
				prepare.setNull(2, java.sql.Types.TIMESTAMP);
			else
				prepare.setTimestamp(2, Timestamp.valueOf(centity.getIntroduced()));

			if (centity.getDiscontinued() == null)
				prepare.setNull(3, java.sql.Types.TIMESTAMP);
			else
				prepare.setTimestamp(3, Timestamp.valueOf(centity.getDiscontinued()));

			if (centity.getCompanyId() == null || centity.getCompanyId() == 0)
				prepare.setNull(4, java.sql.Types.INTEGER);
			else
				prepare.setInt(4, centity.getCompanyId());
			prepare.executeUpdate();
			final ResultSet rs = prepare.getGeneratedKeys();
			rs.next();
			entity.setId(rs.getInt(1));
			_entity = centity;
		} catch (final SQLException ex) {
			Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE, null, ex);
		}
		return _entity;
	}

	@Override
	public ArrayList<Computer> find() {
		ArrayList<Computer> entities = null;
		try {
			final ResultSet resultQ = this.connection
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
					.executeQuery("SELECT * FROM " + this.tableName);

			entities = new ArrayList<>();
			while (resultQ.next())
				entities.add(new Computer(resultQ.getInt("id"), resultQ.getString("name"),
						(resultQ.getTimestamp("introduced") != null)
								? resultQ.getTimestamp("introduced").toLocalDateTime() : null,
						(resultQ.getTimestamp("discontinued") != null)
								? resultQ.getTimestamp("discontinued").toLocalDateTime() : null,
						resultQ.getInt("company_id")));

		} catch (final SQLException ex) {
			Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE, null, ex);
		}
		return entities;
	}

	@Override
	public Computer find(final int id) {
		Computer entity = null;
		try {
			final ResultSet resultQ = this.connection
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
					.executeQuery("SELECT * FROM " + this.tableName + " WHERE id = " + id);
			if (resultQ.first())
				entity = new Computer(resultQ.getInt("id"), resultQ.getString("name"),
						(resultQ.getTimestamp("introduced") != null)
								? resultQ.getTimestamp("introduced").toLocalDateTime() : null,
						(resultQ.getTimestamp("discontinued") != null)
								? resultQ.getTimestamp("discontinued").toLocalDateTime() : null,
						resultQ.getInt("company_id"));

		} catch (final SQLException ex) {
			Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE, null, ex);
		}
		return entity;
	}

	@Override
	public ArrayList<Computer> findRange(final int first, final int nbRecord) {
		ArrayList<Computer> entities = null;
		try {
			final ResultSet resultQ = this.connection
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
					.executeQuery("SELECT * FROM " + this.tableName + " LIMIT " + first + "," + nbRecord);

			entities = new ArrayList<>();
			while (resultQ.next())
				entities.add(new Computer(resultQ.getInt("id"), resultQ.getString("name"),
						(resultQ.getTimestamp("introduced") != null)
								? resultQ.getTimestamp("introduced").toLocalDateTime() : null,
						(resultQ.getTimestamp("discontinued") != null)
								? resultQ.getTimestamp("discontinued").toLocalDateTime() : null,
						resultQ.getInt("company_id")));

		} catch (final SQLException ex) {
			Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE, null, ex);
		}
		return entities;
	}

	@Override
	public Entity update(final IEntity entity) {
		final Computer centity = (Computer) entity;
		Computer _entity = null;
		try {
			final PreparedStatement prepare = this.connection.prepareStatement(
					"UPDATE " + this.tableName
							+ " SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?",
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			prepare.setString(1, centity.getName());
			if (centity.getIntroduced() == null)
				prepare.setNull(2, java.sql.Types.TIMESTAMP);
			else
				prepare.setTimestamp(2, Timestamp.valueOf(centity.getIntroduced()));

			if (centity.getDiscontinued() == null)
				prepare.setNull(3, java.sql.Types.TIMESTAMP);
			else
				prepare.setTimestamp(3, Timestamp.valueOf(centity.getDiscontinued()));

			if (centity.getCompanyId() == null || centity.getCompanyId() == 0)
				prepare.setNull(4, java.sql.Types.INTEGER);
			else
				prepare.setInt(4, centity.getCompanyId());

			prepare.setInt(5, entity.getId());
			prepare.executeUpdate();
			_entity = centity;
		} catch (final Exception ex) {
			Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE, null, ex);
		}
		return _entity;
	}
}
