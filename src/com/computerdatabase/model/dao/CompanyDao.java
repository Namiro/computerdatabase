package com.computerdatabase.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.computerdatabase.model.entity.Company;
import com.computerdatabase.model.entity.IEntity;
import com.computerdatabase.model.idao.ICompanyDao;

/**
 * @author Junior Burleon
 *
 */
public class CompanyDao extends Dao<Company> implements ICompanyDao {

	public CompanyDao() {
		this.tableName = Company.class.getSimpleName().toLowerCase();
	}

	@Override
	public Company create(final IEntity entity) {
		final Company centity = (Company) entity;
		Company _entity = null;
		try {
			final PreparedStatement prepare = this.connection.prepareStatement(
					"INSERT INTO " + this.tableName + " SET name = ?", Statement.RETURN_GENERATED_KEYS);
			prepare.setString(1, centity.getName());
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
	public ArrayList<Company> find() {
		ArrayList<Company> entities = null;
		try {
			final ResultSet resultQ = this.connection
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
					.executeQuery("SELECT * FROM " + this.tableName);

			entities = new ArrayList<>();
			while (resultQ.next())
				entities.add(new Company(resultQ.getInt("id"), resultQ.getString("name")));

		} catch (final SQLException ex) {
			Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE, null, ex);
		}
		return entities;
	}

	@Override
	public Company find(final int id) {
		Company entity = null;
		try {
			final ResultSet resultQ = this.connection
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
					.executeQuery("SELECT * FROM " + this.tableName + " WHERE id = " + id);
			if (resultQ.first())
				entity = new Company(resultQ.getInt("id"), resultQ.getString("name"));

		} catch (final SQLException ex) {
			Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE, null, ex);
		}
		return entity;
	}

	@Override
	public ArrayList<Company> findRange(final int first, final int last) {
		ArrayList<Company> entities = null;
		try {
			final ResultSet resultQ = this.connection
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
					.executeQuery("SELECT * FROM " + this.tableName + "LIMIT " + first + "," + last);

			entities = new ArrayList<>();
			while (resultQ.next())
				entities.add(new Company(resultQ.getInt("id"), resultQ.getString("name")));

		} catch (final SQLException ex) {
			Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE, null, ex);
		}
		return entities;
	}

	@Override
	public Company update(final IEntity entity) {
		final Company centity = (Company) entity;
		Company _entity = null;
		try {
			final PreparedStatement prepare = this.connection.prepareStatement(
					"UPDATE " + this.tableName + " SET name = ? WHERE id = ?", ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			prepare.setString(1, centity.getName());
			prepare.setInt(2, entity.getId());
			prepare.executeUpdate();
			_entity = centity;
		} catch (final SQLException ex) {
			Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE, null, ex);
		}
		return _entity;
	}
}