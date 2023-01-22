/*
 * Copyright (c) peter.braun@fhws.de
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package de.fhws.fiw.fds.sutton.server.database.dbms;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * The IPersistency interface provides a method to establish a connection to a DBMS
 * and another method to shut down the created connection
 * */
public interface IPersistency
{
	/**
	 * Attempts to establish a connection with a data source
	 * @return a connection {@link Connection} to the data source
	 * @throws SQLException
	 * */
	Connection getConnection( ) throws SQLException;

	/**
	 * Closes the connection to a data source
	 * */
	void shutdown( );
}
