package org.logogenesis.wording.io;

import java.io.IOException;

/**
 * A storable entity.
 * 
 * @author Daniel Couto Vale <danielvale@uni-bremen.de>
 */
public interface Storable {

	/**
	 * Renames the file to the specified file name.
	 * 
	 * @param fileName the new file name
	 */
	public void renameTo(String fileName);

	/**
	 * Moves the file to the specified base path.
	 * 
	 * @param basePath the destination base path of the movement
	 */
	public void moveTo(String basePath);

	/**
	 * Saves the changes of the storable document to the file.
	 * 
	 * @throws IOException when the changes cannot be saved 
	 */
	public void saveChanges() throws IOException;

}
