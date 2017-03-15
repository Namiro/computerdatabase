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
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = this.connection.prepareStatement("INSERT INTO " + this.tableName + " SET name = ?",
					Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, centity.getName());
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
	public ArrayList<Company> find() {
		ArrayList<Company> entities = null;
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
				entities.add(new Company(resultSet.getInt("id"), resultSet.getString("name")));

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
	public Company find(final long id) {
		Company entity = null;
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
				entity = new Company(resultSet.getInt("id"), resultSet.getString("name"));

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
	public ArrayList<Company> findRange(final int first, final int nbRecord) {
		ArrayList<Company> entities = null;
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
				entities.add(new Company(resultSet.getInt("id"), resultSet.getString("name")));

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
	public Company update(final IEntity entity) {
		final Company centity = (Company) entity;
		Company _entity = null;
		PreparedStatement statement = null;
		try {
			statement = this.connection.prepareStatement("UPDATE ? SET name = ? WHERE id = ?",
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			statement.setString(1, this.tableName);
			statement.setString(2, centity.getName());
			statement.setLong(3, entity.getId());
			statement.executeUpdate();
			_entity = centity;
		} catch (final SQLException ex) {
			Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE, null, ex);
		} finally {
			DatabaseConnection.INSTANCE.closeStatement(statement);
			DatabaseConnection.INSTANCE.closeConnection(this.connection);
		}
		return _entity;
	}
}
