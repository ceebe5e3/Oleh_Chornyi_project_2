package chornyi.conferences.db.dao;

import java.util.List;

/**
 * Interface for base working with data source, supported CRUD methods
 * @param <T> Type of entry, which source has
 */

public interface AbstractDao<T> {

    /**
     * Create a new entry into data source
     * @param item object for insertion
     * @return identity key, which this entry got in data source
     */
    boolean add(T item);

    /**
     * Updates an entry in data source
     * @param item object, which contains all information for update
     */
    void update(T item);

    /**
     * Deletes an entry from data source
     * @param id object to be deleted
     */
    boolean delete(long id);

    /**
     * Returns all table entries, which match to a key parameter
     * @param id, parameter to specify selection
     * @return of objects from data source
     */
    T getById(long id, String language);

    /**
     * Returns all data source, which exist in data source
     * @return of data source
     */
    List<T> getAll(String language);
}
